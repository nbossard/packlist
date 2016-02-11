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

/**
 * Items in a pre-packing category.
 * @author Created by nbossard on 11/02/16.
 */
public class CatItem {

// *********************** FIELDS *************************************************************************

    String mName;

// *********************** METHODS **************************************************************************

    public CatItem(String parName) {
        setName(parName);
    }

    public String getName() {
        return mName;
    }

    public void setName(String parName) {
        mName = parName;
    }
}
