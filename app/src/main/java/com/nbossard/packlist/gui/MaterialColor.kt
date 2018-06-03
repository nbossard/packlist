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

package com.nbossard.packlist.gui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.Log

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
 * Colors are one of "material colors" listed in "arrays.xml"
 * This is used for computing background color of round icons in trip list.
 *
 * @author Created by nbossard on 14/07/16.
 */

class MaterialColor
// *********************** METHODS **********************************************************************

/**
 * Standard constructor.
 *
 * @param parContext used to retrieve one color in ressources
 */
(
        // ********************** FIELDS ************************************************************************

        /**
         * Context, will be used to retrieve colors in ressources.
         */
        private val mContext: Context) {

    /**
     * Return a material color number, kind of random but always the same for string parString.
     * It is one of arrays.xml.
     * This is for round icons bgd, so the opacity is high to be readable with a white text.
     *
     * @param parString the string to retrieve corresponding color. Can be null, but this is stupid.
     * @return a color number
     */
    fun getMatColorForIcon(parString: String?): Int {
        return getMatColor(parString, "500")
    }

    /**
     * Return a material color number, kind of random but always the same for string parString.
     * It is one of arrays.xml.
     *
     * @param parString       the string to retrieve corresponding color. Can be null, but this is stupid.
     * @param parOpacityRange the opacity range, a string, i.e. "500'. Can be null, but this is stupid.
     * @return a color number
     */
    fun getMatColor(
            parString: String?,
            parOpacityRange: String?): Int {
        var returnColor = DEFAULT_COLOR
        val ressourceName = "mdcolor_" + parOpacityRange!!
        val arrayId = mContext.resources.getIdentifier(ressourceName, "array", mContext.packageName)

        if (arrayId != 0) {
            val resColorsArray = mContext.resources.obtainTypedArray(arrayId)
            if (parString != null) {
                val index = Math.abs(parString.hashCode() % resColorsArray.length())
                returnColor = resColorsArray.getColor(index, Color.BLACK)
            } else {
                Log.w(TAG, "Null parString, this is a bad usage"
                        + ". Using fallback default color = " + DEFAULT_COLOR)
            }
            resColorsArray.recycle()
        } else {
            Log.w(TAG, "Could not find array id of name = " + ressourceName
                    + ". Using fallback default color = " + DEFAULT_COLOR)
        }
        return returnColor
    }

    companion object {

        // ********************** CONSTANTS *********************************************************************

        /**
         * Log tag.
         */
        private val TAG = MassImportFragment::class.java.name

        /**
         * Fallback color that will be used if anything goes wrong.
         */
        val DEFAULT_COLOR = Color.BLACK
    }
}
