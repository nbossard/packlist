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

import android.test.ActivityInstrumentationTestCase2;

import com.nbossard.packlist.R;
import com.nbossard.packlist.TestValues;
import com.nbossard.packlist.model.Trip;
import com.robotium.solo.Solo;

import junit.framework.Assert;

import java.util.GregorianCalendar;

/**
 * Robotium tests on {@link MassImportFragment} using {@link MainActivityForTest}
 * @author Created by nbossard on 08/04/16.
 */
public class MassImportFragmentTest  extends ActivityInstrumentationTestCase2<MainActivityForTest> {


    private static final String TEST_TRIP_NAME = "Rome";
    private static final GregorianCalendar TEST_START_DATE = new GregorianCalendar(2011, 1, 1);
    private static final GregorianCalendar TEST_END_DATE = new GregorianCalendar(2012, 2, 2);
    private static final String TEST_NOTE = "Have fun";
    private static final String TEST_ITEM_NAME = "Socks";

    private final Trip testEmptyItemSetTrip =
            new Trip(TEST_TRIP_NAME, TEST_START_DATE, TEST_END_DATE, TEST_NOTE);

    // ********************** FIELDS ************************************************************************

    /**
     * Solo object ???
     */
    private Solo mSolo;

    // ********************** METHODS ***********************************************************************

    public MassImportFragmentTest() {
        super(MainActivityForTest.class);
    }

    /**
     * Will launch activity to be tested.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void setUp() throws Exception
    {
        mSolo = new Solo(getInstrumentation(), getActivity());
        getActivity().openMassImportFragment(testEmptyItemSetTrip);
    }

    /**
     * Test that the string that should always appear are displayed.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void testAlwaysThereStrings() throws Exception
    {
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.mass_import__principle_explanation__label)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.mass_import__import__button)));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }
}
