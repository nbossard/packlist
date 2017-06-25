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

/*
@startuml
    class com.nbossard.packlist.model.ScoredItem {
        int mScore
    }

    com.nbossard.packlist.model.Item <|-- com.nbossard.packlist.model.ScoredItem
@enduml
 */

/**
 * An {@link Item} with a score indicating its number of times it is used in other trips.
 *
 * @author Created by nbossard on 01/05/17.
 */

public class ScoredItem extends Item {

// *********************** FIELDS *************************************************************************

    /**
     * Score associated to this item, default 1, the higher the better.
     */
    private int mScore;

// *********************** METHODS **************************************************************************

    /**
     * Default empty constructor.
     */
    @SuppressWarnings("unused")
    public ScoredItem() {
        super();
        mScore = 1;
    }

    /**
     * Constructor from an existing item and a precomputed score.
     *
     * @param parItem  item to associate a score
     * @param parScore score for this item the higher the better.
     */
    public ScoredItem(final Item parItem, final int parScore) {
        super(parItem);
        mScore = parScore;
    }


    /**
     * @return the score for this Item.
     */
    public int getScore() {
        return mScore;
    }

}