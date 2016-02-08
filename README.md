# packlist

## An open-source packing list for Android

Why another packing-list... because existing are incomplete, out-of-date or expensive and any way not open-source.
Which is very disappointing when you wish to save your lists.

### Objectives in term of functionality : 
 * free
 * multi-language
 * activities based
 
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
- groovy instead of Java
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
 -0.4 8th february 2016
- 0.3 alpha 
- 0.2 alpha 24th january 2016
- 0.1 alpha 6th january 2016

### Current IDE suggested configuration :
- Android Studio 2 preview 7
    - git ignore plugin
    - markdown plugin (Markdown Support)

### Coding conventions (WIP)
- fields should start with "m" (enforced in codeStyleSettings.xml)
- parameters should start with "par" (enforced in codeStyleSettings.xml)
