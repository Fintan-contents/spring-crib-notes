# tomcat-access-log
Tomcatのアクセスログの出力を確認するためのサンプルです。

アプリケーションの起動方法です。

```bash
mvn clean spring-boot:run
```

下のページにアクセスすることで標準出力にTomcatのアクセスログが出力されます。

* GET http://localhost:8080
* POST http://localhost:8080
* GET http://localhost:8080/api