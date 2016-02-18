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

import java.util.GregorianCalendar;
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
    private Trip mTestTrip3;

//

// *********************** METHODS **************************************************************************
    public void setUp() throws Exception {
        super.setUp();
        mTestTrip1 =
                new Trip("Rennes",
                        new GregorianCalendar(2015,12,12),
                        new GregorianCalendar(2015,12,14),
                        "pas trop loin");
        mTestTrip2 =
                new Trip("Dublin",
                        new GregorianCalendar(2015,5,1),
                        new GregorianCalendar(2015,5,8),
                        "Bèèèèè");
        mTestTrip3 =
                new Trip("Londres",
                        new GregorianCalendar(2015,6,4),
                        new GregorianCalendar(2015,6,9),
                        "beurk");
        mTestedSavingModule = SavingFactory.getNewSavingModule(getInstrumentation().getTargetContext());
        mTestedSavingModule.deleteAllTrips();
    }

    public void testLoadSavedTrips() throws Exception {
        mTestedSavingModule.deleteAllTrips();
        mTestedSavingModule.addOrUpdateTrip(mTestTrip1);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip2);
        List<Trip> loadedTrips = mTestedSavingModule.loadSavedTrips();

        //Checking result
        assertEquals(2,loadedTrips.size());
        assertTrue(loadedTrips.contains(mTestTrip1));
        assertTrue(loadedTrips.contains(mTestTrip2));
    }

    public void testDeleteTrip() {
        mTestedSavingModule.addOrUpdateTrip(mTestTrip1);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip2);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip3);

        mTestedSavingModule.deleteTrip(mTestTrip2.getUUID());
        List<Trip> loadedTrips = mTestedSavingModule.loadSavedTrips();

        // checking result
        assertEquals(2,loadedTrips.size());
        assertTrue(loadedTrips.contains(mTestTrip1));
        assertTrue(loadedTrips.contains(mTestTrip3));
    }

    public void testAddOrUpdateTrip() throws Exception {
        mTestedSavingModule.addOrUpdateTrip(mTestTrip1);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip2);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip3);

        mTestTrip2.setNote("Bouh");
        mTestedSavingModule.addOrUpdateTrip(mTestTrip2);

        Trip loadedTrip = mTestedSavingModule.loadSavedTrip(mTestTrip2.getUUID());

        assertEquals("Bouh", loadedTrip.getNote());
    }

//
}