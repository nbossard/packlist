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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nbossard.packlist.databinding.FragmentItemDetailBinding;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Item;

import hugo.weaving.DebugLog;

/*
 * @startuml
 * class com.nbossard.packlist.gui.ItemDetailFragment {
 *  +setItem(Item)
 * }
 * @enduml
 */

/**
 * Class for displaying details about an {@link Item}.
 * @author Created by nbossard on 17/03/16.
 */
public class ItemDetailFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /** Bundle parameter when instantiating this fragment. */
    private static final String BUNDLE_PAR_ITEM = "bundleParItem";

    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for when user clicks on "submit" button.
     */
    private final View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @DebugLog
        @Override
        public void onClick(final View v) {

            // update item
            mItem.setName(mNameEdit.getText().toString());
            mItem.setWeight(Integer.valueOf(mWeightEdit.getText().toString()));

            // asking supporting activity to update item
            mIHostingActivity.updateItem(mItem);

            // navigating back
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            fragMgr.beginTransaction().remove(ItemDetailFragment.this).commit();
            fragMgr.popBackStack();
        }
    };

    // *********************** FIELDS ***********************************************************************

    /** The root view, will be used to findViewById. */
    private View mRootView;


    /** Item object to be displayed and edited. */
    private Item mItem;


    /** Supporting activity, to save trip.*/
    private IItemDetailFragmentActivity mIHostingActivity;

    /** Edit text for item name. */
    private EditText mNameEdit;

    /** Edit text for item weight. */
    private EditText mWeightEdit;

    /** Button to save and close. */
    private Button mSubmitButton;

    // *********************** METHODS **********************************************************************

    public static ItemDetailFragment newInstance(Item parItem) {
        ItemDetailFragment f = new ItemDetailFragment();
        if (parItem != null) {
            Bundle b = new Bundle();
            b.putSerializable(BUNDLE_PAR_ITEM, parItem);
            f.setArguments(b);
        }
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mItem = (Item) args.getSerializable(BUNDLE_PAR_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Magic of binding
        // Do not use this syntax, it will overwrite activity (we are in a fragment)
        //mBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_trip_detail);
        FragmentItemDetailBinding mBinding = DataBindingUtil.bind(mRootView);
        mBinding.setItem(mItem);
        mBinding.executePendingBindings();

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIHostingActivity = (IItemDetailFragmentActivity) getActivity();


        // Getting views
        mNameEdit = (EditText) mRootView.findViewById(R.id.item_detail__name__edit);
        mWeightEdit = (EditText) mRootView.findViewById(R.id.item_detail__weight__edit);
        mSubmitButton = (Button) mRootView.findViewById(R.id.item_detail__submit__button);


        // Adding listeners
        addListenerOnSubmitButton();

    }

    /**
     * Add a listener on "submit" button.
     */
    private void addListenerOnSubmitButton() {
        mSubmitButton.setOnClickListener(mSubmitListener);
    }

    /**
     * Set item to be displayed by this view
     *
     * @param parRetrievedItem item to be displayed
     */
    public void setItem(Item parRetrievedItem) {
        mItem = parRetrievedItem;
    }
}
