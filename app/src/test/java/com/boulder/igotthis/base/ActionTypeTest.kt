/*
 * Copyright (C) 2018 Ankit Sadana
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

import org.junit.Test

import org.junit.Assert.*

/**
 * @author asadana
 * @since 4/19/18
 */
class ActionTypeTest {

	@Test
	fun getStringResource() {
		assertNotEquals(-1, ActionType.getStringResource(ActionType.TURN_WIFI_ON))
		assertNotEquals(-1, ActionType.getStringResource(ActionType.TURN_WIFI_OFF))
		assertNotEquals(-1, ActionType.getStringResource(ActionType.TURN_WIFI_ON))
		assertNotEquals(-1, ActionType.getStringResource(ActionType.TURN_BLUETOOTH_ON))
		assertNotEquals(-1, ActionType.getStringResource(ActionType.TURN_BLUETOOTH_OFF))
		assertNotEquals(-1, ActionType.getStringResource(ActionType.NONE))
	}

	@Test
	fun valueAt() {
		assertEquals(ActionType.valueAt(0), ActionType.NONE)
		assertEquals(ActionType.valueAt(1), ActionType.TURN_BLUETOOTH_ON)
		assertEquals(ActionType.valueAt(2), ActionType.TURN_BLUETOOTH_OFF)
		assertEquals(ActionType.valueAt(3), ActionType.TURN_WIFI_ON)
		assertEquals(ActionType.valueAt(4), ActionType.TURN_WIFI_OFF)
		assertEquals(ActionType.valueAt(5), ActionType.OPEN_APP)
	}

	@Test
	fun getPositionOf() {
		assertEquals(0, ActionType.getPositionOf(ActionType.NONE))
		assertEquals(1, ActionType.getPositionOf(ActionType.TURN_BLUETOOTH_ON))
		assertEquals(2, ActionType.getPositionOf(ActionType.TURN_BLUETOOTH_OFF))
		assertEquals(3, ActionType.getPositionOf(ActionType.TURN_WIFI_ON))
		assertEquals(4, ActionType.getPositionOf(ActionType.TURN_WIFI_OFF))
		assertEquals(5, ActionType.getPositionOf(ActionType.OPEN_APP))
	}
}