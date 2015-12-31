package com.nbossard.packlist.process;

import com.nbossard.packlist.model.Trip;

import java.util.List;

/**
 * @author Created by nbossard on 31/12/15.
 */
public interface ISavingModule {

    /** Retrieve the list of saved trips. */
    List<Trip> loadSavedTrips();

}
