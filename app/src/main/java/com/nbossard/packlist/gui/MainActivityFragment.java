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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;

import java.util.List;

/**
 * A placeholder fragment containing a simple list view.
 */
public class MainActivityFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /** Log tag. */
    private static final String TAG = MainActivityFragment.class.getName();

    // *********************** FIELDS ***********************************************************************

    /** The saving module to retrieve and update data (trips).*/
    private ISavingModule mSavingModule;
    /** The root view, will be used to findViewById. */
    private View mRootView;
    /** The trip list view. */
    private ListView mTripListView;
    /** The object to support Contextual Action Bar (CAB). */
    private ActionMode mActionMode;

    // *********************** METHODS **********************************************************************

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavingModule = ((PackListApp) getActivity().getApplication()).getSavingModule();
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        return mRootView;
    }

    @Override
    public final void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateList();
    }

    @Override
    public final void onResume() {
        super.onResume();
        populateList();
    }
    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Populate list with data in {@link ISavingModule}.
     */
    private void populateList() {
        mTripListView = (ListView) mRootView.findViewById(R.id.main__trip_list);
        List<Trip> tripList;

        tripList = mSavingModule.loadSavedTrips();

        TripAdapter tripAdapter = new TripAdapter(tripList, this.getActivity());
        mTripListView.setEmptyView(mRootView.findViewById(R.id.main__trip_list_empty));
        mTripListView.setAdapter(tripAdapter);
        mTripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip clickedTrip = (Trip) mTripListView.getItemAtPosition(position);
                ((IMainActivity) getActivity()).openTripDetailFragment(clickedTrip.getUUID().toString());
            }

        });
        mTripListView.setOnItemLongClickListener(tripListLongClickListener());
        mTripListView.invalidate();
    }

    @NonNull
    private AdapterView.OnItemLongClickListener tripListLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                mActionMode = getActivity().startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
                        mode.setTitle("Selected");

                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.menu_main_cab, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                int position = (int) mActionMode.getTag();
                                deleteTripClicked(position);
                                return true;
                            default:
                                doneClicked();
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(final ActionMode mode) {
                        doneClicked();
                    }
                });
                mActionMode.setTag(pos);
                arg1.setSelected(true);
                return true;
            }
        };
    }

    /** Effectively delete selected trip then refresh the list.
     * @param parPosition position in list of trip to be deleted
     */
    private void deleteTripClicked(final int parPosition) {
        Trip selectedTrip = (Trip) mTripListView.getItemAtPosition(parPosition);
        mSavingModule.deleteTrip(selectedTrip.getUUID());
        mActionMode.finish();
        populateList();
    }

    private void doneClicked() {
    }
}
