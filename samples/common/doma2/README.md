# Doma2
Doma2を使うためのサンプルプロジェクトです。

アプリケーションの起動方法です。
データベースの情報からDoma2用Entityが自動生成されアプリケーションが実行されます。

```bash
mvn clean spring-boot:run
```

curlコマンドを使うことで確認が出来ます。

```bash
$ curl -X post http://localhost:8080/users
 {"timestamp":"2018-05-18T00:25:42.819+0000","status":405,"error":"Method Not Allowed","message":"Request method 'post' not supported","path":"/users"}
```