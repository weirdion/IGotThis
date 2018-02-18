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

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.NonNull
import com.boulder.igotthis.base.ActionType
import com.boulder.igotthis.base.EventType

/**
 * Task is a class defined to contain all the objects required to define a task to be
 * ran in the background after user selection.
 *
 * @author asadana
 * @since 12/24/17
 */
class Task(@NonNull eventType: EventType, @NonNull actionTypeList: List<ActionType>) : Parcelable {
	var eventType = EventType.NONE
	var actionTypeList: MutableList<ActionType>

	init {
		this.eventType = eventType
		this.actionTypeList = actionTypeList.toMutableList()
	}

	constructor(parcel: Parcel) : this(
			EventType.values()[parcel.readInt()],
			mutableListOf<ActionType>().apply {
				parcel.readTypedList(this, ActionType.CREATOR)
			})

	companion object CREATOR : Parcelable.Creator<Task> {
		override fun createFromParcel(parcel: Parcel): Task {
			return Task(parcel)
		}

		override fun newArray(size: Int): Array<Task?> {
			return arrayOfNulls(size)
		}
	}

	override fun writeToParcel(dest: Parcel?, flags: Int) {
		dest?.writeInt(eventType.ordinal)
		dest?.writeTypedList(actionTypeList)
	}

	override fun describeContents(): Int {
		return 0
	}
}
