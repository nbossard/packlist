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
    class com.nbossard.packlist.model.ItemComparatorAdditionDate {
    }
@enduml
 */

/**
 * Comparator to sort Items based on unpacked first.
 *
 * @author Created by nbossard on 02/05/16.
 */
public class ItemComparatorAdditionDate implements Comparator<TripItem> {
    @Override
    public final int compare(final TripItem parItem, final TripItem parAnother) {
        int res;
        if (parItem.getAdditionDate() == null || parAnother.getAdditionDate() == null) {
            res = 0;
        } else if (parItem.getAdditionDate().equals(parAnother.getAdditionDate())) {
            res = 0;
        } else if (parItem.getAdditionDate().before(parAnother.getAdditionDate())) {
            res = -1;
        } else {
            res = 1;
        }
        return res;
    }
}
