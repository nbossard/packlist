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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.NewTripFragment {
    }

    com.nbossard.packlist.gui.NewTripFragment --> com.nbossard.packlist.gui.IMainActivity
@enduml
 */

/**
 * Allow user  to input trip characteristics.
 *
 * @author Created by nbossard on 30/12/15.
 */
public class NewTripFragment extends Fragment {


    // ********************** CONSTANTS *********************************************************************

    /** Log tag. */
    private static final String TAG = NewTripFragment.class.getName();

    // *********************** FIELDS ***********************************************************************

    /** For communicating with hosting activity. */
    private IMainActivity mHostingActivity;

    private View mRootView;

    /** Hosting activity interface. */
    private IMainActivity mIMainActivity;

    // *********************** LISTENERS ********************************************************************
    View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @DebugLog
        @Override
        public void onClick(View v) {
            // Getting data
            TextView nameTV = (TextView) mRootView.findViewById(R.id.new_trip__name__edit);
            TextView startDateTV = (TextView) mRootView.findViewById(R.id.new_trip__start_date__edit);
            TextView endDateTV = (TextView) mRootView.findViewById(R.id.new_trip__end_date__edit);
            TextView noteTV = (TextView) mRootView.findViewById(R.id.new_trip__note__edit);

            // asking supporting activity to launch creation of new trip
            mHostingActivity.createNewTrip(nameTV.getText().toString(),
                    startDateTV.getText().toString(),
                    endDateTV.getText().toString(),
                    noteTV.getText().toString());

            // navigating back
            getActivity().getSupportFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };
    // *********************** METHODS **********************************************************************

    /**
     * Empty constructor.
     */
    public NewTripFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendReportToTracker();

        mIMainActivity = (IMainActivity) getActivity();
    }

    @DebugLog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_new_trip, container, false);
        return mRootView;
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHostingActivity = (IMainActivity) getActivity();
        addListenerOnSubmitButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIMainActivity.showFAB(false);
    }

    private void addListenerOnSubmitButton() {
        Button button = (Button) mRootView.findViewById(R.id.new_trip__submit__button);
        button.setOnClickListener(mSubmitListener);
    }

    /**
     * Send report to tracker, currently Google Analytics, this could change.
     */
    private void sendReportToTracker() {
        PackListApp application = (PackListApp) getActivity().getApplication();
        Tracker tracker = application.getDefaultTracker();
        tracker.setScreenName(TAG);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
