
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

package com.nbossard.packlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class uiAutomator {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.nbossard.packlist.debug";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }

    @Test
    public void testOpenMenu() throws UiObjectNotFoundException, InterruptedException {
        UiObject menuButton = mDevice.findObject(new UiSelector().descriptionContains("More options"));

        // Simulate a user-click on the menu button, if found.
        if (menuButton.exists() && menuButton.isEnabled()) {
            menuButton.click();
        }

        mDevice.wait(Until.findObject(By.text("About")), TestValues.LET_UI_THREAD_UPDATE_DISPLAY);

        UiObject menuSendAReport = mDevice.findObject(new UiSelector().text("Send a report"));
        UiObject menuWhatsNew = mDevice.findObject(new UiSelector().text("What's new"));
        UiObject menuAbout = mDevice.findObject(new UiSelector().text("About"));

        assertTrue(menuSendAReport.exists());
        assertTrue(menuWhatsNew.exists());
        assertTrue(menuAbout.exists());
    }

    @Test
    public void testEmptyList() throws UiObjectNotFoundException, InterruptedException {

        deleteAllTrips();

        UiObject menuAbout = mDevice.findObject(new UiSelector().text("No trip planned"));
        assertTrue(menuAbout.exists());
    }

    @Test
    public void testAddTrip() throws UiObjectNotFoundException, InterruptedException {

        deleteAllTrips();

        addTripToRome();

    }

    private void addTripToRome() {
        //TODO
    }

    // *********************** PRIVATE METHODS **************************************************************

    private void deleteAllTrips() throws UiObjectNotFoundException {
        UiObject listView = mDevice.findObject(new UiSelector().className("android.widget.ListView"));
        UiObject firstLine = listView.getChild(new UiSelector().clickable(true).index(0));

        while (firstLine.exists() && firstLine.isEnabled()) {

            Rect firstLineRect = firstLine.getBounds();
            mDevice.swipe(firstLineRect.centerX(),
                    firstLineRect.centerY(),
                    firstLineRect.centerX(),
                    firstLineRect.centerY(), 300);


            UiObject deleteButton = mDevice.findObject(new UiSelector().descriptionContains("Delete"));
            deleteButton.click();
        }
    }
}