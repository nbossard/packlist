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

package com.nbossard.packlist.model;

import junit.framework.TestCase;

/**
 * Test class for {@link ItemComparatorPacking} class.
 *
 * @author Created by nbossard on 23/07/16.
 */
public class ItemComparatorPackingTest extends TestCase {

    private static final String ITEM_TEST_NAME_A = "AItemTestName";
    private static final String ITEM_TEST_NAME_B = "BItemTestName";
    private static final String ITEM_TEST_NAME_C = "CItemTestName";
    private static final String ITEM_TEST_NAME_D = "DItemTestName";
    private TripItem mTestItemBeforeUnpackedNameA;
    private TripItem mTestItemBeforeUnpackedNameB;
    private TripItem mTestItemAfterPackedNameC;
    private TripItem mTestItemAfterPackedNameD;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Trip testTrip = new Trip();
        mTestItemBeforeUnpackedNameA = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemBeforeUnpackedNameA.setPacked(TripItem.UNPACKED);
        mTestItemBeforeUnpackedNameB = new TripItem(testTrip, ITEM_TEST_NAME_B);
        mTestItemBeforeUnpackedNameB.setPacked(TripItem.UNPACKED);
        mTestItemAfterPackedNameC = new TripItem(testTrip, ITEM_TEST_NAME_C);
        mTestItemAfterPackedNameC.setPacked(TripItem.PACKED);
        mTestItemAfterPackedNameD = new TripItem(testTrip, ITEM_TEST_NAME_D);
        mTestItemAfterPackedNameD.setPacked(TripItem.PACKED);
    }

    public void testCompare() {
        ItemComparatorPacking comparator = new ItemComparatorPacking();
        // An unpacked is before a packed
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameA, mTestItemAfterPackedNameC) < 0);
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameA, mTestItemAfterPackedNameD) < 0);
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameB, mTestItemAfterPackedNameC) < 0);

        // comparing with myself gives 0
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameA, mTestItemBeforeUnpackedNameA) == 0);
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameB, mTestItemBeforeUnpackedNameB) == 0);
        assertTrue(comparator.compare(mTestItemAfterPackedNameD, mTestItemAfterPackedNameD) == 0);

        // a packed is always after an unpacked
        assertTrue(comparator.compare(mTestItemAfterPackedNameC, mTestItemBeforeUnpackedNameA) > 0);

        // two packed are sorted alphabetical order
        assertTrue(comparator.compare(mTestItemBeforeUnpackedNameA, mTestItemBeforeUnpackedNameB) < 0);

        // two unpacked are sorted alphabetical order
        assertTrue(comparator.compare(mTestItemAfterPackedNameC, mTestItemAfterPackedNameD) < 0);
    }

}