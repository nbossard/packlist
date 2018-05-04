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

package com.nbossard.packlist.gui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.nbossard.packlist.R

//CHECKSTYLE:OFF: LineLength
/*
@startuml
    class com.nbossard.packlist.gui.SettingsActivity {
    }
    com.nbossard.packlist.gui.SettingsActivity <.. com.nbossard.packlist.gui.MainActivity : start through intent
    com.nbossard.packlist.gui.SettingsFragment <..  com.nbossard.packlist.gui.SettingsActivity : launch in container
@enduml
 */
//CHECKSTYLE:ON: LineLength

/**
 * An activity acting as a container for SettingsFragment.
 */
class SettingsActivity : AppCompatActivity() {

    // *********************** METHODS **************************************************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Display the fragment as the main content.
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()

    }
}
