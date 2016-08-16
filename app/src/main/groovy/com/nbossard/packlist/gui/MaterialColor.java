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
import android.support.annotation.Nullable;
import android.util.Log;
/*
@startuml
    class com.nbossard.packlist.gui.MaterialColor {
        getMatColor(...)
    }

    com.nbossard.packlist.gui.MaterialColor <.. com.nbossard.packlist.gui.TripAdapter

@enduml
 */
/**
 * Utilitary class to compute a random material color.
 *
 * @author Created by nbossard on 14/07/16.
 */

@SuppressWarnings("WeakerAccess")
public class MaterialColor {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = MassImportFragment.class.getName();

    /**
     * Fallback color that will be used if anything goes wrong.
     */
    public static final int DEFAULT_COLOR = Color.BLACK;

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
     * @param parString the string to retrieve corresponding color. Can be null, but this is stupid.
     * @return a color number
     */
    @SuppressWarnings("WeakerAccess")
    public final int getMatColor(@Nullable final String parString) {
        return getMatColor(parString, "500");
    }

    /**
     * Return a material color number, kind of random but always the same for string parString.
     * It is one of arrays.xml.
     *
     * @param parString       the string to retrieve corresponding color. Can be null, but this is stupid.
     * @param parOpacityRange the opacity range, a string, i.e. "500'. Can be null, but this is stupid.
     * @return a color number
     */
    @SuppressWarnings("WeakerAccess")
    public final int getMatColor(
            @Nullable final String parString,
            @Nullable final String parOpacityRange) {
        int returnColor = DEFAULT_COLOR;
        String ressourceName = "mdcolor_" + parOpacityRange;
        int arrayId = mContext.getResources().
                getIdentifier(ressourceName, "array", mContext.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = mContext.getResources().obtainTypedArray(arrayId);
            if (parString != null) {
                int index = Math.abs((parString.hashCode() % colors.length()));
                returnColor = colors.getColor(index, Color.BLACK);
            } else {
                Log.w(TAG, "Null parString, this is a bad usage"
                        + ". Using fallback default color = " + DEFAULT_COLOR);
            }
            colors.recycle();
        } else {
            Log.w(TAG, "Could not find array id of name = " + ressourceName
                    + ". Using fallback default color = " + DEFAULT_COLOR);
        }
        return returnColor;
    }
}
