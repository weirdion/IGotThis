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
	private var isReceiverRegistered = false

	private val connectivityReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			if (context != null && intent != null) {
				Log.d(tag, "action: " + intent.action)
				val extras = intent.extras
				if (extras != null) {
					extras.keySet().forEach {
						if (BuildConfig.DEBUG) {
							Log.d(tag, "key [" + it + "]: " + extras.get(it))
						}
						if (Constants.networkInfoKey == it) {
							onNetworkChange(extras.get(Constants.networkInfoKey) as NetworkInfo)
						}
					}
					if (intent.hasExtra(Constants.networkInfoKey)) {

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
	}

	override fun onHandleIntent(intent: Intent?) {
		Log.d(tag, "Intent received " + intent)
		if (intent != null && intent.extras != null) {
			if (intent.hasExtra(Constants.clearTaskListKey)) {
				Log.d(tag, "Intent had key " + Constants.clearTaskListKey)
				taskList.clear()
				unregisterAllReceivers()
			}
			if (intent.hasExtra(Constants.taskIntentKey)) {
				Log.d(tag, "Intent had key " + Constants.taskIntentKey)
				taskList.add(intent.getParcelableExtra(Constants.taskIntentKey))
				registerReceivers()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		unregisterAllReceivers()
	}

	private fun registerReceivers() {
		// TODO fix this when multiple cases are handled. Maybe have a boolean flag to keep track of receivers.
		taskList
				.asSequence()
				.filter { EventType.WIFI_CONNECTED == it.eventType || EventType.WIFI_DISCONNECTED == it.eventType }
				.forEach {
					applicationContext.registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
					isReceiverRegistered = true
					// isConnectivityReceiverRegistered = true // TODO
				}
	}

	private fun unregisterAllReceivers() {
		if (isReceiverRegistered) {
			applicationContext.unregisterReceiver(connectivityReceiver)
		}
	}

	private fun onNetworkChange(networkInfo: NetworkInfo) {
		Log.d(tag, "onNetworkChange: NetworkInfo: " + networkInfo)
		when {
			ConnectivityManager.TYPE_WIFI == networkInfo.type   -> {
				taskList
						.asSequence()
						.filter { EventType.WIFI_CONNECTED == it.eventType || EventType.WIFI_DISCONNECTED == it.eventType }
						.forEach { Toast.makeText(this, "onNetworkChange: " + networkInfo.extraInfo, Toast.LENGTH_SHORT).show() }
			}
			ConnectivityManager.TYPE_MOBILE == networkInfo.type -> {
				Toast.makeText(applicationContext, "MOBILE: connected: " + networkInfo.isConnected, Toast.LENGTH_SHORT).show()
				Log.d(tag, "Not implemented yet.")
			}
			else                                                -> Log.w(tag, "Unknown Network Type: " + networkInfo.type)
		}
	}
}