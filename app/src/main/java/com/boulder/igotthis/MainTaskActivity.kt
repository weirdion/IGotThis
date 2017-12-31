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

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import com.boulder.igotthis.views.TaskCreation
import kotlinx.android.synthetic.main.activity_main_task.*

/**
 * MainTaskActivity
 * This class is used to handle the tasking activity with a bottom navigation layout.
 *
 * @author asadana
 * @since 12/24/17
 */
class MainTaskActivity : AppCompatActivity() {
    private val tag: String = this.javaClass.name

    private lateinit var context: Context
    private lateinit var taskCreationObj: TaskCreation

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.task_creation_menu -> {
                main_content.removeAllViews()
                main_content.addView(taskCreationObj.rootView)
                return@OnNavigationItemSelectedListener true
            }
            R.id.pre_deploy_menu -> {
                main_content.removeAllViews()
                Toast.makeText(applicationContext, "Pre Deploy Menu", Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.active_tasks_menu -> {
                main_content.removeAllViews()
                Toast.makeText(applicationContext, "Active Tasks Menu", Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                Log.w(tag, "Unknown option selection in bottom navigation drawer: " + item)
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
    }
}
