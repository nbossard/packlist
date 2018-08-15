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

package com.nbossard.packlist.process;

import android.test.InstrumentationTestCase;

import com.nbossard.packlist.model.TripItem;
import com.nbossard.packlist.model.SortModes;
import com.nbossard.packlist.model.Trip;
import com.nbossard.packlist.process.importexport.ImportExport;

import java.util.GregorianCalendar;

/**
 * Test class for {@link ImportExport} class.
 *
 * @author Created by nbossard on 01/05/16.
 */
public class ImportExportTest extends InstrumentationTestCase {

    // ********************** CONSTANTS *********************************************************************

    private static final String TRIP_NAME = "London";
    private static final String TRIP_NOTE = "Gonna be cold";
    private static final String FIRST_LINE_NAME = "Chapeau";
    private static final String SECOND_LINE_CATEGORY = "Ludique";
    private static final String SECOND_LINE_NAME = "Livres";
    private static final int SECOND_LINE_WEIGHT = 50;
    private static final int DEFAULT_WEIGHT = 0;
    private static final int THIRD_LINE_WEIGHT = 80;
    private static final String THIRD_LINE_NAME = "Pantalons";
    private static final int START_DATE_YEAR = 2016;
    private static final int START_DATE_MONTH = 1;
    private static final int START_DATE_DAY = 16;
    private static final int END_DATE_YEAR = 2017;
    private static final int END_DATE_MONTH = 11;
    private static final int END_DATE_DAY = 25;

    // ********************** FIELDS ************************************************************************

    private Trip mTestTrip;
    private ImportExport mTestPort;

    // *********************** METHODS **********************************************************************

    /**
     * Will launch activity to be tested.
     *
     * @throws Exception if a test has failed.
     */
    public final void setUp() throws Exception {
        super.setUp();
        mTestTrip = new Trip(TRIP_NAME,
                new GregorianCalendar(START_DATE_YEAR, START_DATE_MONTH, START_DATE_DAY),
                new GregorianCalendar(END_DATE_YEAR, END_DATE_MONTH, END_DATE_DAY),
                TRIP_NOTE,
                SortModes.DEFAULT);
        mTestPort = new ImportExport();

        // line to be ignored cause starting by ignoresymbol
        String testStr = "# Dublin\n";
        // empty line to be ignored cause just spaces
        testStr += "    \n";
        // basic case
        testStr += FIRST_LINE_NAME + "\n";
        // with weight and spaces in middle
        testStr += SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)\n";
        // with weight and spaces and spaces before
        testStr += "  " + THIRD_LINE_NAME + " (" + THIRD_LINE_WEIGHT + "g)\n";

        mTestPort.massImportItems(mTestTrip, testStr);
    }

    /**
     * @see ImportExport#massImportItems(Trip, String)
     */
    public void testMassImportItems() throws Exception {

        TripItem importedItem0 = mTestTrip.getListOfItems().get(0);
        assertEquals(FIRST_LINE_NAME, importedItem0.getName());
        assertEquals(DEFAULT_WEIGHT, importedItem0.getWeight());

        //with weight and spaces in the middle
        TripItem importedItem1 = mTestTrip.getListOfItems().get(1);
        assertEquals(SECOND_LINE_NAME, importedItem1.getName());
        assertEquals(SECOND_LINE_WEIGHT, importedItem1.getWeight());

        //with weight and spaces in the middle and spaces before
        TripItem importedItem2 = mTestTrip.getListOfItems().get(2);
        assertEquals(THIRD_LINE_NAME, importedItem2.getName());
        assertEquals(THIRD_LINE_WEIGHT, importedItem2.getWeight());
    }

    /**
     * @see ImportExport#parseOneItemLine(Trip, String)
     */
    public void testParseOneItemLine() {

        // stupid line
        TripItem resItem = mTestPort.parseOneItemLine(mTestTrip, FIRST_LINE_NAME);
        assertFalse(resItem.isPacked());
        assertEquals(null, resItem.getCategory());
        assertEquals(FIRST_LINE_NAME, resItem.getName());
        assertEquals(0, resItem.getWeight());

        // line with weight
        resItem = mTestPort.parseOneItemLine(mTestTrip,
                SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)");
        assertFalse(resItem.isPacked());
        assertEquals(null, resItem.getCategory());
        assertEquals(SECOND_LINE_NAME, resItem.getName());
        assertEquals(SECOND_LINE_WEIGHT, resItem.getWeight());

        // line with weight and packed symbol
        resItem = mTestPort.parseOneItemLine(mTestTrip,
                ImportExport.CHECKED_CHAR + " " + SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)");
        assertTrue(resItem.isPacked());
        assertEquals(null, resItem.getCategory());
        assertEquals(SECOND_LINE_NAME, resItem.getName());
        assertEquals(SECOND_LINE_WEIGHT, resItem.getWeight());

        // line without weight but packed symbol
        resItem = mTestPort.parseOneItemLine(mTestTrip,
                ImportExport.CHECKED_CHAR + " " + SECOND_LINE_NAME);
        assertTrue(resItem.isPacked());
        assertEquals(null, resItem.getCategory());
        assertEquals(SECOND_LINE_NAME, resItem.getName());

        // line with weight and unpacked symbol
        resItem = mTestPort.parseOneItemLine(mTestTrip,
                ImportExport.UNCHECKED_CHAR + " " + SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)");
        assertFalse(resItem.isPacked());
        assertEquals(null, resItem.getCategory());
        assertEquals(SECOND_LINE_NAME, resItem.getName());
        assertEquals(SECOND_LINE_WEIGHT, resItem.getWeight());

        //line with category and weight and packed symbol
        resItem = mTestPort.parseOneItemLine(mTestTrip,
                ImportExport.CHECKED_CHAR + " " + SECOND_LINE_CATEGORY + " : "
                        + SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)");
        assertTrue(resItem.isPacked());
        assertEquals(SECOND_LINE_CATEGORY, resItem.getCategory());
        assertEquals(SECOND_LINE_NAME, resItem.getName());
        assertEquals(SECOND_LINE_WEIGHT, resItem.getWeight());
    }

    public void testToSharableString() {
        String shareStr = mTestPort.toSharableString(getInstrumentation().getTargetContext(), mTestTrip);
        assertTrue(shareStr.contains(ImportExport.TRIPNAME_SYMBOL));
        assertTrue(shareStr.contains(TRIP_NAME));
        assertTrue(shareStr.contains(ImportExport.TRIPDATE_SYMBOL));
        assertTrue(shareStr.contains(String.valueOf(START_DATE_YEAR)));
        assertTrue(shareStr.contains(String.valueOf(START_DATE_MONTH + 1)));
        assertTrue(shareStr.contains(String.valueOf(START_DATE_DAY)));
        assertTrue(shareStr.contains(String.valueOf(END_DATE_YEAR)));
        assertTrue(shareStr.contains(String.valueOf(END_DATE_MONTH + 1)));
        assertTrue(shareStr.contains(String.valueOf(END_DATE_DAY)));
        assertTrue(shareStr.contains(ImportExport.TRIPNOTE_SYMBOL));
        assertTrue(shareStr.contains(TRIP_NOTE));
        assertTrue(shareStr.contains(FIRST_LINE_NAME));
        assertTrue(shareStr.contains(SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)"));
        assertTrue(shareStr.contains(THIRD_LINE_NAME + " (" + THIRD_LINE_WEIGHT + "g)"));
    }
}