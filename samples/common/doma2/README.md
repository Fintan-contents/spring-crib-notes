# Doma2
Doma2を使うためのサンプルプロジェクトです。

アプリケーションの起動方法です。
データベースの情報からDoma2用Entityが自動生成されアプリケーションが実行されます。

```bash
mvn clean spring-boot:run
```

curlコマンドを使うことで確認が出来ます。

```bash
$ curl -X POST http://localhost:8080/users
{"id":161,"name":"name_1526875365164"}
```