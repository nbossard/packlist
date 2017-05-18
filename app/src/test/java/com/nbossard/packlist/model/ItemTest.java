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
 * Test class for {@link Item} class.
 *
 * @author Created by nbossard on 30/04/17.
 */

public class ItemTest extends TestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


    public void testEquals() {

        Item testItem = new Item();
        Item testItem2 = new Item();

        testItem.setName("crème solaire");
        testItem2.setName("crème solaire");

        assertTrue(testItem.equals(testItem2));

        testItem.setName("toto");
        testItem2.setName("bobo");

        assertFalse(testItem.equals(testItem2));

        testItem2.setName("toto");

        assertTrue(testItem.equals(testItem2));

        testItem.setCategory("clothes");

        // testItem2 does not have category ==> false
        assertFalse(testItem.equals(testItem2));

        testItem2.setCategory("clothes");

        // now they have same category ==> true
        assertTrue(testItem.equals(testItem2));

        testItem2.setCategory("various");

        // now they have different category ==> false
        assertFalse(testItem.equals(testItem2));

    }

}
