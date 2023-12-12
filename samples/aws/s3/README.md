# Spring Cloud AWSを使用してS3にファイルをアップロード及びダウンロードする手順

## 1.事前準備

### ローカル開発環境

* [LocalStack](https://localstack.cloud/)を使用して、Amazon S3の代替とします。ここではDockerを使用してlocalstackを起動します。
  ```
  docker run --rm -it -p 4566:4566 -p 4510-4559:4510-4559 localstack/localstack
  ```
* LocalStack上のサービスを操作するため、AWS CLIをインストールします。インストール方法は[installガイド](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)を参照してください。
  * [LocalStackが提供しているCLI](https://docs.localstack.cloud/user-guide/integrations/aws-cli/#localstack-aws-cli-awslocal)を使用することもできます。後述の`endpoint-url`パラメータの指定が不要になる等、使用方法が少し異なりますので、詳細はリファレンスを参照してください。
* AWS CLIで、AWSアカウント情報を設定します。LocalStackの実行時には設定値は使用されませんので、値はダミーで構いません。（参考：[Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html)）
  * 基本的な方法としては、`configure`コマンドを使用して設定します。
  ```
  aws configure
  ```
  * 環境変数を使用する場合は、`AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数に設定します
* AWS CLIを使用して、LocalStackのS3上にアップロード対象のバケットを作成します。AWS CLIでLocalStackに接続するため、`endpoint-url`オプションで接続先を指定します。
  ```
  aws --endpoint-url=http://localhost:4566 s3 mb s3://keel-s3-bucket-test
  ```
* `application.properties` の `s3.bucket-name` にアップロード対象のバケットを設定します。

### AWS環境

* AWSのS3に、アップロード対象のバケットを作成します。
* `application.properties` の `s3.bucket-name` にアップロード対象のバケット名を設定します。
* AWS上でアプリケーション実行環境のEC2を構築します。
  * EC2からS3へアクセスできるように設定します。
* アプリケーション（JARファイル）をEC2上に配置します。

## 2.アプリケーションの起動

### ローカル開発環境

`spring.cloud.aws.s3.endpoint`プロパティで接続先をLocalStackに設定して、Sprint Bootを起動します。

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="<アップロード対象のファイルパス> --spring.cloud.aws.s3.endpoint=http://s3.localhost.localstack.cloud:4566"
```

例えば、`c:\text.png` をアップロードしたい場合は、以下のように実行します。
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="c:\text.png --spring.cloud.aws.s3.endpoint=http://s3.localhost.localstack.cloud:4566"
```

実行が終了すると…
* 対象バケットの `upload` 配下にファイルがアップロードされます。
* アップロードしたファイルが、 先頭に`download-`を付与したファイル名でカレントディレクトリ配下にダウンロードされます。

### AWS環境

Sprint Bootを起動します。

```bash
java -jar aws-s3-0.0.1-SNAPSHOT.jar <アップロード対象のファイルパス> 
```

実行結果はローカル開発環境と同じです。
