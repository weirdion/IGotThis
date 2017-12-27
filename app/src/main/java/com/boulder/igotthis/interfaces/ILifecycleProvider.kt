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

package com.boulder.igotthis.interfaces

import android.view.View

/**
 * Interface class for providing lifecycle Views used to replace the main content in the activity.
 *
 * @author asadana
 * @since 12/24/17
 */
interface ILifecycleProvider {
    /**
     * Method that serves as a lifecycle call to be called from Activity's onResume().
     */
    fun onResume()

    /**
     * Method that serves as a lifecycle call to be called from Activity's onPause().
     */
    fun onPause()

    /**
     * Method that serves as a base method to return layout view.
     */
    fun getRootView(): View
}