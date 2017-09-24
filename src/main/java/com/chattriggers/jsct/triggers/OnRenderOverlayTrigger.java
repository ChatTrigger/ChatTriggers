package com.chattriggers.jsct.triggers;

import com.chattriggers.jsct.JSCT;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import javax.script.ScriptException;

public class OnRenderOverlayTrigger extends Trigger {
    public OnRenderOverlayTrigger(String methodName) {
        super(methodName);
    }

    @Override
    public void trigger(Object... args) {
        if (!(args[0] instanceof RenderGameOverlayEvent)) {
            throw new IllegalArgumentException("Argument 1 must be of type RenderGameOverlayEvent");
        }

        RenderGameOverlayEvent event = (RenderGameOverlayEvent) args[0];

        try {
            JSCT.getInstance().getInvocableEngine().invokeFunction(methodName, event);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
