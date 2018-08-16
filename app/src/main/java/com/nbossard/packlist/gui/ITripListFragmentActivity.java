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

import com.nbossard.packlist.model.Trip;

import java.util.List;
import java.util.UUID;

/*
@startuml
    interface com.nbossard.packlist.gui.ITripListFragmentActivity {
        + openNewTripFragment(...)
        + loadSavedTrips()
        + cloneTrip(UUID)
        + deleteTrip(UUID)
    }

    com.nbossard.packlist.gui.IMainActivity <|-- com.nbossard.packlist.gui.ITripListFragmentActivity

@enduml
 */


/**
 * The what {@link TripListFragment} expects from hosting activity.
 * @author Created by nbossard on 01/01/16.
 */
interface ITripListFragmentActivity extends IMainActivity {


    /**
     * Ask activity to open a mass import fragment.
     *
     * @param parTrip trip on which mass import will be done
     */
    void openMassImportFragment(Trip parTrip);

    /**
     * Ask Main activity to open detail fragment to display Trip of provided UUID.
     *
     * @param parTrip a trip object to be displayed
     * @return the just opened TripDetailFragment
     */
    TripDetailFragment openTripDetailFragment(final Trip parTrip);

    /**
     * Ask main activity to return the list of trips to be displayed.
     * @return a fulfilled list of trips
     */
    List<Trip> loadSavedTrips();

    /**
     * Ask main activity to clone the trip of provided id.
     * @param parUUID the id of the trip to be cloned
     */
    void cloneTrip(UUID parUUID);

    /**
     * Ask main activity to delete the trip of provided id.
     * @param parUUID the id of the trip to be deleted.
     */
    void deleteTrip(UUID parUUID);
}
