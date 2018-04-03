# Application icon

Application icon, if changed or updated needs to be : 
- saved in this folder : graphical_resources
- update file for play store "pub/res_pub/app_icon.png", resolution must be 512x512,
  visible at https://play.google.com/store/apps/details?id=com.nbossard.packlist
- update file for play store  "pub/res_pub/app_icon_playstore.png", resolution must be 1024x500
  visible when opening play store on a mobile  
- update application icon in "main/res/mipmap" folders, this must be done using image asset studio, 
  input must consist of two layers of 108x108 in order to support adaptative icons
- think to update references for contributor in about page

Documentation : 
https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive.html