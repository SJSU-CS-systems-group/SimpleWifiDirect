# WiFiDirect

A SJSU CS Systems Group Disconnected Data Distribution project under Prof. Ben Reed.

## About:

- This repository contains WifiDirect code salvaged from https://github.com/SJSU-CS-systems-group/WiFiDirect. This code contains a simple android app to test the sjsu.ddd.android.wifidirect package located under app/src/main/java in this repo.
 

## Requirements:
- Either Android Studio or Gradle and JDK installed (Android Studio comes with both automatically)
- Android Smart Phone

## Installation and Running this code :
- Download and install Android Studio https://developer.android.com/studio 
- Clone this repository
```
git clone https://github.com/SJSU-CS-systems-group/SimpleWifiDirect
```
- Open up Android Studio. If it is your first time opening up Android Studio once you have finished configuring any settings that pop up when you get to a screen with a button that has an option to create a new project click the Open button next to it instead and open up the repository you just cloned.

- If you already have a project opened up in Android Studio go to the top left under File > New > Import Project and click on the folder containing this repository.

- A window containing the project should pop up and android studio should run the Gradle Sync to build the project automatically. At the top of the window go there should an option Build > Run Generate Sources Gradle Tasks. NOTE: SJSU Wi-Fi as of 9-15-2021 apparently blocks Gradle from syncing and downloading needed dependencies. Recommend doing this part on a non- SJSU Wi-Fi network or by hotspotting your smartphone using cellular data.

- After the Gradle Sync is finished, connect your Android smartphone directly to your computer. On your Android device go to Settings > Developer options and then click to enable USB debugging. If every is working correctly somewhere to the top right of your screen in Android Studio should display your phone name.

- Click the run button next to your phone name and the program should run and display on your phone.

- The app should display but will not display other devices until you give the app location permissions. Swipe out of the app on hold click onto the app and click app info. Give the app location and file permissions.

- Congratulations you have have the basic app running!

## Additional Notes :

- Join the Facebook Group for additional documentation about this project https://www.facebook.com/groups/sjsu.cs.ddd
Ask Ben for permission :)

- Android Studio likes to change it's UI every couple of years, so try to find Android Studio tutorials at most 1 -2 years before the time you are currently reading this.

- See SimpleWifiDirectGuide.pdf for a companion guide for understanding this app.

- Good Luck!
