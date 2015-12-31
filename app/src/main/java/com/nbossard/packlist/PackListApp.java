package com.nbossard.packlist;

import android.app.Application;

import com.nbossard.packlist.process.ISavingModule;
import com.nbossard.packlist.process.PrefsSavingModule;

/**
 * Application level initialisations.
 *
 * @author Created by nbossard on 31/12/15.
 */
public class PackListApp extends Application {

// *********************** FIELDS *************************************************************************
    private PrefsSavingModule mSavingModule;
//

// *********************** METHODS **************************************************************************
    public ISavingModule getSavingModule() {
        if (mSavingModule ==null) {
            mSavingModule = new PrefsSavingModule();
        }
        return mSavingModule;
    }
//

}
