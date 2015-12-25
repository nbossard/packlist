package com.nbossard.packlist.dui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nbossard.packlist.R;
import com.nbossard.packlist.model.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ListView mTripListView;

    public MainActivityFragment() {
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
        mTripListView= (ListView) getView().findViewById(R.id.main__trip_list);
        List<Trip> testList= new ArrayList<>();

        // TODO remove this stub
        testList.add(new Trip("Verdun", "23 décembre 2015", "28 décembre 2015"));
        testList.add(new Trip("Guadeloupe", "1er mars 2016", "15 mars 2016"));

        final TripAdapter customAdapter = new TripAdapter(testList, this.getActivity());
        mTripListView.setAdapter(customAdapter);

    }
}
