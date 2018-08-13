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

    private static final String ITEM_TEST_CATP = "catP";
    private static final String ITEM_TEST_CATQ = "catQ";

    private TripItem mTestItemUnpackedNameA;
    private TripItem mTestItemUnpackedNameB;
    private TripItem mTestItemPackedNameCCatP;
    private TripItem mTestItemPackedNameDCatP;
    private TripItem mTestItemPackedNameACatQ;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Trip testTrip = new Trip();
        mTestItemUnpackedNameA = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemUnpackedNameA.setPacked(TripItem.UNPACKED);
        mTestItemUnpackedNameB = new TripItem(testTrip, ITEM_TEST_NAME_B);
        mTestItemUnpackedNameB.setPacked(TripItem.UNPACKED);
        mTestItemPackedNameCCatP = new TripItem(testTrip, ITEM_TEST_NAME_C);
        mTestItemPackedNameCCatP.setPacked(TripItem.PACKED);
        mTestItemPackedNameCCatP.setCategory(ITEM_TEST_CATP);
        mTestItemPackedNameDCatP = new TripItem(testTrip, ITEM_TEST_NAME_D);
        mTestItemPackedNameDCatP.setPacked(TripItem.PACKED);
        mTestItemPackedNameDCatP.setCategory(ITEM_TEST_CATP);
        mTestItemPackedNameACatQ = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemPackedNameACatQ.setPacked(TripItem.PACKED);
        mTestItemPackedNameACatQ.setCategory(ITEM_TEST_CATQ);
    }

    public void testCompare() {
        ItemComparatorPacking comparator = new ItemComparatorPacking();
        // An unpacked is before a packed
        assertTrue(comparator.compare(mTestItemUnpackedNameA, mTestItemPackedNameCCatP) < 0);
        assertTrue(comparator.compare(mTestItemUnpackedNameA, mTestItemPackedNameDCatP) < 0);
        assertTrue(comparator.compare(mTestItemUnpackedNameB, mTestItemPackedNameCCatP) < 0);

        // comparing with myself gives 0
        assertTrue(comparator.compare(mTestItemUnpackedNameA, mTestItemUnpackedNameA) == 0);
        assertTrue(comparator.compare(mTestItemUnpackedNameB, mTestItemUnpackedNameB) == 0);
        assertTrue(comparator.compare(mTestItemPackedNameDCatP, mTestItemPackedNameDCatP) == 0);

        // a packed item  is always after an unpacked item
        assertTrue(comparator.compare(mTestItemPackedNameCCatP, mTestItemUnpackedNameA) > 0);

        // two unpacked items are sorted category alphabetical order then name alphabetical order
        assertTrue(comparator.compare(mTestItemUnpackedNameA, mTestItemUnpackedNameB) < 0);

        // two packed items are sorted category alphabetical order then name alphabetical order
        assertTrue(comparator.compare(mTestItemPackedNameCCatP, mTestItemPackedNameDCatP) < 0);
        assertTrue(comparator.compare(mTestItemPackedNameDCatP, mTestItemPackedNameACatQ) < 0);
    }

}