package com.boulder.igotthis.task.util;

import com.boulder.igotthis.R;

/**
 * ActionType is a enum class that holds all the actions that might happen.
 *
 * @author ankit
 * @since 04/24/2017
 */
public enum ActionType {
    NONE,
    TURN_BLUETOOTH_ON,
    TURN_BLUETOOTH_OFF,
    TURN_WIFI_ON,
    TURN_WIFI_OFF,
    OPEN_APP,
    // TODO: make this generic
    //SEND_MESSAGE
    SEND_MESSAGE_USING_ALLO,
    PERFORM_CUSTOM_ACTION;


    /**
     * Method to fetch the String resource for the corresponding enum.
     *
     * @param actionType Contains the enum value for the resource.
     * @return the resource for the string.
     */
    public static int getStringResource(ActionType actionType) {
        switch (actionType) {
            case TURN_BLUETOOTH_ON:
                return R.string.action_type_bluetooth_turn_on;
            case TURN_BLUETOOTH_OFF:
                return R.string.action_type_bluetooth_turn_off;
            case TURN_WIFI_ON:
                return R.string.action_type_wifi_turn_on;
            case TURN_WIFI_OFF:
                return R.string.action_type_wifi_turn_off;
            case OPEN_APP:
                return R.string.action_type_open_app;
            case SEND_MESSAGE_USING_ALLO:
                return R.string.action_type_send_message_allo;
            case PERFORM_CUSTOM_ACTION:
                return R.string.action_type_perform_custom_action;
            default:
                return R.string.action_type_default;
        }
    }

    /**
     * Method to get the enum at a particular position.
     * This is primarily used to check which of the list element was chosen by the user.
     *
     * @param position Contains the position chosen from the list of enums.
     * @return the corresponding enum.
     */
    public static ActionType valueAt(int position) {
        if (position < ActionType.values().length) {
            return ActionType.values()[position];
        }
        return ActionType.NONE;
    }

    /**
     * Method to get the position of the ActionType passed.
     *
     * @param actionType Contains the ActionType enum object being queried.
     * @return the corresponding position, -1 if not found.
     */
    public static int getPositionOf(ActionType actionType) {
        for (int position = 0; position < ActionType.values().length; position++) {
            if (actionType.equals(ActionType.values()[position]))
                return position;
        }
        return -1;
    }
}
