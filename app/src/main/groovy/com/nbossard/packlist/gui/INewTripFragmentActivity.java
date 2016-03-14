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

package com.nbossard.packlist.gui;

/*
@startuml
    interface com.nbossard.packlist.gui.INewTripFragmentActivity {
        + saveTrip(...)
    }
@enduml
 */

import android.support.annotation.Nullable;

import com.nbossard.packlist.model.Trip;

import java.util.UUID;

/**
 * The what {@link NewTripFragment} expects from hosting activity.
 * @author Created by nbossard on 01/01/16.
 */
interface INewTripFragmentActivity {

    /**
     * Creation and saving of a new trip.
     *
     * @param parTrip Trip to be saved
     */
    void saveTrip(Trip parTrip);

}
