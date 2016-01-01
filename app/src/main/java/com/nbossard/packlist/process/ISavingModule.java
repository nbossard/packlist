package com.nbossard.packlist.process;

import com.nbossard.packlist.model.Trip;

import java.util.List;

/**
 * Interface for all possible implementations of  saving modules.
 *
 * @author Created by nbossard on 31/12/15.
 */
public interface ISavingModule {

    /** Retrieve the list of saved trips. */
    List<Trip> loadSavedTrips();

    /** Add a new trip to the list of saved Trips. */
    void addNewTrip(Trip parTmpTrip);
}
