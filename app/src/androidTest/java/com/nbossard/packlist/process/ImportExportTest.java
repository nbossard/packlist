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

import android.test.InstrumentationTestCase;

import com.nbossard.packlist.model.Item;
import com.nbossard.packlist.model.SortModes;
import com.nbossard.packlist.model.Trip;

import java.util.GregorianCalendar;

/**
 * Test class for {@link ImportExport} class.
 *
 * @author Created by nbossard on 01/05/16.
 */
public class ImportExportTest extends InstrumentationTestCase {

    // ********************** CONSTANTS *********************************************************************

    public static final String FIRST_LINE_NAME = "Chapeau";
    public static final String SECOND_LINE_NAME = "Livres";
    public static final int SECOND_LINE_WEIGHT = 50;
    public static final int DEFAULT_WEIGHT = 0;
    public static final int THIRD_LINE_WEIGHT = 80;
    public static final String THIRD_LINE_NAME = "Pantalons";

    // ********************** FIELDS ************************************************************************

    Trip mTestTrip;
    private ImportExport mTestPort;

    // *********************** METHODS **********************************************************************

    /**
     * Will launch activity to be tested.
     *
     * @throws Exception if a test has failed.
     */
    public final void setUp() throws Exception {
        mTestTrip = new Trip("Dublin",
                new GregorianCalendar(2016, 20, 10),
                new GregorianCalendar(2016, 25, 10),
                "Gonna be cold",
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

    public void testMassImportItems() throws Exception {

        Item importedItem0 = mTestTrip.getListOfItems().get(0);
        assertEquals(FIRST_LINE_NAME, importedItem0.getName());
        assertEquals(DEFAULT_WEIGHT, importedItem0.getWeight());

        //with weight and spaces in the middle
        Item importedItem1 = mTestTrip.getListOfItems().get(1);
        assertEquals(SECOND_LINE_NAME, importedItem1.getName());
        assertEquals(SECOND_LINE_WEIGHT, importedItem1.getWeight());

        //with weight and spaces in the middle and spaces before
        Item importedItem2 = mTestTrip.getListOfItems().get(2);
        assertEquals(THIRD_LINE_NAME, importedItem2.getName());
        assertEquals(THIRD_LINE_WEIGHT, importedItem2.getWeight());
    }

    public void testToSharableString() {
        String shareStr = mTestPort.toSharableString(getInstrumentation().getTargetContext(), mTestTrip);
        assertTrue(shareStr.contains(FIRST_LINE_NAME));
        assertTrue(shareStr.contains(SECOND_LINE_NAME + " (" + SECOND_LINE_WEIGHT + "g)"));
        assertTrue(shareStr.contains(THIRD_LINE_NAME + " (" + THIRD_LINE_WEIGHT + "g)"));
    }
}