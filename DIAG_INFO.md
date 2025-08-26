# Collection and Transmission of Diagnostic Information

Some of the APIs provided by the StarIO10 library (StarIO10.aar) collect information regarding the user's device and the connected printer at runtime, and transmit it to the cloud server managed by Star Micronics Co., Ltd. as diagnostic information.

## About Collected Information and Handling of Diagnostic Information

- The information collected and transmitted by this framework does not include any personal information.
- The collected and transmitted information is used to improve our customer services and enhance Star Micronics products. It will not be provided to any third parties. For more details, please refer to [this page](https://www.star-m.jp/prjump/000193.html).

## Target APIs

In library version V1.11.0, the following APIs collect and transmit information:  
**If you use any of these APIs, please ensure that your privacy policy includes appropriate information on the relevant app distribution platform.**

- [StarPrinterSetting](https://star-m.jp/products/s_print/sdk/starxpand/manual/en/android-kotlin-api-reference/stario10/star-printer-setting-firmware/index.html)
  - [updateAsync()](https://star-m.jp/products/s_print/sdk/starxpand/manual/en/android-kotlin-api-reference/stario10/star-printer-setting-firmware/update-async.html)

## Disabling the Feature

You can disable the collection and transmission of information by using the following API. By default, this feature is enabled.

- [StarIO10DiagInfoUpload](https://star-m.jp/products/s_print/sdk/starxpand/manual/en/android-kotlin-api-reference/stario10/star-io10-diag-info-upload/index.html)
  - [isEnabled](https://star-m.jp/products/s_print/sdk/starxpand/manual/en/android-kotlin-api-reference/stario10/star-io10-diag-info-upload/is-enabled.html)
