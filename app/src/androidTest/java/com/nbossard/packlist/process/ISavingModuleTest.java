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

import android.test.InstrumentationTestCase;

import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;
import com.nbossard.packlist.process.saving.SavingFactory;

import java.util.List;

/**
 * Test class for ISavingModule.
 *
 * @author Created by nbossard on 01/01/16.
 */
public class ISavingModuleTest extends InstrumentationTestCase {

// *********************** FIELDS *************************************************************************
    private ISavingModule mTestedSavingModule;
    private Trip mTestTrip1;
    private Trip mTestTrip2;
//

// *********************** METHODS **************************************************************************
    public void setUp() throws Exception {
        super.setUp();
        mTestTrip1 = new Trip("Rennes", "12 décembre 2015", "14 décembre 2015");
        mTestTrip2 = new Trip("Dublin", "1 mai 2015", "8 mai 2015");
        mTestedSavingModule = SavingFactory.getNewSavingModule(getInstrumentation().getTargetContext());
    }

    public void testLoadSavedTrips() throws Exception {
        mTestedSavingModule.deleteAllTrips();
        mTestedSavingModule.addNewTrip(mTestTrip1);
        mTestedSavingModule.addNewTrip(mTestTrip2);
        List<Trip> loadedTrips = mTestedSavingModule.loadSavedTrips();
        assertEquals(2,loadedTrips.size());
        assertTrue(loadedTrips.contains(mTestTrip1));
        assertTrue(loadedTrips.contains(mTestTrip2));
    }

//
}