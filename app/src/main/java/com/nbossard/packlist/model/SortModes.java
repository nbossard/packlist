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
    enum com.nbossard.packlist.model.SortModes {
        DEFAULT
        UNPACKED_FIRST
        ALPHABETICAL
        CATEGORY
        next()
    }
@enduml
 */

/**
 * List of possible item sorting modes.
 *
 * @author Created by nbossard on 22/04/16.
 */
public enum SortModes {
    /**
     * Default (no) sorting mode, in fact sorted by addition date .
     */
    DEFAULT,
    /**
     * First all unpacked then all packed items.
     */
    UNPACKED_FIRST,
    /**
     * By alphabetical order.
     */
    ALPHABETICAL,
    /**
     * By category.
     */
    CATEGORY;

    /**
     * Used for computing next sorting mode.
     */
    private static SortModes[] vals = values();

    /**
     * @return next sorting mode in the list.
     */
    public SortModes next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
