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
 * Test class for {@link ItemComparatorCategoryAlphabetical} class.
 *
 * @author Created by nbossard on 23/07/16.
 */
public class ItemComparatorCategoryAlphabeticalTest extends TestCase {

    private static final String ITEM_TEST_NAME_A = "AItemTestName";
    private static final String ITEM_TEST_NAME_B = "BItemTestName";
    private static final String CAT_NAME_A = "Acat";
    private static final String CAT_NAME_B = "Bcat";
    private TripItem mTestItemBefore;
    private TripItem mTestItemAfter;
    private TripItem mTestItemBeforeWithCatA;
    private TripItem mTestItemBeforeWithCatB;
    private TripItem mTestSameCatDiffNameBefore;
    private TripItem mTestSameCatDiffNameAfter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Trip testTrip = new Trip();
        mTestItemBefore = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemBeforeWithCatA = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemBeforeWithCatA.setCategory(CAT_NAME_A);
        mTestItemBeforeWithCatB = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestItemBeforeWithCatB.setCategory(CAT_NAME_B);
        mTestItemAfter = new TripItem(testTrip, ITEM_TEST_NAME_B);

        mTestSameCatDiffNameBefore = new TripItem(testTrip, ITEM_TEST_NAME_A);
        mTestSameCatDiffNameBefore.setCategory(CAT_NAME_A);
        mTestSameCatDiffNameAfter = new TripItem(testTrip, ITEM_TEST_NAME_B);
        mTestSameCatDiffNameBefore.setCategory(CAT_NAME_A);
    }

    public void testCompare() throws Exception {
        ItemComparatorCategoryAlphabetical comparator = new ItemComparatorCategoryAlphabetical();

        // No  category so alphabetical
        assertTrue(comparator.compare(mTestItemBefore, mTestItemAfter) < 0);
        assertTrue(comparator.compare(mTestItemBefore, mTestItemBefore) == 0);
        assertTrue(comparator.compare(mTestItemAfter, mTestItemBefore) > 0);

        // item with categories first
        mTestItemBefore.setCategory(CAT_NAME_A);
        assertTrue(comparator.compare(mTestItemBeforeWithCatA, mTestItemAfter) < 0);
        assertTrue(comparator.compare(mTestItemBeforeWithCatA, mTestItemBeforeWithCatA) == 0);


        // Both have different categories so categories alphabetical
        assertTrue(comparator.compare(mTestItemBeforeWithCatA, mTestItemBeforeWithCatB) < 0);
        assertTrue(comparator.compare(mTestItemBeforeWithCatA, mTestItemBeforeWithCatA) == 0);
        assertTrue(comparator.compare(mTestItemBeforeWithCatB, mTestItemBeforeWithCatA) > 0);

        // Both have same category so name alphabetical
        assertTrue(comparator.compare(mTestSameCatDiffNameBefore, mTestSameCatDiffNameAfter) < 0);
        assertTrue(comparator.compare(mTestSameCatDiffNameBefore, mTestSameCatDiffNameBefore) == 0);
        assertTrue(comparator.compare(mTestSameCatDiffNameAfter, mTestSameCatDiffNameBefore) > 0);
    }

}