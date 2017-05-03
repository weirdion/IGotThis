package com.boulder.igotthis.task;

import android.support.annotation.NonNull;

import com.boulder.igotthis.task.util.ActionType;
import com.boulder.igotthis.task.util.EventType;

/**
 * Task is a class defined to contain all the objects required to define a task to be
 * ran in the background after user selection.
 *
 * @author ankit
 * @since 04/25/2017
 */

public class Task {
    private EventType eventType = EventType.NONE;
    private ActionType actionType = ActionType.NONE;

    public Task(@NonNull EventType eventType, @NonNull ActionType actionType) {
        this.eventType = eventType;
        this.actionType = actionType;
    }
}
