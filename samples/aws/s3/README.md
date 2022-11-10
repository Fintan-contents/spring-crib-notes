# Spring Cloud AWSを使用してS3にファイルをアップロード及びダウンロードする手順

## 1.事前準備

### ローカル開発環境

* [LocalStack](https://localstack.cloud/)を使用して、Amazon S3の代替とします。ここではDockerを使用してlocalstackを起動します。
  ```
  docker run --rm -it -p 4566:4566 -p 4510-4559:4510-4559 localstack/localstack
  ```
* LocalStack上のサービスを操作するため、AWS CLIをインストールします。インストール方法は[installガイド](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)を参照してください。
  * [LocalStackが提供しているCLI](https://docs.localstack.cloud/integrations/aws-cli/)を使用することもできます。後述の`endpoint-url`パラメータの指定が不要になる等、使用方法が少し異なりますので、詳細はリファレンスを参照してください。
* AWS CLIで、AWSアカウント情報を設定します。LocalStackの実行時には設定値は使用されませんので、値はダミーで構いません。（参考：[Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html)）
  * 基本的な方法としては、`configure`コマンドを使用して設定します。
  ```
  aws configure
  ```
  * 環境変数を使用する場合は、`AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数に設定します
* AWS CLIを使用して、LocalStackのS3上にアップロード対象のバケットを作成します。AWS CLIでLocalStackに接続するため、`endpoint-url`オプションで接続先を指定します。
  ```
  aws --endpoint-url=http://localhost:4566 s3 mb s3://keel-bucket-test
  ```
* `application-local.properties` の `s3.bucket-name` にアップロード対象のバケットを設定します。

### AWS環境

* AWSのS3に、アップロード対象のバケットを作成します。
* `application-ec2.properties` の `s3.bucket-name` にアップロード対象のバケット名を設定します。
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します。

## 2.アプリケーションの起動

実行環境に合わせてプロファイルを指定し、Sprint Bootを起動します。

* 指定するプロファイル
  * AWSのEC2上で実行する場合は `ec2`
  * ローカル開発環境で実行する場合は `local`

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=<アップロード対象のファイルパス> -Dspring-boot.run.profiles=<プロファイル>
```

例えば、ローカル開発環境上で `c:\text.png` をアップロードしたい場合は、以下のように実行します。
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="c:\text.png" -Dspring-boot.run.profiles=local
```

実行が終了すると…
* 対象バケットの `upload` 配下にファイルがアップロードされます。
* アップロードしたファイルが、 先頭に`download-`を付与したファイル名でカレントディレクトリ配下にダウンロードされます。
