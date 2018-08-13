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
    class com.nbossard.packlist.model.ItemComparatorPacking {
    }
@enduml
 */

/**
 * Comparator to sort Items based on unpacked first, then use alphabetical order.
 *
 * @author Created by nbossard on 02/05/16.
 */
public class ItemComparatorPacking implements Comparator<TripItem> {
    @Override
    public final int compare(final TripItem parItem, final TripItem parAnother) {
        int res = 0;
        if (!parItem.isPacked() && parAnother.isPacked()) {
            //noinspection CheckStyle
            res = -2;
        } else if (parItem.isPacked() && !parAnother.isPacked()) {
            res = 2;
        } else if ((!parItem.isPacked() && !parAnother.isPacked())
                    || (parItem.isPacked() && parAnother.isPacked())) {
            // both are unpacked, or both are packed, then using alphabetical order
            if ((parItem.getName() != null) && (parAnother.getName() != null)) {
                ItemComparatorCategoryAlphabetical tmp = new ItemComparatorCategoryAlphabetical();
                res = tmp.compare(parItem, parAnother);
            }
        }
        return res;
    }
}
