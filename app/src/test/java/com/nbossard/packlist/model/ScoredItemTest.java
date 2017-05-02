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
 * Test class for {@link ScoredItem} class.
 *
 * @author Created by nbossard on 30/04/17.
 */
public class ScoredItemTest extends TestCase {

    private static final int LOW_SCORE = 2;
    private static final int HIGH_SCORE = 6;
    private static final String ITEM1_NAME = "Chapeau";
    private static final String ITEM2_NAME = "Boules quiès";

    private ScoredItem mSc1;
    private ScoredItem mSc2;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Item item1;
        Item item2;

        item1 = new Item();
        item2 = new Item();
        item1.setName(ITEM1_NAME);
        item2.setName(ITEM2_NAME);

        mSc1 = new ScoredItem(item1, LOW_SCORE);
        mSc2 = new ScoredItem(item2, HIGH_SCORE);
    }

    public void testGetScore() {


        // everything is ready, let's test

        assertTrue(mSc1.getScore() == LOW_SCORE);
        assertTrue(mSc2.getScore() == HIGH_SCORE);
    }
}