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

import android.os.Binder
import com.boulder.igotthis.util.Task

/**
 * Interface class the extends {@link android.os.Binder} to provide methods to the Activity.
 * This methods can be used to clear or update the Task list in the service.
 *
 * @author asadana
 * @since 2/18/18
 */
abstract class IGotThisBinder : Binder() {
	/**
	 * Function to add a single {@link com.boulder.igotthis.util.Task} object.
	 * @param taskObj   Contains the Task created by the user to be added to the taskList.
	 */
	abstract fun addTask(taskObj: Task)
	/**
	 * Function to add a list of {@link com.boulder.igotthis.util.Task} objects.
	 * @param taskList  Contains a list of Tasks created by the user to be added to the taskList.
	 */
	abstract fun addTaskList(taskList: List<Task>)
	/**
	 * Function to allow the user to clear the existing taskList in the service.
	 * @return Returns a Boolean flag as sign if the list was successfully cleared.
	 */
	abstract fun clearTaskList(): Boolean
}