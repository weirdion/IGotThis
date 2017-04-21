package com.boulder.igotthis.task.util;

import com.boulder.igotthis.R;

/**
 * Class Description
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

    public static EventType valueAt(int position) {
        if (position < EventType.values().length) {
            return EventType.values()[position];
        }
        return EventType.NONE;
    }
}
