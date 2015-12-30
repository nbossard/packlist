package com.nbossard.packlist.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbossard.packlist.R;

/**
 * Allow user  to input trip characteristics.
 * @author Created by nbossard on 30/12/15.
 */
public class NewTripFragment extends Fragment {

    public NewTripFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_trip, container, false);
    }
}
