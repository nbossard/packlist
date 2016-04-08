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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.Trip;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.MassImportFragment {
        +{static} newInstance(Trip)
    }

    com.nbossard.packlist.gui.MassImportFragment ..> com.nbossard.packlist.gui.IMassImportFragmentActivity

@enduml
 */

/**
 * Mass item import fragment.
 *
 * @author Created by naub7473 on 19/01/2016.
 */
public class MassImportFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = MassImportFragment.class.getName();

    /** Key identifier for serialising trip in bundle. */
    private static final String BUNDLE_PAR_TRIP = "bundle_par_trip";

    // *********************** FIELDS ***********************************************************************

    /** For communicating with hosting activity. */
    private IMassImportFragmentActivity mIHostingActivity;

    /** Root view for easy findViewById use.*/
    private View mRootView;

    /** Button to launch mass import. */
    private Button mMassImportButton;

    /** Text edit area to input text. */
    private EditText mItemsEditText;

    /** Trip onto which mass import items. */
    private Trip mTrip;

    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for click on mass import button.
     */
    @DebugLog
    private void onClickMassImport() {

        enableGUI(false);

        String textToImport = mItemsEditText.getText().toString();
        String[] names = textToImport.split("\n");
        for (String name : names) {
            Item newItem = new Item(mTrip, name);
            mTrip.addItem(newItem);
        }

        mIHostingActivity.saveTrip(mTrip);

        // navigating back
        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
        fragMgr.beginTransaction().remove(MassImportFragment.this).commit();
        fragMgr.popBackStack();
    }

    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MassImportFragment that will be initialized
     * with the given arguments.
     * @param parTrip trip to be added items
     * @return fragment to be displayed
     */
    public static MassImportFragment newInstance(final Trip parTrip) {
        MassImportFragment f = new MassImportFragment();
        if (parTrip != null) {
            Bundle b = new Bundle();
            b.putSerializable(BUNDLE_PAR_TRIP, parTrip);
            f.setArguments(b);
        }
        return f;
    }

    /**
     * Standard empty constructor, required for a fragment.
     */
    public MassImportFragment() {
    }

    /**
     * During creation, if arguments have been supplied to the fragment
     * then parse those out.
     */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIHostingActivity = (IMassImportFragmentActivity) getActivity();

        Bundle args = getArguments();
        mTrip = null;
        if (args != null) {
            mTrip = (Trip) args.getSerializable(BUNDLE_PAR_TRIP);
        } else {
            Log.e(TAG, "onCreate() : This should never occur");
        }
    }

    @DebugLog
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.mass_import_fragment, container, false);

        return mRootView;
    }

    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting views
        mItemsEditText = (EditText) mRootView.findViewById(R.id.mass_import__items__edit);
        mMassImportButton = (Button) mRootView.findViewById(R.id.mass_import__import__button);
        mMassImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickMassImport();
            }
        });
    }

    @DebugLog
    @Override
    public final void onResume() {
        super.onResume();
        mIHostingActivity.showFABIfAccurate(false);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
        mIHostingActivity.showFABIfAccurate(true);
    }

    /**
     * Enable to disable GUI, to prevent user interactions when processing.
     *
     * @param parEnable false to disable, true to enable
     */
    private void enableGUI(final boolean parEnable) {
        mMassImportButton.setEnabled(parEnable);
    }
}
