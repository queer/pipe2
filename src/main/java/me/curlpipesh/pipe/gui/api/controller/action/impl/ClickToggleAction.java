package me.curlpipesh.pipe.gui.api.controller.action.impl;

import me.curlpipesh.pipe.gui.api.controller.action.MouseClickAction;
import me.curlpipesh.pipe.gui.api.model.base.interfaces.IWidget;

public class ClickToggleAction implements MouseClickAction<IWidget> {
    @Override
    public void click(IWidget component, int button) {
        if(button == 0) {
            component.setState(!component.isState());
        }
    }
}
