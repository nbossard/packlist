# Notes about Google analytics

Followed process at :
https://developers.google.com/analytics/devguides/collection/android/v4/start?configured=true
https://developers.google.com/analytics/devguides/collection/android/v4/#send-an-event

# marqueurs mis en place

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

# consulation des analytics
https://analytics.google.com/analytics/web/

# remarques

Vu sur leur site web :

> Note: Unlike web tracking, the Analytics Mobile SDK uploads activity signals in batches, both to conserve the device's radio and to handle periods of offline user activity.

