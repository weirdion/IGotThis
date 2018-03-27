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

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.support.annotation.NonNull
import android.view.ViewGroup

/**
 * Base class that extends {@link ILifecycleProvider} to provide common logic constructor.
 *
 * @author asadana
 * @since 12/24/17
 */
abstract class BaseLifecycleProvider(@NonNull protected var context: Context,
                                     @NonNull protected var viewGroupContainer: ViewGroup) :
		ILifecycleProvider, LifecycleObserver {

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	abstract override fun onResume()

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	abstract override fun onPause()
}