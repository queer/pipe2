package me.curlpipesh.pipe.bytecode;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author audrey
 * @since 4/29/15
 */
@SuppressWarnings("unused")
public class ClassEnumerator {
    /**
     * Parses a directory for jar files and class files
     * <p>
     * Recurses through if necessary
     *
     * @param directory directory to parse
     * @return class array
     */
    @SuppressWarnings("ConstantConditions")
    public static List<Class<?>> getClassesFromExternalDirectory(final File directory) {
        if(directory == null) {
            throw new IllegalArgumentException("May not load classes from a null directory!");
        }
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getName() + " is not a directory!");
        }
        final List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader;
        try {
            classLoader = new URLClassLoader(new URL[]{
                    directory.toURI().toURL()
            }, ClassEnumerator.class.getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if(directory.listFiles() != null) {
            for (final File file : directory.listFiles()) {
                try {
                    if (file.getName().toLowerCase().trim().endsWith(".class")) {
                        classes.add(classLoader.loadClass(file.getName().replace(".class", "")
                                .replace("/", ".")));
                    }
                    if (file.getName().toLowerCase().trim().endsWith(".jar")) {
                        classes.addAll(getClassesFromJar(file, classLoader));
                    }
                    if (file.isDirectory()) {
                        classes.addAll(getClassesFromExternalDirectory(file));
                    }
                } catch (final ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalArgumentException("directory.listFiles() returned null!");
        }
        return classes;
    }

    /**
     * Returns the class array of all classes within a package
     *
     * @param classe class to get code source location for
     * @return class array
     */
    public static Class<?>[] getClassesFromPackage(final Class<?> classe) {
        final List<Class<?>> classes = new ArrayList<>();
        URI uri = null;
        try {
            uri = classe.getProtectionDomain().getCodeSource().getLocation().toURI();
        } catch (final URISyntaxException e) {
            e.printStackTrace();
        }
        if (uri == null) {
            throw new RuntimeException("No uri for "
                    + classe.getProtectionDomain().getCodeSource().getLocation());
        }
        classes.addAll(processDirectory(new File(uri), ""));
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Returns all class files inside a jar
     *
     * @param file        jar file
     * @param classLoader classloader created previously using the jar file
     * @return class list
     */
    public static List<Class<?>> getClassesFromJar(final File file, final ClassLoader classLoader) {
        final List<Class<?>> classes = new ArrayList<>();
        try {
            final JarFile jarFile = new JarFile(file);
            final Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                final JarEntry jarEntry = enumeration.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().toLowerCase().trim().endsWith(".class")) {
                    continue;
                }
                classes.add(classLoader.loadClass(jarEntry.getName().replace(".class", "")
                        .replace("/", ".")));
            }
            jarFile.close();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * Processes a directory and retrieves all classes from it and its
     * subdirectories
     * <p>
     * Recurses if necessary
     *
     * @param directory directory file to traverse
     * @return list of classes
     */
    private static List<Class<?>> processDirectory(final File directory, final String append) {
        final List<Class<?>> classes = new ArrayList<>();
        final String[] files = directory.list();
        if(files != null) {
            for (final String fileName : files) {
                String className = null;
                if (fileName.endsWith(".class")) {
                    className = append + '.' + fileName.replace(".class", "");
                }
                if (className != null) {
                    try {
                        classes.add(Class.forName(className.substring(1)));
                    } catch(ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                final File subdir = new File(directory, fileName);
                if (subdir.isDirectory()) {
                    classes.addAll(processDirectory(subdir, append + "." + fileName));
                }
            }
        } else {
            System.err.println(">> Directory `" + directory.getAbsolutePath() + "` has null File#list()!?");
        }
        return classes;
    }
}
