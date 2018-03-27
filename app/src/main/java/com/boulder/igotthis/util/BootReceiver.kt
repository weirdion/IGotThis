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

package com.boulder.igotthis.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * BootReceiver to listen for BOOT_COMPLETED intent and start the service
 *
 * @author asadana
 * @since 12/31/17
 */
class BootReceiver : BroadcastReceiver() {
	private val tag: String = this.javaClass.name

	override fun onReceive(context: Context?, intent: Intent?) {
		Log.d(tag, "Boot Completed received.")
		if (context != null) {
			// val serviceIntent = Intent(context, IGotThisService::class.java)
			//		context?.startService(serviceIntent)
			//      TODO keep commented out till app is ready for regular use
		}
	}
}