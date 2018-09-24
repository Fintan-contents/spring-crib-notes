# Spring Cloud AWSを使用してS3にファイルをアップロード及びダウンロードする手順

## 1.事前準備
* AWS上にアップロード対象のS3バケットを作成します
* `application.properties` の `s3.bucket-name` にアップロード対象のS3バケット名を設定します
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します

## 2.アプリケーションの起動
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=<アップロード対象のファイルパス>
```

例えば、 `c:\text.png` をアップロードしたい場合は、以下のように実行します。
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=c:\text.png
```

実行が終了すると…
* 対象バケットの `upload` 配下にファイルがアップロードされます
* アップロードしたファイルが、 `download` ディレクトリ配下にダウンロードされます

