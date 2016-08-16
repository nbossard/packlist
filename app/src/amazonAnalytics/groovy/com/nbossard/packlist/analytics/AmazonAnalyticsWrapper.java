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

import com.amazonaws.mobileconnectors.amazonmobileanalytics.InitializationException;
import com.amazonaws.mobileconnectors.amazonmobileanalytics.MobileAnalyticsManager;
import android.util.Log;

import java.util.Map;

/**
 * Implementation of generic analytics by Amazon Analytics
 *
 * Created by naub7473 on 16/08/16.
 */
public class AmazonAnalyticsWrapper implements com.nbossard.packlist.analytics.IAnalytic
{
    private static MobileAnalyticsManager mTracker;
    private final Context mApplicationContext;

    public AmazonAnalyticsWrapper(final Context parApplicationContext)
    {
        mApplicationContext = parApplicationContext;
    }

    @Override
    public void sendScreenDisplayedReportToTracker(final String parTag)
    {


        try {
            mTracker = MobileAnalyticsManager.getOrCreateInstance(
                    mApplicationContext,
                    "c5f75d79bb4f4eb59c73c89615ca1645", //Amazon Mobile Analytics App ID
                    "us-east-1:322075f7-ca72-48f9-9730-2f85aae8a3a5" //Amazon Cognito Identity Pool ID
            );
        } catch(InitializationException ex) {
            Log.e(this.getClass().getName(), "Failed to initialize Amazon Mobile Analytics", ex);
        }



    }

    @Override
    public void sendScreenPausedReportToTracker(final String parTag)
    {
        if(mTracker != null) {
            mTracker.getSessionClient().pauseSession();
            mTracker.getEventClient().submitEvents();
        }
    }

    @Override
    public void sendScreenResumedReportToTracker(final String parTag)
    {
        if(mTracker != null) {
            mTracker.getSessionClient().resumeSession();
        }
    }

    @Override
    public void sendEvent(AnalyticsEventList parEvent)
    {

    }

}
