/*
 * Copyright (C) 2017 Ankit Sadana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boulder.igotthis.base

import com.boulder.igotthis.R

/**
 * ActionType is a enum class that holds all the actions that might happen.
 *
 * @author asadana
 * @since 12/24/17
 */
enum class ActionType {
    NONE,
    TURN_BLUETOOTH_ON,
    TURN_BLUETOOTH_OFF,
    TURN_WIFI_ON,
    TURN_WIFI_OFF,
    OPEN_APP,
    // TODO: make this generic
    //SEND_MESSAGE
    SEND_MESSAGE_USING_TELEGRAM,
    PERFORM_CUSTOM_ACTION;

    companion object {

        /**
         * Method to fetch the String resource for the corresponding enum.
         *
         * @param actionType Contains the enum value for the resource.
         * @return the resource for the string.
         */
        fun getStringResource(actionType: ActionType?): Int {
            return when (actionType) {
                TURN_BLUETOOTH_ON -> R.string.action_type_bluetooth_turn_on
                TURN_BLUETOOTH_OFF -> R.string.action_type_bluetooth_turn_off
                TURN_WIFI_ON -> R.string.action_type_wifi_turn_on
                TURN_WIFI_OFF -> R.string.action_type_wifi_turn_off
                OPEN_APP -> R.string.action_type_open_app
                SEND_MESSAGE_USING_TELEGRAM -> R.string.action_type_send_message_telegram
                PERFORM_CUSTOM_ACTION -> R.string.action_type_perform_custom_action
                else -> R.string.action_type_default
            }
        }

        /**
         * Method to get the enum at a particular position.
         * This is primarily used to check which of the list element was chosen by the user.
         *
         * @param position Contains the position chosen from the list of enums.
         * @return the corresponding enum.
         */
        fun valueAt(position: Int?): ActionType {
            if (position != null) {
                if (position < ActionType.values().size) {
                    return ActionType.values()[position]
                }
            }
            return NONE
        }

        /**
         * Method to get the position of the ActionType passed.
         *
         * @param actionType Contains the ActionType enum object being queried.
         * @return the corresponding position, -1 if not found.
         */
        fun getPositionOf(actionType: ActionType?): Int {
            if (actionType != null) {
                for (position in 0 until EventType.values().size) {
                    if (actionType == valueAt(position))
                        return position
                }
            }
            return -1
        }
    }
}