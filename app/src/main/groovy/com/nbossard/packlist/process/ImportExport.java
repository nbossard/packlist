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
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.model.TripFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

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
     * The "checked" char ☑ , to indicate it is packed.
     */
    public static final String CHECKED_CHAR = "\u2611";

    /**
     * The "unchecked" char ☐ , to indicate it is packed.
     */
    public static final String UNCHECKED_CHAR = "\u2610";

    /**
     * The char used to separate category and item name.
     */
    public static final String CAT_NAME_SEPARATOR = ":";

    // *********************** METHODS **********************************************************************

    /**
     * Mass import items into an existing provided trip parTrip.
     *
     * @param parTrip         trip in which to be added items
     * @param parTextToImport a multiple lines text (a list ot items) to be added to parTrip
     */
    public final void massImportItems(final Trip parTrip, final String parTextToImport) {
        String[] lines = parTextToImport.split("\n");

        for (String oneLine : lines) {

            oneLine = oneLine.trim();

            // Testing if line should be ignored
            if (oneLine.startsWith(IGNORE_SYMBOL) || oneLine.length() == 0) {
                Log.d(TAG, "massImportItems: ignoring this line, because starts with " + IGNORE_SYMBOL);
            } else {
                // normal case, it is an item to be added

                Item newItem = parseOneItemLine(parTrip, oneLine);
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

        res.append(exportHeader(parContext, parRetrievedTrip));

        res.append("\n");
        for (Item oneItem : parRetrievedTrip.getListOfItems()) {
            exportOneItem(res, oneItem);
        }
        return res.toString();
    }

    /**
     * Parse one line, that is supposed to be an item.
     *
     * @param parTrip    will be used to create item.
     * @param parOneLine line to be parsed, non empty
     * @return an item ready to be added to trip
     */
    @NonNull
    @VisibleForTesting
    public final Item parseOneItemLine(final Trip parTrip, final String parOneLine) {

        String yetToBeParsed;
        boolean checked;
        String name;
        String weightStr;
        Item newItem = new Item(parTrip, "");


        // splitting in packed and rest using a regex
        // This regex has been tested using : https://regex101.com/
        // and test lists in androidTest folder
        yetToBeParsed = parOneLine;
        Pattern p0 = Pattern.compile("(" + UNCHECKED_CHAR + "|" + CHECKED_CHAR + ")(.*)");
        Matcher m0 = p0.matcher(yetToBeParsed);
        if (m0.find()) {
            String checkbox = m0.group(1).trim();
            checked = checkbox.contentEquals(CHECKED_CHAR);
            yetToBeParsed = m0.group(2);
        } else {
            checked = false;
        }

        //working on the rest
        // searching for a category
        Pattern pCat = Pattern.compile("(.*):(.*)");
        Matcher mCat = pCat.matcher(yetToBeParsed);
        if (mCat.find()) {
            String category = mCat.group(1).trim();
            yetToBeParsed = mCat.group(2);
            newItem.setCategory(category);
        }

        // splitting in name and weight using a regex

        Pattern p = Pattern.compile("\\s*(.*) ?[(]([0-9]+)g?[)]");
        Matcher m = p.matcher(yetToBeParsed);
        if (m.find()) {
            // Trying to use a regex
            name = m.group(1).trim();
            weightStr = m.group(2);
        } else {
            // 2nd try, the whole block is considered as name without weight
            name = parOneLine.trim();
            weightStr = "0";
        }

        // Building item to be parsed
        newItem.setName(name);
        newItem.setPacked(checked);
        newItem.setWeight(parseInt(weightStr));
        return newItem;
    }

    // *********************** PRIVATE METHODS ***************************************************************

    /**
     * export trip info as a human readable.
     *
     * @param parContext       will be used to create a trip formatter.
     * @param parRetrievedTrip trip to be exported and appended to result
     */
    @NonNull
    private String exportHeader(final Context parContext, final Trip parRetrievedTrip) {
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
        if (parRetrievedTrip.getNote() != null && parRetrievedTrip.getNote().length() > 0) {
            res.append(IGNORE_SYMBOL);
            res.append(parRetrievedTrip.getNote());
            res.append("\n");
        }
        if (parRetrievedTrip.getTotalWeight() > 0) {
            res.append(IGNORE_SYMBOL);
            res.append(tripFormatter.getFormattedWeight(parRetrievedTrip.getTotalWeight(),
                    parRetrievedTrip.getPackedWeight()));
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * export one item as a human readable line.
     *
     * @param parRes     result to be appended
     * @param parOneItem item to be exported and appended to result
     */
    private void exportOneItem(final StringBuilder parRes, final Item parOneItem) {
        if (parOneItem.isPacked()) {
            parRes.append(CHECKED_CHAR); // checked
        } else {
            parRes.append(UNCHECKED_CHAR); // unchecked
        }
        parRes.append(' ');
        if (parOneItem.getCategory() != null && parOneItem.getCategory().length() > 0) {
            parRes.append(parOneItem.getCategory());
            parRes.append(' ');
            parRes.append(CAT_NAME_SEPARATOR);
            parRes.append(' ');
        }
        parRes.append(parOneItem.getName());
        parRes.append(' ');
        if (parOneItem.getWeight() > 0) {
            parRes.append("(");
            parRes.append(parOneItem.getWeight());
            parRes.append("g)");
        }
        parRes.append("\n");
    }
}
