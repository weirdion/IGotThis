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

package com.boulder.igotthis.views

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.boulder.igotthis.R
import com.boulder.igotthis.base.ActionType
import com.boulder.igotthis.base.BaseLifecycleProvider
import com.boulder.igotthis.base.EventType
import com.boulder.igotthis.views.widget.TaskCreationAddWidget
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL

/**
 * Class Description
 *
 * @author asadana
 * @since 12/24/17
 */

class TaskCreation(context: Context, viewGroupContainer: ViewGroup) : BaseLifecycleProvider(context, viewGroupContainer) {
	private val tag: String = this.javaClass.name

	override lateinit var rootView: View

	// Event Elements
	private val eventDropDownSpinner: Spinner
	private val addActionItemsLayout: LinearLayout
	private val addEventActionButton: Button
	private val resetEventActionButton: Button

	private val performRequestAsyncTask = PerformRequestAsyncTask()

	init {
		Log.d(tag, "init")
		rootView = LayoutInflater.from(context).inflate(R.layout.task_creation_layout, viewGroupContainer, false)
		eventDropDownSpinner = rootView.findViewById(R.id.event_drop_down_spinner)
		addActionItemsLayout = rootView.findViewById(R.id.task_creation_add_action_items_layout)
		addEventActionButton = rootView.findViewById(R.id.button_add_created_task)
		resetEventActionButton = rootView.findViewById(R.id.button_reset_task_creation)

		addEventActionButton.isEnabled = false
		resetEventActionButton.isEnabled = false
		addButtonListeners()
		populateEventSpinner()
	}

	private fun addButtonListeners() {
		addEventActionButton.setOnClickListener { view -> Log.d(tag, "setOnClickListener clicked for addEventActionButton" + view) }
	}

	private fun populateEventSpinner() {
		val eventDropDownList: MutableList<String> = mutableListOf()
		// TODO : make this dependent on the permissions given, otherwise disable it.
		EventType.values().mapTo(eventDropDownList) { context.getString(EventType.getStringResource(it)) }

		val eventDropDownAdapter: ArrayAdapter<String>? = ArrayAdapter(context, R.layout.drop_down_item, eventDropDownList)
		eventDropDownAdapter?.setDropDownViewResource(R.layout.drop_down_item)
		eventDropDownSpinner.adapter = eventDropDownAdapter
		eventDropDownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
				when (EventType.valueAt(position)) {
					EventType.BLUETOOTH_CONNECTED,
					EventType.BLUETOOTH_DISCONNECTED,
					EventType.WIFI_CONNECTED,
					EventType.WIFI_DISCONNECTED     -> addActionView(EventType.valueAt(position))

				// TODO temporarily do nothing for charging urlConnectionected/disurlConnectionected.
					EventType.CHARGING_CONNECTED,
					EventType.CHARGING_DISCONNECTED -> addActionItemsLayout.removeAllViews()
					else                            -> addActionItemsLayout.removeAllViews()
				}
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun addActionView(eventType: EventType?) {
		// TODO: This likely needs to be refactored in a way to include multiple action additions
		val taskCreationAddWidget = TaskCreationAddWidget(context, addActionItemsLayout)
		addActionItemsLayout.addView(taskCreationAddWidget.layoutView)
		val actionDropDownList: MutableList<String> = mutableListOf()
		// TODO : make this dependent on the permissions given, otherwise disable it.
		// TODO : make this dynamic so there's no duplicates from previous choices?
		for (actionType: ActionType in ActionType.values()) {
			actionDropDownList.add(context.getString(ActionType.getStringResource(actionType)))
		}
		val actionDropDownAdapter: ArrayAdapter<String>? = ArrayAdapter(context, R.layout.drop_down_item, actionDropDownList)
		actionDropDownAdapter?.setDropDownViewResource(R.layout.drop_down_item)

		taskCreationAddWidget.addActionItemDropDownSpinner.adapter = actionDropDownAdapter
		taskCreationAddWidget.addActionItemDropDownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
				when (ActionType.valueAt(position)) {
					ActionType.TURN_BLUETOOTH_ON           -> {
						val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
						if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled)
							bluetoothAdapter.enable()
					}
					ActionType.TURN_BLUETOOTH_OFF          -> {
						val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
						if (bluetoothAdapter != null && bluetoothAdapter.isEnabled)
							bluetoothAdapter.disable()
					}
					ActionType.TURN_WIFI_ON                -> {
						val wifiManager: WifiManager? = context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
						if (wifiManager != null && !wifiManager.isWifiEnabled)
							wifiManager.isWifiEnabled = true
					}
					ActionType.TURN_WIFI_OFF               -> {
						val wifiManager: WifiManager? = context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
						if (wifiManager != null && wifiManager.isWifiEnabled)
							wifiManager.isWifiEnabled = false
					}
					ActionType.OPEN_APP                    -> {
						val launchIntent: Intent? = context.packageManager?.getLaunchIntentForPackage("com.waze")
						launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
						context.startActivity(launchIntent)
					}
					ActionType.SEND_MESSAGE_USING_TELEGRAM -> {
						// TODO
					}
					ActionType.PERFORM_CUSTOM_ACTION       -> {
						performRequestAsyncTask.execute("https://www.google.com", "GET")
					}
					else                                   -> {
						// TODO
					}
				}
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
			}
		}
	}

	override fun onResume() {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onPause() {
		if (performRequestAsyncTask.status == AsyncTask.Status.PENDING ||
				performRequestAsyncTask.status == AsyncTask.Status.RUNNING) {
			performRequestAsyncTask.cancel(true)
		}
	}

	inner class PerformRequestAsyncTask : AsyncTask<String, String, String>() {
		override fun doInBackground(vararg params: String?): String {
			val urlString: String? = params[0]
			var resultToDisplay = ""
			var httpURLConnection: HttpURLConnection? = null

			try {

				val url = URL(urlString)
				httpURLConnection = url.openConnection() as HttpURLConnection
				httpURLConnection.readTimeout = 15000
				httpURLConnection.connectTimeout = 15000
				httpURLConnection.requestMethod = params[1] ?: "GET"
				httpURLConnection.doInput = true
				//                httpURLConnection.doOutput = true
				/*

													OutputStream os = httpURLConnection.getOutputStream();
													BufferedWriter writer = new BufferedWriter(
															new OutputStreamWriter(os, "UTF-8"));
													writer.write(getPostDataString(postDataParams));

													writer.flush();
													writer.close();
													os.close();
				*/

				val responseCode: Int? = httpURLConnection.responseCode
				Log.e(tag, "doInBackground: " + responseCode)

				//if (responseCode == HttpURLConnection.HTTP_OK) {

				resultToDisplay = httpURLConnection.inputStream.bufferedReader().use(BufferedReader::readText)

				/*} else {
								resultToDisplay = "false : " + responseCode;
				}*/

			}
			catch (error: MalformedURLException) {
				//Handles an incorrectly entered URL
				Log.e(tag, "doInBackground: ", error)
			}
			catch (error: SocketTimeoutException) {
				//Handles URL access timeout.
				Log.e(tag, "doInBackground: ", error)
			}
			catch (error: IOException) {
				//Handles input and output error
				Log.e(tag, "doInBackground: ", error)
			}
			finally {
				if (httpURLConnection != null) // Make sure the connection is not null.
					httpURLConnection.disconnect()
			}
			return resultToDisplay
		}

		override fun onPostExecute(result: String) {
			super.onPostExecute(result)
			Toast.makeText(context, result, Toast.LENGTH_LONG).show()
		}
	}
}
