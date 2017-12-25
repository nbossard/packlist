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

import com.nbossard.packlist.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/*
@startuml
    class com.nbossard.packlist.gui.PresentableItem {
    }

    com.nbossard.packlist.model.Item <|.. com.nbossard.packlist.gui.PresentableItem
@enduml
 */

/**
 * Class for adding presentation methods to Item.
 *
 * @author Created by nbossard on 29/04/17.
 */

public class PresentableItem extends Item {


    // ********************** CONSTANTS *********************************************************************

    private static final String CATEGORY_BEFORE = "(";
    private static final String CATEGORY_AFTER = ")";

    // *********************** METHODS **********************************************************************


    public PresentableItem(final Item parOneItem) {
        super(parOneItem);
    }


    /**
     * Empty required constructor.
     */
    public PresentableItem() {
        super();
    }

    /**
     * @return a human readable Item : name followed by category
     */
    public String toPresentableString() {
        String res;
        res = getName();
        if (getCategory() != null) {
            res += " " + CATEGORY_BEFORE + getCategory() + CATEGORY_AFTER;
        }
        return res;
    }

    /**
     * @return a human readable Item : name followed by category
     */
    public static Item fromPresentableString(final String parPresentableString) {
        Item res = new Item();
        String resName;
        String resCategory = null;

        if (parPresentableString.contains(CATEGORY_BEFORE) && parPresentableString.contains(CATEGORY_AFTER)) {
            // we have a category
            String[] subTab = parPresentableString.split("[\\(\\)]");
            resName = subTab[0];
            resCategory = subTab[1];
        } else {
            // put it all
            resName = parPresentableString.trim();
        }
        res.setName(resName);
        res.setCategory(resCategory);
        return res;
    }

    public static List<String> toList(final Set<Item> parItemSet) {
        List<String> resList = new ArrayList<>();
        for (Item oneItem : parItemSet) {
            PresentableItem pres = new PresentableItem(oneItem);
            resList.add(pres.toPresentableString());
        }
        return resList;
    }
}
