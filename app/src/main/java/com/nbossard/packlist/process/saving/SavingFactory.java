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

package com.nbossard.packlist.process.saving;

import android.content.Context;

/*
@startuml
    interface com.nbossard.packlist.process.saving.SavingFactory {
        + {static} getNewSavingModule(...)
    }
@enduml
*/

/**
 * Factory for generating a new best {@link ISaving}.
 * @author Created by nbossard on 01/01/16.
 */
public final class SavingFactory {

    /**
     * Private constructor as this is an utilitary class.
     */
    private SavingFactory() {
    }

    /**
     * Method to provide the best saving module implementation, actually only one.
     *
     * @param parContext context to be provided to saving module.
     * @return the best saving module.
     */
    public static ISaving getNewSavingModule(final Context parContext) {
        return new PrefsSaving(parContext);
    }
}
