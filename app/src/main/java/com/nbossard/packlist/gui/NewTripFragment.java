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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nbossard.packlist.R;

/**
 * Allow user  to input trip characteristics.
 *
 * @author Created by nbossard on 30/12/15.
 */
public class NewTripFragment extends Fragment {

    private IMainActivity mHostingActivity;

    public NewTripFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHostingActivity = (IMainActivity) getActivity();
        addListenerOnSubmitButton();
    }

    private void addListenerOnSubmitButton() {
        final View mainView = getView();
        Button button = (Button) getView().findViewById(R.id.new_trip__submit__button);
        button.setOnClickListener(
            new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      TextView nameTV = (TextView) mainView.findViewById(R.id.new_trip__name__edit);
                      TextView startDateTV = (TextView) mainView.findViewById(R.id.new_trip__start_date__edit);;
                      TextView endDateTV =  (TextView) mainView.findViewById(R.id.new_trip__end_date__edit);;
                      mHostingActivity.createNewTrip(nameTV.getText(), startDateTV.getText(), endDateTV.getText());

                      getActivity().getSupportFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
                  }
              }
        );
    }

}
