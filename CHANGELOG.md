- [日本語](docs/CHANGELOG_JP.md)

# Change Log

## 1.9.0 (2025/05/19)

* Added mC-Label2 support.
* Added the actionSeparatorFeed method to execute paper feeding per black mark and gap for die-cut label paper, etc.
* Added the styleBaseMagnification method to set the base print size.
* Added [Printing Samples](app/src/main/java/com/starmicronics/starxpandsdk/printingsamples/README.md) for mC-Label2 (300dpi model).
* Changed minimum supported OS version from 9 to 10.
* Added Android 16 support (tested on platform stability version).
* Bug Fix:
  * Fixed an issue where the execution of StarPrinter.printAsync() occasionally failed immediately after the printer came back online (when PrinterDelegate onReady() was called) with the LAN interface of TSP650II, TSP700II, TSP800II, and SP700. [#38](https://github.com/star-micronics/StarXpand-SDK-Android/issues/38)

## 1.8.0 (2024/11/01)

* Added TSP100IV-UEWB and TSP100IV-UEWB SK support.
* Added API to get detail information of printer.
  * detail property of StarPrinterInformation
  * StarPrinterInformationDetail
  * StarPrinterInformationLAN
  * StarPrinterInformationBluetooth
  * StarPrinterInformationUSB
* Added API to get detail information of error.
  * errorDetail property of StarPrinter
  * StarIO10ErrorDetail

## 1.7.0 (2024/06/24)

* Added BSC10II support.
* Added TearOff (feed to tear bar) to the CutType enum.
* Added India to the printer.InternationalCharacterType enum.

## 1.6.0 (2024/02/09)

* Added POP10CBI support.
* Added API for template printing function.
* Added a screen for template printing to the sample app.
* Added API for specifying number of character function.
* Added API for setting full/half-width of Unicode ambiguous characters.
* Changed the Auto Switch Interface function to enabled by default.
* Improved switching speed of the Auto Switch Interface function.
* Added several samples of printing patterns using template printing function.
* Added Android 14 support.

## 1.5.0 (2023/10/27)

* Added MCP31CI/CBI support.
* Added a sample to generate receipt images from text data.

* Bug Fix:
  * Fixed an issue where decorations (methods starting with `style`) set with `DocumentBuilder.addPrinter()` were not reset when the method ended.

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
