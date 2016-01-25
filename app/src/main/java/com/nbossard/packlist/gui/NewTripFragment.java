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

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.nbossard.packlist.R;

import org.w3c.dom.Text;

import java.util.Calendar;

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

    /** constant for "do not vibrate" in calendar. */
    private static final boolean DO_NOT_VIBRATE = false;

    /** Frag to identify fragment for date. */
    public static final String DATEPICKER_TAG = "datepicker";


    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for when user clicks on "submit" button.
     */
    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @DebugLog
        @Override
        public void onClick(View v) {

            // asking supporting activity to launch creation of new trip
            mHostingActivity.createNewTrip(mNameTV.getText().toString(),
                    mStartDateTV.getText().toString(),
                    mEndDateTV.getText().toString(),
                    mNoteTV.getText().toString());

            // navigating back
            getActivity().getSupportFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private DatePickerDialog.OnDateSetListener dateSelectedListener =
            new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(final DatePickerDialog parDatePickerDialog,
                              final int year, final int month, final int day) {
            mStartDateTV.setText(year + "-" + (month + 1) + "-" + day);

        }
    };

    // *********************** FIELDS ***********************************************************************

    /** For communicating with hosting activity. */
    private IMainActivity mHostingActivity;

    /** Root view for easy findviewById use.*/
    private View mRootView;

    /** Hosting activity interface. */
    private IMainActivity mIMainActivity;

    final Calendar calendar = Calendar.getInstance();
    final DatePickerDialog datePickerDialog =
            DatePickerDialog.newInstance(dateSelectedListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), DO_NOT_VIBRATE);

    /** Text view for input of "trip start date". */
    private TextView mStartDateTV;

    /** Text view for input of "trip end date". */
    private TextView mEndDateTV;

    /** Text view for input of "free notes on trip". */
    private TextView mNoteTV;

    /** Text view for input of "trip name". */
    private TextView mNameTV;

    // *********************** METHODS **********************************************************************

    /**
     * Empty constructor.
     */
    public NewTripFragment() {
    }

    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @DebugLog
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_new_trip, container, false);
        return mRootView;
    }

    @DebugLog
    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHostingActivity = (IMainActivity) getActivity();

        // Getting views
        mNameTV = (TextView) mRootView.findViewById(R.id.new_trip__name__edit);
        mStartDateTV = (TextView) mRootView.findViewById(R.id.new_trip__start_date__edit);
        mEndDateTV = (TextView) mRootView.findViewById(R.id.new_trip__end_date__edit);
        mNoteTV = (TextView) mRootView.findViewById(R.id.new_trip__note__edit);

        // Adding listeners
        addListenerOnSubmitButton();
        addListenerOnStartDate();
    }

    @Override
    public final void onResume() {
        super.onResume();
        mIMainActivity.showFAB(false);
    }

    /**
     * Add a listener on "submit" button.
     */
    private void addListenerOnSubmitButton() {
        Button button = (Button) mRootView.findViewById(R.id.new_trip__submit__button);
        button.setOnClickListener(mSubmitListener);
    }

    /**
     * Add a listener on "trip start date" text field.
     */
    private void addListenerOnStartDate() {
        mStartDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });
    }


}
