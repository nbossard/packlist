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

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Test class for {@link Trip} class.
 * @author Created by nbossard on 23/01/16.
 */
public class TripTest  {

    private static final String TRIP_NAME = "London";
    private static final String UPDATED_TRIP_NAME = "Dublin";
    private static final GregorianCalendar TRIP_DATE = new GregorianCalendar(2015,2,25);
    private static final GregorianCalendar UPDATED_TRIP_DATE = new GregorianCalendar(2015,2,28);
    private static final GregorianCalendar TRIP_END = new GregorianCalendar(2015,3,30);
    private static final GregorianCalendar UPDATED_TRIP_END = new GregorianCalendar(2015,4,30);
    private static final String TRIP_NOTE = "A nice trip";
    private static final String UPDATED_TRIP_NOTE = "A REALLY nice trip";
    private static final String NEW_ITEM_NAME = "newItemName";
    private static final String NEW_ITEM_NAME2 = "newItemName2";
    private static final String NEW_ITEM_NAME3 = "newItemName3";
    private static final String NEW_ITEM_NAME4 = "newItemName4";

    private static final String TRIP2_NAME = "Paris";
    private static final String TRIP2_NOTE = "City of LOVE";
    private static final GregorianCalendar TRIP2_DATE = new GregorianCalendar(2015,8,25);
    private static final GregorianCalendar TRIP2_END = new GregorianCalendar(2015,8,25);

    private static final String TRIP3_NAME = "Toronto";

    private Trip mTestTrip;
    private Trip mTestTrip2;
    private Trip mTestTrip3NoDate;

    @Before
    public void setUp() throws Exception {
        mTestTrip = new Trip(TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        mTestTrip2 = new Trip(TRIP2_NAME, TRIP2_DATE, TRIP2_END, TRIP2_NOTE);
        mTestTrip3NoDate = new Trip();
        mTestTrip3NoDate.setName(TRIP3_NAME);

    }

    @Test
    public void testSetNote() throws Exception {
        assertEquals(mTestTrip.getNote(), TRIP_NOTE);
        mTestTrip.setNote(UPDATED_TRIP_NOTE);
        assertEquals(mTestTrip.getNote(), UPDATED_TRIP_NOTE);
    }

    @Test
    public void testGetNote() throws Exception {
        assertEquals(mTestTrip.getNote(), TRIP_NOTE);
        mTestTrip.setNote(UPDATED_TRIP_NOTE);
        assertEquals(mTestTrip.getNote(), UPDATED_TRIP_NOTE);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(mTestTrip.getName(), TRIP_NAME);
        mTestTrip.setName(UPDATED_TRIP_NAME);
        assertEquals(mTestTrip.getName(), UPDATED_TRIP_NAME);
    }

    @Test
    public void testSetName() throws Exception {
        assertEquals(mTestTrip.getName(), TRIP_NAME);
        mTestTrip.setName(UPDATED_TRIP_NAME);
        assertEquals(mTestTrip.getName(), UPDATED_TRIP_NAME);
    }

    @Test
    public void testGetStartDate() throws Exception {
        assertEquals(mTestTrip.getStartDate(), TRIP_DATE);
        mTestTrip.setStartDate(UPDATED_TRIP_DATE);
        assertEquals(mTestTrip.getStartDate(), UPDATED_TRIP_DATE);
    }

    @Test
    public void testSetStartDate() throws Exception {
        assertEquals(mTestTrip.getStartDate(), TRIP_DATE);
        mTestTrip.setStartDate(UPDATED_TRIP_DATE);
        assertEquals(mTestTrip.getStartDate(), UPDATED_TRIP_DATE);
    }

    @Test
    public void testGetEndDate() throws Exception {
        assertEquals(mTestTrip.getEndDate(), TRIP_END);
        mTestTrip.setEndDate(UPDATED_TRIP_END);
        assertEquals(mTestTrip.getEndDate(), UPDATED_TRIP_END);
    }

    @Test
    public void testSetEndDate() throws Exception {
        assertEquals(mTestTrip.getEndDate(), TRIP_END);
        mTestTrip.setEndDate(UPDATED_TRIP_END);
        assertEquals(mTestTrip.getEndDate(), UPDATED_TRIP_END);
    }

    @Test
    public void testGetUUID() throws Exception {
        assertNotNull(mTestTrip.getUUID().toString());
        assertTrue(mTestTrip.getUUID().toString().length()>0);
    }

    @Test
    public void testAddItem() throws Exception {
        assertTrue(mTestTrip.getListOfItems().size()==0);
        mTestTrip.addItem(NEW_ITEM_NAME);
        assertTrue(mTestTrip.getListOfItems().size()==1);
        assertNotNull(mTestTrip.getListOfItems().get(0).getName());
        assertTrue(mTestTrip.getListOfItems().get(0).getName().contentEquals(NEW_ITEM_NAME));
    }

    @Test
    public void testDeleteItem() throws Exception {

        mTestTrip.addItem(NEW_ITEM_NAME);
        UUID delUUID = mTestTrip.addItem(NEW_ITEM_NAME2);
        mTestTrip.addItem(NEW_ITEM_NAME3);
        mTestTrip.addItem(NEW_ITEM_NAME4);

        assertTrue(mTestTrip.getListOfItems().size()==4);

        mTestTrip.deleteItem(delUUID);
    }

    @Test
    public void testGetListItem() throws Exception {
        assertTrue(mTestTrip.getListOfItems().size()==0);
        mTestTrip.addItem(NEW_ITEM_NAME);
        assertTrue(mTestTrip.getListOfItems().size()==1);
    }


    @Test
    public void testCompareTo() throws Exception {
        assertTrue(mTestTrip.compareTo(mTestTrip2) < 0);
        assertTrue(mTestTrip2.compareTo(mTestTrip) > 0);
        assertTrue(mTestTrip.compareTo(mTestTrip) == 0);
    }

    @Test
    public void testClone() throws Exception {
        mTestTrip.addItem(NEW_ITEM_NAME);
        mTestTrip.addItem(NEW_ITEM_NAME2);
        Trip clonedTrip = mTestTrip.clone();

        assertTrue(mTestTrip.getNote().contentEquals(clonedTrip.getNote()));
        assertTrue(mTestTrip.getStartDate()==clonedTrip.getStartDate());
        assertTrue(mTestTrip.getEndDate()==clonedTrip.getEndDate());
        assertTrue(mTestTrip.getListOfItems().size()==clonedTrip.getListOfItems().size());

        assertTrue(mTestTrip.getListOfItems().get(0).getUUID() != clonedTrip.getListOfItems().get(0).getUUID());
        assertTrue(mTestTrip.getName().contentEquals(clonedTrip.getName()));
        assertTrue(mTestTrip.getUUID()!=clonedTrip.getUUID());
    }

    @Test
    public void testGetRemainingDays() {
        // test getRemainingDays when no start date
       assertEquals(0L, mTestTrip3NoDate.getRemainingDays());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(mTestTrip.toString());
        assertTrue(mTestTrip.toString().contains(TRIP_NAME));
        assertTrue(mTestTrip.toString().contains(TRIP_NOTE));
    }
}