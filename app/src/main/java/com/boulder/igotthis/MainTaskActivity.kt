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

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.boulder.igotthis.base.ActionType
import com.boulder.igotthis.base.EventType
import com.boulder.igotthis.base.IGotThisListenerInterface
import com.boulder.igotthis.base.IGotThisServiceBinder
import com.boulder.igotthis.util.Constants
import com.boulder.igotthis.util.Task
import com.boulder.igotthis.views.TaskCreation
import kotlinx.android.synthetic.main.activity_main_task.bottom_navigation_view
import kotlinx.android.synthetic.main.activity_main_task.main_content

/**
 * MainTaskActivity
 * This class is used to handle the tasking activity with a bottom navigation layout.
 *
 * @author asadana
 * @since 12/24/17
 */
class MainTaskActivity : AppCompatActivity(), ServiceConnection {
	private val tag: String = this.javaClass.name
	private val identifierString: String = MainTaskActivity::javaClass.name + System.identityHashCode(this)

	private lateinit var context: Context
	private lateinit var taskCreationObj: TaskCreation
	private lateinit var iGotThisServiceBinder: IGotThisServiceBinder
	private lateinit var iGotThisListenerInterface: IGotThisListenerInterface

	private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
		when (item.itemId) {
			R.id.task_creation_menu -> {
				main_content.removeAllViews()
				main_content.addView(taskCreationObj.rootView)
				return@OnNavigationItemSelectedListener true
			}
			R.id.pre_deploy_menu    -> {
				main_content.removeAllViews()
				Toast.makeText(applicationContext, "Pre Deploy Menu", Toast.LENGTH_LONG).show()
				return@OnNavigationItemSelectedListener true
			}
			R.id.active_tasks_menu  -> {
				main_content.removeAllViews()
				Toast.makeText(applicationContext, "Active Tasks Menu", Toast.LENGTH_LONG).show()
				return@OnNavigationItemSelectedListener true
			}
			else                    -> {
				Log.w(tag, "Unknown option selection in bottom navigation drawer: $item")
				return@OnNavigationItemSelectedListener false
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_task)
		initialize()
	}

	private fun initialize() {
		context = this.applicationContext
		bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
		taskCreationObj = TaskCreation(context, main_content)
		bottom_navigation_view.selectedItemId = R.id.task_creation_menu

		// TODO: PoC: Delete later
		val taskTemp = Task(EventType.WIFI_DISCONNECTED, mutableListOf(ActionType.TURN_BLUETOOTH_ON))
		//		val intent = Intent(context, IGotThisService::class.java)
		//		intent.putExtra(Constants.clearTaskListKey, true)
		//		intent.putExtra(Constants.taskIntentKey, taskTemp)
		//		context.startService(intent)
	}

	override fun onPostResume() {
		super.onPostResume()
		Log.d(tag, "onPostResume")
		Log.d(tag, "Binding IGotThisService")
		applicationContext.bindService(Intent(Constants.iGotThisServiceBinder), this, Context.BIND_AUTO_CREATE)
	}

	override fun onPause() {
		super.onPause()
		Log.d(tag, "onPause")
		applicationContext.unbindService(this)
	}

	override fun onServiceConnected(component: ComponentName?, binder: IBinder?) {
		Log.d(tag, "IGotThisService bound successfully.")
		iGotThisServiceBinder = binder as IGotThisServiceBinder
		iGotThisServiceBinder.register(iGotThisListenerInterface, identifierString)
	}

	override fun onServiceDisconnected(component: ComponentName?) {
		Log.d(tag, "IGotThisService disconnected.")
		// TODO should we try to reconnect?
	}
}
