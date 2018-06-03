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
    interface com.nbossard.packlist.gui.IItemDetailFragmentActivity {
        + updateItem(...)
        getListOfCategories()
    }

    com.nbossard.packlist.gui.IMainActivity <|-- com.nbossard.packlist.gui.IItemDetailFragmentActivity
@enduml
 */

import com.nbossard.packlist.model.TripItem

/**
 * The what [ItemDetailFragment] expects from hosting activity.
 * @author Created by nbossard on 01/01/16.
 */
internal interface IItemDetailFragmentActivity : IMainActivity {

    /**
     * @return retrieve all previously created item categories.
     */
    val listOfCategories: Set<String>

    /**
     * Called when an item has been updated and needs to be saved and its displays to be updated.
     *
     * @param parItem Trip to be updated
     */
    fun updateItem(parItem: TripItem)

}
