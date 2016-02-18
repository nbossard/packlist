# Process for publishing packlist
 
 - ensure you are on branch develop
 - run all tests
 - ensure that versionCode is increased in build.gradle
 - ensure that versionName is increased in build.gradle
 - Create a new folder in pub/res_pub
 - change to branch master
 - git pull origin master
 - git merge develop
 - change build Variants to release
 - rebuild project
 - update versionCode in build.gradle
 - update versionName in build.gradle