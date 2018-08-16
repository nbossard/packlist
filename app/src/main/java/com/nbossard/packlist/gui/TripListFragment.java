/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2017 Nicolas Bossard and other contributors.
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
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISaving;

import java.util.List;

import javax.inject.Inject;

/*
@startuml
    class com.nbossard.packlist.gui.TripListFragment {
    }

    com.nbossard.packlist.gui.TripListFragment --> com.nbossard.packlist.gui.ITripListFragmentActivity
    com.nbossard.packlist.gui.TripAdapter <.. com.nbossard.packlist.gui.TripListFragment

@enduml
 */

/**
 * A placeholder fragment containing a simple list view of {@link Trip}.
 */
public class TripListFragment extends Fragment {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    @SuppressWarnings("unused")
    private static final String TAG = TripListFragment.class.getName();

    // *********************** FIELDS ***********************************************************************

    /**
     * The trip list view.
     */
    private ListView mTripListView;
    /**
     * The object to support Contextual Action Bar (CAB).
     */
    private ActionMode mActionMode;
    /**
     * Hosting activity interface.
     */
    private ITripListFragmentActivity mIHostingActivity;

    // *********************** iINJECTED FIELDS *************************************************************

    /** The saving module to retrieve and update data (trips).*/
    @Inject
    protected ISaving mSavingModule;

    // *********************** LISTENERS ********************************************************************

    /**
     * Listener for click on one item of the list.
     * Opens a new fragment displaying detail on trip.
     */
    private final AdapterView.OnItemClickListener mClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(final AdapterView<?> parent,
                                final View view,
                                final int position,
                                final long id) {
            Trip clickedTrip = (Trip) mTripListView.getItemAtPosition(position);
            mIHostingActivity.openTripDetailFragment(clickedTrip);
        }
    };

    /**
     * Listener for long click on one item of the list.
     * Opens the contextual action bar.
     */
    @NonNull
    private final AdapterView.OnItemLongClickListener mLongClickListener
            = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1,
                                       final int pos, final long id) {

            // keep item selected
            mTripListView.setItemChecked(pos, true);

            // starting action mode
            mActionMode = getActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
                    mode.setTitle(getString(R.string.trip_detail__selected));

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
                    int position = (int) mActionMode.getTag();

                    switch (item.getItemId()) {
                        case R.id.action_clone:
                            cloneTripClicked(position);
                            return true;
                        case R.id.action_delete:
                            deleteTripClicked(position);
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(final ActionMode mode) {
                }
            });

            // mActionMode can be null if canceled
            if (mActionMode != null) {
                mActionMode.setTag(pos);
                arg1.setSelected(true);
            }

            return true;
        }

    };

    // *********************** METHODS **********************************************************************

    /**
     * Empty constructor.
     */
    public TripListFragment() {
    }

    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Injection boiler plate code
        ((PackListApp) getActivity().getApplication())
                .getSavingComponent()
                .inject(TripListFragment.this);

        mIHostingActivity = (ITripListFragmentActivity) getActivity();

    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                                   final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public final void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // custom menu for this fragment
        setHasOptionsMenu(true);

        populateList();
    }

    @Override
    public final void onResume() {
        super.onResume();
        populateList();
        mIHostingActivity.showFABIfAccurate(true);
    }


    @Override
    public final void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_trip_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_trip__import_txt:
                mIHostingActivity.openMassImportFragment(null);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Populate list with data in {@link ISaving}.
     */
    @SuppressWarnings("WeakerAccess")
    public final void populateList() {
        if (getView() != null)
        {
            Log.d(TAG, "populateList() : getView() not null, updating");

            mTripListView = getView().findViewById(R.id.main__trip_list);
            List<Trip> tripList;

            tripList = mSavingModule.loadSavedTrips();

            TripAdapter tripAdapter = new TripAdapter(tripList, this.getActivity());
            mTripListView.setEmptyView(getView().findViewById(R.id.main__trip_list_empty));
            mTripListView.setAdapter(tripAdapter);
            mTripListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            mTripListView.setOnItemClickListener(mClickListener);
            mTripListView.setOnItemLongClickListener(mLongClickListener);
            mTripListView.invalidate();
        } else {
            Log.d(TAG, "populateList() : getView() returned null, probably the fragment is not attached");
        }
    }


    /**
     * Ask user to confirm deletion of selected trip then call {@link #effectivelyDeleteTrip(int)}.
     *
     * @param parPosition position in list of trip to be deleted
     */
    private void deleteTripClicked(final int parPosition) {
        // make user confirm, as this is not a good idea to delete old trip :
        // they serve as a database for new trips
        TripDeletionConfirmDialogFragment dialogFragment = new TripDeletionConfirmDialogFragment();
        dialogFragment.setConfirmedListener((parDialogInterface, parI) -> effectivelyDeleteTrip(parPosition));
        dialogFragment.show(getActivity().getSupportFragmentManager(), DialogFragment.class.getSimpleName());
    }

    /**
     * Effectively delete selected trip then refresh the list.
     *
     * @param parPosition position in list of trip to be deleted
     */
    private void effectivelyDeleteTrip(final int parPosition) {
        Trip selectedTrip = (Trip) mTripListView.getItemAtPosition(parPosition);
        mSavingModule.deleteTrip(selectedTrip.getUUID());
        mActionMode.finish();
        populateList();
    }

    /**
     * Effectively clone selected trip then refresh the list.
     *
     * @param parPosition position in list of trip to be deleted
     */
    private void cloneTripClicked(final int parPosition) {
        Trip selectedTrip = (Trip) mTripListView.getItemAtPosition(parPosition);
        mSavingModule.cloneTrip(selectedTrip.getUUID());
        mActionMode.finish();
        populateList();
    }

}
