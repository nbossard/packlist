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

package com.nbossard.packlist.process.saving;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.nbossard.packlist.model.SortModes;
import com.nbossard.packlist.model.Trip;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Test class for ISaving.
 *
 * @author Created by nbossard on 01/01/16.
 */

@RunWith(AndroidJUnit4.class)
public class ISavingTest
{

// *********************** FIELDS *************************************************************************

    private ISaving mTestedSavingModule;
    private Trip mTestTrip1;
    private Trip mTestTrip2;
    private Trip mTestTrip3;


// *********************** METHODS **************************************************************************

    @Before
    public void setUp() throws Exception {

        mTestTrip1 =
                new Trip("Rennes",
                        new GregorianCalendar(2015,12,12),
                        new GregorianCalendar(2015,12,14),
                        "pas trop loin",
                        SortModes.DEFAULT);
        mTestTrip2 =
                new Trip("Dublin",
                        new GregorianCalendar(2015,5,1),
                        new GregorianCalendar(2015,5,8),
                        "Bèèèèè",
                        SortModes.DEFAULT);
        mTestTrip3 =
                new Trip("Londres",
                        new GregorianCalendar(2015,6,4),
                        new GregorianCalendar(2015,6,9),
                        "beurk",
                        SortModes.DEFAULT);
        mTestedSavingModule = SavingFactory.getNewSavingModule(InstrumentationRegistry.getInstrumentation().getTargetContext());
        mTestedSavingModule.deleteAllTrips();
    }

    @Test
    public void testLoadSavedTrips() throws Exception {
        mTestedSavingModule.deleteAllTrips();
        mTestedSavingModule.addOrUpdateTrip(mTestTrip1);
        mTestedSavingModule.addOrUpdateTrip(mTestTrip2);
        List<Trip> loadedTrips = mTestedSavingModule.loadSavedTrips();

        //Checking result
        assertEquals(2,loadedTrips.size());
        assertTrue(loadedTrips.contains(mTestTrip1));
        assertTrue(loadedTrips.contains(mTestTrip2));
        // ensuring it is in right order (incoming trip first, then passed), not the addition one
        assertTrue(loadedTrips.get(0).getUUID().compareTo(mTestTrip1.getUUID()) == 0);
        assertTrue(loadedTrips.get(1).getUUID().compareTo(mTestTrip2.getUUID()) == 0);
    }

    @Test
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

    @SuppressWarnings("ConstantConditions")
    @Test
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