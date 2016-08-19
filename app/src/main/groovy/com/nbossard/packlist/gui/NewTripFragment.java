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

package com.nbossard.packlist.gui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatImageButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.analytics.IAnalytic;
import com.nbossard.packlist.databinding.FragmentNewTripBinding;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.model.TripFormatter;
import com.nbossard.packlist.process.saving.ISavingModule;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.NewTripFragment {
    }

    com.nbossard.packlist.gui.NewTripFragment --> com.nbossard.packlist.gui.INewTripFragmentActivity
@enduml
 */

/**
 * Allow user to input new trip characteristics or edit.
 *
 * @author Created by nbossard on 30/12/15.
 */
public class NewTripFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /** Log tag. */
    private static final String TAG = NewTripFragment.class.getName();

    /** Bundle mandatory parameter when instantiating this fragment. */
    private static final String BUNDLE_PAR_TRIP_ID = "bundleParTripId";

    /** constant for "do not vibrate" in calendar. */
    private static final boolean DO_NOT_VIBRATE = false;

    /** Frag to identify fragment for start date picker. */
    private static final String DATE_PICKER_START_TAG = "datePickerStart";

    /** Frag to identify fragment for end date picker. */
    private static final String DATE_PICKER_END_TAG = "datePickerEnd";

    /** End of trip date as a GregorianCalendar. */
    private GregorianCalendar mEndDate;

    /** Start of trip date as a GregorianCalendar. */
    private GregorianCalendar mStartDate;

    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for when user clicks on "submit" button.
     */
    private final View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @DebugLog
        @Override
        public void onClick(final View v) {

            // update trip
            mTrip.setName(mNameTV.getText().toString());
            mTrip.setStartDate(mStartDate);
            mTrip.setEndDate(mEndDate);
            mTrip.setNote(mNoteTV.getText().toString());

            // asking supporting activity to launch creation of new trip
            mHostingActivity.saveTrip(mTrip);

            // navigating back
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            fragMgr.beginTransaction().remove(NewTripFragment.this).commit();
            fragMgr.popBackStack();
        }
    };

    /**
     * Listener for when user has selected a start date.
     */
    private final DatePickerDialog.OnDateSetListener dateStartSelectedListener =
        new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(final DatePickerDialog parDatePickerDialog,
                                  final int year, final int month, final int day) {
                mStartDate = new GregorianCalendar(year, month, day);
                mStartDateTV.setText(
                        DateFormat.getDateInstance(DateFormat.SHORT).format(mStartDate.getTime()));
        }
    };


    /**
     * Listener for when user has selected a end date.
     */
    private final DatePickerDialog.OnDateSetListener dateEndSelectedListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(final DatePickerDialog parDatePickerDialog,
                                      final int year, final int month, final int day) {
                    mEndDate = new GregorianCalendar(year, month, day);
                    mEndDateTV.setText(
                            DateFormat.getDateInstance(DateFormat.SHORT).format(mEndDate.getTime()));
                }
            };

    // *********************** FIELDS ***********************************************************************

    /** For communicating with hosting activity. */
    private INewTripFragmentActivity mHostingActivity;

    /** Root view for easy findViewById use.*/
    private View mRootView;

    /** Hosting activity interface. */
    private INewTripFragmentActivity mIHostingActivity;

    /** Calendar to retrieve current date. */
    private final Calendar mCalendar = Calendar.getInstance();

    /** start date dialog picker. */
    private final DatePickerDialog dateStartPickerDialog =
            DatePickerDialog.newInstance(dateStartSelectedListener,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH), DO_NOT_VIBRATE);

    /** end date dialog picker. */
    private final DatePickerDialog dateEndPickerDialog =
            DatePickerDialog.newInstance(dateEndSelectedListener,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH), DO_NOT_VIBRATE);

    /** Text view for input of "trip start date". */
    private TextView mStartDateTV;

    /** Text view for input of "trip end date". */
    private TextView mEndDateTV;

    /** EditText for input of "free notes on trip". */
    private EditText mNoteTV;

    /** EditText for input of "trip name". */
    private EditText mNameTV;

    /** Button to open dialog to pick a start date. */
    private AppCompatImageButton mStartDateButton;

    /** Button to open dialog to pick a end date. */
    private AppCompatImageButton mEndDateButton;

    /** Button to save and close. */
    private Button mSubmitButton;

    /** Value provided when instantiating this fragment, unique identifier of trip. */
    @SuppressWarnings("FieldCanBeLocal")
    private UUID mTripId;

    /** The saving module to retrieve and update data (trips).*/
    @SuppressWarnings("FieldCanBeLocal")
    private ISavingModule mSavingModule;

    /** Trip object to be displayed and added item. */
    private Trip mTrip;

    /** Google Analytics tracker. */
    private IAnalytic mAnalytic;

    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     * @param parTripId identifier of trip to be displayed
     * @return a NewTripFragment called with accurate arguments
     */
    public static NewTripFragment newInstance(final UUID parTripId) {
        NewTripFragment f = new NewTripFragment();
        if (parTripId != null) {
            Bundle b = new Bundle();
            b.putCharSequence(BUNDLE_PAR_TRIP_ID, parTripId.toString());
            f.setArguments(b);
        }
        return f;
    }

    /**
     * Empty constructor.
     */
    public NewTripFragment() {
    }


    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSavingModule = ((PackListApp) getActivity().getApplication()).getSavingModule();
        mIHostingActivity = (INewTripFragmentActivity) getActivity();
        mAnalytic = ((PackListApp) getActivity().getApplication()).getTracker();
        mAnalytic.sendScreenDisplayedReportToTracker(TAG);

        Bundle args = getArguments();
        mTripId = null;
        if (args != null) {
            mTripId = UUID.fromString(args.getString(BUNDLE_PAR_TRIP_ID, ""));
            if (mTripId != null) {
                mTrip = mSavingModule.loadSavedTrip(mTripId);
            } else {
                mTrip = new Trip();
            }
        } else {
            mTrip = new Trip();
        }
    }

    @DebugLog
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_new_trip, container, false);

        // Magic of binding
        // Do not use this syntax, it will overwrite activity (we are in a fragment)
        //mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_trip_detail);
        FragmentNewTripBinding mBinding = DataBindingUtil.bind(mRootView);
        mBinding.setTrip(mTrip);
        mBinding.setTripFormatter(new TripFormatter(getContext()));
        mBinding.executePendingBindings();

        mStartDate = mTrip.getStartDate();
        mEndDate = mTrip.getEndDate();

        return mRootView;
    }

    @DebugLog
    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHostingActivity = (INewTripFragmentActivity) getActivity();

        // Getting views
        mNameTV = (EditText) mRootView.findViewById(R.id.new_trip__name__edit);
        mStartDateTV = (TextView) mRootView.findViewById(R.id.new_trip__start_date__edit);
        mStartDateButton = (AppCompatImageButton) mRootView.findViewById(R.id.new_trip__start_date__button);
        mEndDateButton = (AppCompatImageButton) mRootView.findViewById(R.id.new_trip__end_date__button);
        mEndDateTV = (TextView) mRootView.findViewById(R.id.new_trip__end_date__edit);
        mNoteTV = (EditText) mRootView.findViewById(R.id.new_trip__note__edit);
        mSubmitButton = (Button) mRootView.findViewById(R.id.new_trip__submit__button);


        // Adding listeners
        addListenerOnStartDateTextView();
        addListenerOnStartDateButton();
        addListenerOnEndDateTextView();
        addListenerOnEndDateButton();
        addListenerOnSubmitButton();

        disableSubmitButtonIfEmptyText();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mAnalytic.sendScreenPausedReportToTracker(getActivity(), TAG);
    }

    @DebugLog
    @Override
    public final void onResume() {
        super.onResume();
        mAnalytic.sendScreenResumedReportToTracker(getActivity(), TAG);
        mIHostingActivity.showFABIfAccurate(false);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
        mIHostingActivity.showFABIfAccurate(true);
    }

    /**
     * Add a listener on "submit" button.
     */
    private void addListenerOnSubmitButton() {
        mSubmitButton.setOnClickListener(mSubmitListener);
    }

    /**
     * Add a listener on "trip start date" text field.
     */
    private void addListenerOnStartDateButton() {
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dateStartPickerDialog.show(getFragmentManager(), DATE_PICKER_START_TAG);
            }
        });
    }

    /**
     * Add a listener on "trip end date" text field.
     */
    private void addListenerOnEndDateButton() {
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dateEndPickerDialog.show(getFragmentManager(), DATE_PICKER_END_TAG);
            }
        });
    }

    /**
     * Add a listener on "trip start date" text field.
     */
    private void addListenerOnStartDateTextView() {
        mStartDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dateStartPickerDialog.show(getFragmentManager(), DATE_PICKER_START_TAG);
            }
        });
    }

    /**
     * Add a listener on "trip end date" text field.
     */
    private void addListenerOnEndDateTextView() {
        mEndDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dateEndPickerDialog.show(getFragmentManager(), DATE_PICKER_END_TAG);
            }
        });
    }

    /**
     * Disable the "Add item" button if item text is empty.
     */
    private void disableSubmitButtonIfEmptyText() {

        mSubmitButton.setEnabled(mNameTV.length() > 0);

        mNameTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s,
                                          final int start,
                                          final int count,
                                          final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s,
                                      final int start,
                                      final int before,
                                      final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                mSubmitButton.setEnabled(s.length() > 0);
            }
        });
    }

    // *********************** PRIVATE METHODS **************************************************************


}
