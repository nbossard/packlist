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

package com.nbossard.packlist.gui;

import android.test.InstrumentationTestCase;


/**
 * Robotium tests on {@link MaterialColor}
 *
 * @author Created by nbossard on 26/07/16.
 */
public class MaterialColorTest extends InstrumentationTestCase {

    private MaterialColor mMaterialColorTested;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMaterialColorTested = new MaterialColor(getInstrumentation().getTargetContext());
    }

    /**
     * Testing getMaterialColor, version with one parameter
     *
     * @throws Exception if test fails
     */
    public void testGetMatColor() throws Exception {
        // Testing normal case, checking it is constant
        int resColor = mMaterialColorTested.getMatColor("test", "500");
        int resColor2 = mMaterialColorTested.getMatColor("test", "500");
        assertEquals(resColor, resColor2);

        // testing special values : empty or null strings
        mMaterialColorTested.getMatColor("", "");
        assertEquals(MaterialColor.DEFAULT_COLOR, mMaterialColorTested.getMatColor(null, null));
    }

    /**
     * Testing getMaterialColor, version with two parameters
     *
     * @throws Exception if test fails
     */
    public void testGetMatColor1() throws Exception {
        // Testing normal case, checking it is constant
        int resColor = mMaterialColorTested.getMatColor("test", "500");
        int resColor2 = mMaterialColorTested.getMatColor("test", "500");
        assertEquals(resColor, resColor2);

        // testing special values : empty or null strings
        mMaterialColorTested.getMatColor("test", "");
        assertEquals(MaterialColor.DEFAULT_COLOR, mMaterialColorTested.getMatColor("test", "invalid"));
        assertEquals(MaterialColor.DEFAULT_COLOR, mMaterialColorTested.getMatColor("test", null));
    }

}