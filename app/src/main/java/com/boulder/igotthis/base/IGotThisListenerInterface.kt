/*
 * Copyright (C) 2018 Ankit Sadana
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

import com.boulder.igotthis.util.Task

/**
 * Interface to provide callback functions for IGotThisService to notify or deliver data back to
 * the application.
 *
 * @author asadana
 * @since 3/26/18
 */
public interface IGotThisListenerInterface {
	/**
	 * Function to deliver all the tasks that the user has added so far.
	 * @return Returns the entire list of [Task]
	 */
	fun deliverAllTasks(): List<Task>
	/**
	 * Function to deliver only the tasks that the user has enabled.
	 * @return Returns a list of [Task]
	 */
	fun deliverEnabledTasks(): List<Task>
}