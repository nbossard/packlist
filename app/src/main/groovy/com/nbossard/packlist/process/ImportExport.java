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

package com.nbossard.packlist.process;

import android.content.Context;
import android.util.Log;

import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.model.TripFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//CHECKSTYLE:OFF: LineLength
/*
@startuml
    class com.nbossard.packlist.process.ImportExport {
        + massImportItems(...)
        + toSharableString(...)
    }
@enduml
 */
//CHECKSTYLE:ON: LineLength

/**
 * Regrouping of classes related to (mass) import / export (share).
 *
 * @author Created by nbossard on 01/05/16.
 */
public class ImportExport {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = ImportExport.class.getName();

    // *********************** FIELDS ***********************************************************************


    /**
     * Char to be added at start of line when sharing to mark this is not an item
     * but a line part of trip header.
     */
    private static final String IGNORE_SYMBOL = "# ";

    /**
     * Mass import items into an existing provided trip parTrip.
     *
     * @param parTrip         trip in which to be added items
     * @param parTextToImport a multiple lines text (a list ot items) to be added to parTrip
     */
    public final void massImportItems(final Trip parTrip, final String parTextToImport) {
        String[] lines = parTextToImport.split("\n");
        String name;
        String weightStr;

        for (String oneLine : lines) {

            // Testing if line should be ignored
            if (oneLine.startsWith(IGNORE_SYMBOL)) {
                Log.d(TAG, "massImportItems: ignoring this line, because starts with " + IGNORE_SYMBOL);
            } else {
                // normal case, it is an item to be added

                // splitting in name and weight using a regex
                Pattern p = Pattern.compile("\\s*(.*) ?[(]([0-9]+)g?[)]");
                Matcher m = p.matcher(oneLine);
                if (m.find()) {
                    // Trying to use a regex
                    name = m.group(1).trim();
                    weightStr = m.group(2);
                } else {
                    // 2nd try, all xonsidered as name without weight
                    name = oneLine.trim();
                    weightStr = "0";
                }

                Item newItem = new Item(parTrip, name);
                newItem.setWeight(Integer.valueOf(weightStr));
                parTrip.addItem(newItem);
            }
        }
    }


    /**
     * Make a pretty plaintext presentation of trip so we can share it.
     *
     * @param parContext       will be provided to {@link TripFormatter}
     * @param parRetrievedTrip trip to be shared
     * @return trip as a human readable string
     */
    public final String toSharableString(final Context parContext, final Trip parRetrievedTrip) {
        StringBuilder res = new StringBuilder();
        TripFormatter tripFormatter = new TripFormatter(parContext);

        if (parRetrievedTrip.getName() != null) {
            res.append(IGNORE_SYMBOL);
            res.append(parRetrievedTrip.getName());
            res.append("\n");
        }
        if ((parRetrievedTrip.getStartDate() != null) && (parRetrievedTrip.getStartDate() != null)) {
            res.append(IGNORE_SYMBOL);
            if (parRetrievedTrip.getStartDate() != null) {
                res.append(tripFormatter.getFormattedDate(parRetrievedTrip.getStartDate()));
            }
            res.append("\u2192"); // Arrow right
            if (parRetrievedTrip.getEndDate() != null) {
                res.append(tripFormatter.getFormattedDate(parRetrievedTrip.getEndDate()));
            }
            res.append("\n");
        }
        if (parRetrievedTrip.getNote() != null) {
            res.append(IGNORE_SYMBOL);
            res.append(parRetrievedTrip.getNote());
            res.append("\n");
        }
        res.append(IGNORE_SYMBOL);
        res.append(tripFormatter.getFormattedWeight(parRetrievedTrip.getTotalWeight(),
                parRetrievedTrip.getPackedWeight()));
        res.append("\n");
        res.append("\n");
        for (Item oneItem : parRetrievedTrip.getListOfItems()) {
            if (oneItem.isPacked()) {
                res.append("\u2611"); // checked
            } else {
                res.append("\u2610"); // unchecked
            }
            res.append(" ");
            res.append(oneItem.getName());
            res.append(" ");
            if (oneItem.getWeight() > 0) {
                res.append("(");
                res.append(oneItem.getWeight());
                res.append("g)");
            }
            res.append("\n");
        }
        return res.toString();
    }
}
