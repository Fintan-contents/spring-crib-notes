# Spring Cloud AWSを使用してRDSにアクセスする手順

## 1.事前準備
* AWS上にRDS(MySQL)を構築します
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します

## 2.アプリケーションの起動
アプリケーションをRDSにアクセスできるEC2上に配置します。
`mvn` コマンドで実行する場合には、以下のようにアクティブプロファイルに `ec2` を指定して実行します。
```bash
mvn spring-boot:run -Dspring.profiles.active=ec2
```

※ローカルPCではMySQL環境をローカルに準備し、アクティブプロファイルに `local` を指定して実行します。
```bash
mvn spring-boot:run -Dspring.profiles.active=local
```

