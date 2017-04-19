package com.boulder.igotthis.task.util;

/**
 * Class Description
 *
 * @author ankit
 * @since 04/18/2017
 */

public enum EventType {
    BLUETOOTH_CONNECTED,
    BLUETOOTH_DISCONNECTED,
    CHARGING_CONNECTED,
    CHARGING_DISCONNECTED,
    WIFI_CONNECTED,
    WIFI_DISCONNECTED;

    public int getString (EventType eventType) {
        switch (eventType) {
            case BLUETOOTH_CONNECTED:
                return 0;
            case BLUETOOTH_DISCONNECTED:
                return 0;
            case CHARGING_CONNECTED:
                return 0;
            case CHARGING_DISCONNECTED:
                return 0;
            case WIFI_CONNECTED:
                return 0;
            case WIFI_DISCONNECTED:
                return 0;
            default:
                return 0;
        }
    }
}
