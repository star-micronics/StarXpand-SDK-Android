- [日本語](docs/CHANGELOG_JP.md)

# Change Log

## 1.4.0 (2023/09/08)

* Added TSP100IV SK support.
* Added Kiosk SK series (SK1-211/221/V211, SK1-311/321/V311, SK1-41 and SK5-31) support.
* Changed minimum supported OS version from 6 to 9.
* Added API for Presenter and Bezel.
* Updated Bluetooth module information for SM-L200.

## 1.3.0 (2023/03/31)

* Added mC-Label3 support.
* Added internal processing required for Bluetooth changes of SM-S210i, SM-S230i, SM-T300i and SM-T400i.
* Added API for spooler function.
* Added API for Page Mode.
* Added API for setting/getting Star Configuration.
* Added API for getting detailed status.
* Added printing samples of the spooler function and several samples of printing patterns for labels to the example app.

* Bug Fix:
  * Fixed an issue that command generation does not execute when adding a Builder immediately after instantiation.

  * Android
    * Fixed a rare crash while performing a discovery. [#3](https://github.com/star-micronics/StarXpand-SDK-Android/issues/3)
    * Fixed an issue that printing may fail via USB in certain devices and will not recover after failure without the USB cable removal and insertion.

## 1.0.1 (2022/05/10)

* Added new Bluetooth module information for SM-L200.

* Fixed to avoid obfuscation to unnecessary parts.

## 1.0.0 (2022/01/31)

* First release
