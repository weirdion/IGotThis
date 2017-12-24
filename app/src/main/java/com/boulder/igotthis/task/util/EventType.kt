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

package com.boulder.igotthis.task.util

import com.boulder.igotthis.R

/**
 * @author asadana
 * @since 12/24/17
 */
enum class EventType {
    NONE,
    BLUETOOTH_CONNECTED,
    BLUETOOTH_DISCONNECTED,
    CHARGING_CONNECTED,
    CHARGING_DISCONNECTED,
    WIFI_CONNECTED,
    WIFI_DISCONNECTED;

    companion object {

        /**
         * Method to fetch the String resource for the corresponding enum.
         *
         * @param eventType Contains the enum value for the resource.
         * @return the resource for the string.
         */
        fun getStringResource(eventType: EventType?): Int {
            return when (eventType) {
                BLUETOOTH_CONNECTED -> R.string.event_type_bluetooth_connected
                BLUETOOTH_DISCONNECTED -> R.string.event_type_bluetooth_disconnected
                CHARGING_CONNECTED -> R.string.event_type_charging_connected
                CHARGING_DISCONNECTED -> R.string.event_type_charging_disconnected
                WIFI_CONNECTED -> R.string.event_type_wifi_connected
                WIFI_DISCONNECTED -> R.string.event_type_wifi_disconnected
                else -> R.string.event_type_default
            }
        }

        /**
         * Method to get the enum at a particular position.
         * This is primarily used to check which of the list element was chosen by the user.
         *
         * @param position Contains the position chosen from the list of enums.
         * @return the corresponding enum.
         */
        fun valueAt(position: Int?): EventType {
            if (position != null) {
                if (position < EventType.values().size) {
                    return EventType.values()[position]
                }
            }
            return EventType.NONE
        }

        /**
         * Method to get the position of the EventType passed.
         *
         * @param eventType Contains the EventType enum object being queried.
         * @return the corresponding position, -1 if not found.
         */
        fun getPositionOf(eventType: EventType?): Int {
            if (eventType != null) {
                for (position in 0 until EventType.values().size) {
                    if (eventType == EventType.valueAt(position))
                        return position
                }
            }
            return -1
        }
    }
}