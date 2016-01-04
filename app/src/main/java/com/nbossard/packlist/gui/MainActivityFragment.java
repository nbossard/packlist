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
import com.nbossard.packlist.process.saving.ISavingModule;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    // *********************** FIELDS ***********************************************************************

    private ISavingModule mSavingModule;
    private View mRootView;

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
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // populating list
        populatingList();
    }

    @Override
    public void onResume() {
        super.onResume();
        populatingList();
    }

    private void populatingList() {
        ListView tripListView = (ListView) mRootView.findViewById(R.id.main__trip_list);
        List<Trip> tripList;

        tripList = mSavingModule.loadSavedTrips();

        final TripAdapter customAdapter = new TripAdapter(tripList, this.getActivity());
        tripListView.setEmptyView(mRootView.findViewById(R.id.main__trip_list_empty));
        tripListView.setAdapter(customAdapter);
        tripListView.invalidate();
    }
}
