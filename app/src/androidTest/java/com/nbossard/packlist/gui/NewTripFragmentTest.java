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

import android.test.ActivityInstrumentationTestCase2;

import com.nbossard.packlist.R;
import com.nbossard.packlist.TestValues;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * Robotium tests on {@link NewTripFragment} using {@link MainActivityForTest}
 * Created by naub7473 on 26/01/2016.
 */
public class NewTripFragmentTest extends ActivityInstrumentationTestCase2<MainActivityForTest> {

    // ********************** FIELDS ************************************************************************

    /**
     * Solo object ???
     */
    private Solo mSolo;

    // ********************** METHODS ***********************************************************************


    public NewTripFragmentTest() {
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
        getActivity().openNewTripFragment();
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

        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__name)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__start_date)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__end_date)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__note)));
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__submit)));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }

    /**
     * Test that start date picker opens and allows selection of date.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void testClickOnStartDate() throws Exception
    {
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        mSolo.clickOnImageButton(0);

        Assert.assertTrue(mSolo.waitForText("2016",1,TestValues.TIMEOUT_1000_MS, TestValues.DO_NOT_SCROLL));
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);
        mSolo.clickOnText("OK");
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);
        Assert.assertTrue(mSolo.waitForText("/2016"));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }

    /**
     * Test that end date picker opens and allows selection of date.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void testClickOnEndDate() throws Exception
    {
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        mSolo.clickOnImageButton(1);

        Assert.assertTrue(mSolo.waitForText("2016",1,TestValues.TIMEOUT_1000_MS, TestValues.DO_NOT_SCROLL));
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);
        mSolo.clickOnText("OK");
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);
        Assert.assertTrue(mSolo.waitForText("/2016"));

        // let human see the screen
        mSolo.sleep(Common.HUMAN_TIME_FOR_READING);
    }

    /**
     * Test that name is mandatory for unlocking submit button.
     *
     * @throws Exception
     *             if a test has failed.
     */
    public final void testMandatoryName() throws Exception
    {
        mSolo.sleep(TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        // initially button is blocked
        mSolo.clickOnButton(mSolo.getString(R.string.new_trip__submit));

        // we are still on same fragment
        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.new_trip__name)));

        mSolo.typeText(0,"Rome");

        // button should be unblocked
        mSolo.clickOnButton(mSolo.getString(R.string.new_trip__submit));

        Assert.assertTrue(mSolo.waitForText(mSolo.getString(R.string.main__welcome)));

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