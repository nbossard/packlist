# Process for publishing packlist
 
 - ensure you are on branch develop
 - run all tests
 - ensure that versionCode is increased in build.gradle
 - ensure that versionName is increased in build.gradle
 - close date in changelog.xml
 - update readme.md
 - Create a new folder in pub/res_pub
 - commit and push
 - change to branch master
 - git pull origin master
 - git merge develop
 - change build Variants to release
 - rebuild project
 - menu "build/generate signed apk"
 - quick test apk
    - check about page
    - check what's new
    - add a new trip, add items, close and reopen
 - make new screenshots if useful
 - copy apk to pub/res_pub folder
 - updload APK to android console
 - add apk and screenshots to git / commit / push
 - commit and push master
 - draft new release in github

 
 # Preparing next version
 - go back to branch develop
 - cherry pick commit from master if required
 - update versionCode in build.gradle
 - update versionName in build.gradle
 - add new block in both changelog.xml
 - git add / commit /push
