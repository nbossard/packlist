# Notes about analytics

# usage

La sélection de l'analytic à utiliser se fait via le système de flavor (Build Variants) dans android studio.
 Dans une application en fonctionnement il est possible de voir l'analytic couramment utilisé dans l'écran "à propos".

# reste à faire :


## marqueurs mis en place

- ouverture de l'écran TripListFragment (liste des voyages)
- ouverture de l'écran NewTripFragment (saisie des infos d'un voyage)
- ouverture de l'écran TripDetailFragment (détail d'un voyage)
- ouverture de l'écran ItemDetailFragment (détail d'un item)
- ouverture de l'écran HelpAboutActivity (à propos)
- ouverture de l'écran HelpThirdPartyActivity (librairies tierces)
- ouverture de l'écran MassImportFragment (import d'un fichier texte)

Dans l'écran détail d'un voyage
- click sur le bouton EditTrip (modifier les infos d'un voyage)
- click sur le bouton AddItem (ajouter un item)
- click sur le bouton AddDetailedItem (ajouter un item détaillé)
- click sur le bouton action_trip__share (partager )
- click sur le bouton action_trip__import_txt (ouvrir écran import d'un fichier texte)
- click sur le bouton action_trip__unpack_all (décocher tous les items)
- click sur le bouton action_trip__sort (changement du mode de tri)

# Amazon analytics

Compte : nicolas.bossard@orange.com / t...4
Doc setup specific packlist:
https://console.aws.amazon.com/mobileanalytics/home/#/overview?consoleState=integrationSteps&appId=c5f75d79bb4f4eb59c73c89615ca1645

Doc générale howto :
https://docs.aws.amazon.com/mobile/sdkforandroid/developerguide/analytics.html#record-custom-events

> Note :
"Your app data will appear in the Amazon Mobile Analytics dashboard within 60 minutes of integrating the code and running. "

## encountered problem
- huge library, makes overpass the 64k methods limit : https://developer.android.com/studio/build/multidex.html
- bug :
> 08-17 13:55:51.482 4520-4520/com.nbossard.packlist.debug D/MobileAnalyticsManager: Amazon Mobile Analytics SDK(2.2.22) initialization successfully completed
        08-17 13:56:05.672 4520-5284/com.nbossard.packlist.debug E/DefaultDeliveryClient: AmazonServiceException occured during send of put event
                                                                                          com.amazonaws.services.cognitoidentity.model.InvalidIdentityPoolConfigurationException: Invalid identity pool configuration. Check assigned IAM roles for this pool. (Service: AmazonCognitoIdentity; Status Code: 400; Error Code: InvalidIdentityPoolConfigurationException; Request ID: 94aa7639-6471-11e6-9277-d506bb087f8c)
                                                                                              at com.amazonaws.http.AmazonHttpClient.handleErrorResponse(AmazonHttpClient.java:712)
                                                                                              at com.amazonaws.http.AmazonHttpClient.executeHelper(AmazonHttpClient.java:388)
                                                                                              at com.amazonaws.http.AmazonHttpClient.execute(AmazonHttpClient.java:199)
                                                                                              at com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient.invoke(AmazonCognitoIdentityClient.java:558)
                                                                                              at com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient.getCredentialsForIdentity(AmazonCognitoIdentityClient.java:388)
                                                                                              at com.amazonaws.auth.CognitoCredentialsProvider.populateCredentialsWithCognito(CognitoCredentialsProvider.java:651)
                                                                                              at com.amazonaws.auth.CognitoCredentialsProvider.startSession(CognitoCredentialsProvider.java:577)
                                                                                              at com.amazonaws.auth.CognitoCredentialsProvider.getCredentials(CognitoCredentialsProvider.java:371)
                                                                                              at com.amazonaws.auth.CognitoCachingCredentialsProvider.getCredentials(CognitoCachingCredentialsProvider.java:441)
                                                                                              at com.amazonaws.auth.CognitoCachingCredentialsProvider.getCredentials(CognitoCachingCredentialsProvider.java:76)
                                                                                              at com.amazonaws.services.mobileanalytics.AmazonMobileAnalyticsClient.invoke(AmazonMobileAnalyticsClient.java:362)
                                                                                              at com.amazonaws.services.mobileanalytics.AmazonMobileAnalyticsClient.putEvents(AmazonMobileAnalyticsClient.java:322)
                                                                                              at com.amazonaws.mobileconnectors.amazonmobileanalytics.internal.delivery.DefaultDeliveryClient.submitEvents(DefaultDeliveryClient.java:317)
                                                                                              at com.amazonaws.mobileconnectors.amazonmobileanalytics.internal.delivery.DefaultDeliveryClient$3.run(DefaultDeliveryClient.java:282)
                                                                                              at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
                                                                                              at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
                                                                                              at java.lang.Thread.run(Thread.java:818)
        08-17 13:56:05.682 4520-5284/com.nbossard.packlist.debug W/DefaultDeliveryClient: Unable to successfully deliver events to server. Events will be saved, error likely recoverable.  Response status code 400 , response error code InvalidIdentityPoolConfigurationExceptionInvalid identity pool configuration. Check assigned IAM roles for this pool. (Service: AmazonCognitoIdentity; Status Code: 400; Error Code: InvalidIdentityPoolConfigurationException; Request ID: 94aa7639-6471-11e6-9277-d506bb087f8c)
        08-17 13:56:05.682 4520-5284/com.nbossard.packlist.debug W/DefaultDeliveryClient: Recieved an error response: Invalid identity pool configuration. Check assigned IAM roles for this pool. (Service: AmazonCognitoIdentity; Status Code: 400; Error Code: InvalidIdentityPoolConfigurationException; Request ID: 94aa7639-6471-11e6-9277-d506bb087f8c)

solution :
modify "federated identities" in cognito service, default is created with a configuration error :-()

## consulation des analytics

https://605071407153.signin.aws.amazon.com/console
login : pcrepieux
pwd : <demander à nicolas>

puis choisir le service "mobile analytics"

ARN utilisé dans IAM manager:
arn:partition:service:region:account-id:resource
arn:aws:mobileanalytics:us-east-1:605071407153:c5f75d79bb4f4eb59c73c89615ca1645

    accountid : 605071407153
    Service : mobileanalytics

créé policy : policygen-201608181406

# Notes about Google analytics

Followed process at :
https://developers.google.com/analytics/devguides/collection/android/v4/start?configured=true
https://developers.google.com/analytics/devguides/collection/android/v4/#send-an-event

console :
https://analytics.google.com/analytics/web/?hl=fr&pli=1#realtime/rt-app-overview/a47815384w110622418p115389926/
## remarques

Vu sur leur site web :

> Note: Unlike web tracking, the Analytics Mobile SDK uploads activity signals in batches, both to conserve the device's radio and to handle periods of offline user activity.

# Azure Analytics

console access :
https://france.portal.mobileengagement.windows.net?hint=nicolas.bossard%40orange.com#project/1813/details

# Etude consommations
## utilisation de battery historian

suivre la procédure :
https://github.com/google/battery-historian

installation de paquet golang
création du dossier de workspace
ajout de GOPATH à .bashrc

> NBO ADDITION : go workspace
> export GOPATH=$HOME/gowork
> export GOBIN=$GOPATH/bin
> export PATH=${PATH}:$HOME/gowork/bin

lancement
>cd $GOPATH/src/github.com/google/battery-historian
>go run cmd/battery-historian/battery-historian.go [--port <default:9999>]

faire un reset préalable :
> adb shell dumpsys batterystats --reset

## tests réalisés
En utilisant la méthode uiAutomator#testLongUsage avec 50 boucles

 ### 1er test
conditions de test :
- mercrei 24 aout 15h20, 15h38
- devices : nexus 5X, android 6
- version avec Google Analytics
- wifi éteint, carte sim présente
- utilisation de usage timelines pour détecter les applis perturbatrices, désinstallation notamment de :
  - keep
  - désactivation de drive

Bilan 1er run :
- durée 18 minutes
- penser à mettre à jour les applications avant pour éviter du traffic lié au play store en bgd
- présence de perturbation liées à la synchro de google calendar

 ### Test checklist
- devices : nexus 5X, android 6
- version avec
- wifi éteint, carte sim présente
- synchronisation des comptes désactivée
- resetter battery historian
> adb shell dumpsys batterystats --reset
- Faire croire au système que la charge par USB n'est pas effective (https://stanfy.com/blog/android-shell-part-1-mocking-battery-status/)
> adb shell dumpsys battery unplug


 ### 2eme test
conditions de test :
- mercrei 24 aout 16h09, 15h38
- devices : nexus 5X, android 6
- version sans Analytics
- wifi éteint, carte sim présente

Bilan  run :
- durée > 18 minutes car mauvaise commande de reset
- présence de perturbation liées à la synchro de google calendar et play kiosque ==> désactivation de toutes les synchros du compte google


 ### 3ème test
- devices : nexus 5X, android 6
- version avec no analytics
- wifi éteint, carte sim présente
- synchronisation des comptes désactivée
- resetter battery historian
> adb shell dumpsys batterystats --reset
- Faire croire au système que la charge par USB n'est pas effective (https://stanfy.com/blog/android-shell-part-1-mocking-battery-status/)
> adb shell dumpsys battery unplug

Bilan :
- présence de connections radio toutes les minutes :-( nombreux trafic lié à  Google quick search box ==> remplacement du launcher google par KISS launcher


### 4ème test (jeudi 25 aout 2016)
- devices : nexus 5X, android 6
- version avec no analytics
- wifi éteint, carte sim présente
- synchronisation des comptes désactivée
- reboot
- reset battery historian
- désactivation de la charge par USB
- kiss launcher

Bilan :
mieux, mais toujours du traffic lié à googlequicksearchbox ==> forcer arrêt et limitation des données en arrière plan de "Appli google"

### 5ème test (jeudi 25 aout 2016) fin 11h46
- devices : nexus 5X, android 6
- version avec no analytics
- wifi éteint, carte sim présente
- synchronisation des comptes désactivée
- reset battery historian
- désactivation de la charge par USB
- kiss launcher
- désactivation agenda
- f-droid pas de mise à jour
- désactivation google cloud print
- désactivation appli photo
- désactivation google maps
- désinstallation google +
- désactivation google play music
- désactivation hangout
- désactivation messenger
- uninstall f-droid
- désactivation youtube
- désactivation données en arrière plan play store
- désactivation chrome
- tuer toutes les apps dans l'historique'

### 6ème test (jeudi 25 aout 2016) début 12:00 fin 13:00

- devices : nexus 5X, android 6
- reboot
- version avec no analytics
- wifi éteint, carte sim présente, 4G activé
- synchronisation des comptes désactivée
- reset battery historian
- désactivation de la charge par USB
- kiss launcher activé
- vérification avec usage timelines
- tuer toutes les apps dans l'historique'

Bilan pas mal : bien que temps déborde, qques perturbations de usage timeline


### 7ème test (jeudi 25 aout 2016) début 14:20 fin 14:36

- devices : nexus 5X, android 6
- reboot
- version avec amazon analytics
- wifi éteint, carte sim présente, 4G activé
- synchronisation des comptes désactivée
- reset battery historian
- désactivation de la charge par USB
- kiss launcher activé
- vérification avec usage timelines
- tuer toutes les apps dans l'historique'

### 8ème test (jeudi 25 aout 2016) début 15:41

- devices : nexus 5X, android 6
- installation version avec google analytics
- reboot
- wifi éteint, carte sim présente, 4G activé
- synchronisation des comptes désactivée
- vérification avec usage timelines
- reset battery historian
- désactivation de la charge par USB
- kiss launcher activé
- tuer toutes les apps dans l'historique'

bilan : oublié de faire le reset battery, inutilisable. etrangment peu de data ==> relancer en surveillant la console

### 9ème test (jeudi 25 aout 2016) début 16:10 fin 16:21

- devices : nexus 5X, android 6
- installation version avec google analytics
- reboot
- wifi éteint, carte sim présente, 4G activé
- synchronisation des comptes désactivée
- vérification avec usage timelines
- reset battery historian
- désactivation de la charge par USB
- kiss launcher activé
- tuer toutes les apps dans l'historique'

**bilan : vu dans la console les evts toutes les 2 minutes, pourtant quasiment pas de données est compté pour l'application, en fait les données sont comptees dans les google play services.**

### 10ème test (jeudi 25 aout 2016) début 16:10 fin 16:21

- devices : nexus 5X, android 6
- installation version avec azure analytics
- reboot
- wifi éteint, carte sim présente, 4G activé
- synchronisation des comptes désactivée
- vérification avec usage timelines
- reset battery historian
- désactivation de la charge par USB
- kiss launcher activé
- tuer toutes les apps dans l'historique


bilan : **vu dans la console mise à jour toutes les minutes. Mais gros prb la connection est maintenue en permanence :-( ==> discuté avec Pierre, c'est problématique mais attendu.**

# Bilan

Résumé des conditions de tests :
- Tests réalisés sur un Nexus 5x sous android 6
- Toutes applications désinstallées ou désactivées.
- wifi éteint, carte sim présente, 4G activé
- battery historian resetté avant chaque éxécution
- désactivation de la charge par USB
- synchronisation des comptes désactivée
- remplacement du launcher google par kiss launcher
- apps dans l'historique tuées

Résumé des consommations :

| analytic | CPU user time    | Mobile active time | Mobile active count | Mobile data |
| ---------|------------------|--------------------|---------------------|-------------|
| no       | 1mn 58s          | **0mn 6s**         | 1                   |   2ko       |
| google   |                  | 2mn 34             | 15                  | 138ko       |
| amazon   | 1mn51s           | 2mn 23s            | 15                  |  88ko       |
| azure    | 1mn 53s          | **12mn3s**         | 2                   | 261ko       |

A noter :
Dans battery historian les consos liées à google ne sont pas rattachées à l'appli mais aux play services.

Questions :
- google, pics de 2 minutes mutualisées entre applis ? Faisons de même avec Orange et Moi pour mutualiser ?
- quel est le cout des analytics ?

remarques : regarder le SDK sur github