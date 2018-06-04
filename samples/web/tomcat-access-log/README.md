# Tomcatのアクセスログの出力を確認するためのサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn clean spring-boot:run
```

### 2. Tomcatのアクセスログの確認

下のページにアクセスすることで標準出力にTomcatのアクセスログが出力されます。

* GET http://localhost:8080
* POST http://localhost:8080
* GET http://localhost:8080/api