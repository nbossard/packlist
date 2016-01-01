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
    class com.nbossard.packlist.model.Trip {
        String mName
        String mStartDate
        String mEndDate
    }
@enduml
 */

/**
 * A trip
 *
 * @author Created by nbossard on 25/12/15.
 */
public class Trip {

// *********************** FIELDS *************************************************************************

    private CharSequence mName;
    private CharSequence mStartDate;
    private CharSequence mEndDate;

    //
// *********************** METHODS **************************************************************************
    public Trip(CharSequence parName, CharSequence parStartDate, CharSequence parEndDate) {
        setName(parName);
        setStartDate(parStartDate);
        setEndDate(parEndDate);
    }

    public CharSequence getName() {
        return mName;
    }

    public void setName(CharSequence parName) {
        mName = parName;
    }

    public CharSequence getStartDate() {
        return mStartDate;
    }

    public void setStartDate(CharSequence parStartDate) {
        mStartDate = parStartDate;
    }

    public CharSequence getEndDate() {
        return mEndDate;
    }

    public void setEndDate(CharSequence parEndDate) {
        mEndDate = parEndDate;
    }
}
