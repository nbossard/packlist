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

import android.content.Context;

import com.amazonaws.mobileconnectors.amazonmobileanalytics.AnalyticsEvent;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.InitializationException;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.MobileAnalyticsManager;
import android.util.Log;

/**
 * Implementation of generic analytics by Amazon Analytics
 *
 * Created by naub7473 on 16/08/16.
 */
public class AmazonAnalyticsWrapper implements com.nbossard.packlist.analytics.IAnalytic
{
    /**
     * The amazon main object to deal with analytics.
     */
    private static MobileAnalyticsManager mMobileAnalyticsManager;

    /**
     * Application context, provided in constructor, to inialize amazon analytics.
     */
    private final Context mApplicationContext;

    /**
     * Standard constructor.
     * @param parApplicationContext application context useful to configure analytic
     */
    public AmazonAnalyticsWrapper(final Context parApplicationContext)
    {
        mApplicationContext = parApplicationContext;
    }

    @Override
    public final void sendScreenDisplayedReportToTracker(final String parTag)
    {


        try {
            mMobileAnalyticsManager = MobileAnalyticsManager.getOrCreateInstance(
                    mApplicationContext,
                    "c5f75d79bb4f4eb59c73c89615ca1645", //Amazon Mobile Analytics App ID
                    "us-east-1:322075f7-ca72-48f9-9730-2f85aae8a3a5" //Amazon Cognito Identity Pool ID
            );
        } catch (InitializationException ex) {
            Log.e(this.getClass().getName(), "Failed to initialize Amazon Mobile Analytics", ex);
        }



    }

    @Override
    public final void sendScreenPausedReportToTracker(final String parTag)
    {
        if (mMobileAnalyticsManager != null) {
            mMobileAnalyticsManager.getSessionClient().pauseSession();
            mMobileAnalyticsManager.getEventClient().submitEvents();
        }
    }

    @Override
    public final void sendScreenResumedReportToTracker(final String parTag)
    {
        if (mMobileAnalyticsManager != null) {
            mMobileAnalyticsManager.getSessionClient().resumeSession();
        }
    }

    @Override
    public final void sendEvent(final AnalyticsEventList parEvent)
    {
        switch (parEvent) {
        case action_trip_share:
            AnalyticsEvent event =
                    mMobileAnalyticsManager.getEventClient().createEvent("action_trip__share");
            mMobileAnalyticsManager.getEventClient().recordEvent(event);

            break;
        case action_trip__import_txt:
            AnalyticsEvent eventImport =
                    mMobileAnalyticsManager.getEventClient().createEvent("action_trip__import_txt");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventImport);
            break;
        case action_trip__sort:
            AnalyticsEvent eventSort =
                    mMobileAnalyticsManager.getEventClient().createEvent("action_trip__sort");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventSort);
            break;
        case action_trip__unpack_all:
            AnalyticsEvent eventUnpack =
                    mMobileAnalyticsManager.getEventClient().createEvent("action_trip__unpack_all");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventUnpack);
            break;
        case onClickEditTripButton:
            AnalyticsEvent eventEdit =
                    mMobileAnalyticsManager.getEventClient().createEvent("onClickEditTripButton");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventEdit);
            break;
        case onClickAddItemButton:
            AnalyticsEvent eventAdd =
                    mMobileAnalyticsManager.getEventClient().createEvent("onClickAddItemButton");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventAdd);
            break;
        case onClickAddDetailedButton:
            AnalyticsEvent eventAddDetailed =
                    mMobileAnalyticsManager.getEventClient().createEvent("onClickAddDetailedButton");
            mMobileAnalyticsManager.getEventClient().recordEvent(eventAddDetailed);
            break;
        default:
            break;
        }
    }

}
