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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hugo.weaving.DebugLog;

/**
 * A trip data model.
 *
 * @author Created by nbossard on 25/12/15.
 */
public class Trip {

// *********************** FIELDS *************************************************************************

    /** A unique identifier for this trip. */
    private UUID mUUID;

    /** The trip name usually destination. */
    private String mName;

    /** Trip start date. */
    private String mStartDate;

    /** Trip return date. */
    private String mEndDate;

    /** Additional notes, free text. */
    private String mNote;


    /** List of items to bring in this trip. */
    private List<Item> mListItem;

    //
// *********************** METHODS **************************************************************************

    /**
     * Full parameters constructor.
     * @param parName trip name, usually destination. i.e. : "Dublin"
     * @param parStartDate trip start date
     * @param parEndDate trip return date
     * @param parNote additional notes, free text
     */
    public Trip(final String parName,
                final String parStartDate,
                final String parEndDate,
                final String parNote) {

        mUUID = UUID.randomUUID();
        setName(parName);
        setStartDate(parStartDate);
        setEndDate(parEndDate);
        setNote(parNote);

        mListItem = new ArrayList<>();
        mListItem.add(new Item("Café"));
        mListItem.add(new Item("Thé"));
        mListItem.add(new Item("Pantalons"));
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
    public final String getStartDate() {
        return mStartDate;
    }

    /**
     * Setter for start date of trip.
     * @param parStartDate trip start date
     */
    @SuppressWarnings("WeakerAccess")
    public final void setStartDate(final String parStartDate) {
        mStartDate = parStartDate;
    }

    /**
     * Getter for trip return date.
     * @return trip return date
     */
    public final String getEndDate() {
        return mEndDate;
    }

    /**
     * Setter for trip return date.
     * @param parEndDate trip return date
     */
    @SuppressWarnings("WeakerAccess")
    public final void setEndDate(final String parEndDate) {
        mEndDate = parEndDate;
    }

    /**
     *
     * @return automatically set UUID
     */
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
     * Set the full list of items to bring for this trip.
     * @param parListItem a list of items.
     */
    public final void setListItem(final List<Item> parListItem) {
        mListItem = parListItem;
    }

    /**
     * Get the full list of items to bring for this trip.
     * @return a list of items.
     */
    public final List<Item> getListItem() {
        return mListItem;
    }


    @Override
    public final boolean equals(Object parO) {
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
