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

import java.util.Date;

/**
 * Test class for {@link TripItem} class.
 *
 * @author Created by nbossard on 24/01/16.
 */
public class ItemTest extends TestCase {

    private static final String ITEM_TEST_NAME = "ItemTestName";
    private static final String ITEM_TEST_CATEGORY = "ItemTestCat";
    private static final String UPDATED_ITEM_TEST_NAME = "UpdatedItemTestName";
    private TripItem mTestItem;
    private Trip mTestTrip;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mTestTrip = new Trip();
        mTestItem = new TripItem(mTestTrip, ITEM_TEST_NAME);
    }

    public void testGetName() throws Exception {
        assertTrue(mTestItem.getName().contentEquals(ITEM_TEST_NAME));
    }

    public void testSetName() throws Exception {
        assertTrue(mTestItem.getName().contentEquals(ITEM_TEST_NAME));
        mTestItem.setName(UPDATED_ITEM_TEST_NAME);
        assertTrue(mTestItem.getName().contentEquals(UPDATED_ITEM_TEST_NAME));
    }

    public void testWeight() {
        // test that default weight is 0
        assertTrue(mTestItem.getWeight()==0);
        mTestItem.setWeight(123);
        assertTrue(mTestItem.getWeight()==123);
    }

    public void testPacked() {
        // test that default weight is 0
        assertFalse(mTestItem.isPacked());
        mTestItem.setPacked(true);
        assertTrue(mTestItem.isPacked());
        mTestItem.setPacked(false);
        assertFalse(mTestItem.isPacked());
    }

    public void testCloneAndUUID() throws CloneNotSupportedException {
        // test that there is always an UUID
        assertNotNull(mTestItem.getUUID());
        TripItem clonedItem = mTestItem.clone();
        assertNotNull(clonedItem.getUUID());
        assertNotSame(mTestItem.getUUID(), clonedItem.getUUID());
    }


    public void testGetTripUUID() throws Exception {
        assertNotNull(mTestItem.getTripUUID());
        assertNotNull(mTestTrip.getUUID());
        assertTrue(mTestItem.getTripUUID().compareTo(mTestTrip.getUUID()) == 0);
    }

    public void testGetAdditionDate() throws Exception {
        Date curDate = new Date();
        assertTrue(mTestItem.getAdditionDate().getTime() - curDate.getTime() < 100);
    }

    public void testGetCategory() throws Exception {
        assertNull(mTestItem.getCategory());
        mTestItem.setCategory(ITEM_TEST_CATEGORY);
        assertEquals(ITEM_TEST_CATEGORY, mTestItem.getCategory());
    }

    public void testToString() throws Exception {
        assertNotNull(mTestItem.toString());
        assertTrue(mTestItem.toString().contains(ITEM_TEST_NAME));
    }
}