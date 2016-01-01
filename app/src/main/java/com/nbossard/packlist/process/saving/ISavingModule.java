/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard.
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

package com.nbossard.packlist.process.saving;

import com.nbossard.packlist.model.Trip;

import java.util.List;

/**
 * Interface for all possible implementations of  saving modules.
 *
 * @author Created by nbossard on 31/12/15.
 */
public interface ISavingModule {

    /** Retrieve the list of saved trips.
     * @return a list filled with the current trips or an empty list if was never saved. */
    List<Trip> loadSavedTrips();

    /** Add a new trip to the list of saved Trips. */
    void addNewTrip(Trip parTmpTrip);

    /** Delete all previously saved trips, mainly for testing. */
    void deleteAllTrips();
}
