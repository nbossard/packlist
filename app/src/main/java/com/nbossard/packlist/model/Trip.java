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
 * @author Created by nbossard on 25/12/15.
 */
public class Trip {

    private String mName;
    private String mStartDate;
    private String mEndDate;

    public String getName() {
        return mName;
    }

    public void setName(String parName) {
        mName = parName;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String parStartDate) {
        mStartDate = parStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String parEndDate) {
        mEndDate = parEndDate;
    }
}
