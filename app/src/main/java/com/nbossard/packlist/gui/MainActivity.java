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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.saving.ISavingModule;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.MainActivity {
    }

    com.nbossard.packlist.gui.IMainActivity <|-- com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.gui.NewTripFragment <.. com.nbossard.packlist.gui.MainActivity : launch in\ncontainer
    com.nbossard.packlist.gui.MainActivityFragment <.. com.nbossard.packlist.gui.MainActivity : launch in\ncontainer
    com.nbossard.packlist.gui.AboutActivity <..  com.nbossard.packlist.gui.MainActivity : start through intent

    ' Moved to main file
    ' com.nbossard.packlist.process.saving.ISavingModule <-- com.nbossard.packlist.gui.MainActivity
@enduml
 */

public class MainActivity extends AppCompatActivity implements IMainActivity{

// *********************** FIELDS ***************************************************************************

    /** The saving module to retrieve and update data (trips).*/
    private ISavingModule mSavingModule;

// *********************** METHODS **************************************************************************

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mainact__fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewTripFragment();            }
        });

        // Handle deep-app indexing
        onNewIntent(getIntent());

    }

    @DebugLog
    @Override
    protected void onStart() {
        super.onStart();
        mSavingModule = ((PackListApp) getApplication()).getSavingModule();

        openMainActivityFragment();
    }

    @DebugLog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
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
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String tripId = data.substring(data.lastIndexOf("/") + 1);
            openTripDetailFragment(tripId);
        }
    }

// ----------- implementing interface IMainActivity -------------------

    @Override
    @DebugLog
    public void createNewTrip(String parName, String parStartDate, String parEndDate, String parNote) {
        Trip tmpTrip = new Trip(parName, parStartDate, parEndDate, parNote);
        mSavingModule.addNewTrip(tmpTrip);
    }

// ----------- end of implementing interface IMainActivity ------------

// *********************** PRIVATE METHODS ******************************************************************


    @DebugLog
    private void openTripDetailFragment(String parTripId) {

        // Create fragment and give it an argument specifying the article it should show
        TripDetailFragment newFragment =  TripDetailFragment.newInstance(parTripId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @DebugLog
    private void openMainActivityFragment() {

        // Create fragment and give it an argument specifying the article it should show
        MainActivityFragment newFragment = new MainActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    /**
     * Handle user click on "add a trip" button and open a new fragment allowing him to input trip
     * Characteristics.
     */
    @DebugLog
    private void openNewTripFragment() {

        // Create fragment and give it an argument specifying the article it should show
        NewTripFragment newFragment = new NewTripFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainactcont__fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
//
}
