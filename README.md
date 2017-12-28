# Quasar #

> Twinkle, twinkle, quasi-star,
> Biggest puzzle from afar,
> How unlike the other ones,
> Brighter than a billion suns,
> Twinkle, twinkle, quasi-star,
> How I wonder what you are.

## Description ##

This is a very simple Android app that uses a custom overlay to show any image off the users the phone as a filter/watermar on top of the screen. Accessibility is also used as a hack to automatically hide and show the overlay since Android won't let the user accept any permissions or change certain settings when there is an overlay showing due to security concerns.

## Check it out! ##

<img src="https://user-images.githubusercontent.com/21000943/34424927-64f6e526-ebf5-11e7-9d17-470e91c0d0b7.png" width="250" height="444" title="screenshot_1"> <img src="https://user-images.githubusercontent.com/21000943/34424930-66ada1ca-ebf5-11e7-9111-649bc011a4e6.png" width="250" height="444" title="screenshot_2"> <img src="https://user-images.githubusercontent.com/21000943/34424931-67681d84-ebf5-11e7-9aaa-67636d3c01e1.png" width="250" height="444" title="screenshot_3">

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
