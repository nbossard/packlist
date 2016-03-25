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
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nbossard.packlist.BuildConfig;
import com.nbossard.packlist.R;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.AboutActivity {
    }
@enduml
 */

/**
 * About activity.
 * Groovy class.
 *
 * @author nicolas Bossard
 */
class AboutActivity extends AppCompatActivity {

    // *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final def TAG = AboutActivity.class.getName()

    // *********************** METHODS **********************************************************************

    @Override
    @DebugLog
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        def toolbar = (Toolbar) findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        def fab = (FloatingActionButton) findViewById(R.id.about_act__fab);
        fab.onClickListener = {openBrowser()} as View.OnClickListener

        getSupportActionBar()?.setDisplayHomeAsUpEnabled true

        // setting button listener
        def mButtonThirdParty = (Button) findViewById(R.id.help__third_party__button);
        mButtonThirdParty.onClickListener = {openThirdPartyActivity()} as View.OnClickListener

        // updating version number
        def mGeneralInfo = (TextView) findViewById(R.id.help__general_info__label);
        mGeneralInfo.setText(
                String.format(getString(R.string.about__main),
                            BuildConfig.VERSION_NAME,
                            getString(R.string.about__additional__info)))

    }


// *********************** PRIVATE METHODS ******************************************************************

    /**
     * Open the browser with project sources on Github.
     */
    @DebugLog
    private void openBrowser() {
        def url = "https://github.com/nbossard/packlist"
        def i = new Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity i
    }


    /**
     * Open the activity to display third party softwares licences.
     */
    @DebugLog
    private void openThirdPartyActivity()
    {
        def intent = new Intent(this, HelpThirdPartyActivity.class)
        startActivity intent
    }
//

}
