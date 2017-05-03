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
    class com.nbossard.packlist.model.TripItem {
        UUID mUUID
        String mWeight
        boolean mIsPacked
    }

    com.nbossard.packlist.model.Item <|-- com.nbossard.packlist.model.TripItem
@enduml
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * An item with additional info when taken in a trip.
 *
 * @author Created by nbossard on 17/01/16.
 */
public class TripItem extends Item implements Serializable, Cloneable {


    // ********************** CONSTANTS *********************************************************************

    // These constants are for better code readability
    /**
     * An unpacked item.
     */
    @SuppressWarnings("WeakerAccess")
    public static final boolean UNPACKED = false;
    /**
     * A packed item.
     */
    @SuppressWarnings("WeakerAccess")
    public static final boolean PACKED = true;

// *********************** FIELDS *************************************************************************

    /**
     * A unique identifier for this item.
     */
    private UUID mUUID;

    /**
     * A unique identifier of the {@link Trip} this item belongs to.
     */
    private UUID mTripUUID;

    /**
     * The item weight.
     */
    private int mWeight;

    /**
     * Has this item been packed already. true=yes, false=no.
     */
    private boolean mIsPacked;

    /**
     * Date of addition of this item.
     */
    private Date mAdditionDate;

// *********************** METHODS **************************************************************************

    /**
     * No params constructor.
     */
    private TripItem() {
        mUUID = UUID.randomUUID();
    }

    /**
     * Full params constructor.
     *
     * @param parTrip the {@link Trip} this item belongs to.
     * @param parName new item name. i.e. : "socks"
     */
    public TripItem(@NonNull final Trip parTrip, final String parName) {
        this();
        setTripUUID(parTrip.getUUID());
        setName(parName);
        mAdditionDate = new Date();
    }

    /**
     * Full params constructor.
     *
     * @param parTrip the {@link Trip} this item belongs to.
     * @param parItem item
     */
    public TripItem(@NonNull Trip parTrip, final Item parItem) {
        super(parItem);
        mUUID = UUID.randomUUID();
        setTripUUID(parTrip.getUUID());
        mAdditionDate = new Date();
    }

    /**
     * @return automatically set UUID (unique identifier)
     */
    public final
    @NonNull
    UUID getUUID() {
        return mUUID;
    }


    /**
     * Getter for weight.
     *
     * @return a weight in grams. 0 if never set. i.e. : "100" grammes
     */
    public final int getWeight() {
        return mWeight;
    }

    /**
     * Setter for weight.
     *
     * @param parWeight item weight in grammes.
     */
    @SuppressWarnings("WeakerAccess")
    public final void setWeight(final int parWeight) {
        mWeight = parWeight;
    }

    /**
     * Getter for boolean whether or not this item is packed.
     *
     * @return true=yes, default value is false=no.
     */
    public final boolean isPacked() {
        return mIsPacked;
    }

    /**
     * Setter for  whether or not this item is packed.
     *
     * @param parPacked true=yes, false=no.
     */
    public final void setPacked(final boolean parPacked) {
        mIsPacked = parPacked;
    }

    /**
     * @param parTripUUID The UUID of the {@link Trip} this item belongs to.
     */
    @SuppressWarnings("WeakerAccess")
    public final void setTripUUID(final UUID parTripUUID) {
        mTripUUID = parTripUUID;
    }

    /**
     * @return The UUID of the {@link Trip} this item belongs to, can be null if loading old versions.
     */
    public final
    @Nullable
    UUID getTripUUID() {
        return mTripUUID;
    }

    /**
     * Getter for addition date of this item.
     *
     * @return the addition date of this item, will be used by default sorting mode
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    public final Date getAdditionDate() {
        Date res = null;
        if (mAdditionDate != null) {
            // Findbugs suggests cloning for security reasons
            res = (Date) mAdditionDate.clone();
        }
        return res;
    }

    @Override
    protected final TripItem clone() throws CloneNotSupportedException {
        TripItem res = (TripItem) super.clone();

        // setting another UUID for the clone
        res.mUUID = UUID.randomUUID();

        return res;
    }

    @Override
    public boolean equals(Object parO) {
        return super.equals(parO);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder("TripItem{");
        sb.append("mUUID=").append(mUUID);
        sb.append(", mCategory='").append(getCategory()).append('\'');
        sb.append(", mName='").append(getName()).append('\'');
        sb.append(", mWeight='").append(mWeight).append('\'');
        sb.append(", mIsPacked='").append(mIsPacked).append('\'');
        sb.append(", mAdditionDate='").append(mAdditionDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
