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

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.databinding.FragmentTripDetailBinding;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;

import java.util.UUID;
/*
@startuml
    class com.nbossard.packlist.gui.TripDetailFragment {
    }

@enduml
 */

/**
 * Open a Trip for viewing / editing.
 * @author Created by nbossard on 09/01/16.
 */
public class TripDetailFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /** Bundle mandatory parameter when instantiating this fragment. */
    public static final String BUNDLE_PAR_TRIP_ID = "bundleParTripId";

    // *********************** FIELDS ***********************************************************************

    /** The root view, will be used to findViewById. */
    private View mRootView;

    /** Trip object to be displayed and added item. */
    private Trip mRetrievedTrip;

    private IMainActivity mHostingActivity;

    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     * @param parTrip ttrip to be displayed
     */
    public static TripDetailFragment newInstance(final Trip parTrip) {
        TripDetailFragment f = new TripDetailFragment();
        Bundle b = new Bundle();
        b.putSerializable(BUNDLE_PAR_TRIP_ID, parTrip);
        f.setArguments(b);
        return f;
    }

    /**
     * During creation, if arguments have been supplied to the fragment
     * then parse those out.
     */
    @Override public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mRetrievedTrip = (Trip) args.getSerializable(BUNDLE_PAR_TRIP_ID);
        }

    }

    /**
    * Create the view for this fragment, using the arguments given to it.
    */
    @Override public final View onCreateView(final LayoutInflater inflater,
                                       final ViewGroup container,
                                       final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        // Magic of binding
        // Do not use this syntax, it will overwrite actvity (we are in a fragment)
        //mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_trip_detail);
        FragmentTripDetailBinding mBinding = DataBindingUtil.bind(mRootView);
        mBinding.setTrip(mRetrievedTrip);
        mBinding.executePendingBindings();

        return mRootView;
    }

    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO old style, improve this
        final Button addItemButton = (Button) mRootView.findViewById(R.id.trip_detail__new_item__button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem();
            }
        });
        addItemButton.setEnabled(false);
        disableButtonIfEmptyText(addItemButton);


        final Button editButton = (Button) mRootView.findViewById(R.id.trip_detail__edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEditTrip();
            }
        });


        populateList();
    }

    /**
     * Handle click on "Add item" button.
     * Will add a new item.
     */
    public final void onClickEditTrip() {
        ((IMainActivity) getActivity()).openNewTripFragment(mRetrievedTrip.getUUID());
    }

    /**
     * Handle click on "Add item" button.
     * Will add a new item.
     */
    public final void onClickAddItem() {
        EditText newItem = (EditText) mRootView.findViewById(R.id.trip_detail__new_item__edit);
        String tmpStr = newItem.getText().toString();
        mRetrievedTrip.addItem(tmpStr);
        mHostingActivity.saveTrip(mRetrievedTrip);
        newItem.setText("");
        populateList();
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Disable the "Add item" button if item text is empty.
     * @param parMButton button to be disabled
     */
    private void disableButtonIfEmptyText(final Button parMButton) {
        EditText newItem = (EditText) mRootView.findViewById(R.id.trip_detail__new_item__edit);
        newItem.addTextChangedListener(new TextWatcher() {
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
                parMButton.setEnabled(s.length() > 0);
            }
        });
    }

    /**
     * Populate list with data in {@link ISavingModule}.
     */
    private void populateList() {
        ListView mItemListView = (ListView) mRootView.findViewById(R.id.trip_detail__list);
        mItemListView.setEmptyView(mRootView.findViewById(R.id.trip_detail__list_empty));
        ItemAdapter itemAdapter = new ItemAdapter(mRetrievedTrip.getListItem(), this.getActivity());
        mItemListView.setAdapter(itemAdapter);
        mItemListView.invalidate();
    }

}
