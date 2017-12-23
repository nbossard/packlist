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

package com.nbossard.packlist.process.saving;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.ScoredItem;
import com.nbossard.packlist.model.TripItem;
import com.nbossard.packlist.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import hugo.weaving.DebugLog;

//CHECKSTYLE:OFF: LineLength
/*
@startuml
    class com.nbossard.packlist.process.saving.PrefsSavingModule {
    }
    com.nbossard.packlist.process.saving.ISavingModule <|.. com.nbossard.packlist.process.saving.PrefsSavingModule
    com.nbossard.packlist.process.saving.ITripChangeListener "one or many" <.. com.nbossard.packlist.process.saving.PrefsSavingModule
@enduml
 */
//CHECKSTYLE:ON: LineLength

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

    /** The key for saving in preferences the list of {@link Trip}. */
    private static final String LIST_TRIPS_KEY = "LIST_TRIPS";

    /** The name of filename in where to save preferences. */
    private static final String PREFS_FILENAME = "PREFS";
//
// *********************** FIELDS *************************************************************************

    /** Object to manipulate shared preferences. */
    private final SharedPreferences mSharedPreferences;

    /** The Gson serialisation tool. */
    private final Gson mGson;

    /** The list of listeners to be notified of change. */
    private final List<ITripChangeListener> mChangeListeners = new ArrayList<>();

// *********************** METHODS **************************************************************************

    /**
     * Standard constructor.
     * @param parContext context for loading shared preferences.
     */
    PrefsSavingModule(final Context parContext) {
        mSharedPreferences = parContext.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    @Override
    public final
    @NonNull
    List<Trip> loadSavedTrips() {
        List<Trip> savedTripsList;
        String listTrips = mSharedPreferences.getString(LIST_TRIPS_KEY, "");

        if (listTrips.length() == 0) {
            savedTripsList = new ArrayList<>();
        } else {
            try {
                Trip[] tmpArray = mGson.fromJson(listTrips, Trip[].class);
                savedTripsList = new ArrayList<>(Arrays.asList(tmpArray));
                Collections.sort(savedTripsList);
            } catch (JsonParseException jpe) {
                savedTripsList = new ArrayList<>();
            }
        }
        return savedTripsList;
    }

    @Override
    @DebugLog
    public final Trip loadSavedTrip(@Nullable final UUID parUUID) {
        Trip res = null;

        if (parUUID == null) {
            // Do nothing
            Log.w(TAG, "loadSavedTrip() : null parUUID, doing nothing");
        } else {
            List<Trip> tmpList = loadSavedTrips();
            for (Trip oneTrip:tmpList) {
                if (oneTrip.getUUID().compareTo(parUUID) == 0) {
                    res = oneTrip;
                }
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

    @Override
    public final void cloneTrip(final UUID parUUID) {
        // retrieve current list
        List<Trip> prevSavedTrips = loadSavedTrips();

        // clone one Trip
        Trip tripToClone = null;
        for (Trip oneTrip:prevSavedTrips) {
            if (oneTrip.getUUID().compareTo(parUUID) == 0) {
                tripToClone = oneTrip;
                break;
            }
        }
        if (tripToClone != null) {
            try {
                Trip clonedTrip = tripToClone.clone();
                clonedTrip.setName(clonedTrip.getName() + " (cloned)");
                clonedTrip.unpackAll();
                prevSavedTrips.add(clonedTrip);
            } catch (CloneNotSupportedException cnse) {
                Log.e(TAG, "cloneTrip: This should never occur : " + cnse);
            }

        } else {
            Log.w(TAG, "cloneTrip: failed finding trip to remove of UUID" + parUUID);
        }

        // save
        save(prevSavedTrips);
    }

    @Override
    public final void addListener(final ITripChangeListener parListener) {
        mChangeListeners.add(parListener);
    }

    @Override
    public final void removeListener(final ITripChangeListener parListener) {
        mChangeListeners.remove(parListener);
    }

    @Override
    public final boolean updateItem(final TripItem parItem) {
        // retrieve trip of item
        Trip prevSavedTrips = loadSavedTrip(parItem.getTripUUID());

        // update item
        boolean res;
        if (prevSavedTrips != null) {
            prevSavedTrips.deleteItem(parItem.getUUID());
            prevSavedTrips.addItem(parItem);
            updateTrip(prevSavedTrips);
            res = true;
        } else {
            res = false;
        }
        return res;
    }

    @Override
    public final Set<String> getAllCategories() {

        Set<String> resSet = new HashSet<>();

        List<Trip> tripList = loadSavedTrips();
        for (Trip oneTrip : tripList) {
            List<TripItem> tripItems = oneTrip.getListOfItems();
            for (TripItem oneItem : tripItems) {
                if (oneItem.getCategory() != null && oneItem.getCategory().length() > 0) {
                    resSet.add(oneItem.getCategory());
                }
            }
        }

        return resSet;
    }

    @Override
    public final Set<Item> getAllPossibleItems() {

        Set<Item> resSet = new HashSet<>();

        List<Trip> tripList = loadSavedTrips();
        for (Trip oneTrip : tripList) {
            List<TripItem> tripItems = oneTrip.getListOfItems();
            for (TripItem oneItem : tripItems) {
                if (oneItem.getName() != null && oneItem.getName().length() > 0) {
                    resSet.add(oneItem);
                }
            }
        }

        return resSet;
    }


    @Override
    public final List<ScoredItem> getProbableItemsList() {

        // Assigning a score to each previously seen item
        Map<Item, Integer> resMapNameScore = new TreeMap<>();
        List<ScoredItem> resList = new ArrayList<>();

        // simple version : score is the counting number of occurrences of each item name
        List<Trip> tripList = loadSavedTrips();
        for (Trip oneTrip : tripList) {
            List<TripItem> tripItems = oneTrip.getListOfItems();
            for (Item oneItem : tripItems) {
                if (oneItem.getName() != null && oneItem.getName().length() > 0) {
                    if (resMapNameScore.containsKey(oneItem)) {
                        Integer value = resMapNameScore.get(oneItem);
                        resMapNameScore.put(oneItem, value + 1);
                    } else {
                        resMapNameScore.put(oneItem, 1);
                    }
                }
            }
        }

        // sorting according to score assigned to each item
        /*
        ValueComparator bvc = new ValueComparator(resMapNameScore);
        TreeMap resMapSorted = new TreeMap<>(bvc);
        resMapSorted.putAll(resMapNameScore);
           */
        // converting to expected result type, a (ordered) list
        for (Item oneItem : resMapNameScore.keySet()) {
            if (resMapNameScore.get(oneItem) == null) {
                Log.e(TAG, "WTF I can't get in this fucking map");
            } else {
                ScoredItem scoredItem = new ScoredItem(oneItem, resMapNameScore.get(oneItem));
                resList.add(scoredItem);
            }
        }
        Collections.sort(resList, (parA, parB) -> {
            int res;
            if (parB.getScore() < parA.getScore()) {
                res = -1;
            } else if (parB.getScore() > parA.getScore()) {
                res = +1;
            } else {
                res = 0;
            }
            return res;
        });
        return resList;
    }

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Update an existing trip.
     * @param parTmpTrip trip to be updated
     */
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

        // notify listeners
        for (ITripChangeListener oneListener : mChangeListeners) {
            oneListener.onTripChange();
        }
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

    /**
     * Add a new Trip.
     * @param parTmpTrip trip to be added.
     */
    private void addTrip(final Trip parTmpTrip) {
        // retrieve current list
        List<Trip> prevSavedTrips = loadSavedTrips();

        // Adding new trip
        prevSavedTrips.add(parTmpTrip);

        // save updated list
        save(prevSavedTrips);
    }
}
