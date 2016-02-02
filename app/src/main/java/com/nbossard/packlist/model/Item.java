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
        String mName
    }
@enduml
 */

import java.io.Serializable;

/**
 * An item to take in a trip.
 * @author Created by nbossard on 17/01/16.
 */
public class Item implements Serializable {

    /** The trip name usually destination. */
    private String mName;

    /**
     * Full params constructor.
     *
     * @param parName new item name. i.e. : "socks"
     */
    public Item(final String parName) {
        setName(parName);
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("mName='").append(mName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
