# error-handling
アプリケーションの起動方法です。

```bash
mvn clean spring-boot:run
```

下記のページにアクセスすると例外が送出され、ControllerAdviceにて例外が処理されます。
また、例外が送出されたことを示すdebugログが出力されます。

* GET http://localhost:8080/users/10

下記のページにアクセスすると ``Controller`` で例外が処理されクライアントに404ページが返ります。
また、例外が送出されたことを示すdebugログが出力されます。

* GET http://localhost:8080/users2/10
