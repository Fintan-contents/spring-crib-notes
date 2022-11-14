# MyBatisを使用したサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn clean spring-boot:run
```

データベースの情報からDoma2用Entityが自動生成されアプリケーションが実行されます。

### 2. 確認方法

> **Note** HTTP APIへのアクセスは、[`curl`](https://curl.haxx.se/) やChrome拡張機能の [Restlet Client \- REST API Testing \- Chrome ウェブストア](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=ja) を利用してください。

以下のURLに `POST` でアクセスすると、ユーザ情報が登録できます。

```bash
$ curl -X POST http://localhost:8080/users
```

登録したユーザ情報がレスポンスとして返却されます。

```json
{"id": 1,"name": "name_1527842358165"}
```
