package com.chattriggers.ctjs.triggers;

import com.chattriggers.ctjs.CTJS;
import com.chattriggers.ctjs.utils.console.Console;

import javax.script.ScriptException;

public class OnWorldLoadTrigger extends OnTrigger {

    public OnWorldLoadTrigger(String methodName) {
        super(methodName);
    }

    @Override
    public void trigger(Object... args) {
        try {
            CTJS.getInstance().getInvocableEngine().invokeFunction(methodName);
        } catch (ScriptException | NoSuchMethodException e) {
            Console.getConsole().printStackTrace(e);
        }
    }
}
