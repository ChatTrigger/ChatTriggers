package com.chattriggers.ctjs.triggers;

import com.chattriggers.ctjs.CTJS;
import com.chattriggers.ctjs.modules.Module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TriggerRegister {
    public static Module currentModule = null;

    /**
     * Helper method to make registering a trigger more like JavaScript.<br>
     * Used from provided libraries as <code>register("trigger type", "function name");</code><br>
     * Example: <code>register("chat","triggerOnChat");</code>
     * @param triggerType the type of trigger
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnTrigger register(String triggerType, String methodName) {
        String capitalizedName = triggerType.substring(0, 1).toUpperCase() + triggerType.substring(1);
        Method method;

        try {
             method = TriggerRegister.class.getDeclaredMethod(
                    "register" + capitalizedName,
                    String.class
            );
        } catch (NoSuchMethodException e) {
            CTJS.getInstance().getConsole().printStackTrace(e);
            return null;
        }

        try {
            Object returned = method.invoke(null, methodName);
            return (OnTrigger) returned;
        } catch (IllegalAccessException | InvocationTargetException e) {
            CTJS.getInstance().getConsole().printStackTrace(e);
            return null;
        }
    }

    /**
     * Registers a new chat trigger.<br>
     * Available modifications:<br>
     * {@link OnChatTrigger#setChatCriteria(String)} Sets the chat criteria<br>
     * {@link OnChatTrigger#setParameter(String)} Sets the chat parameter<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnChatTrigger registerChat(String methodName) {
        return new OnChatTrigger(methodName);
    }

    /**
     * Registers a new world load trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerWorldLoad(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.WORLD_LOAD);
    }

    /**
     * Registers a new world unload trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerWorldUnload(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.WORLD_UNLOAD);
    }

    /**
     * Registers a new clicked trigger.<br>
     * Runs on both down and up action on the mouse for mouse buttons 0 through 5.<br>
     * Passes through 4 arguments:<br>
     *     &nbsp;mouseX<br>
     *     &nbsp;mouseY<br>
     *     &nbsp;button<br>
     *     &nbsp;buttonState<br>
     * Available modifications:<br>
     *     &nbsp;{@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerClicked(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.CLICKED);
    }

    /**
     * Registers a new dragged trigger.<br>
     * Runs while a mouse button is being held down.<br>
     * Passes through 5 arguments:<br>
     *     &nbsp;deltaMouseX<br>
     *     &nbsp;deltaMouseY<br>
     *     &nbsp;mouseX<br>
     *     &nbsp;mouseY<br>
     *     button<br>
     * Available modifications:<br>
     *     &nbsp;{@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerDragged(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.DRAGGED);
    }

    /**
     * Registers a new sound play trigger.<br>
     * Available modifications:<br>
     * {@link OnSoundPlayTrigger#setSoundNameCriteria(String)} Sets the sound name criteria<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnSoundPlayTrigger registerSoundPlay(String methodName) {
        return new OnSoundPlayTrigger(methodName);
    }

    /**
     * Registers a new tick trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerTick(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.TICK);
    }

    /**
     * Registers a new step trigger.<br>
     * Available modifications:<br>
     * {@link OnStepTrigger#setFps(long)} Sets the fps<br>
     * {@link OnStepTrigger#setDelay(long)} Sets the delay in seconds<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnStepTrigger registerStep(String methodName) {
        return new OnStepTrigger(methodName);
    }

    /**
     * Registers a new render overlay trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerRenderOverlay(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.RENDER_OVERLAY);
    }

    /**
     * Registers a new game load trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerGameLoad(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.GAME_LOAD);
    }

    /**
     * Registers a new game unload trigger.<br>
     * Available modifications:<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnRegularTrigger registerGameUnload(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.GAME_UNLOAD);
    }

    /**
     * Registers a new method that receives a command input.<br>
     * Available modifications:<br>
     * {@link OnCommandTrigger#setCommandName(String)} Sets the command name<br>
     * {@link OnCommandTrigger#setCommandUsage(String)} Sets the command usage<br>
     * {@link OnTrigger#setPriority(OnTrigger.Priority)} Sets the priority<br>
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger for additional modification
     */
    public static OnCommandTrigger registerCommand(String methodName) {
        return new OnCommandTrigger(methodName);
    }

    /**
     * Registers a new method that gets run when a new gui is opened.
     * @param methodName the name of the method to callback when the event is fired
     * @return the trigger (useless)
     */
    public static OnRegularTrigger registerGuiOpened(String methodName) {
        return new OnRegularTrigger(methodName, TriggerType.GUI_OPENED);
    }
}