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

package com.nbossard.packlist.gui;

/*
@startuml
    interface com.nbossard.packlist.gui.ITripDetailFragmentActivity {
        + saveTrip(...)
    }

    com.nbossard.packlist.gui.IMainActivity <|-- com.nbossard.packlist.gui.ITripDetailFragmentActivity
@enduml
 */


import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.ScoredItem;
import com.nbossard.packlist.model.Trip;

import java.util.List;
import java.util.Set;

/**
 * The what {@link TripDetailFragment} expects from hosting activity.
 * @author Created by nbossard on 01/01/16.
 */
interface ITripDetailFragmentActivity extends IMainActivity {

    /**
     * Creation and saving of a new trip.
     *
     * @param parTrip Trip to be saved
     */
    void saveTrip(Trip parTrip);

    /**
     * Ask activity to open a mass import fragment.
     * @param parTrip trip on which mass import will be done
     */
    void openMassImportFragment(Trip parTrip);

    /**
     * @return retrieve set of all previously created items.
     */
    Set<Item> getSetOfItems();

    /**
     * @return A list of missing items in this trip by decreasing order of probability.
     */
    List<ScoredItem> getProbableItemsList();
}
