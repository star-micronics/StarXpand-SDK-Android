<p align="center">
  <img
    src="logo.png"
    width="600"
    style="margin-top: 20px; margin-bottom: 20px;"
  />
</p>

- [English](../README.md)

# StarXpand SDK for Android

`StarXpand SDK for Android` はスター精密デバイス向けアプリケーション開発をサポートするソフトウェア開発キットです。

本ソフトウェア開発キットでは、スター精密デバイスを制御するためのライブラリとして、StarIO10ライブラリ(StarIO10.aar)を提供しています。

## 動作環境

| Platform | OS Version | Arch |
| --- | --- | --- |
| Android | Android 6.0 以降 | arm64-v8a, armeabi-v7a, x86, x86_64 |

## 導入

### 1. StarIO10ライブラリをプロジェクトへ追加する

StarIO10ライブラリをAndroidアプリケーションに組み込むには、Mavenリポジトリを使用します。 `app/build.gradle` の `dependencies` ブロックに下記の依存関係を追加してください。

`VERSION_NUMBER` 部分はライブラリのバージョンです。ライブラリの最新バージョンは[app/build.gradle](../app/build.gradle)を参照してください。

```gradle
dependencies {
    implementation 'com.starmicronics:stario10:VERSION_NUMBER'
    ...
}
```

Androidアプリケーションにライブラリを組み込む手順の詳細については下記URLをご覧ください。

https://developer.android.com/studio/build/dependencies

### 2. プロジェクトに設定を追加する
#### 2.1. Bluetoothプリンターを使用する場合

##### 2.1.1. targetSdkVersionを31以降に設定する場合

[サンプルコード](../app/src/main/java/com/starmicronics/starxpandsdk)を参考にして、プリンターとの通信や検索を開始する前に、BLUETOOTH_CONNECTパーミッションを取得してください。

##### 2.1.2. targetSdkVersionを30以前に設定する場合

StarIO10ライブラリには、APIレベル 31にて追加されたBLUETOOTH_CONNECTパーミッションが含まれています。AndroidManifest.xmlに下記の二つの設定を行い、BLUETOOTH_CONNECTパーミッションを削除してください。

* `manifest` 要素に `xmlns:tools="http://schemas.android.com/tools"` 属性を追加します。
* `<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" tools:node="remove"/>` 要素を追加します。

```xml
<manifest
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.starmicronics.starxpandsdk">

    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" tools:node="remove"/>
    ...
</manifest>
```

#### 2.2. USBケーブル挿抜の度に接続許可ダイアログを表示させないようにしたい場合

USBプリンターと通信を行うとき、接続許可を求めるダイアログが表示されます。この接続許可は、USBケーブルを挿抜する（プリンターの電源オンオフも含む）とリセットされます。

USBケーブル挿抜の度に接続許可ダイアログを表示させないようにしたい場合、次の設定を行ってください。また、この設定を行うことで、USBケーブルを挿入したときにアプリケーションが自動で起動するようになります。

##### 2.2.1. AndroidManifest.xmlに設定を追加する
AndroidManifest.xmlに下記の `<intent-filter>` 要素と `<meta-data>` 要素を追加してください。

```xml
<intent-filter>
    <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
    <action android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" />
</intent-filter>

<meta-data android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" android:resource="@xml/device_filter" />
<meta-data android:name="android.hardware.usb.action.USB_ACCESSORY_ATTACHED" android:resource="@xml/accessory_filter" />
```

##### 2.2.2. リソースファイルを追加する
下記のリソースファイルを `res/xml` 以下に `device_filter.xml`、`accessory_filter.xml` という名前で保存してください。

- device_filter.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <usb-device class="255" subclass="66" protocol="1" />
    <usb-device vendor-id="1305" product-id="0003" />   <!--TSP100IIU+/IIIU/IV  - printerClass-->
    <usb-device vendor-id="1305" product-id="0071" />   <!--mC-Print3           - printerClass-->
    <usb-device vendor-id="1305" product-id="0073" />   <!--mC-Print2           - printerClass-->
    <usb-device vendor-id="1305" product-id="0023" />   <!--mPOP                - printerClass-->
</resources>
```

- accessory_filter.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <usb-accessory model="Star TSP143IV-UE" manufacturer="STAR"/>
</resources>
```

## ドキュメント

[ここを参照ください。](https://www.star-m.jp/starxpandsdk-oml.html)

## サンプル

### Discover devices

```kotlin

var _manager: StarDeviceDiscoveryManager? = null

fun discovery() {
    try {
        // Specify your printer interface types.
        val interfaceTypes: List<InterfaceType> = listOf(
          InterfaceType.Lan, 
          InterfaceType.Bluetooth, 
          InterfaceType.Usb
        )

        _manager = StarDeviceDiscoveryManagerFactory.create(
            interfaceTypes,
            applicationContext
        )

        // Set discovery time. (option)
        _manager?.discoveryTime = 10000
        
        _manager?.callback = object : StarDeviceDiscoveryManager.Callback {
            // Callback for printer found.
            override fun onPrinterFound(printer: StarPrinter) {
                Log.d("Discovery", "Found printer: ${printer.connectionSettings.identifier}.")
            }

            // Callback for discovery finished. (option)
            override fun onDiscoveryFinished() {
                Log.d("Discovery", "Discovery finished.")
            }
        }

        // Start discovery.
        _manager?.startDiscovery()

        // Stop discovery.
        //_manager?.stopDiscovery()
    } catch (exception: Exception) {
        // Exception.
        Log.d("Discovery", "${exception.message}")
    }
}
```

### Print

```kotlin
private fun onPressPrintButton() {
    // Specify your printer connection settings.
    val settings = StarConnectionSettings(interfaceType.Lan, "00:11:62:00:00:00")
    val printer = StarPrinter(settings, applicationContext)

    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)
    scope.launch {
        try {
            // Connect to the printer.
            printer.openAsync().await()
            
            // create printing data. (Please refer to 'Create Printing data')
            val builder = StarXpandCommandBuilder()
            // ...
            val commands = builder.getCommands()
            // Print.
            printer.printAsync(commands).await()
        } catch (e: Exception) {
            // Exception.
            Log.d("Printing", "${e.message}")
        } finally {
            // Disconnect from the printer.
            printer.closeAsync().await()
        }
    }
}
```

### Create printing data

```kotlin
// Create printing data using StarXpandCommandBuilder object.
val builder = StarXpandCommandBuilder()
builder.addDocument(
    DocumentBuilder()
        .addPrinter(
            PrinterBuilder()
                .actionPrintImage(ImageParameter(logo, 406))
                .styleInternationalCharacter(InternationalCharacterType.Usa)
                .styleCharacterSpace(0.0)
                .styleAlignment(Alignment.Center)
                .actionPrintText(
                    "Star Clothing Boutique\n" +
                            "123 Star Road\n" +
                            "City, State 12345\n" +
                            "\n"
                )
                .styleAlignment(Alignment.Left)
                .actionPrintText(
                    "Date:MM/DD/YYYY    Time:HH:MM PM\n" +
                            "--------------------------------\n" +
                            "\n"
                )
                .add(
                     PrinterBuilder()
                         .styleBold(true)
                         .actionPrintText("SALE\n")
                )
                .actionPrintText(
                    "SKU         Description    Total\n" +
                            "300678566   PLAIN T-SHIRT  10.99\n" +
                            "300692003   BLACK DENIM    29.99\n" +
                            "300651148   BLUE DENIM     29.99\n" +
                            "300642980   STRIPED DRESS  49.99\n" +
                            "300638471   BLACK BOOTS    35.99\n" +
                            "\n" +
                            "Subtotal                  156.95\n" +
                            "Tax                         0.00\n" +
                            "--------------------------------\n"
                )
                .actionPrintText("Total     ")
                .add(
                    PrinterBuilder()
                        .styleMagnification(MagnificationParameter(2, 2))
                        .actionPrintText("   $156.95\n")
                )
                .actionPrintText(
                    "--------------------------------\n" +
                            "\n" +
                            "Charge\n" +
                            "156.95\n" +
                            "Visa XXXX-XXXX-XXXX-0123\n" +
                            "\n"
                )
                .add(
                    PrinterBuilder()
                        .styleInvert(true)
                        .actionPrintText("Refunds and Exchanges\n")
                )
                .actionPrintText("Within ")
                .add(
                     PrinterBuilder()
                        .styleUnderLine(true)
                        .actionPrintText("30 days")
                )
                .actionPrintText(" with receipt\n")
                .actionPrintText(
                    "And tags attached\n" +
                            "\n"
                )
                .styleAlignment(Alignment.Center)
                .actionPrintBarcode(
                    BarcodeParameter("0123456", BarcodeSymbology.Jan8)
                        .setBarDots(3)
                        .setHeight(5.0)
                        .setPrintHri(true)
                )
                .actionFeedLine(1)
                .actionPrintQRCode(
                    QRCodeParameter("Hello, World\n")
                        .setLevel(QRCodeLevel.L)
                        .setCellSize(8)
                )
                .actionCut(CutType.Partial)
        )
)
// Get printing data from StarXpandCommandBuilder object.
val commands = builder.getCommands()
```

### Get printer status

```kotlin
fun getStatus() {
    // Specify your printer connection settings.
    val settings = StarConnectionSettings(interfaceType.Lan, "00:11:62:00:00:00")
    val printer = StarPrinter(settings, applicationContext)

    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    scope.launch {
        try {
            // Connect to the printer.
            printer.openAsync().await()

            // Get printer status.
            val status = printer.getStatusAsync().await()
            Log.d("Status", "${status}")
        } catch (e: Exception) {
            // Exception.
            Log.d("Status", "${e.message}")
        } finally {
            // Disconnect from the printer.
            printer.closeAsync().await()
        }
    }
}
```

### Monitor printer

```kotlin
fun monitor() {
    val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Default + job)

    scope.launch {
        // Specify your printer connection settings.
        val settings = StarConnectionSettings(interfaceType.Lan, "00:11:62:00:00:00")
        printer = StarPrinter(settings, applicationContext)

        // Callback for printer state changed.
        printer?.printerDelegate = object : PrinterDelegate() {
            override fun onReady() {
                super.onReady()
                Log.d("Monitor", "Printer: Ready")
            }

            // ...
            // Please refer to document for other callback.
        }

        printer?.drawerDelegate = object : DrawerDelegate() {
            override fun onOpenCloseSignalSwitched(openCloseSignal: Boolean) {
                super.onOpenCloseSignalSwitched(openCloseSignal)
                Log.d("Monitor", "Drawer: Open Close Signal Switched: ${openCloseSignal}")
            }

            // ...
            // Please refer to document for other callback.
        }

        printer?.inputDeviceDelegate = object : InputDeviceDelegate() {
            override fun onDataReceived(data: List<Byte>) {
                super.onDataReceived(data)
                Log.d("Monitor", "Input Device: DataReceived ${data}")
            }

            // ...
            // Please refer to document for other callback.
        }

        printer?.displayDelegate = object : DisplayDelegate() {
            override fun onConnected() {
                super.onConnected()
                Log.d("Monitor", "Display: Connected")
            }

            // ...
            // Please refer to document for other callback.
        }

        try {
            // Connect to the printer.
            printer?.openAsync()?.await()
        } catch (e: Exception) {
            // Exception.
            Log.d("Monitor", "${e.message}")
        }
    }
}
```

## Copyright

Copyright 2022 Star Micronics Co., Ltd. All rights reserved.
