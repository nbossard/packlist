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
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nbossard.packlist.R;

import hugo.weaving.DebugLog;
import it.gmariotti.changelibs.library.view.ChangeLogRecyclerView;

/*
@startuml
    class com.nbossard.packlist.gui.DialogStandardFrag {
    }
@enduml
 */

/**
 * Changelog dialog.
 *
 * Created by naub7473 on 19/01/2016.
 */
public class DialogStandardFrag extends DialogFragment {

    public DialogStandardFrag() {
    }

    @DebugLog
    @Override
    public final Dialog onCreateDialog(final Bundle savedInstanceState) {

        // Try to get a root view for inflater
        // doest not matter if it is null finally
        ViewGroup rootView = (ViewGroup) getActivity().findViewById(android.R.id.content);

        LayoutInflater layoutInflater =  getActivity().getLayoutInflater();
        ChangeLogRecyclerView chgList =
                (ChangeLogRecyclerView) layoutInflater.inflate(
                        R.layout.demo_changelog_fragment_dialogstandard, rootView, false);

        return new AlertDialog.Builder(getActivity(), R.style.AppTheme)
                .setTitle(R.string.demo_changelog_title_standarddialog)
                .setView(chgList)
                .setPositiveButton(R.string.about_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();

    }

}
