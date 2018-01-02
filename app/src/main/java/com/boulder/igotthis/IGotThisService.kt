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

package com.boulder.igotthis

import android.app.IntentService
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.boulder.igotthis.base.ActionType
import com.boulder.igotthis.base.EventType
import com.boulder.igotthis.util.ConnectivityReceiver

/**
 * @author asadana
 * @since 12/31/17
 */
class IGotThisService(name: String?) : IntentService(name) {
	private val tag: String = this.javaClass.name
	private lateinit var taskMap: MutableMap<EventType, MutableList<ActionType>>

	constructor() : this("IGotThisService")

	override fun onCreate() {
		taskMap = mutableMapOf()
		// LOAD taskMap first
		registerReceivers()
	}

	override fun onHandleIntent(intent: Intent?) {
		Log.e(tag, "REMOVE ME: Intent incoming had the following data: " + intent?.dataString)
	}

	private fun registerReceivers() {
		if (taskMap.containsKey(EventType.WIFI_CONNECTED) || taskMap.containsKey(EventType.WIFI_DISCONNECTED)) {
			applicationContext.registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
		}
	}
}