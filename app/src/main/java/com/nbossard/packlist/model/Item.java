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
    class com.nbossard.packlist.model.Item {
        UUID mUUID
        String mName
        String mWeight
        String mCategory
        boolean mIsPacked
    }
@enduml
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * An item to take in a trip.
 * @author Created by nbossard on 17/01/16.
 */
public class Item implements Serializable, Cloneable {

// *********************** FIELDS *************************************************************************

    /** A unique identifier for this item. */
    private UUID mUUID;

    /** A unique identifier of the {@link Trip} this item belongs to. */
    private UUID mTripUUID;

    /** The item name. */
    private String mName;

    /** The item weight. */
    private int mWeight;

    /**
     * The item category for grouping.
     */
    private String mCategory;

    /** Has this item been packed already. true=yes, false=no. */
    private boolean mIsPacked;

    /**
     * Date of addition of this item.
     */
    private Date mAdditionDate;

// *********************** METHODS **************************************************************************

    /**
     * No params constructor.
     */
    private Item() {
        mUUID = UUID.randomUUID();
    }

    /**
     * Full params constructor.
     *
     * @param parTrip the {@link Trip} this item belongs to.
     * @param parName new item name. i.e. : "socks"
     */
    public Item(@NonNull final Trip parTrip, final String parName) {
        this();
        setTripUUID(parTrip.getUUID());
        setName(parName);
        mAdditionDate = new Date();
    }

    /**
     * @return automatically set UUID (unique identifier)
     */
    public final @NonNull UUID getUUID() {
        return mUUID;
    }

    /**
     * Getter for name.
     * @return i.e. : "Socks"
     */
    public final String getName() {
        return mName;
    }

    /**
     * Setter for name/ destination of item.
     * @param parName trip name, usually destination. i.e. : "Dublin"
     */
    @SuppressWarnings("WeakerAccess")
    public final void setName(final String parName) {
        mName = parName;
    }


    /**
     * Getter for weight.
     * @return a weight in grams. 0 if never set. i.e. : "100" grammes
     */
    public final int getWeight() {
        return mWeight;
    }

    /**
     * Setter for weight.
     * @param parWeight item weight in grammes.
     */
    @SuppressWarnings("WeakerAccess")
    public final void setWeight(final int parWeight) {
        mWeight = parWeight;
    }

    /**
     * Getter for boolean whether or not this item is packed.
     * @return true=yes, default value is false=no.
     */
    public final boolean isPacked() {
        return mIsPacked;
    }

    /**
     * Setter for  whether or not this item is packed.
     * @param parPacked true=yes, false=no.
     */
    public final void setPacked(final boolean parPacked) {
        mIsPacked = parPacked;
    }

    /**
     * @param parTripUUID The UUID of the {@link Trip} this item belongs to.
     */
    public final void setTripUUID(final UUID parTripUUID) {
        mTripUUID = parTripUUID;
    }

    /**
     * @return The UUID of the {@link Trip} this item belongs to, can be null if loading old versions.
     */
    public final @Nullable UUID getTripUUID() {
        return mTripUUID;
    }

    /**
     * Getter for addition date of this item.
     * @return the addition date of this item, will be used by default sorting mode
     */
    public final Date getAdditionDate() {
        return mAdditionDate;
    }

    /**
     * Getter for category, an optional characteristic.
     *
     * @return category or null if never set
     */
    @Nullable
    public String getCategory() {
        return mCategory;
    }

    /**
     * @param parCategory new category name.
     */
    public void setCategory(String parCategory) {
        mCategory = parCategory;
    }

    @Override
    protected final Item clone() throws CloneNotSupportedException {
        Item res = (Item) super.clone();

        // setting another UUID for the clone
        res.mUUID = UUID.randomUUID();

        return res;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("mUUID=").append(mUUID);
        sb.append(", mCategory='").append(mCategory).append('\'');
        sb.append(", mName='").append(mName).append('\'');
        sb.append(", mWeight='").append(mWeight).append('\'');
        sb.append(", mIsPacked='").append(mIsPacked).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
