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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nbossard.packlist.R;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.MassImportFragment {
    }
@enduml
 */

/**
 * Mass import dialog.
 *
 * @author Created by naub7473 on 19/01/2016.
 */
public class MassImportFragment extends Fragment {

    // *********************** FIELDS ***********************************************************************

    /** For communicating with hosting activity. */
    private IMassImportFragmentActivity mIHostingActivity;

    /** Root view for easy findViewById use.*/
    private View mRootView;

    /** Button to launch mass import. */
    private Button mMassImportButton;

    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for click on mass import button.
     */
    private void onClickMassImport() {
        //TODO
    }

    // *********************** METHODS **********************************************************************


    public static MassImportFragment newInstance() {
        return new MassImportFragment();
    }

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
    }

    @DebugLog
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.mass_import_fragment, container, false);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMassImportButton = (Button) mRootView.findViewById(R.id.trip_detail__new_item__button);
        mMassImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickMassImport();
            }
        });
    }
}
