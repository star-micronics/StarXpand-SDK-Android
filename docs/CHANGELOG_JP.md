- [English](../CHANGELOG.md)

# 変更点

## 1.9.0 (2025/05/19)

* mC-Label2 に対応
* ブラックマークやダイカットラベル紙等のギャップ単位で用紙送りを実行するactionSeparatorFeedメソッドを追加
* 基本印字サイズを設定するstyleBaseMagnificationメソッドを追加
* mC-Label2（300dpiモデル）用の[Printing Samples](../app/src/main/java/com/starmicronics/starxpandsdk/printingsamples/README.md)を追加
* 最低サポートOSバージョンを9から10に変更
* Android16 に対応（プラットフォーム安定版で動作確認）
* 不具合修正
  * TSP650II、TSP700II、TSP800II、SP700のLAN I/Fでオンライン復帰直後（PrinterDelegateのonReady()が呼ばれた時）のStarPrinter.printAsync()の実行がまれに失敗する問題を修正 [#38](https://github.com/star-micronics/StarXpand-SDK-Android/issues/38)

## 1.8.0 (2024/11/01)

* TSP100IV-UEWB およびTSP100IV-UEWB SK に対応
* プリンターの詳細情報を取得するAPIを追加
  * StarPrinterInformationのdetailプロパティ
  * StarPrinterInformationDetail
  * StarPrinterInformationLAN
  * StarPrinterInformationBluetooth
  * StarPrinterInformationUSB
* エラーの詳細情報を取得するAPIを追加
  * StarPrinterのerrorDetailプロパティ
  * StarIO10ErrorDetail

## 1.7.0 (2024/06/24)

* BSC10II に対応
* CutTypeのenumにTearOff（ティアバーまで用紙送り）を追加
* printer.InternationalCharacterTypeのenumにIndiaを追加

## 1.6.0 (2024/02/09)

* POP10CBI に対応
* テンプレート印刷APIを追加
* サンプルアプリにテンプレート印刷画面を追加
* 文字数指定APIを追加
* Unicodeあいまい文字全半角設定APIを追加
* インターフェイス自動切り替え機能をデフォルト有効に変更
* インターフェイス自動切り替え機能の切り替え速度を改善
* テンプレート印刷APIを使った印刷パターンのサンプルを複数追加
* Android14 に対応

## 1.5.0 (2023/10/27)

* MCP31CI/CBI に対応
* テキストデータからレシート画像を生成するサンプルを追加

* 不具合修正
  * `DocumentBuilder.addPrinter()`で設定した装飾（styleで始まるメソッド）が、メソッド終了時に解除されない問題を修正

## 1.4.0 (2023/09/08)

* TSP100IV SK に対応
* Kiosk SKシリーズ（SK1-211/221/V211、SK1-311/321/V311、SK1-41、SK5-31） に対応
* プレゼンターAPIとベゼルAPIを追加
* 最低サポートOSバージョンを6から9に変更
* SM-L200のBluetoothモジュール情報を更新

## 1.3.0 (2023/03/31)

* mC-Label3 に対応
* SM-S210i/SM-S230i/SM-T300i/SM-T400iのBluetooth変更に伴い必要な内部処理を追加
* スプーラー機能APIを追加
* ページモードAPIを追加
* Star Configuration設定・読み込みAPIを追加
* 詳細ステータス取得APIを追加
* スプーラー機能の印刷サンプルと、ラベル用の印刷パターンのサンプルを複数追加

* 不具合修正
  * インスタンス生成直後のBuilderをaddすると、コマンド生成が行われない問題を修正

  * Android
    * 検索の実行中にまれにクラッシュする問題を修正 [#3](https://github.com/star-micronics/StarXpand-SDK-Android/issues/3)
    * 特定の端末においてUSBで印刷に失敗することがあり、失敗後はUSBケーブルの挿抜を行わないと復帰しない問題を修正

## 1.0.1 (2022/05/10)

* SM-L200のBluetoothモジュール情報を新規追加

* 不要な部分まで難読化されないように修正

## 1.0.0 (2022/01/31)

* 初回リリース
