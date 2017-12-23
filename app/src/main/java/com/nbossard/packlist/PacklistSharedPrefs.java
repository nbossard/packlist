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

package com.nbossard.packlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Application preferences encapsulation class.
 *
 * @author Created by nbossard on 05/04/17.
 */

public class PacklistSharedPrefs {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = PacklistSharedPrefs.class.getName();

    // *********************** FIELDS ***********************************************************************


    /**
     * Context, provided in constructor.
     */
    private Context mContext;

    // *********************** METHODS **********************************************************************

    /**
     * @param parContext will be used to retrieve defaultsharedpreferences.
     */
    PacklistSharedPrefs(final Context parContext) {
        mContext = parContext;
    }

    /**
     * @return true if date related info should be displayed, false otherwise.
     * Set by user in preferences activity.
     */
    public boolean isDisplayDatesPref() {
        // Retrieve preferences
        SharedPreferences mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean displayDatesPref = mSharedPref.getBoolean("pref_display_dates", true);
        Log.d(TAG, "isDisplayDatesPref: returning = " + displayDatesPref);

        return displayDatesPref;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PacklistSharedPrefs{");
        sb.append("isDisplayDatesPref=").append(isDisplayDatesPref());
        sb.append('}');
        return sb.toString();
    }
}
