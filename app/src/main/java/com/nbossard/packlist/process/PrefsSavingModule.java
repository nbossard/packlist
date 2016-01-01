package com.nbossard.packlist.process;

import com.nbossard.packlist.model.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by nbossard on 31/12/15.
 */
public class PrefsSavingModule implements ISavingModule {

    @Override
    public List<Trip> loadSavedTrips() {
        List<Trip> testList= new ArrayList<>();
        testList.add(new Trip("Verdun", "23 décembre 2015", "28 décembre 2015"));
        testList.add(new Trip("Guadeloupe", "1er mars 2016", "15 mars 2016"));
        return testList;
    }

    @Override
    public void addNewTrip(Trip parTmpTrip) {
        // TODO implement this
    }
}
