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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by nbossard on 11/02/16.
 */
public class Category {

    public static final boolean ALWAYS = false;
    public static final boolean OPTIONAL = true;

// *********************** FIELDS *************************************************************************

    /** Name for category. */
    private String mName;

    /** Is it an optional category. */
    private boolean mOptional;

    /** The list of items in this category. */
    private List<CatItem> mItemList = new ArrayList<>();

// *********************** METHODS **************************************************************************

    /**
     * Rich constructor.
     *
     * @param parName
     * @param parOptional
     */
    public Category(final String parName, final boolean parOptional) {
        setName(parName);
        setOptional(parOptional);
    }

    /**
     * Should this category be automatically added to all trips or asked to user.
     * @param parOptional
     */
    public final void setOptional(final boolean parOptional) {
        mOptional = parOptional;
    }

    /**
     *
     * @param parName name for this category
     */
    public final void setName(final String parName) {
        mName = parName;
    }

    public void setItemList(List<CatItem> parItemList) {
        mItemList = parItemList;
    }

    public List<CatItem> getItemList() {
        return mItemList;
    }

    public void addItem(CatItem parItem) {
        mItemList.add(parItem);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("mName='").append(mName).append('\'');
        sb.append(", mOptional=").append(mOptional);
        sb.append(", mItemList=").append(mItemList);
        sb.append('}');
        return sb.toString();
    }
}
