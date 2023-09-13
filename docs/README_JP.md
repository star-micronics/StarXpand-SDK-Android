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

## ドキュメント

StarXpand SDKのドキュメントは[こちら](https://www.star-m.jp/starxpandsdk-oml.html)を参照ください。

ドキュメントにはSDKの概要、サンプルアプリのビルド方法、APIの使用方法、APIリファレンスなどが含まれます。

## 動作環境

| Platform | OS Version | Arch |
| --- | --- | --- |
| Android | Android 9.0 以降 | arm64-v8a, armeabi-v7a, x86, x86_64 |

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

[サンプルコード](../app/src/main/java/com/starmicronics/starxpandsdk)を参考にして、プリンターとの通信や検索を開始する前に、BLUETOOTH_CONNECTパーミッションを取得してください。

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

## サンプル

StarXpand SDKにはプリンターと組み合わせて動作を確認できるサンプルアプリが含まれています。リンク先の各機能の解説と合わせてご利用ください。

#### 1. [プリンターの検索](https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/searchPrinter.html)

#### 2. [印刷データの生成](https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/basic-step1.html)

ラベル用の印刷レイアウトを作成するのに活用できる各業態の[印刷サンプル](../app/src/main/java/com/starmicronics/starxpandsdk/printingsamples/PrintingSamples.md)（サンプルコードと印刷結果画像）もご利用ください。

> :warning: プリンターのモデルによっては印刷できないサンプルがあります。また、ご利用の際は適宜レイアウトを調節してください。

#### 3. [印刷データの送信](https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/basic-step2.html)

#### 4. [スプーラー機能を利用した印刷データの送信](https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/spooler.html)

#### 5. [プリンターステータスの取得](#GetPrinterStatus)

#### 6. [プリンターステータスの監視](#MonitorPrinter)

<a id="GetPrinterStatus"></a>
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

<a id="MonitorPrinter"></a>
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
