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

package com.nbossard.packlist.model;

/*
@startuml
    class com.nbossard.packlist.model.Trip {
        String mName
        String mStartDate
        String mEndDate
        String mNote

        addItem()
    }
@enduml
 */

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A trip data model.
 * Note that this class has to be left in Java folder for data binding.
 *
 * @author Created by nbossard on 25/12/15.
 */
public class Trip implements Serializable, Comparable<Trip>, Cloneable {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = Trip.class.getName();


// *********************** FIELDS *************************************************************************

    /** A unique identifier for this trip. */
    private UUID mUUID;

    /** The trip name usually destination. */
    private String mName;

    /** Trip start date. */
    private GregorianCalendar mStartDate;

    /** Trip return date. */
    private GregorianCalendar mEndDate;

    /** Additional notes, free text. */
    private String mNote;


    /** List of items to bring in this trip. */
    private List<Item> mListItem;

    //
// *********************** METHODS **************************************************************************

    /**
     * No parameter Constructor.
     */
    public Trip() {
        mUUID = UUID.randomUUID();
        mListItem = new ArrayList<>();
    }

    /**
     * Full parameters constructor.
     * @param parName trip name, usually destination. i.e. : "Dublin"
     * @param parStartDate trip start date
     * @param parEndDate trip return date
     * @param parNote additional notes, free text
     */
    public Trip(final String parName,
                final GregorianCalendar parStartDate,
                final GregorianCalendar parEndDate,
                final String parNote) {
        this();
        setName(parName);
        setStartDate(parStartDate);
        setEndDate(parEndDate);
        setNote(parNote);
    }

    /**
     * setter for note.
     * @param parNote new value for note. i.e. : "un chouette coin"
     */
    public final void setNote(final String parNote) {
        this.mNote = parNote;
    }

    /**
     * Getter for note.
     * @return i.e. : "un chouette coin"
     */
    public final String getNote() {
        return mNote;
    }

    /**
     * Getter for name.
     * @return i.e. : "Dublin"
     */
    public final String getName() {
        return mName;
    }

    /**
     * Setter for name/ destination of trip.
     * @param parName trip name, usually destination. i.e. : "Dublin"
     */
    @SuppressWarnings("WeakerAccess")
    public final void setName(final String parName) {
        mName = parName;
    }

    /**
     * Getter for trip start date.
     * @return trip start date
     */
    public final GregorianCalendar getStartDate() {
        return mStartDate;
    }

    /**
     * Setter for start date of trip.
     * @param parStartDate trip start date
     */
    @SuppressWarnings("WeakerAccess")
    public final void setStartDate(final GregorianCalendar parStartDate) {
        mStartDate = parStartDate;
    }

    /**
     * Getter for trip return date.
     * @return trip return date
     */
    public final GregorianCalendar getEndDate() {
        return mEndDate;
    }

    /**
     * Setter for trip return date.
     * @param parEndDate trip return date
     */
    @SuppressWarnings("WeakerAccess")
    public final void setEndDate(final GregorianCalendar parEndDate) {
        mEndDate = parEndDate;
    }

    /**
     * @return automatically set UUID
     */
    @NonNull
    public final UUID getUUID() {
        return mUUID;
    }

    /**
     * Add a new item in the list of items to bring with this trip.
     * @param parName name of new item
     * @return UUID of newly created item
     */
    public final UUID addItem(final String parName) {
        Item newItem = new Item(this, parName);
        mListItem.add(newItem);
        return newItem.getUUID();
    }

    /**
     * Add a new item in the list of items to bring with this trip.
     * @param parItem new item
     */
    @SuppressWarnings("WeakerAccess")
    public final void addItem(final Item parItem) {
        mListItem.add(parItem);
    }


    /**
     * Get the full list of items to bring for this trip.
     * @return a list of items.
     */
    @NonNull
    public final List<Item> getListOfItems() {
        return mListItem;
    }

    /**
     * Delete the item of provided UUID.
     * @param parUUID unique identifier of item to be deleted
     */
    public final void deleteItem(final UUID parUUID) {
        Item toDeleteItem = null;
        for (Item oneItem:mListItem) {
            if (oneItem.getUUID().compareTo(parUUID) == 0) {
                toDeleteItem = oneItem;
            }
        }
        if (toDeleteItem != null) {
            mListItem.remove(toDeleteItem);
        }
    }

    /**
     * @return Number of days before trip, can be a negative value if trip is in the past.
     * 0, default value if no startDate defined.
     */
    public final long getRemainingDays() {
        Log.d(TAG, "getRemainingDays() called.");

        long diffInMilliSeconds = 0;
        if (mStartDate != null) {
            diffInMilliSeconds = (mStartDate.getTimeInMillis() - System.currentTimeMillis());
        }
        Log.d(TAG, "getRemainingDays: diffInMilliSeconds = " + diffInMilliSeconds);

        long res = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
        Log.d(TAG, "getRemainingDays() returned: " + res);
        return res;
    }


    @Override
    public final boolean equals(final Object parO) {
        if (this == parO) return true;
        if (parO == null || getClass() != parO.getClass()) return false;

        Trip trip = (Trip) parO;

        if (mName != null ? !mName.equals(trip.mName) : trip.mName != null) return false;
        //noinspection SimplifiableIfStatement
        if (mStartDate != null ? !mStartDate.equals(trip.mStartDate) : trip.mStartDate != null) return false;
        return mEndDate != null ? mEndDate.equals(trip.mEndDate) : trip.mEndDate == null;

    }

    @Override
    public final int hashCode() {
        int result = mName != null ? mName.hashCode() : 0;
        result = 31 * result + (mStartDate != null ? mStartDate.hashCode() : 0);
        result = 31 * result + (mEndDate != null ? mEndDate.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull final Trip  parAnotherTrip) {
        int curRemainingDays = ((Long) getRemainingDays()).intValue();
        int otherRemainingDays = ((Long) parAnotherTrip.getRemainingDays()).intValue();
        return curRemainingDays - otherRemainingDays;
    }

    @Override
    public Trip clone() throws CloneNotSupportedException {
        Trip clonedTrip = (Trip) super.clone();

        // setting another UUID
        clonedTrip.mUUID = UUID.randomUUID();

        // cloning also trip list
        clonedTrip.mListItem = new ArrayList<>();
        for(Item item: getListOfItems()) clonedTrip.addItem(item.clone());
        return clonedTrip;
    }

    @Override
    public final String toString() {
        return "Trip{" + "mUUID=" + mUUID
                + ", mName=" + mName
                + ", mStartDate=" + mStartDate
                + ", mEndDate=" + mEndDate
                + ", mNote=" + mNote
                + ", mListItem=" + mListItem
                + '}';
    }
}
