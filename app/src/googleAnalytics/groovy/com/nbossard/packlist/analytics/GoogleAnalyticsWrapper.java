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

package com.nbossard.packlist.analytics;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Implementation of generic analytics by Google Analytics
 *
 * Created by naub7473 on 16/08/16.
 */
public class GoogleAnalyticsWrapper implements IAnalytic
{
    // *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = GoogleAnalyticsWrapper.class.getName();

    // ********************** FIELDS ************************************************************************

    /** Google analytics main component. */
    private final Tracker mTracker;

    // *********************** METHODS **********************************************************************

    /** standard constructor, initialises Google analytics component. */
    public GoogleAnalyticsWrapper(final Context parContext)
    {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(parContext);
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        mTracker = analytics.newTracker("UA-47815384-2");
    }

    @Override
    public final void sendScreenDisplayedReportToTracker(final String parTag)
    {
        mTracker.setScreenName(parTag);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    @Override
    public void sendScreenPausedReportToTracker(final Activity parActivity, final String parTag)
    {
        // unused by Google Analytics
    }

    @Override
    public void sendScreenResumedReportToTracker(final Activity parActivity, final String parTag)
    {
        // unused by Google Analytics
    }

    @Override
    public final void sendEvent(final AnalyticsEventList parEvent)
    {
        switch (parEvent) {
        case action_trip_share:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("action_trip__share")
                    .build());
            break;
        case action_trip__import_txt:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("action_trip__import_txt")
                    .build());
            break;
        case action_trip__sort:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("action_trip__sort")
                    .build());
            break;
        case action_trip__unpack_all:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("action_trip__unpack_all")
                    .build());
            break;
        case onClickEditTripButton:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("EditTrip")
                    .build());
            break;
        case onClickAddItemButton:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("AddItem")
                    .build());
            break;
        case onClickAddDetailedButton:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction("AddDetailedItem")
                    .build());
            break;
        default:
            break;
        }
    }

}
