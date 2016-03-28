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
import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.Trip;
import com.robotium.solo.Solo;

import junit.framework.Assert;

import java.util.GregorianCalendar;

/**
 * Robotium tests on {@link TripDetailFragment} using {@link MainActivityForTest}
 * @author Created by naub7473 on 26/01/2016.
 */
public class TripDetailFragmentTest extends ActivityInstrumentationTestCase2<MainActivityForTest> {

    // ********************** CONSTANTS *********************************************************************

    public static final String TEST_TRIP_NAME = "Rome";
    public static final GregorianCalendar TEST_START_DATE = new GregorianCalendar(2011,1,1);
    public static final GregorianCalendar TEST_END_DATE = new GregorianCalendar(2012,2,2);
    public static final String TEST_NOTE = "Have fun";
    public static final String TEST_ITEM_NAME = "Socks";

    // ********************** FIELDS ************************************************************************

    /**
     * Solo object ???
     */
    private Solo mSolo;

    private Trip testEmptyItemSetTrip = new Trip(TEST_TRIP_NAME, TEST_START_DATE, TEST_END_DATE, TEST_NOTE);
    private Trip testNonEmptyItemSetTrip = new Trip(TEST_TRIP_NAME, TEST_START_DATE, TEST_END_DATE, TEST_NOTE);
    private Item firstTestItem = new Item(TEST_ITEM_NAME);


    // ********************** METHODS ***********************************************************************


    public TripDetailFragmentTest() {
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

        testNonEmptyItemSetTrip.addItem(TEST_ITEM_NAME);

    }

    /**
     * Test that the string that should always appear are displayed.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void testAlwaysThereStrings() throws Exception {
        getActivity().openTripDetailFragment(testEmptyItemSetTrip);

        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.trip_detail__add_item___button)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.trip_detail__edit_trip)));

        // Note :tried here to detect overdraw on these fields, but don't known how to do that

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }

    public final void testEmptyItemSet() throws Exception {
        getActivity().openTripDetailFragment(testEmptyItemSetTrip);

        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.trip_detail__no_item_yet__label)));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }


    public final void testNonEmptyItemSet() throws Exception {
        getActivity().openTripDetailFragment(testNonEmptyItemSetTrip);

        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        Assert.assertTrue(mSolo.waitForText(TEST_ITEM_NAME));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }


    /**
     * Required however the second opening of fragment will send a "Test failed to run to completion.
     * Reason: 'Instrumentation run failed due to 'java.lang.NullPointerException''"

     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
    }

}