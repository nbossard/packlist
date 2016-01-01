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
