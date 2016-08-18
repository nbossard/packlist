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

# Notes about Google analytics

Followed process at :
https://developers.google.com/analytics/devguides/collection/android/v4/start?configured=true
https://developers.google.com/analytics/devguides/collection/android/v4/#send-an-event

ARN utilisé dans IAM manager:
arn:partition:service:region:account-id:resource
arn:aws:mobileanalytics:us-east-1:605071407153:c5f75d79bb4f4eb59c73c89615ca1645

    accountid : 605071407153
    Service : mobileanalytics

créé policy : policygen-201608181406


## consulation des analytics

https://605071407153.signin.aws.amazon.com/console
login : pcrepieux
pwd : <demander à nicolas>

puis choisir le service "mobile analytics"


## remarques

Vu sur leur site web :

> Note: Unlike web tracking, the Analytics Mobile SDK uploads activity signals in batches, both to conserve the device's radio and to handle periods of offline user activity.

