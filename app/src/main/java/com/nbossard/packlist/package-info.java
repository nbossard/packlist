
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

/**
 * Model related classes for PackList.
 *
 * @author Nicolas BOSSARD
 */
package com.nbossard.packlist;

/*

============================== DIAGRAMS OF CLASS ==================================

// To generate graphics please use jar available at :
//     http://sourceforge.net/projects/plantuml/files/plantuml.jar/download
// syntax description can be found at :
//     http://plantuml.sourceforge.net/classes.html
//     http://www.linux-france.org/prj/edu/archinet/DA/fiche-uml-relations/fiche-uml-relations.html
@startuml
    package com.nbossard.packlist {
        !include gui/package-info.java
        !include model/package-info.java
        !include process/package-info.java

        !include PackListApp.java
    }

    com.nbossard.packlist.process.saving.ISaving <--  com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.process.saving.ITripChangeListener <|.. com.nbossard.packlist.gui.MainActivity
    com.nbossard.packlist.process.importexport.ImportExport <-- com.nbossard.packlist.gui.MassImportFragment
    com.nbossard.packlist.process.importexport.ImportExport <-- com.nbossard.packlist.gui.TripDetailFragment

    com.nbossard.packlist.gui.ItemAdapter ..> com.nbossard.packlist.model.ItemComparatorAdditionDate
    com.nbossard.packlist.gui.ItemAdapter ..> com.nbossard.packlist.model.ItemComparatorAlphabetical
    com.nbossard.packlist.gui.ItemAdapter ..>  com.nbossard.packlist.model.ItemComparatorPacking
    com.nbossard.packlist.gui.ItemAdapter --> "*" com.nbossard.packlist.model.TripItem

    com.nbossard.packlist.gui.TripAdapter --> "*" com.nbossard.packlist.model.Trip

    com.nbossard.packlist.PackListApp *-- com.nbossard.packlist.process.saving.ISaving
    com.nbossard.packlist.PackListApp ..> com.nbossard.packlist.process.saving.SavingFactory

    com.nbossard.packlist.model.SortModes <-- com.nbossard.packlist.gui.ItemAdapter

@enduml

*/
