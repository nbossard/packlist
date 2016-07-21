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

package com.nbossard.packlist.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nbossard.packlist.R;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/*
@startuml
    class com.nbossard.packlist.model.TripFormatter {
        + getFormattedDate(...)
        + getFormattedWeight(...)
    }
@enduml
 */

/**
 * Formatter for {@link Trip}, used in data binding for presentation.
 *
 * @author Created by naub7473 on 21/03/16.
 */
public class TripFormatter
{

// *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = TripFormatter.class.getName();

// *********************** FIELDS ***************************************************************************

    /**
     * Context provided in constructor, will be used to retrieve strings and format dates.
     */
    private final Context mContext;

// *********************** METHODS **************************************************************************

    /**
     * Standard constructor.
     *
     * @param parContext will be used to retrieve strings and format dates
     */
    public TripFormatter(final Context parContext)
    {
        mContext = parContext;
    }

    /**
     * The trip start date but as a locale formatted date.
     * @param parDate date to be returned formatted
     * @return locale formatted date or null if never set
     */
    public final String getFormattedDate(final GregorianCalendar parDate) {

        Log.d(TAG, "getFormattedDate() called with: " + "parDate = [" + parDate + "]");

        String res = null;
        DateFormat dateFormat;

        //S3 : works fine on recent android (without date format)
        // and takes into account date format on old android
        if (mContext != null) {
            dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
        } else {
            // seen this case is crash mails received...
            // Using a fallback
            Log.w(TAG, "Very strange context is null");
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        }

        /*
        //S2 : the whole block
        final String format = Settings.System.getString(
                            mContext.getContentResolver(), Settings.System.DATE_FORMAT);

        if (TextUtils.isEmpty(format)) {
            // S1
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        } else {
            dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        }
        */

        if (parDate != null) {
            res = dateFormat.format(parDate.getTime());
        }

        Log.d(TAG, "getFormattedDate() returned: " + res);
        return res;
    }

    /**
     * The trip formatted weight.
     * @param parWeight weight in grams to be formatted for display, an integer
     * @param parPackedWeight weight of packed items in grams to be formatted for display, an integer
     * @return formatted string including weights
     */
    public final
    @Nullable
    String getFormattedWeight(final int parWeight, final int parPackedWeight) {

        Log.d(TAG, "getFormattedWeight() called with: "
                + "parWeight = [" + parWeight + "]"
                + ", parPackedWeight = [" + parPackedWeight + "]");
        String res;
        if (mContext != null) {
            res = String.format(mContext.getString(R.string.trip_detail__before_weight__label),
                    parWeight, parPackedWeight);
        } else {
            Log.w(TAG, "getFormattedWeight() found a null context, VERY STRANGE.");
            res = null;
        }
        Log.d(TAG, "getFormattedWeight() returned: " + res);
        return res;
    }

}
