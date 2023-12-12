# Spring Cloud AWSを使用してSES経由でメールを送信する手順

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
  * 環境変数を使用する場合は、`AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数に設定します。
* `application.properties` の `mail.to` 及び `mail.from` にメールアドレスを設定します。
  * `mail.to` には送信先のメールアドレスを設定します。
  * `mail.from` には送信者のメールアドレスを設定します。
* AWS CLIを使用して、送信者のメールアドレスを検証済に設定します。AWS CLIでLocalStackに接続するため、`endpoint-url`オプションで接続先を指定します。
  ```
  aws --endpoint-url=http://localhost:4566 ses verify-email-identity --email-address foo@test.com
  ```

### AWS環境

* AWS上でSESを利用可能にします。
* `application.properties` の `mail.to` 及び `mail.from` にメールアドレスを設定します。  
  * `mail.to` には送信先のメールアドレスを設定します。(SESで送信可能なメールアドレスを設定する必要があります)
  * `mail.from` には送信者のメールアドレスを設定します。(SESでメールアドレスの確認を行う必要があります)
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します。

## 2.アプリケーションの起動

Sprint Bootを起動します。

```bash
mvn spring-boot:run
```

ローカル開発環境でLocalStackに接続する場合は、`spring.cloud.aws.endpoint`プロパティで接続先を変更します。

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.cloud.aws.endpoint=http://localhost:4566"
```

実行が終了すると…
*  `mail.to` に設定したメールアドレス宛に、添付ファイルなしと添付ファイルありのメールが送信されます

LocalStackのSESを使用している場合は、 以下のエンドポイントへアクセスすることで送信したメールの内容を確認できます。
```
GET http://localhost:4566/_aws/ses/
```
