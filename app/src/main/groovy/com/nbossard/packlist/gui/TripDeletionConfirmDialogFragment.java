/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard and other contributors.
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

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.nbossard.packlist.R;

/**
 * Dialog to ask user to confirm deletion of trip, as this is not a good idea we incite him not to do it.
 *
 * @author Created by nbossard on 25/07/16.
 */
@SuppressWarnings("WeakerAccess")
public class TripDeletionConfirmDialogFragment extends DialogFragment {

    // *********************** FIELDS ***********************************************************************

    /**
     * Context provided at attachment, will be used to retrieve strings from resources.
     */
    private Context mContext;

    /**
     * Listener to be called if user confirms deletion.
     */
    private DialogInterface.OnClickListener mConfirmedListener;

    // *********************** METHODS **********************************************************************

    /**
     * Required by fragments empty constructor.
     */
    public TripDeletionConfirmDialogFragment() {
    }

    @Override
    public final void onAttach(final Context context) {
        super.onAttach(context);
        mContext = getContext();
    }

    /**
     * set listener to be called if user confirms deletion.
     *
     * @param parConfirmedListener listener
     */
    public final void setConfirmedListener(final DialogInterface.OnClickListener parConfirmedListener) {
        mConfirmedListener = parConfirmedListener;
    }

    @NonNull
    @Override
    public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(mContext.getString(R.string.trip_deletion_confirm__title));
        alertDialogBuilder.setMessage(mContext.getString(R.string.trip_deletion_confirm__body));
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton(mContext.getString(android.R.string.ok), mConfirmedListener);
        alertDialogBuilder.setNegativeButton(mContext.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.dismiss();
                    }
                });


        return alertDialogBuilder.create();
    }
}
