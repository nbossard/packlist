/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2017 Nicolas Bossard and other contributors.
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

package com.nbossard.packlist.gui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup

import com.nbossard.packlist.R

import it.gmariotti.changelibs.library.view.ChangeLogRecyclerView

/*
@startuml
    class com.nbossard.packlist.gui.ChangeLogDialog {
    }
@enduml
 */

/**
 * Changelog dialog.
 *
 * @author Created by naub7473 on 19/01/2016.
 */
/**
 * Standard empty constructor.
 */
class ChangeLogDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Try to get a root view for inflater
        // doest not matter if it is null finally
        val rootView = activity!!.findViewById<View>(android.R.id.content) as ViewGroup

        val layoutInflater = activity!!.layoutInflater
        val chgList = layoutInflater.inflate(
                R.layout.changelog_fragment_dialogstandard, rootView, false) as ChangeLogRecyclerView

        return AlertDialog.Builder(activity!!, R.style.AppTheme)
                .setTitle(R.string.demo_changelog_title_standarddialog)
                .setView(chgList)
                .setPositiveButton(R.string.about_ok) { dialog, _ -> dialog.dismiss() }
                .create()

    }

}
