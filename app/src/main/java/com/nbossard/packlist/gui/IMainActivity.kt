/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2017 Nicolas Bossard and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.nbossard.packlist.gui

/*
@startuml
    interface com.nbossard.packlist.gui.IMainActivity {
        + openTripDetailFragment(...)
        + openNewTripFragment(...)
        + showFABIfAccurate(boolean)
        + updateTitleBar(String)
    }
@enduml
 */

import com.nbossard.packlist.model.TripItem

import java.util.UUID

/**
 * The what fragments expects from main hosting activity.
 * @author Created by nbossard on 01/01/16.
 */
internal interface IMainActivity {

    /**
     * Ask Main activity to open new trip fragment to display Trip of provided UUID.
     *
     * @param parTripId a trip unique identifier (UUID) if editing, null otherwise.
     */
    fun openNewTripFragment(parTripId: UUID?)

    /**
     * Display provided TripItem to allow editing of details.
     * @param parItem item to be edited
     */
    fun openItemDetailFragment(parItem: TripItem)

    /**
     * Hide or show FAB, depending on fragment.
     * @param parShow true to show, false to hide
     */
    fun showFABIfAccurate(parShow: Boolean)

    /**
     * Ask activity to update title bar with provided new title.
     * This will also display homeAsUp.
     *
     * @param parNewTitleInTitleBar new title to be displayed in title bar
     */
    fun updateTitleBar(parNewTitleInTitleBar: String)
}
