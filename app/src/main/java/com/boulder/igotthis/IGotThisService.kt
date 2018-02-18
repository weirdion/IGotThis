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
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.boulder.igotthis.base.ActionType
import com.boulder.igotthis.base.EventType
import com.boulder.igotthis.util.Constants
import com.boulder.igotthis.util.Task

/**
 * @author asadana
 * @since 12/31/17
 */
class IGotThisService(name: String?) : IntentService(name) {
	private val tag: String = this.javaClass.name
	private lateinit var taskList: MutableList<Task>

	private val connectivityReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			if (context != null && intent != null) {
				Log.d(tag, "action: " + intent.action)
				val extras = intent.extras
				if (extras != null) {
					for (key in extras.keySet()) {
						onNetworkChange(extras.get(key) as NetworkInfo)
					}
				}
				else {
					Log.d(tag, "no extras")
				}
			}
			else {
				Log.w(tag, "Context or Intent was null")
			}
		}
	}

	constructor() : this("IGotThisService")

	override fun onCreate() {
		super.onCreate()
		taskList = mutableListOf()
		// LOAD taskMap first
		registerReceivers()
	}

	override fun onHandleIntent(intent: Intent?) {
		if (intent != null && intent.extras != null) {
			Log.d(tag, "Intent received " + intent)
			if (intent.extras.containsKey(Constants.clearTaskListKey)) {
				taskList.clear()
			}
			when {
				intent.extras.containsKey(Constants.taskIntentKey)      -> {
					taskList.add(intent.extras.get(Constants.taskIntentKey) as Task)
				}
				intent.extras.containsKey(Constants.taskListIntentKey)  -> {
					taskList = intent.extras.get(Constants.taskListIntentKey) as MutableList<Task>
				}
			}
		}

		Log.e(tag, "REMOVE ME: Intent incoming had the following data: " + intent?.dataString)
	}

	private fun registerReceivers() {
		taskList
				.asSequence()
				.filter { EventType.WIFI_CONNECTED == it.eventType || EventType.WIFI_DISCONNECTED == it.eventType }
				.forEach { applicationContext.registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)) }
	}

	private fun onNetworkChange(networkInfo: NetworkInfo) {
		Log.d(tag, "onNetworkChange")
		when {
			ConnectivityManager.TYPE_WIFI == networkInfo.type   -> {
				taskList
						.asSequence()
						.filter { EventType.WIFI_CONNECTED == it.eventType || EventType.WIFI_DISCONNECTED == it.eventType }
						.forEach { Toast.makeText(this, "onNetworkChange: " + it.eventType, Toast.LENGTH_SHORT).show() }
			}
			ConnectivityManager.TYPE_MOBILE == networkInfo.type -> {
				Toast.makeText(applicationContext, "MOBILE: connected: " + networkInfo.isConnected, Toast.LENGTH_SHORT).show()
				Log.d(tag, "Not implemented yet.")
			}
			else                                                -> Log.w(tag, "Unknown Network Type: " + networkInfo.type)
		}
	}
}