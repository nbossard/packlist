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

package com.nbossard.packlist.gui;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nbossard.packlist.R;
import com.nbossard.packlist.model.TripItem;
import com.nbossard.packlist.model.ItemComparatorAdditionDate;
import com.nbossard.packlist.model.ItemComparatorAlphabetical;
import com.nbossard.packlist.model.ItemComparatorCategoryAlphabetical;
import com.nbossard.packlist.model.ItemComparatorPacking;
import com.nbossard.packlist.model.SortModes;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;

/*
@startuml
    class com.nbossard.packlist.gui.ItemAdapter {
        mItemList
        mSortMode
    }

@enduml
*/

/**
 * An adapter for displaying an item {@link TripItem} in a ListView, see {@link TripDetailFragment}.
 *
 * @author Created by nbossard on 17/01/16.
 */
class ItemAdapter extends BaseAdapter {

    // ********************** CONSTANTS *********************************************************************

    /**
     * Log tag.
     */
    private static final String TAG = ItemAdapter.class.getName();

    // *********************** INNER CLASS *****************************************************************

    /**
     * View holder, works better for recycling of views.
     *
     * @author Nicolas BOSSARD
     *
     */
    private class InnerMyViewHolder
    {
        /**
         * The whole row.
         */
        private View globalRow;
        /**
         * Reference (result of findviewbyid) to the item category.
         */
        private TextView tvCategory;
        /**
         * Reference (result of findviewbyid) to the item name.
         */
        private TextView tvName;
        /**
         * Reference (result of findviewbyid) to the is packed checkbox.
         */
        private AppCompatCheckBox tvIsPacked;

    }
    // ********************** FIELDS ************************************************************************

    /**
     * User selected item sort mode.
     */
    private SortModes mSortMode = SortModes.DEFAULT;

    /**
     * Items to be displayed in the list.
     */
    private final List<TripItem> mItemList;

    /**
     * Provided context.
     */
    private final Context mContext;

    /**
     * Cache for preventing recomputing colors for each line.
     */
    private final Map<String, Integer> mBgdColorsCache;

    // *********************** METHODS **********************************************************************

    /**
     * Standard constructor.
     *
     * @param parResList
     *            data to be displayed in the list.
     * @param parContext
     *            context sic
     */
    ItemAdapter(final List<TripItem> parResList, final Context parContext)
    {
        super();
        mItemList = parResList;
        mContext = parContext;

        mBgdColorsCache = new HashMap<>();
    }

    // For applying sorting
    @Override
    public void notifyDataSetChanged() {

        Comparator<? super TripItem> itemComparator = null;
        if (mSortMode == SortModes.DEFAULT) {
            itemComparator = new ItemComparatorAdditionDate();
        } else if (mSortMode == SortModes.UNPACKED_FIRST) {
            itemComparator = new ItemComparatorPacking();
        } else if (mSortMode == SortModes.ALPHABETICAL) {
            itemComparator = new ItemComparatorAlphabetical();
        } else if (mSortMode == SortModes.CATEGORY) {
            itemComparator = new ItemComparatorCategoryAlphabetical();
        }
        Log.d(TAG, "sorting mode is : " + mSortMode);
        Collections.sort(mItemList, itemComparator);

        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(final int parPosition) {
        return mItemList.get(parPosition);
    }

    @Override
    public long getItemId(final int parPosition) {
        return parPosition;
    }

    @Override
    @DebugLog
    public View getView(final int parPosition,
                        @SuppressWarnings("CheckStyle") View parConvertView,
                        final ViewGroup parParentView) {
        InnerMyViewHolder vHolderRecycle;
        if (parConvertView == null)
        {
            vHolderRecycle = new InnerMyViewHolder();
            final LayoutInflater inflater = (LayoutInflater) ItemAdapter.this.
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            parConvertView = inflater.inflate(R.layout.item_adapter, parParentView, false);

            // getting views
            vHolderRecycle.globalRow = parConvertView.findViewById(R.id.ia__global);
            vHolderRecycle.tvCategory = (TextView) parConvertView.findViewById(R.id.ia__category);
            vHolderRecycle.tvName = (TextView) parConvertView.findViewById(R.id.ia__name);
            vHolderRecycle.tvIsPacked = (AppCompatCheckBox) parConvertView.findViewById(R.id.ia__packed);
        } else
        {
            vHolderRecycle = (InnerMyViewHolder) parConvertView.getTag();
        }

        final TripItem curItem = mItemList.get(parPosition);

        // updating views
        if (curItem.getCategory() != null) {
            int bgColor = getBgColor(curItem.getCategory());
            vHolderRecycle.globalRow.setBackgroundColor(bgColor);
        } else {
            vHolderRecycle.globalRow.setBackgroundColor(0);
        }
        String nameAndWeight = curItem.getName();
        if (curItem.getWeight() > 0) {
            nameAndWeight += "(" + curItem.getWeight() + "g)";
        }
        if (curItem.getCategory() != null && curItem.getCategory().length() > 0) {
            vHolderRecycle.tvCategory.setVisibility(View.VISIBLE);
            vHolderRecycle.tvCategory.setText(curItem.getCategory());
        } else {
            vHolderRecycle.tvCategory.setVisibility(View.GONE);
        }
        vHolderRecycle.tvName.setText(nameAndWeight);
        vHolderRecycle.tvIsPacked.setChecked(curItem.isPacked());

        // saving viewholder
        parConvertView.setTag(vHolderRecycle);
        return parConvertView;
    }

    /**
     * Set current items sorting mode.
     *
     * @param parSortMode new sorting mode to use
     */
    @DebugLog
    void setSortMode(final SortModes parSortMode) {
        mSortMode = parSortMode;
    }

    /**
     * Get a color automatically, based on category name.
     * BUT the color has to be so that text is readable by a human.
     *
     * @param parCategory item category for which we need a category background color
     * @return a color as an int ready to be used for
     */
    @DebugLog
    private int getBgColor(final String parCategory) {
        @ColorInt int candidateColor;
        // searching in cache
        if (mBgdColorsCache.containsKey(parCategory)) {
            candidateColor = mBgdColorsCache.get(parCategory);
            Log.d(TAG, "Found color in cache");
        } else {
            // not in cache computing it
            Log.d(TAG, "Did NOT Found color in cache, computing it");
            candidateColor = parCategory.hashCode();
            while (luminance(candidateColor) < 0.5) {
                // this color is too dark to be readable with a black text
                candidateColor = increaseLuminance(candidateColor);
                Log.d(TAG, "Color is too dark : " + candidateColor + ", improving luminance");
            }
            Log.d(TAG, "Found color with accurate luminance");
            mBgdColorsCache.put(parCategory, candidateColor);
        }
        return candidateColor;
    }

    // *********************** PRIVATE METHODS ***************************************************************

    /**
     * Computes color luminance, either directly if android N+ or using approximation found on stackoverflow.
     *
     * @param parColorToGetLuminance an integer representing a color
     * @return a value between 0 (darkest black) and 1 (lightest white)
     */
    private double luminance(@ColorInt int parColorToGetLuminance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Color.luminance(parColorToGetLuminance);
        } else {
            return (0.2126 * Color.red(parColorToGetLuminance)
                    + 0.7152 * Color.green(parColorToGetLuminance)
                    + 0.0722 * Color.blue(parColorToGetLuminance));
        }
    }

    /**
     * Increase luminance of provided color.
     *
     * @param colorIntValue a color integer with too low luminance
     * @return a color integer with better luminance
     */
    private int increaseLuminance(@ColorInt final int colorIntValue) {
        float[] updatedColor = new float[3];
        Color.colorToHSV(colorIntValue, updatedColor);

        // decreasing Saturation and increasing Value will increase luminance
        updatedColor[1] = updatedColor[1] / 2;
        updatedColor[2] = updatedColor[2] * 2;
        if (updatedColor[2] > 1) {
            updatedColor[2] = 1;
        }

        return Color.HSVToColor(updatedColor);
    }
}
