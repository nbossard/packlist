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
        String mSortMode

        addItem()
        deleteItem(UUID)
        unpackAll()
    }
@enduml
 */

import android.support.annotation.NonNull;

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
    @SuppressWarnings("unused")
    private static final String TAG = Trip.class.getName();

    // For better code readability

    /**
     * Count weight of all items.
     */
    private static final boolean PACKED_ITEMS_ONLY = true;

    /**
     * Count weight of all packed items only.
     */
    private static final boolean ALL_ITEMS = false;


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

    /** The total weight of all items in this trip. */
    private int mTotalWeight;

    /**
     * The total weight of all items in this trip... that are packed
     */
    private int mPackedWeight;

    /**
     * The trip saved sort mode.
     */
    private SortModes mSortMode;

// *********************** METHODS **************************************************************************

    /**
     * No parameter Constructor.
     */
    public Trip() {
        mUUID = UUID.randomUUID();
        mListItem = new ArrayList<>();
        setSortMode(SortModes.DEFAULT);
    }

    /**
     * Full parameters constructor.
     *
     * @param parName      trip name, usually destination. i.e. : "Dublin"
     * @param parStartDate trip start date
     * @param parEndDate   trip return date
     * @param parNote      additional notes, free text
     * @param parSortMode sort mode (alphabetical, packed...)
     */
    public Trip(final String parName,
                final GregorianCalendar parStartDate,
                final GregorianCalendar parEndDate,
                final String parNote,
                final SortModes parSortMode) {
        this();
        setName(parName);
        setStartDate(parStartDate);
        setEndDate(parEndDate);
        setNote(parNote);
        setSortMode(parSortMode);
        mTotalWeight = 0;
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
     * Add a new item in the list of items to bring with this trip.<br>
     *
     * Automatically updates total weight.
     * @param parItem new item
     */
    @SuppressWarnings("WeakerAccess")
    public final void addItem(final Item parItem) {
        mListItem.add(parItem);
        setTotalWeight(recomputeTotalWeight(ALL_ITEMS));
        setPackedWeight(recomputeTotalWeight(PACKED_ITEMS_ONLY));
    }

    /**
     * @return total weight of all items that have a weight.
     */
    public final int getTotalWeight() {
        return mTotalWeight;
    }

    /**
     * @return total weight of all items that have a weight and that are packed.
     */
    public final int getPackedWeight() {
        return mPackedWeight;
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
     * Delete the item of provided UUID.<br>
     * <p/>
     * Automatically updates total weight.
     *
     * @param parUUID unique identifier of item to be deleted
     */
    public final void deleteItem(final UUID parUUID) {
        Item toDeleteItem = null;
        for (Item oneItem : mListItem) {
            if (oneItem.getUUID().compareTo(parUUID) == 0) {
                toDeleteItem = oneItem;
            }
        }
        if (toDeleteItem != null) {
            mListItem.remove(toDeleteItem);
        }
        setTotalWeight(recomputeTotalWeight(ALL_ITEMS));
        setPackedWeight(recomputeTotalWeight(PACKED_ITEMS_ONLY));
    }

    /**
     * unpack all items of this trip. Update packing weight.
     */
    public final void unpackAll() {
        for (Item oneItem : mListItem) {
            oneItem.setPacked(false);
        }
        packingChange();
    }

    /**
     * Set the current sort mode.
     *
     * @param parSortMode the new current sort mode.
     */
    public final void setSortMode(final SortModes parSortMode) {
        if (parSortMode == null) {
            mSortMode = SortModes.DEFAULT;
        } else {
            mSortMode = parSortMode;
        }
    }

    /**
     * @return current sort mode
     */
    public final SortModes getSortMode() {
        return mSortMode;
    }

    /**
     * Check if an article of same name is already in the list.
     *
     * @param parItemName name of new article
     * @return true if an article of exactly same name is in the list
     */
    public final boolean alreadyContainsItemOfName(final String parItemName) {
        boolean res = false;
        for (Item oneItem : getListOfItems()) {
            if (oneItem.getName().contentEquals(parItemName)) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Updating of total weight.
     *
     * @param parTotalWeight the new total weight in grams.
     */
    private void setTotalWeight(final int parTotalWeight) {
        mTotalWeight = parTotalWeight;
    }

    /**
     * Updating of packed weight.
     *
     * @param parPackedWeight the new packed weight in grams.
     */
    private void setPackedWeight(final int parPackedWeight) {
        mPackedWeight = parPackedWeight;
    }


    /**
     * @return Number of days before trip, can be a negative value if trip is in the past.
     * 0, default value if no startDate defined.
     */
    public final long getRemainingDays() {

        long diffInMilliSeconds = 0;
        if (mStartDate != null) {
            diffInMilliSeconds = (mStartDate.getTimeInMillis() - System.currentTimeMillis());
        }

        return TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
    }

    //CHECKSTYLE : BEGIN GENERATED CODE
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

    //CHECKSTYLE : END GENERATED CODE

    @Override
    public final int compareTo(@NonNull final Trip parAnotherTrip) {
        int curRemainingDays = ((Long) getRemainingDays()).intValue();
        int otherRemainingDays = ((Long) parAnotherTrip.getRemainingDays()).intValue();
        return otherRemainingDays - curRemainingDays;
    }

    /**
     * Notify this trip that one of its item has changed its packing state.
     */
    public final void packingChange() {
        setPackedWeight(recomputeTotalWeight(PACKED_ITEMS_ONLY));
    }

    @Override
    public final Trip clone() throws CloneNotSupportedException {
        Trip clonedTrip = (Trip) super.clone();

        // setting another UUID
        clonedTrip.mUUID = UUID.randomUUID();

        // cloning also trip list
        clonedTrip.mListItem = new ArrayList<>();
        for (Item item : getListOfItems()) {
            clonedTrip.addItem(item.clone());
        }
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

    // *********************** PRIVATE METHODS **************************************************************

    /**
     * Recomputes the total weight by adding all weight of all items.
     *
     * @param parPackedOnly if true, count only the packed items
     * @return a weight or 0 if no item at all has a weight.
     */
    private int recomputeTotalWeight(final boolean parPackedOnly) {
        int resTotalWeight = 0;
        for (Item item : mListItem) {
            //noinspection ConstantConditions
            if (!parPackedOnly || (parPackedOnly && item.isPacked())) {
                resTotalWeight += item.getWeight();
            }
        }
        return resTotalWeight;
    }
}
