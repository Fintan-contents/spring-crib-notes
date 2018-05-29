# Spring Boot + Doma2を使用した楽観ロックのサンプルアプリケーション（API）

## 実行手順

### 1. アプリケーションの起動

```bash
mvn spring-boot:run
```

### 2. ユーザを追加

> **Note** HTTP APIへのアクセスは、[`curl`](https://curl.haxx.se/) やChrome拡張機能の [Restlet Client \- REST API Testing \- Chrome ウェブストア](https://chrome.google.com/webstore/detail/restlet-client-rest-api-t/aejoelaoggembcahagimdiliamlcdmfm?hl=ja) を利用してください。

ユーザ情報を以下のURLに `POST` して、ユーザを作成します。

`http://localhost:8080/users`

```json
{"name": "名前", "age": 1, "role": "admin"}
```

ユーザの作成時には、次の内容をバリデーションしています。

* 名前と年齢とロールは必須
* 年齢は数値のみ
* ロールの存在チェック
  * `admin`, `user` ロールのみが存在しているので、 これら以外を入力するとバリデーションエラーとなります

### 3. 作成したユーザの情報を取得

以下のURLに `GET` でアクセスして、作成したユーザの情報を取得します。（ `1` は作成したユーザの `id` の値です。）

`http://localhost:8080/users/1`

次のように、バージョン番号を含むJSONが返却されます。

```json
{"id":1,"name":"名前","role":"admin","age":1,"versionNo":1}
```

存在しない `id` のURLにアクセスすると、　`404 NotFound` のステータスでレスポンスされます。

### 3. 正しく更新できることを確認

3.で取得したデータの `name` を更新して、以下のURLに `POST` すると、データが更新され、新しいバージョン番号が返ります。（ `1` は作成したユーザの `id` の値です。）

`http://localhost:8080/users/1`

**Request**

```json
{"id":1,"name":"updated","role":"admin","age":1,"versionNo":1}
```

**Response**

```json
{"id":1,"name":"updated","role":"admin","age":1,"versionNo":2}
```

### 4. 排他制御エラーが発生することを確認

3.で送信したのとまったく同じデータを、再度 `POST` すると排他制御エラーが発生し、`409 Conflict` のステータスでレスポンスされます。

