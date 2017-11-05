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
 * Test class for {@link ItemComparatorAlphabetical} class.
 *
 * @author Created by nbossard on 23/07/16.
 */
public class ItemComparatorAlphabeticalTest extends TestCase {

    private static final String ITEM_TEST_NAME_A = "AItemTestName";
    private static final String ITEM_TEST_NAME_B = "BItemTestName";
    private TripItem mTestItemBefore;
    private TripItem mTestItemAfter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Trip testTrip = new Trip();
        mTestItemBefore = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemAfter = new TripItem(testTrip, ITEM_TEST_NAME_B);
    }

    public void testCompare() throws Exception {
        ItemComparatorAlphabetical comparator = new ItemComparatorAlphabetical();
        assertTrue(comparator.compare(mTestItemBefore, mTestItemAfter) < 0);
        assertTrue(comparator.compare(mTestItemBefore, mTestItemBefore) == 0);
        assertTrue(comparator.compare(mTestItemAfter, mTestItemBefore) > 0);
    }

}