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
import java.util.List;

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
 * An adapter for displaying a trip {@link TripItem} in a ListView, see {@link TripDetailFragment}.
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
        private View global;
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
            vHolderRecycle.global = parConvertView.findViewById(R.id.ia__global);
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
            vHolderRecycle.global.setBackgroundColor(curItem.getCategory().hashCode());
        } else {
            vHolderRecycle.global.setBackgroundColor(0);
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
    public void setSortMode(final SortModes parSortMode) {
        mSortMode = parSortMode;
    }

}
