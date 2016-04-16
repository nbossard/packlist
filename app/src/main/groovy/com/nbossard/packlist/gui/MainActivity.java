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

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;
import com.nbossard.packlist.process.saving.ITripChangeListener;

import java.util.UUID;

import hugo.weaving.DebugLog;

//CHECKSTYLE:OFF: LineLength
/*
@startuml
    class com.nbossard.packlist.gui.MainActivity {
    }

    com.nbossard.packlist.gui.ITripListFragmentActivity <|.. com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.gui.INewTripFragmentActivity <|.. com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.gui.ITripDetailFragmentActivity <|.. com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.gui.IMassImportFragmentActivity <|.. com.nbossard.packlist.gui.MainActivity

    com.nbossard.packlist.gui.NewTripFragment <.. com.nbossard.packlist.gui.MainActivity : launch in\n container
    com.nbossard.packlist.gui.TripListFragment <.. com.nbossard.packlist.gui.MainActivity : launch in\n container
    com.nbossard.packlist.gui.AboutActivity <..  com.nbossard.packlist.gui.MainActivity : start through intent
    com.nbossard.packlist.gui.DialogStandardFrag  <..  com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.gui.MassImportFragment <..  com.nbossard.packlist.gui.MainActivity : launch in\n container

    ' Moved to main file
    ' ISavingModule <-- com.nbossard.packlist.gui.MainActivity
    ' com.nbossard.packlist.process.saving.ITripChangeListener <|.. com.nbossard.packlist.gui.MainActivity
@enduml
 */
//CHECKSTYLE:ON: LineLength

/**
 * Main activity, supports most fragments.
 */
public class MainActivity
        extends AppCompatActivity
        implements
        ITripListFragmentActivity,
        INewTripFragmentActivity,
        ITripDetailFragmentActivity,
        IItemDetailFragmentActivity,
        IMassImportFragmentActivity,
        ITripChangeListener {

// *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = MainActivity.class.getName();

// *********************** FIELDS ***************************************************************************

    /** The saving module to retrieve and update data (trips).*/
    private ISavingModule mSavingModule;

    /** The Floating Action Button. */
    private FloatingActionButton mFab;

    /** The fragment MainActivity instantiated. */
    private TripListFragment mTripListFragment;

    /** The fragment trip detail if already opened. */
    private TripDetailFragment mTripDetailFragment;

// *********************** METHODS **************************************************************************

    @DebugLog
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.mainact__fab);

        // Handle deep-app indexing
        onNewIntent(getIntent());
    }


    @DebugLog
    @Override
    protected final void onStart() {
        super.onStart();
        mSavingModule = ((PackListApp) getApplication()).getSavingModule();
        mSavingModule.addListener(this);

        mTripListFragment = openMainActivityFragment();
    }

    @Override
    public final void onConfigurationChanged(final Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged() called with: " + "newConfig = [" + newConfig + "]");
        super.onConfigurationChanged(newConfig);
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
            openDialogFragment(new DialogStandardFrag());
        } else  if (id == R.id.action__about) {
            openAboutActivity();
        } else if (id == R.id.action__send_logs) {
            PackListApp.sendUserDebugReport();
        }

        return super.onOptionsItemSelected(item);
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

// ----------- implementing interface ITripChangeListener -------------------


    @Override
    public final void onTripChange() {
        mTripListFragment.populateList();

        //update detail trip fragment
        if (mTripDetailFragment != null) {
            UUID curTripUUID = mTripDetailFragment.getCurrentTrip().getUUID();
            Trip loadedTrip = mSavingModule.loadSavedTrip(curTripUUID);
            mTripDetailFragment.displayTrip(loadedTrip);
        }
    }

    // ----------- implementing interface IItemDetailFragmentActivity -------------------

    @Override
    public final void updateItem(final Item parItem) {
        boolean resUpdate = mSavingModule.updateItem(parItem);
        if (resUpdate) {
            Log.d(TAG, "updateItem(...) update of item succeded");
        } else {
            Toast.makeText(this, R.string.toast_update_failed_incompatible_format, Toast.LENGTH_LONG).show();
        }
    }

    // ----------- implementing interface ITripDetailFragmentActivity -------------------

    @Override
    public final void openMassImportFragment(final Trip parTrip) {

        // Create fragment and give it an argument specifying the article it should show
        MassImportFragment newFragment = MassImportFragment.newInstance(parTrip);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(getTargetFragment(), newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();
    }

    @Override
    @DebugLog
    public final void saveTrip(final Trip parTrip) {
        mSavingModule.addOrUpdateTrip(parTrip);

        //update fragments displaying trips
        mTripListFragment.populateList();
        if (mTripDetailFragment != null) {
            mTripDetailFragment.displayTrip(parTrip);
        }
    }

    // ----------- implementing interface IMainActivity -------------------


    /**
     * Handle user click on one line and open a new fragment allowing him to see trip
     * Characteristics.
     * @param parTrip unique
     */
    @DebugLog
    @Override
    public final TripDetailFragment openTripDetailFragment(final Trip parTrip) {

        // Create fragment and give it an argument specifying the article it should show
        mTripDetailFragment =  TripDetailFragment.newInstance(parTrip);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(getTargetFragment(), mTripDetailFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();

        return mTripDetailFragment;
    }


    @Override
    public final void showFABIfAccurate(final boolean parShow) {
        Log.d(TAG, "showFABIfAccurate() called with: " + "parShow = [" + parShow + "]");

        FragmentManager fragMgr = getSupportFragmentManager();
        if (parShow && fragMgr.getBackStackEntryCount() == 0) {
            mFab.show();
        } else {
            mFab.hide();
        }
    }

    /**
     * Handle user click on "add a trip" button and open a new fragment allowing him to input trip
     * Characteristics.
     * @param parTripUUID unique identifier of Trip
     */
    @DebugLog
    @Override
    public final void openNewTripFragment(@Nullable final UUID parTripUUID) {

        // Create fragment and give it an argument specifying the article it should show
        NewTripFragment newFragment = NewTripFragment.newInstance(parTripUUID);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(getTargetFragment(), newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();
    }

    @Override
    public final void openItemDetailFragment(final Item parItem) {

        // Create fragment and give it an argument specifying the article it should show
        ItemDetailFragment newFragment = ItemDetailFragment.newInstance(parItem);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(getTargetFragment(), newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.hide();
    }

    // ----------- end of implementing interface IMainActivity ------------

// *********************** PRIVATE METHODS ******************************************************************

    /**
     * Get the target fragment for new fragment to be opened, different on tablet.
     * @return (right fragment on tablet), same fragment on phone.
     */
    private int getTargetFragment() {
        Resources res = getResources();
        int resTarget;
        if (res.getBoolean(R.bool.tablet_layout)) {
            resTarget = R.id.mainactcont__right_fragment;
        } else {
            resTarget =  R.id.mainactcont__fragment;
        }
        return resTarget;
    }

    /** Open {@link AboutActivity} on top of this activity. */
    @DebugLog
    private void openAboutActivity() {
        Intent view = new Intent(this, AboutActivity.class);
        view.setAction(Intent.ACTION_VIEW);
        startActivity(view);
    }

    /**
     * Open a new fragment allowing him to view trip list.
     * @return the newly created and displayed TripListFragment
     */
    @DebugLog
    private TripListFragment openMainActivityFragment() {

        // Create fragment and give it an argument specifying the article it should show
        TripListFragment newFragment = new TripListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        // NO add to back stack, this is lowest level fragment

        // Commit the transaction
        transaction.commit();

        // updating FAB action
        mFab.show();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                openNewTripFragment(null);
            }
        });
        return newFragment;
    }
//
}
