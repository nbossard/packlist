/*
 * PackList is an open-source packing-list for Android
 *
 * Copyright (c) 2018 Nicolas Bossard and other contributors.
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

package com.nbossard.packlist.process.importexport;

import com.nbossard.packlist.gui.MassImportFragment;
import com.nbossard.packlist.gui.TripDetailFragment;

import javax.inject.Singleton;
import dagger.Component;

/**
 * A Component is a mapping between one or more modules and one or more classes that will use them.
 *
 * During compilation, Dagger 2 creates concrete class implementations triggered on the Component interfaces
 * with names prefixed with Dagger, e.g., ImportExportComponent in this case.
 *
 * The Component is stored in the Application instance.
 */
@Singleton
@Component(modules = ImportExportModule.class)
public interface ImportExportComponent {

    void inject(TripDetailFragment tripDetailFragment);
    void inject(MassImportFragment massImportFragment);
    
}