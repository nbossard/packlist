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

/**
 * Open a Trip for viewing / editing.
 * @author Created by nbossard on 09/01/16.
 */
public class TripDetailFragment extends Fragment {

    // *********************** FIELDS ***********************************************************************

    /** Bundle mandatory parameter when instantiating this fragment. */
    public static final String BUNDLE_PAR_TRIP_ID = "bundleParTripId";

    /** The root view, will be used to findViewById. */
    private View mRootView;

    /** The saving module to retrieve and update data (trips).*/
    private ISavingModule mSavingModule;
    private FragmentTripDetailBinding mBinding;
    /** Value provided when instantiating this fragment, unique identifier of trp. */
    private UUID mTripId;
    private Trip mRetrievedTrip;

    // *********************** METHODS **********************************************************************

    /**
     * Create a new instance of MyFragment that will be initialized
     * with the given arguments.
     * @param parTripId identifier of trip to be displayed
     */
    public static TripDetailFragment newInstance(final String parTripId) {
        TripDetailFragment f = new TripDetailFragment();
        Bundle b = new Bundle();
        b.putCharSequence(BUNDLE_PAR_TRIP_ID, parTripId);
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
        mTripId = null;
        if (args != null) {
            mTripId = UUID.fromString(args.getString(BUNDLE_PAR_TRIP_ID, ""));
        }

        mSavingModule = ((PackListApp) getActivity().getApplication()).getSavingModule();
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
        mBinding = DataBindingUtil.bind(mRootView);
        mRetrievedTrip = mSavingModule.loadSavedTrip(mTripId);
        mBinding.setTrip(mRetrievedTrip);
        mBinding.executePendingBindings();

        return mRootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO old style, improve this
        Button mButton = (Button) mRootView.findViewById(R.id.trip_detail__new_item__button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem();
            }
        });
        populateList();
    }

    public void onClickAddItem() {
        EditText newItem = (EditText) mRootView.findViewById(R.id.trip_detail__new_item__edit);
        String tmpStr = newItem.getText().toString();
        mRetrievedTrip.addItem(tmpStr);
        // TODO clean this ugly block
        mSavingModule.deleteTrip(mRetrievedTrip.getUUID());
        mSavingModule.addNewTrip(mRetrievedTrip);
        newItem.setText("");
        populateList();
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Populate list with data in {@link ISavingModule}.
     */
    private void populateList() {
        ListView mItemListView = (ListView) mRootView.findViewById(R.id.trip_detail__list);
        ItemAdapter itemAdapter = new ItemAdapter(mRetrievedTrip.getListItem(), this.getActivity());
        mItemListView.setEmptyView(mRootView.findViewById(R.id.main__trip_list_empty));
        mItemListView.setAdapter(itemAdapter);
        mItemListView.invalidate();
    }

}
