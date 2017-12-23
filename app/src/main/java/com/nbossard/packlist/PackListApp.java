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

package com.nbossard.packlist;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nbossard.packlist.process.saving.ISavingModule;
import com.nbossard.packlist.process.saving.SavingFactory;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.PackListApp {
        + getSavingModule()
    }
@enduml
*/

/**
 * Application level initialisations :
 * <ul>
 *     <li>Singletons</li>
 *     <li>crash reporter (ACRA)</li>
 * </ul>.
 *
 * @author Created by nbossard on 31/12/15.
 */
@ReportsCrashes(formUri = "",
        sendReportsInDevMode = true,
        logcatArguments = { "-t", "100", "-v", "long", "ActivityManager:I", "MyApp:D", "*:S" },
        mailTo = "open.packlist@gmail.com",
        mode = ReportingInteractionMode.DIALOG,
        resDialogIcon = R.mipmap.packlist_icon,
        resDialogTitle = R.string.acra__dialog__title,
        resDialogText = R.string.acra__crash_report_dialog__label,
        customReportContent = {ReportField.USER_COMMENT,
                ReportField.REPORT_ID,
                ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME,
                ReportField.PACKAGE_NAME,
                ReportField.FILE_PATH,
                ReportField.PHONE_MODEL,
                ReportField.ANDROID_VERSION,
                ReportField.BRAND,
                ReportField.PRODUCT,
                ReportField.DISPLAY,
                ReportField.USER_APP_START_DATE,
                ReportField.USER_CRASH_DATE,
                ReportField.STACK_TRACE,
                ReportField.LOGCAT})
public class PackListApp extends Application {

// ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String LOG_TAG = PackListApp.class.getName();

// *********************** FIELDS *************************************************************************

    /** Saving module singleton. */
    private ISavingModule mSavingModule;

    /**
     * Application settings singleton.
     */
    private PacklistSharedPrefs mPreferences;

// *********************** METHODS **************************************************************************

    @Override
    public final void onCreate() {
        Log.d(LOG_TAG, "onCreate(...)  Entering");
        super.onCreate();

        // load saved settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    protected final void attachBaseContext(final Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        // Except if currently debugging
        if (BuildConfig.DEBUG) {
            Log.d(LOG_TAG, "NOT Initialising ACRA as debugger is currently connected");
        } else {
            Log.d(LOG_TAG, "Initialising ACRA");
            ACRA.init(this);
        }
    }

    /**
     * Send a report using ACRA (user action).
     */
    @DebugLog
    public static void sendUserDebugReport() {
        ACRA.getErrorReporter().handleException(new Exception("User report"));
    }

    /** @return saving module singleton. */
    public final ISavingModule getSavingModule() {
        if (mSavingModule == null) {
            mSavingModule = SavingFactory.getNewSavingModule(this);
        }
        return mSavingModule;
    }

    /**
     * @return application preferences singleton.
     */
    public final PacklistSharedPrefs getPreferences() {
        if (mPreferences == null) {
            mPreferences = new PacklistSharedPrefs(this);
        }
        return mPreferences;
    }
//

}
