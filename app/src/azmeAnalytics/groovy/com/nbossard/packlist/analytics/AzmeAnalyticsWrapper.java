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
import android.util.Log;

import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.EngagementConfiguration;
import com.nbossard.packlist.R;


/**
 * Implementation of generic analytics by Google Analytics
 *
 * Created by naub7473 on 16/08/16.
 */
public class AzmeAnalyticsWrapper implements IAnalytic
{
    // *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = AzmeAnalyticsWrapper.class.getName();

    // *********************** METHODS **********************************************************************

    /**
     * Application context, provided in constructor, to inialize analytics.
     */
    private final Context mApplicationContext;

    public AzmeAnalyticsWrapper(final Context parApplicationContext) {
        mApplicationContext = parApplicationContext;

        EngagementConfiguration engagementConfiguration = new EngagementConfiguration();
        String connectionString =
                "Endpoint=" + parApplicationContext.getString(R.string.ENGAGEMENT_END_POINT)
                        + ";"
                        + "SdkKey=" + parApplicationContext.getString(R.string.ENGAGEMENT_SDK_KEY)
                        + ";"
                        + "AppId=" + parApplicationContext.getString(R.string.ENGAGEMENT_APP_ID);
        engagementConfiguration.setConnectionString(connectionString);
        Log.d(TAG, "AzmeAnalyticsWrapper() connection string : " + connectionString);
        EngagementAgent.getInstance(parApplicationContext).init(engagementConfiguration);
    }

    @Override
    public final void sendScreenDisplayedReportToTracker(final String parTag)
    {
    }

    @Override
    public void sendScreenPausedReportToTracker(final Activity parActivity, final String parTag)
    {
        EngagementAgent.getInstance(mApplicationContext).startActivity(parActivity, parTag, null);
    }

    @Override
    public void sendScreenResumedReportToTracker(final Activity parActivity, final String parTag)
    {
        EngagementAgent.getInstance(mApplicationContext).startActivity(parActivity, parTag, null);
    }

    @Override
    public final void sendEvent(final AnalyticsEventList parEvent)
    {
        switch (parEvent) {
        case action_trip_share:
            EngagementAgent.getInstance(mApplicationContext)
                    .sendEvent("action_trip__share", null);

            break;
        case action_trip__import_txt:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("action_trip__import_txt", null);
            break;
        case action_trip__sort:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("action_trip__sort", null);
            break;
        case action_trip__unpack_all:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("action_trip__unpack_all", null);
            break;
        case onClickEditTripButton:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("EditTrip", null);
            break;
        case onClickAddItemButton:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("AddItem", null);
            break;
        case onClickAddDetailedButton:
            EngagementAgent.getInstance(mApplicationContext).sendEvent("AddDetailedItem", null);
            break;
        default:
            break;
        }
    }

}
