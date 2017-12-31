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

package com.boulder.igotthis.views.widget

import android.content.Context
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import com.boulder.igotthis.R

/**
 * Class to inflate the widget layout and provide variables.
 *
 * @author asadana
 * @since 12/26/17
 */
class TaskCreationAddWidget(@NonNull context: Context, @Nullable viewGroupContainer: ViewGroup) {
	val layoutView: View
	val addActionItemDropDownSpinner: Spinner
	val addImageButton: ImageButton

	init {
		val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		layoutView = layoutInflater.inflate(R.layout.task_creation_item_action, viewGroupContainer, false)
		addActionItemDropDownSpinner = layoutView.findViewById(R.id.add_action_item_drop_down_spinner)
		addImageButton = layoutView.findViewById(R.id.item_action_add_action_button)
	}
}