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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.analytics.IAnalytic;
import com.nbossard.packlist.databinding.FragmentItemDetailBinding;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Item;

import hugo.weaving.DebugLog;

import static java.lang.Integer.parseInt;

/*
 * @startuml
 * class com.nbossard.packlist.gui.ItemDetailFragment {
 *  +setItem(Item)
 * }
 *
 * com.nbossard.packlist.gui.ItemDetailFragment --> com.nbossard.packlist.gui.IItemDetailFragmentActivity
 * @enduml
 */

/**
 * Class for displaying details about an {@link Item}.
 * @author Created by nbossard on 17/03/16.
 */
public class ItemDetailFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /** Log tag. */
    private static final String TAG = ItemDetailFragment.class.getName();

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

            // update item with values
            mItem.setName(mNameEdit.getText().toString().trim());
            mItem.setWeight(parseInt(mWeightEdit.getText().toString().trim()));
            mItem.setCategory(mCategoryEdit.getText().toString().trim());

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

    /**
     * Edit text for item category.
     */
    private AutoCompleteTextView mCategoryEdit;

    /** Button to save and close. */
    private Button mSubmitButton;

    /** Analytics tracker. */
    private IAnalytic mAnalytic;

    // *********************** METHODS **********************************************************************

    /**
     * Method for creating a new DetailFragment and insuring that right bundle is provided.
     * @param parItem item to be displayed
     * @return a new DetailFragment.
     */
    @DebugLog
    public static ItemDetailFragment newInstance(final Item parItem) {
        ItemDetailFragment f = new ItemDetailFragment();
        if (parItem != null) {
            Bundle b = new Bundle();
            b.putSerializable(BUNDLE_PAR_ITEM, parItem);
            f.setArguments(b);
        }
        return f;
    }

    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAnalytic = ((PackListApp) getActivity().getApplication()).getTracker();
        mAnalytic.sendScreenDisplayedReportToTracker(TAG);

        Bundle args = getArguments();
        if (args != null) {
            mItem = (Item) args.getSerializable(BUNDLE_PAR_ITEM);
        }
    }

    @Nullable
    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

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
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mIHostingActivity = (IItemDetailFragmentActivity) getActivity();


        // Getting views
        mNameEdit = (EditText) mRootView.findViewById(R.id.item_detail__name__edit);
        mWeightEdit = (EditText) mRootView.findViewById(R.id.item_detail__weight__edit);
        mSubmitButton = (Button) mRootView.findViewById(R.id.item_detail__submit__button);
        mCategoryEdit = (AutoCompleteTextView) mRootView.findViewById(R.id.item_detail__category__edit);

        // pre-filling list of already existing categories that may match
        String[] alreadyExistCat = mIHostingActivity.getListOfCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, alreadyExistCat);
        mCategoryEdit.setAdapter(adapter);

        // Adding listeners
        addListenerOnSubmitButton();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mAnalytic.sendScreenPausedReportToTracker(getActivity(), TAG);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        mAnalytic.sendScreenResumedReportToTracker(getActivity(), TAG);
    }

    /**
     * Add a listener on "submit" button.
     */
    private void addListenerOnSubmitButton() {
        mSubmitButton.setOnClickListener(mSubmitListener);
    }

    /**
     * Set item to be displayed by this view.
     *
     * @param parRetrievedItem item to be displayed
     */
    @DebugLog
    public final void setItem(final Item parRetrievedItem) {
        mItem = parRetrievedItem;
    }

}
