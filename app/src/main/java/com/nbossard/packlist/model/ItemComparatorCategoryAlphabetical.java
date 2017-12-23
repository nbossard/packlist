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


import java.util.Comparator;

/*
@startuml
    class com.nbossard.packlist.model.ItemComparatorCategoryAlphabetical {
    }
@enduml
 */

/**
 * Comparator to sort Items based on category.
 * Categories will appear first (by alphabetical order) and the rest without categories in alphabetical order.
 *
 * @author Created by nbossard on 02/05/16.
 */
public class ItemComparatorCategoryAlphabetical implements Comparator<TripItem> {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    @SuppressWarnings("unused")
    private static final String TAG = ItemComparatorCategoryAlphabetical.class.getName();

    // Constants for better code readability

    /**
     * parItem before parAnother.
     */
    private static final int PAR_ANOTHER_ITEM_BEFORE_PAR_ITEM = 1;
    /**
     * parAnother before parItem.
     */
    private static final int PAR_ITEM_BEFORE_PAR_ANOTHER = -1;
    /**
     * We don't know who should be first.
     */
    private static final int PAR_EQUALS = 0;

    // *********************** METHODS **********************************************************************

    @Override
    public final int compare(final TripItem parItem, final TripItem parAnother) {
        // Disabling log for unit test
        //Log.v(TAG, "Entering, parItem = " + parItem + ", parAnother = " + parAnother);
        int res;
        if (parItem.getCategory() == null && parAnother.getCategory() == null) {
            // no category for both, comparing on names...
            // unless if null, null should not occur but if it goes last
            res = compareOnNames(parItem, parAnother);
            // only one have category
        } else if (parItem.getCategory() == null && parAnother.getCategory() != null) {
            res = PAR_ANOTHER_ITEM_BEFORE_PAR_ITEM;
        } else if (parAnother.getCategory() == null && parItem.getCategory() != null) {
            res = PAR_ITEM_BEFORE_PAR_ANOTHER;
        } else {
            // both categories are non null

            if (!parItem.getCategory().contentEquals(parAnother.getCategory())) {
                res = parItem.getCategory().compareTo(parAnother.getCategory());
            } else {
                // non null but the same category, comparing on names
                res = compareOnNames(parItem, parAnother);
            }
        }
        //Log.v(TAG, "returning = " + res);
        return res;
    }

    // *********************** INTERNAL METHODS **************************************************************

    private int compareOnNames(final TripItem parItem, final TripItem parAnother) {
        int res;
        if ((parItem.getName() == null) && (parAnother.getName() != null)) {
            res = PAR_ITEM_BEFORE_PAR_ANOTHER;
        } else if ((parAnother.getName() == null) && (parItem.getName() != null)) {
            res = PAR_ANOTHER_ITEM_BEFORE_PAR_ITEM;
        } else if ((parAnother.getName() == null) && (parItem.getName() == null)) {
            res = PAR_EQUALS;
        } else {
            res = parItem.getName().compareTo(parAnother.getName());
        }
        return res;
    }
}
