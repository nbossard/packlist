# packlist

## An open-source packing list for Android

Why another packing-list... because existing are incomplete, out-of-date or expensive and anyway not open-source.
Which is very disappointing when you wish to save your lists and keep your phone out of always running tracking apps.

You can download APK on releases page  : https://github.com/nbossard/packlist/releases
or 
[![Get it on F-Droid](get_it_on_f-droid.png?raw=true)](https://f-droid.org/repository/browse/?fdid=com.nbossard.packlist)
or
[![Get it on Play Store](play_store_logo.png?raw=true)](https://play.google.com/store/apps/details?id=com.nbossard.packlist)

Tech note : Github and Play Store releases use same certificate. F-droid use their own certificate. 

### Objectives in term of functionality : 
 * multi-language (currently French and english but please help for others)
 * pre-filling of list throught questions on what you are planning to do during your trip
 
### Objectives : 
 * free (in terms of free beer)
 * open-source

### Objectives in term of coding :
 - multi-developer (yes you are welcome to help)
 - GitFlow organisation
 - high quality code
   - very strict formatting rules
   - mandatory usage of quality plugins : checkstyle, findbugs
   - exhaustive javadoc
   - unit testing and android testing and Robotium testing
   - contiuous integration using Travis (https://travis-ci.org/nbossard/packlist)
 - rich yet light logging (usage of https://github.com/JakeWharton/hugo)
 - large usage of plantuml for documentation
 - re-use of external libraries on GitHub
   - changelog : https://github.com/gabrielemariotti/changeloglib
   - datetimepicker (https://github.com/flavienlaurent/datetimepicker)


### BUT a place for experimentation of up-to-date technology
- mix of Groovy and Java
- material design
- android studio IDE 2 preview
- vector drawables, using asset studio
- in-app indexing
- android data-binding (http://developer.android.com/tools/data-binding/guide.html)

### Licence
Apache 2

### Devpt status
Basic functionality are still under development

### History of release
- 0.6 14th march 2016
- 0.5 18th february 2016
- 0.4 8th february 2016
- 0.3 alpha 
- 0.2 alpha 24th january 2016
- 0.1 alpha 6th january 2016

### Current IDE suggested configuration :
- Android Studio 2.1 Preview 1
    - git ignore plugin
    - markdown plugin (Markdown Support)
    - PlantUML integration plugin (by Eugene Steinberg)

### Coding conventions (WIP)
- fields should start with "m" (enforced in codeStyleSettings.xml)
- parameters should start with "par" (enforced in codeStyleSettings.xml)
