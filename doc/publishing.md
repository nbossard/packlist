# Process for publishing packlist
 
 - ensure you are on branch develop
 - update translations from [crowdin project](https://crowdin.com/project/packlist), account "nbossard"
    - update files to be translated (from english)
    - ensure all languages are fully translated
    - build and download updated translations
 - save your app data as it might be lost (when running UI Automator tests)
 - run app [accessibility scanner](https://play.google.com/store/apps/details?id=com.google.android.apps.accessibility.auditor&hl=fr)
 - update plantuml global schema
 - run all tests
    - JUNIT tests in "test" folder with coverage, check coverage
    - Robotium tests in "androidTest" folder
    - UI Automator tests in "androidTest" folder
 - ensure that versionCode is increased in build.gradle
 - ensure that versionName is increased in build.gradle
 - close date in all changelog.xml
 - update README.md
 - update screenshots in pub/res_pub/incoming, see [screenshots doc](screenshots.md)
 - update pub/res_pub/incoming/**/playstore_description.md files 
 - create or update whats_new.md based on changelog.xml contents
 - delete "latest" folder content in pub/res_pub (except .gitkeep)
 - duplicate "incoming" folder content in pub/res_pub to "latest"
 - duplicate "latest" folder in pub/res_pub to "vx.x"
 - commit and push
 - change to branch master
 - git pull origin master
 - git merge develop
 - change build Variants to release
 - rebuild project
 - menu "build/generate signed apk", 
    - dont forget updating path of generation to pub/res_pub folder vx.x
    - sign V1 and V2
 - quick test apk
    - check about page
    - check what's new
    - add a new trip, add items, close and reopen
 - copy apk to latest folder
 - upload updated screenshots to android console    
 - updload APK to android console
 - add apk and screenshots to git / commit / push
 - commit and push master
 - draft new release in github
 - [update f-droid metadata file](https://gitlab.com/fdroid/fdroiddata/blob/master/metadata/com.nbossard.packlist.txt)
    - add "Build" block, do not remove old ones
    - take care of trailing spaces
    - field "Current Version"
    - field "Current Version Code"

 
## Preparing next version
 
 - go back to branch develop
 - cherry pick commit from master if required
 - update versionCode in build.gradle
 - update versionName in build.gradle
 - add new block in both changelog.xml
 - git add / commit /push
