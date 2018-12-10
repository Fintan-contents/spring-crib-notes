# Spring Cloud AWSを使用してSES経由でメールを送信する手順

## 1.事前準備
* AWS SESを利用可能にします  
  SESは東京リージョンでは利用できないため、利用可能なリージョンで構築してください
* `application.mailProperties` の `mail.to` 及び `mail.from` にメールアドレスを設定します  
  * `mail.to` には送信先のメールアドレスを設定します(SESで送信可能なメールアドレスを設定する必要があります)
  * `mail.from` には送信者のメールアドレスを設定します(SESでメールだドレスの確認を行う必要があります)
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します

## 2.アプリケーションの起動
* `ec2` 上で実行する場合にはアクティブプロファイルに `ec2` を指定して実行します。
* ローカルPCで実行する場合には、アクティブプロファイルに `local` を指定して実行します。

```bash
mvn spring-boot:run -Dspring.profiles.active=<アクティブプロファイル>
```

実行が終了すると…
* 添付ファイルなしと添付ファイルありのメールが `mail.to` に設定したメールアドレスに送信されます
