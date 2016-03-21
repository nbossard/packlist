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

package com.nbossard.packlist.model;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by naub7473 on 21/03/16.
 */
public class TripFormatter
{
    private final Context mContext;

    public TripFormatter(Context parContext)
    {
        mContext = parContext;
    }

    /**
     * The trip start date but as a locale formatted date.
     * @return locale formatted date or null if never set
     */
    public final String getFormattedDate(GregorianCalendar parDate) {
        String res = null;

        final String format = Settings.System.getString(mContext.getContentResolver(), Settings.System.DATE_FORMAT);
        DateFormat dateFormat;

        if (TextUtils.isEmpty(format)) {
            dateFormat = android.text.format.DateFormat.getMediumDateFormat(mContext.getApplicationContext());
        } else {
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        }

        if (parDate != null) {
            res = dateFormat.format(parDate.getTime());
        }
        return res;
    }

}
