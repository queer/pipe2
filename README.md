Pipe
----
A "Minecraft" mod that works through instrumentation. Apache 2.0-licensed. This repository is a reimplementation of the original, mainly due to the fact that I was dissatisfied with the structure of the original.  

The point of this client is to allow for "intrusive" modifications of the game without violating Mojang's EULA, specifically the following parts:

![Mojang EULA image 1](https://i.imgur.com/Q6pKGDA.png)

![Mojang EULA image 2](https://i.imgur.com/QKGkSGC.png)

These conditions can be found [here](https://account.mojang.com/documents/minecraft_eula) and [here](https://account.mojang.com/terms), respectively.

----------------

Copyright 2015-2016 audrey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

----

Installation:

Before building, one must first `mvn install` the following libraries:
 - [pipe-gl](https://github.com/curlpipesh/pipe-gl)
 
Note that, due to inadequate foresight on my part, `Event` and `pipe-gl` do not have a pom.xml that you can `mvn install` with. This must be created on your own for now.

Once those libraries have been `mvn install`'d:

````
mvn clean package
````
After this, open up the Minecraft launcher. Make a new profile, and add the following under "JVM Arguments":
````
-javaagent:/path/to/target/Pipe-0.1-DEV.jar
````
Once this is finished, run the game. 
