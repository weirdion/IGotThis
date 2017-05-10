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
