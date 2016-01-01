package com.nbossard.packlist.gui;

/**
 * @author Created by nbossard on 01/01/16.
 */
public interface IMainActivity {

    /** Creation and saving of a new trip.
     * @param parName name of new trip
     * @param  parStartDate start date of new trip
     * @param parEndDate  end date of new trip.
     */
    void createNewTrip(CharSequence parName, CharSequence parStartDate, CharSequence parEndDate);
}
