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
    private static final String ITEM_TEST_CATEGORY = "ItemTestCat";
    private static final String UPDATED_ITEM_TEST_NAME = "UpdatedItemTestName";
    private Item mTestItemBeforeUnpacked;
    private Item mTestItemAfterPacked;
    private Trip mTestTrip;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mTestTrip = new Trip();
        mTestItemBeforeUnpacked = new Item(mTestTrip, ITEM_TEST_NAME_A);
        mTestItemBeforeUnpacked.setPacked(Item.UNPACKED);
        mTestItemAfterPacked = new Item(mTestTrip, ITEM_TEST_NAME_B);
        mTestItemAfterPacked.setPacked(Item.PACKED);
    }

    public void testCompare() throws Exception {
        ItemComparatorPacking comparator = new ItemComparatorPacking();
        assertTrue(comparator.compare(mTestItemBeforeUnpacked, mTestItemAfterPacked) < 0);
        assertTrue(comparator.compare(mTestItemBeforeUnpacked, mTestItemBeforeUnpacked) == 0);
        assertTrue(comparator.compare(mTestItemAfterPacked, mTestItemBeforeUnpacked) > 0);
    }

}