# Spring Cloud AWSを使用してRDSにアクセスする手順

## 1.事前準備

### ローカル開発環境

* Dockerを使用してPostgreSQLを起動します。
  ```
  docker run --rm -p 5432:5432 -e POSTGRES_PASSWORD=password postgres
  ```

### AWS環境

* AWS上でRDS（for PostgreSQL）を構築します。
* AWS上でアプリケーション実行環境のEC2を構築します。
* アプリケーションをEC2上に配置します。
* `AWS_ACCESS_KEY_ID` 及び `AWS_SECRET_ACCESS_KEY` 環境変数にAWSアカウントのクレデンシャル情報を設定します

## 2.アプリケーションの起動

実行環境に合わせてプロファイルを指定し、Sprint Bootを起動します。

* 指定するプロファイル
  * AWSのEC2上で実行する場合は `ec2`
  * ローカル開発環境で実行する場合は `local`

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=<プロファイル>
```

例えば、ローカル開発環境上で実行する場合は、以下のように実行します。
```bash
mvn spring-boot:run -Dspring.profiles.active=local
```
