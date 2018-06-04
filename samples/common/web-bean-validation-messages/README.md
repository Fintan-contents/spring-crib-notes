# Bean Validationのエラーメッセージをmessages.propertiesに定義する方法のサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn spring-boot:run
```

### 2. 確認方法

> **Note** HTTP APIへのアクセスは、[`curl`](https://curl.haxx.se/) やChrome拡張機能の [Restlet Client \- REST API Testing \- Chrome ウェブストア](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=ja) を利用してください。

以下のAPIに `GET` でアクセスすると、入力値チェックエラーとなり、messages.propertiesに定義したメッセージが、レスポンスとして返却されます。

http://localhost:8080/validate

バリデーション内容  
* userIdは必須