package com.boulder.igotthis.task.util;

import com.boulder.igotthis.R;

/**
 * Class Description
 *
 * @author ankit
 * @since 04/24/2017
 */

public class ActionMap {
    private enum OffOnType {
        OFF(0), ON(1);

        private int indexOfEnum;

        OffOnType(int index) {
            this.indexOfEnum = index;
        }

        /**
         * Method to return the index of the current enum.
         * @return the index.
         */
        public int getIndexOfEnum() {
            return indexOfEnum;
        }
    }

    /**
     * ActionType is a enum class that holds all the actions that might happen.
     */
    public enum ActionType {
        NONE,
        BLUETOOTH,
        WIFI,
        OPEN_APP,
        SEND_MESSAGE
    }


    /**
     * Method to fetch the String resource for the corresponding enum.
     *
     * @param actionType Contains the enum value for the resource.
     * @return the resource for the string.
     */
    public static int getStringResource(ActionType actionType) {
        switch (actionType) {
            default:
                return R.string.event_type_default;
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
}
