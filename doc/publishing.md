# Process for publishing packlist
 
 - ensure you are on branch develop
 - run all tests
 - ensure that versionCode is increased in build.gradle
 - ensure that versionName is increased in build.gradle
 - close date in changelog.xml
 - update readme.md
 - update screenshots in pub/res_pub/incoming, see [screenshots doc](screenshots.md)
 - create or update whats_new.md based on changelog.xml contents
 - delete "latest" folder
 - Rename "incoming" folder in pub/res_pub to "latest"
 - duplicate "latest" folder in pub/res_pub to "vx.x"
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
 - copy apk to pub/res_pub folder vx.x and latest
 - upload screenshots to android console    
 - updload APK to android console
 - add apk and screenshots to git / commit / push
 - commit and push master
 - draft new release in github
 - [update f-droid metadata file](https://gitlab.com/fdroid/fdroiddata/blob/master/metadata/com.nbossard.packlist.txt)
    - field "Current Version"
    - field "Current Version Code"

 
## Preparing next version
 
 - go back to branch develop
 - cherry pick commit from master if required
 - update versionCode in build.gradle
 - update versionName in build.gradle
 - add new block in both changelog.xml
 - git add / commit /push
