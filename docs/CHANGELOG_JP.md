- [English](../CHANGELOG.md)

# 変更点

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
