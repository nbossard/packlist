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

package com.nbossard.packlist.gui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.process.saving.ISavingModule;

/**
 * Open a Trip for viewing / editing.
 * @author Created by nbossard on 09/01/16.
 */
public class TripDetailFragment extends Fragment {

    // *********************** FIELDS ***********************************************************************

    /** Bundle mandatory parameter when instantiating this fragment. */
    public static final String BUNDLE_PAR_TRIP_ID = "bundleParTripId";

    /** Value provided when instantiating this fragment, unique identifier of trp. */
    private CharSequence mTripId;

    /** The root view, will be used to findViewById. */
    private View mRootView;

    /** The saving module to retrieve and update data (trips).*/
    private ISavingModule mSavingModule;

    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     * @param parTripId identifier of trip to be displayed
     */
    public static TripDetailFragment newInstance(String parTripId) {
        TripDetailFragment f = new TripDetailFragment();
        Bundle b = new Bundle();
        b.putCharSequence(BUNDLE_PAR_TRIP_ID, parTripId);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSavingModule = ((PackListApp) getActivity().getApplication()).getSavingModule();
    }

    /**
     * During creation, if arguments have been supplied to the fragment
     * then parse those out.
     */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mTripId = args.getString(BUNDLE_PAR_TRIP_ID, "");
        }
    }

    /**
     * Create the view for this fragment, using the arguments given to it.
     */
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        populateFields();
        return mRootView;
    }

    /** Populate fields with data from Trip. */
    private void populateFields() {
        mSavingModule.loadSavedTrips();
    }

}
