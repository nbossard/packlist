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

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A trip data model.
 *
 * @author Created by nbossard on 25/12/15.
 */
public class Trip implements Serializable {

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
     * The trip start date but as a locale formatted date.
     * @return locale formatted date or null if never set
     */
    public final String getFormattedStartDate() {
        String res = null;
        if (mStartDate != null) {
            res = DateFormat.getDateInstance(DateFormat.SHORT).format(mStartDate.getTime());
        }
        return res;
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
     * End date formatted according to device locale.
     * @return locale formatted date or null if never set
     */
    public final String getFormattedEndDate() {
        String res = null;
        if (mEndDate != null) {
            res = DateFormat.getDateInstance(DateFormat.SHORT).format(mEndDate.getTime());
        }
        return res;
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
     */
    public final void addItem(final String parName) {
        Item newItem = new Item(parName);
        mListItem.add(newItem);
    }


    /**
     * Get the full list of items to bring for this trip.
     * @return a list of items.
     */
    @NonNull
    public final List<Item> getListItem() {
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
     */
    public final long getRemainingDays() {
        long diffInMillisec = (mStartDate.getTimeInMillis() - System.currentTimeMillis());
        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
        return diffInDays;
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
