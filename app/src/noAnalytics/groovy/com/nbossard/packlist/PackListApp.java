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

import android.app.Application;

import com.nbossard.packlist.analytics.IAnalytic;
import com.nbossard.packlist.analytics.noAnalyticsWrapper;


/*
@startuml
    class com.nbossard.packlist.PackListApp {
        + getSavingModule()
    }
@enduml
*/

/**
 * Application level initialisations :
 * <ul>
 *     <li>Singletons</li>
 *     <li>crash reporter (ACRA)</li>
 * </ul>.
 *
 * @author Created by nbossard on 31/12/15.
 */
public class PackListApp extends AbstractPackListApp {

// ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String LOG_TAG = PackListApp.class.getName();

// *********************** FIELDS *************************************************************************

    /** Tracker object, in example a part of Google Analytics. */
    private IAnalytic mTracker;

// *********************** METHODS **************************************************************************



    public IAnalytic getTracker()
    {
        return getGoogleAnalyticsTracker();
    }

    /**
     * Gets the default analytics for this {@link Application}.
     * @return an analytic wrapped
     */
    private final synchronized  IAnalytic getGoogleAnalyticsTracker() {
        if (mTracker == null) {
            mTracker = new noAnalyticsWrapper(this);
        }
        return mTracker;
    }

}
