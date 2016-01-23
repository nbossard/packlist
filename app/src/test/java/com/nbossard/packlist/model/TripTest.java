/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2016 Nicolas Bossard.
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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test class for {@link Trip} class.
 * @author Created by nbossard on 23/01/16.
 */
public class TripTest  {

    private static final String TRIP_NAME = "London";
    private static final String UPDATED_TRIP_NAME = "Dublin";
    private static final String TRIP_DATE = "25 february 2015";
    private static final String UPDATED_TRIP_DATE = "28 february 2015";
    private static final String TRIP_END = "30 march 2015";
    private static final String UPDATED_TRIP_END = "30 april 2015";
    private static final String TRIP_NOTE = "A nice trip";
    private static final String UPDATED_TRIP_NOTE = "A REALLY nice trip";

    @Test
    public void testSetNote() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getNote(), TRIP_NOTE);
        testTrip.setNote(UPDATED_TRIP_NOTE);
        assertEquals(testTrip.getNote(), UPDATED_TRIP_NOTE);
    }

    @Test
    public void testGetNote() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getNote(), TRIP_NOTE);
        testTrip.setNote(UPDATED_TRIP_NOTE);
        assertEquals(testTrip.getNote(), UPDATED_TRIP_NOTE);
    }

    @Test
    public void testGetName() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getName(), TRIP_NAME);
        testTrip.setName(UPDATED_TRIP_NAME);
        assertEquals(testTrip.getName(), UPDATED_TRIP_NAME);
    }

    @Test
    public void testSetName() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getName(), TRIP_NAME);
        testTrip.setName(UPDATED_TRIP_NAME);
        assertEquals(testTrip.getName(), UPDATED_TRIP_NAME);
    }

    @Test
    public void testGetStartDate() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getStartDate(), TRIP_DATE);
        testTrip.setStartDate(UPDATED_TRIP_DATE);
        assertEquals(testTrip.getStartDate(), UPDATED_TRIP_DATE);
    }

    @Test
    public void testSetStartDate() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getStartDate(), TRIP_DATE);
        testTrip.setStartDate(UPDATED_TRIP_DATE);
        assertEquals(testTrip.getStartDate(), UPDATED_TRIP_DATE);
    }

    @Test
    public void testGetEndDate() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getEndDate(), TRIP_END);
        testTrip.setEndDate(UPDATED_TRIP_END);
        assertEquals(testTrip.getEndDate(), UPDATED_TRIP_END);
    }

    @Test
    public void testSetEndDate() throws Exception {
        Trip testTrip = new Trip (TRIP_NAME, TRIP_DATE, TRIP_END, TRIP_NOTE);
        assertEquals(testTrip.getEndDate(), TRIP_END);
        testTrip.setEndDate(UPDATED_TRIP_END);
        assertEquals(testTrip.getEndDate(), UPDATED_TRIP_END);
    }

    @Test
    public void testGetUUID() throws Exception {

    }

    @Test
    public void testAddItem() throws Exception {

    }

    @Test
    public void testSetListItem() throws Exception {

    }

    @Test
    public void testGetListItem() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }
}