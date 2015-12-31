package com.nbossard.packlist.gui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nbossard.packlist.PackListApp;
import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.ISavingModule;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    // *********************** FIELDS ***********************************************************************

    private ISavingModule mSavingModule;

    // *********************** METHODS **********************************************************************

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavingModule = ((PackListApp) getActivity().getApplication()).getSavingModule();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populating list
        ListView tripListView = (ListView) getView().findViewById(R.id.main__trip_list);
        List<Trip> testList;

        testList = mSavingModule.loadSavedTrips();

        final TripAdapter customAdapter = new TripAdapter(testList, this.getActivity());
        tripListView.setAdapter(customAdapter);

    }
}
