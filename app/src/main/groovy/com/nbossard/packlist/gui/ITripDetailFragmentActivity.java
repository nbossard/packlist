/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard and other contributors.
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

import com.nbossard.packlist.model.Trip;

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
     */
    void openMassImportFragment(Trip parTrip);
}
