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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import android.util.Log;

import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.ScoredItem;
import com.nbossard.packlist.model.TripItem;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import hugo.weaving.DebugLog;


/**
 * Main activity for robotium tests.
 */
public class MainActivityForTest
        extends AppCompatActivity
        implements
        ITripDetailFragmentActivity,
        ITripListFragmentActivity,
        INewTripFragmentActivity,
        IMassImportFragmentActivity {

// *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = MainActivity.class.getName();


// *********************** FIELDS ***************************************************************************

    /** The Floating Action Button. */
    private FloatingActionButton mFab;

    /** Saving module to retrieve Trip. */
    private ISavingModule mSavingModule;

// *********************** METHODS **************************************************************************

    @DebugLog
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = findViewById(R.id.mainact__fab);

        // Handle deep-app indexing
        onNewIntent(getIntent());
    }

    @DebugLog
    @Override
    protected final void onStart() {
        super.onStart();

        /* The saving module to retrieve and update data (trips).*/
        mSavingModule = ((PackListApp) getApplication()).getSavingModule();

        openMainActivityFragment();
    }

    /**
     * Opens the provided fragment in a dialog.
     *
     * @param parDialogStandardFragment fragment to be opened
     */
    @DebugLog
    private void openDialogFragment(final DialogFragment parDialogStandardFragment) {
        if (parDialogStandardFragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment prev = fm.findFragmentByTag("changelog_dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            parDialogStandardFragment.show(ft, "changelog_dialog");
        }
    }

    @DebugLog
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @DebugLog
    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action__whatsnew) {
            openDialogFragment(new ChangeLogDialog());
        } else  if (id == R.id.action__about) {
            openAboutActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    /** Open {@link AboutActivity} on top of this activity. */
    @DebugLog
    private void openAboutActivity() {
        Intent view = new Intent(this, AboutActivity.class);
        view.setAction(Intent.ACTION_VIEW);
        startActivity(view);
    }

    /**
     * For deep-app indexing.
     * @param intent sic
     */
    @DebugLog
    protected final void onNewIntent(final Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String tripId = data.substring(data.lastIndexOf("/") + 1);
            Trip loadedTrip = mSavingModule.loadSavedTrip(UUID.fromString(tripId));
            openTripDetailFragment(loadedTrip);
        }
    }

// ----------- implementing interface IMainActivity -------------------

    @Override
    @DebugLog
    public final void saveTrip(final Trip parTrip) {
       Log.d(TAG, "saveTrip() faked");
    }

    /**
     * Handle user click on one line and open a new fragment allowing him to see trip
     * Characteristics.
     * @param parTrip trip object to be displayed
     */
    @DebugLog
    @Override
    public final TripDetailFragment openTripDetailFragment(final Trip parTrip) {

        // Create fragment and give it an argument specifying the article it should show
        TripDetailFragment newFragment =  TripDetailFragment.newInstance(parTrip);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();

        return newFragment;
    }

    @Override
    public void openNewTripFragment(final UUID parTripId) {
        Log.d(TAG, "openNewTripFragment(...) faked");
    }

    @Override
    public void openItemDetailFragment(final TripItem parItem) {
        Log.d(TAG, "openItemDetailFragment(...) faked");
    }

    @Override
    public final void showFABIfAccurate(final boolean parShow) {
        if (parShow) {
            mFab.show();
        } else {
            mFab.hide();
        }
    }

    @Override
    public void updateTitleBar(String parNewTitleInTitleBar) {

    }
    // ----------- end of implementing interface IMainActivity ------------

// *********************** PRIVATE METHODS ******************************************************************

    /**
     * Open a new fragment allowing him to view trip list.
     */
    @DebugLog
    private void openMainActivityFragment() {

        // Create fragment and give it an argument specifying the article it should show
        TripListFragment newFragment = new TripListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        // NO add to backstack, this is lowest level fragment

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.show();
        mFab.setOnClickListener(view -> openNewTripFragment());
    }

    /**
     * Handle user click on "add a trip" button and open a new fragment allowing him to input trip
     * Characteristics.
     */
    @SuppressWarnings("WeakerAccess")
    @DebugLog
    @VisibleForTesting
    protected void openNewTripFragment() {

        // Create fragment and give it an argument specifying the article it should show
        NewTripFragment newFragment = new NewTripFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();

    }

    // ----------- implementing interface ITripDetailFragmentActivity -------------------

    public void openMassImportFragment(Trip parTrip) {

        // Create fragment and give it an argument specifying the article it should show
        MassImportFragment newFragment = MassImportFragment.newInstance(parTrip);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();
    }

    @Override
    public Set<Item> getSetOfItems() {
        return new HashSet<>();
    }

    @Override
    public List<ScoredItem> getProbableItemsList() {
        return null;
    }
//
}
