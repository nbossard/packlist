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

package com.nbossard.packlist.model;

import android.support.annotation.Nullable;

/*
@startuml
    class com.nbossard.packlist.model.Item {
        String mName
        String mCategory
    }
@enduml
 */

/**
 * An item, generic info about it.
 * @author Created by nbossard on 29/04/17.
 * @see TripItem
 * @see com.nbossard.packlist.gui.PresentableItem
 *
 */

public class Item {

// *********************** FIELDS *************************************************************************

    /** The item name. */
    protected String mName;

    /**
     * The item category for grouping.
     */
    protected String mCategory;

// *********************** METHODS **************************************************************************

    /**
     * Empty constructor. Required.
     */
    public Item() {

    }

    /**
     * Constructor fully qualified.
     * @param parOneItem item to be used
     */
    public Item(Item parOneItem) {
        this.setName(parOneItem.getName());
        this.setCategory(parOneItem.getCategory());
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
     * Getter for category, an optional characteristic.
     *
     * @return category or null if never set
     */

    @Nullable
    public final String getCategory() {
        return mCategory;
    }

    /**
     * @param parCategory new category name.
     */
    public final void setCategory(@Nullable final String parCategory) {
        mCategory = parCategory;
    }


// *********************** GENERATED METHODS ***********************************************************


    @SuppressWarnings("CheckStyle")
    @Override
    public boolean equals(Object parO) {
        if (this == parO) return true;
        if (parO == null || getClass() != parO.getClass()) return false;

        Item item = (Item) parO;

        return getName() != null ? getName().equals(item.getName()) : item.getName() == null
                && (getCategory() != null ? getCategory().equals(item.getCategory()) : item.getCategory() == null);

    }

    @SuppressWarnings("CheckStyle")
    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }
}
