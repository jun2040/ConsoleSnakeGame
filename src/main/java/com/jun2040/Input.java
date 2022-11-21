package com.jun2040;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.util.HashMap;
import java.util.Map;

import static com.jun2040.Action.*;

public class Input {
    private final GlobalKeyboardHook keyboard = new GlobalKeyboardHook();

    // Two layer approach: key to action & action to state
    private Map<Character, Action> actionMap = new HashMap<>();
    public Map<Action, Boolean> states = new HashMap<>();

    public Input() {
        // Register key pair
        registerKeyActionPair();

        // Initialize action map
        actionMap.forEach((k, v) -> states.put(v, false));

        // Add key listener
        keyboard.addKeyListener(new GlobalKeyListener() {
            // Set corresponding action to true when corresponding key is pressed
            @Override
            public void keyPressed(GlobalKeyEvent globalKeyEvent) {
                char key = Character.toUpperCase(globalKeyEvent.getKeyChar());

                if (actionMap.containsKey(key))
                    states.put(actionMap.get(key), true);
            }

            // Set corresponding action to false when corresponding key is released
            @Override
            public void keyReleased(GlobalKeyEvent globalKeyEvent) {
                char key = Character.toUpperCase(globalKeyEvent.getKeyChar());

                if (actionMap.containsKey(key))
                    states.put(actionMap.get(key), false);
            }
        });
    }

    // Register key and its corresponding action
    private void registerKeyActionPair() {
        actionMap.put('W', MOVE_UP);
        actionMap.put('A', MOVE_LEFT);
        actionMap.put('S', MOVE_DOWN);
        actionMap.put('D', MOVE_RIGHT);
    }
}
