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
    class com.nbossard.packlist.model.Item {
        UUID mUUID
        String mName
    }
@enduml
 */

import java.io.Serializable;
import java.util.UUID;

/**
 * An item to take in a trip.
 * @author Created by nbossard on 17/01/16.
 */
public class Item implements Serializable {

// *********************** FIELDS *************************************************************************

    /** A unique identifier for this item. */
    private UUID mUUID;

    /** The item name. */
    private String mName;

    /** Has this item been packed already. true=yes, false=no. */
    private boolean mIsPacked;

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
     * @param parName new item name. i.e. : "socks"
     */
    public Item(final String parName) {
        this();
        setName(parName);
    }

    /**
     * @return automatically set UUID (unique identifier)
     */
    public final UUID getUUID() {
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
     * Getter for boolean whether or not this item is packed.
     * @return true=yes, false=no.
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

    @Override
    public final String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("mUUID=").append(mUUID);
        sb.append(", mName='").append(mName).append('\'');
        sb.append(", mIsPacked='").append(mIsPacked).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
