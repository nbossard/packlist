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

package com.nbossard.packlist.process;

import com.nbossard.packlist.model.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by nbossard on 31/12/15.
 */
public class PrefsSavingModule implements ISavingModule {

    @Override
    public List<Trip> loadSavedTrips() {
        List<Trip> testList= new ArrayList<>();
        testList.add(new Trip("Verdun", "23 décembre 2015", "28 décembre 2015"));
        testList.add(new Trip("Guadeloupe", "1er mars 2016", "15 mars 2016"));
        return testList;
    }

    @Override
    public void addNewTrip(Trip parTmpTrip) {
        // TODO implement this
    }
}
