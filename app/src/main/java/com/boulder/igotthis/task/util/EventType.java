/*
 * Copyright (C) 2017 Ankit Sadana
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.boulder.igotthis.task.util;

import com.boulder.igotthis.R;

/**
 * EventType is a enum class that holds all the events that might happen.
 *
 * @author asadana
 * @since 04/18/2017
 */

public enum EventType {
    NONE,
    BLUETOOTH_CONNECTED,
    BLUETOOTH_DISCONNECTED,
    CHARGING_CONNECTED,
    CHARGING_DISCONNECTED,
    WIFI_CONNECTED,
    WIFI_DISCONNECTED;

    /**
     * Method to fetch the String resource for the corresponding enum.
     *
     * @param eventType Contains the enum value for the resource.
     * @return the resource for the string.
     */
    public static int getStringResource(EventType eventType) {
        switch (eventType) {
            case BLUETOOTH_CONNECTED:
                return R.string.event_type_bluetooth_connected;
            case BLUETOOTH_DISCONNECTED:
                return R.string.event_type_bluetooth_disconnected;
            case CHARGING_CONNECTED:
                return R.string.event_type_charging_connected;
            case CHARGING_DISCONNECTED:
                return R.string.event_type_charging_disconnected;
            case WIFI_CONNECTED:
                return R.string.event_type_wifi_connected;
            case WIFI_DISCONNECTED:
                return R.string.event_type_wifi_disconnected;
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
    public static EventType valueAt(int position) {
        if (position < EventType.values().length) {
            return EventType.values()[position];
        }
        return EventType.NONE;
    }

    /**
     * Method to get the position of the EventType passed.
     *
     * @param eventType Contains the EventType enum object being queried.
     * @return the corresponding position, -1 if not found.
     */
    public static int getPositionOf(EventType eventType) {
        for (int position = 0; position < EventType.values().length; position++) {
            if (eventType.equals(EventType.values()[position]))
                return position;
        }
        return -1;
    }
}
