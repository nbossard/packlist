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

package com.nbossard.packlist.process.saving;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nbossard.packlist.model.CatItem;
import com.nbossard.packlist.model.Category;
import com.nbossard.packlist.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/*
@startuml
    class com.nbossard.packlist.process.saving.PrefsSavingModule {
    }
    com.nbossard.packlist.process.saving.ISavingModule <|-- com.nbossard.packlist.process.saving.PrefsSavingModule
@enduml
 */

/**
 * An implementation of {@link ISavingModule} based on shared preferences
 * and json serialisation / deserialization.
 *
 * @author Created by nbossard on 31/12/15.
 */
public class PrefsSavingModule implements ISavingModule {


// *********************** CONSTANTS**********************************************************************

    /** Log tag. */
    private static final String TAG = PrefsSavingModule.class.getName();

    private static final String LIST_TRIPS_KEY = "LIST_TRIPS";
    private static final String PREFS_FILENAME = "PREFS";
//
// *********************** FIELDS *************************************************************************
    private final SharedPreferences mSharedPreferences;
    private final Gson mGson;
//
// *********************** METHODS **************************************************************************
    PrefsSavingModule(Context parContext) {
        mSharedPreferences = parContext.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
        mGson = new Gson();

    }

    @Override
    public final List<Trip> loadSavedTrips() {
        List<Trip> savedTripsList;
        String listTrips = mSharedPreferences.getString(LIST_TRIPS_KEY, "");
        if (listTrips.length() == 0) {
            savedTripsList = new ArrayList<>();
        } else {
            try {
                Trip[] tmpArray = mGson.fromJson(listTrips, Trip[].class);
                savedTripsList = new ArrayList<>(Arrays.asList(tmpArray));
            } catch (JsonParseException jpe) {
                savedTripsList = new ArrayList<>();
            }
        }
        return savedTripsList;
    }

    @Override
    public final Trip loadSavedTrip(final UUID parUUID) {
        List<Trip> tmpList = loadSavedTrips();
        Trip res = null;
        for (Trip oneTrip:tmpList) {
            if (oneTrip.getUUID().compareTo(parUUID) == 0) {
                res = oneTrip;
            }
        }
        return res;
    }

    @Override
    public final void addOrUpdateTrip(final Trip parTmpTrip) {
        // retrieve current list
        if (loadSavedTrip(parTmpTrip.getUUID()) != null) {
            updateTrip(parTmpTrip);
        } else {
            addTrip(parTmpTrip);
        }
    }

    @Override
    public final void deleteAllTrips() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LIST_TRIPS_KEY, "");
        editor.apply();
    }

    @Override
    public final void deleteTrip(final UUID parUUID) {
        // retrieve current list
        List<Trip> prevSavedTrips = loadSavedTrips();

        // remove one Trip
        Trip tripToRemove = null;
        for (Trip oneTrip:prevSavedTrips) {
            if (oneTrip.getUUID().compareTo(parUUID) == 0) {
                tripToRemove = oneTrip;
                break;
            }
        }
        if (tripToRemove != null) {
            prevSavedTrips.remove(tripToRemove);
        } else {
            Log.w(TAG, "deleteTrip: failed finding trip to remove of UUID" + parUUID);
        }

        // save
        save(prevSavedTrips);
    }

    private void updateTrip(final Trip parTmpTrip) {
        List<Trip> tripList = loadSavedTrips();
        List<Trip> updatedTripList = new ArrayList<>();
        for (Trip oneTrip : tripList) {
            if (oneTrip.getUUID().compareTo(parTmpTrip.getUUID()) == 0) {
                updatedTripList.add(parTmpTrip);
            } else {
                updatedTripList.add(oneTrip);
            }
        }
        save(updatedTripList);
    }

    /**
     * Save provide list of trips, overwrite current.
     * @param parPrevSavedTrips list of trips to be saved in preferences
     */
    private void save(final List<Trip> parPrevSavedTrips) {
        String jsonListTrips = mGson.toJson(parPrevSavedTrips);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LIST_TRIPS_KEY, jsonListTrips);
        editor.apply();
    }

    private final void addTrip(final Trip parTmpTrip) {
        // retrieve current list
        List<Trip> prevSavedTrips = loadSavedTrips();

        // Adding new trip
        prevSavedTrips.add(parTmpTrip);

        // save updated list
        save(prevSavedTrips);
    }


    @Override
    public List<Category> loadCategories() {
        // TODO remove this fake
        List<Category> resList = new ArrayList<>();

        Category fakeCat1 = new Category("Linge de base", Category.ALWAYS);
        fakeCat1.addItem(new CatItem("Culotte"));
        fakeCat1.addItem(new CatItem("Chaussettes"));

        Category fakeCat2 = new Category("Windsurf", Category.OPTIONAL);
        fakeCat1.addItem(new CatItem("Planche"));
        fakeCat1.addItem(new CatItem("Harnais"));

        resList.add(fakeCat1);
        resList.add(fakeCat2);

        return resList;
    }
}
