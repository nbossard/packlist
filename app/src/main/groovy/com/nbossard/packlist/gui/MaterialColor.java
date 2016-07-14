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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

/**
 * Utilitary class to compute a random material color.
 *
 * @author Created by nbossard on 14/07/16.
 */

@SuppressWarnings("WeakerAccess")
public class MaterialColor {

    // ********************** FIELDS ************************************************************************

    /**
     * Context, will be used to retrieve colors in ressources.
     */
    private final Context mContext;

    // *********************** METHODS **********************************************************************

    /**
     * Standard constructor.
     *
     * @param parContext used to retrieve one color in ressources
     */
    public MaterialColor(final Context parContext) {
        mContext = parContext;
    }

    /**
     * Return a material color number, kind of random but always the same for string parString.
     * It is one of arrays.xml.
     *
     * @param parString the string to retrieve corresponding color.
     * @return a color number
     */
    @SuppressWarnings("WeakerAccess")
    public final int getMatColor(final String parString) {
        return getMatColor(parString, "500");
    }

    /**
     * Return a material color number, kind of random but always the same for string parString.
     * It is one of arrays.xml.
     *
     * @param parString       the string to retrieve corresponding color.
     * @param parOpacityRange the opacity range, a string, i.e. "500'
     * @return a color number
     */
    @SuppressWarnings("WeakerAccess")
    public final int getMatColor(
            final String parString,
            final String parOpacityRange) {
        int returnColor = Color.BLACK;
        int arrayId = mContext.getResources().
                getIdentifier("mdcolor_" + parOpacityRange, "array", mContext.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            int index = Math.abs((parString.hashCode() % colors.length()));
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }
}
