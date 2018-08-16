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

package com.nbossard.packlist.gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.PacklistSharedPrefs;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.model.TripFormatter;

import java.util.List;


/*
@startuml
    class com.nbossard.packlist.gui.TripAdapter {
    }
@enduml
 */

/**
 * An adapter for displaying a {@link Trip} in a ListView, see {@link TripListFragment}.
 *
 * @author Created by nbossard on 25/12/15.
 */
class TripAdapter extends BaseAdapter {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = TripAdapter.class.getName();

    // *********************** FIELDS ***********************************************************************

    /**
     * Application preferences custom class.
     */
    private final PacklistSharedPrefs mPreferences;

    // *********************** INNER CLASS *****************************************************************

    /**
     * View holder, works better for recycling of views.
     *
     * @author Nicolas BOSSARD
     *
     */
    private class InnerMyViewHolder
    {

        // getting views

        /**
         * Reference (result of findviewbyid) to the trip name.
         */
        private MaterialLetterIcon letterIcon;

        /**
         * Reference (result of findviewbyid) to the trip name.
         */
        private TextView tvName;

        /**
         * Reference (result of findviewbyid) to the text description of remaining days.
         */
        private TextView tvInXDays;

        /**
         * Reference (result of findviewbyid) to the trip start date.
         */
        private TextView tvStartDate;

        /** The arrow between start and end date. */
        private ImageView arrowDate;

        /**
         * Reference (result of findviewbyid) to the trip end date.
         */
        private TextView tvEndDate;
    }

    // ********************** FIELDS ************************************************************************

    /**
     * Devices to be displayed in the list.
     */
    private final List<Trip> mTripsList;

    /**
     * Provided context.
     */
    private final Context mContext;

    // *********************** METHODS **********************************************************************

    /**
     * Standard constructor.
     *
     * @param parResList
     *            data to be displayed in the list.
     * @param parContext
     *            context sic
     */
    TripAdapter(final List<Trip> parResList, final Context parContext)
    {
        super();
        mTripsList = parResList;
        mContext = parContext;

        // Retrieve preferences
        mPreferences = ((PackListApp) mContext.getApplicationContext()).getPreferences();
        Log.v(TAG, mPreferences.toString());
    }


    @Override
    public int getCount() {
        return mTripsList.size();
    }

    @Override
    public Object getItem(final int parPosition) {
        return mTripsList.get(parPosition);
    }

    @Override
    public long getItemId(final int parPosition) {
        return parPosition;
    }

    @SuppressWarnings("CheckStyle")
    @Override
    public View getView(final int parPosition, View parConvertView, final ViewGroup parParentView) {
        InnerMyViewHolder vHolderRecycle;
        TripFormatter tripFormatter = new TripFormatter(mContext);

        if (parConvertView == null)
        {
            vHolderRecycle = new InnerMyViewHolder();
            final LayoutInflater inflater = (LayoutInflater) TripAdapter.this.
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            parConvertView = inflater.inflate(R.layout.trip_adapter, parParentView, false);
        } else
        {
            vHolderRecycle = (InnerMyViewHolder) parConvertView.getTag();
        }
        // getting views
        vHolderRecycle.letterIcon = parConvertView.findViewById(R.id.ta__lettericon);
        vHolderRecycle.tvName = parConvertView.findViewById(R.id.ta__name);
        vHolderRecycle.tvInXDays = parConvertView.findViewById(R.id.ta__in_x_days);
        vHolderRecycle.tvStartDate = parConvertView.findViewById(R.id.ta__start_date);
        vHolderRecycle.arrowDate = parConvertView.findViewById(R.id.ta__arrow_date);
        vHolderRecycle.tvEndDate = parConvertView.findViewById(R.id.ta__end_date);

        final Trip oneTrip = mTripsList.get(parPosition);

        // updating views
        String firstLetter = " ";
        if (oneTrip.getName() != null && oneTrip.getName().length()>0) {
            firstLetter = oneTrip.getName().substring(0, 1);
        }
        vHolderRecycle.letterIcon.setLetter(firstLetter);
        MaterialColor colorRetriever = new MaterialColor(mContext);
        vHolderRecycle.letterIcon.setShapeColor(colorRetriever.getMatColorForIcon(oneTrip.getName()));
        vHolderRecycle.tvName.setText(oneTrip.getName());

        // user can choose, using settings, not to display dates in homepage
        if (mPreferences.isDisplayDatesPref()) {
            if (oneTrip.getStartDate() != null) {
                vHolderRecycle.tvInXDays.setText(getFormattedRemainingDays(oneTrip.getRemainingDays()));
            }
            vHolderRecycle.tvStartDate.setText(tripFormatter.getFormattedDate(oneTrip.getStartDate()));
            vHolderRecycle.tvEndDate.setText(tripFormatter.getFormattedDate(oneTrip.getEndDate()));
            if ((oneTrip.getStartDate() == null) && (oneTrip.getEndDate() == null)) {
                vHolderRecycle.arrowDate.setVisibility(View.INVISIBLE);
            } else {
                vHolderRecycle.arrowDate.setVisibility(View.VISIBLE);
            }
        } else {
            vHolderRecycle.arrowDate.setVisibility(View.INVISIBLE);
        }

        parConvertView.setTag(vHolderRecycle);
        return parConvertView;
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Get a human readable presentation of number of days before departure.
     *
     * @param parRemainingDays a number of days before or after trip departure
     * @return i.e. : "25 days ago"
     */
    private String getFormattedRemainingDays(final long parRemainingDays) {

        Log.d(TAG, "getFormattedRemainingDays() called with: "
                + "parRemainingDays = [" + parRemainingDays + "]");

        String res;
        if (parRemainingDays < 0) {
            res = String.format(mContext.getResources().
                    getQuantityString(R.plurals.ta__x_days_ago, (int) parRemainingDays), -parRemainingDays);
        } else {
            res =  String.format(mContext.getResources().
                    getQuantityString(R.plurals.ta__in_x_days, (int) parRemainingDays), parRemainingDays);
        }

        Log.d(TAG, "getFormattedRemainingDays() returned: " + res);
        return res;
    }

}
