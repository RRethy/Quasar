# Quasar #

> Twinkle, twinkle, quasi-star,
> Biggest puzzle from afar,
> How unlike the other ones,
> Brighter than a billion suns,
> Twinkle, twinkle, quasi-star,
> How I wonder what you are.

<img src="https://user-images.githubusercontent.com/21000943/34430280-6d5c8dd8-ec31-11e7-8264-828e6ee0cc34.png" title="ToonUp">

<a href="https://play.google.com/store/apps/details?id=com.bonnetrouge.quasar" alt="Play Store Release"><img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="215" height="83" title="Play Store"/></a>

## Description ##

This is a very simple Android app that uses a custom overlay to show any image off the users the phone as a filter/watermar on top of the screen. Accessibility is also used as a hack to automatically hide and show the overlay since Android won't let the user accept any permissions or change certain settings when there is an overlay showing due to security concerns.

## Check it out! ##

[See it in action on Youtube](https://youtu.be/_h-Ca2HfPSc)

<img src="https://user-images.githubusercontent.com/21000943/34636527-7c23761a-f271-11e7-8be2-8cd8e8f17509.png" width="250" height="444"> <img src="https://user-images.githubusercontent.com/21000943/34636523-7bcee5a0-f271-11e7-822d-8b7e54bcf947.png" width="250" height="444"> <img src="https://user-images.githubusercontent.com/21000943/34636522-7bbfe564-f271-11e7-87f8-886c9632c986.png" width="250" height="444"> <img src="https://user-images.githubusercontent.com/21000943/34636528-7c4028aa-f271-11e7-9be5-6dcad67fc5f1.png" width="250" height="444"> <img src="https://user-images.githubusercontent.com/21000943/34636529-7cbcdee0-f271-11e7-8f74-434592116798.png" width="250" height="444">

## Class Descriptions ##

### OverlayService.java ###

This is the core, it's in this service that we inflate the xml (view_overlay.xml), give it the correct layout parameters (specifically WindowManager.LayoutParams.TYPE_PHONE and WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE), and add it to the window manager. Once the inflated view is added to the window manager, it will show up on top of all other views on the screen, I added an alpha so the views underneath are not totally blocked. To hide the overlay, the window manager has a 'removeViewImmediate()' that will stop the overlay without killing the service. This class also has a broadcast receiver for the OverlayAccessibilityService which is expanded on below.

### OverlayAccessibilityService.java ###

This is what listens for all events, we then filter the events based off TYPE_WINDOW_STATE_CHANGED since those are changes to the current screen in the UI, the important information for the current screen that gets shown is the class name, and the package name. The package/class name for the current screen is the compared with a 'ban list' I accumulated through a mix of testing and decompiling screen filter apps that had a similar feature. If it is a screen that cannot have an overlay shown, then it will send a broadcast which the OverlayService will receive and act accordingly.

### accessibility_service_config.java ###

This is the config for the accessibility service that tells Android what kind of stuff we want access to. It doesn't need any modifications from its current state for use in other apps looking to mimic the functionality.

## TODO ##

* Redo the UI, it was very rushed.
  * Change entire flow
  * Add blurred version of image to background of app
* Add customization
  * Adjust filter intensity
  * Allow for use of preset colours
  * Maybe allow a blurred overlay
* Use accessibility to allow user to select which apps on their phone have the overlay showing.

LICENSE
=======

   Copyright 2018 Adam P. Regasz-Rethy

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
