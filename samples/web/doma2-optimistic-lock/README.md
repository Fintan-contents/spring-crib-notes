# Doma2の楽観ロックで排他制御するサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn spring-boot:run
```

### 2. ブラウザで以下のURLにアクセス

http://localhost:8080/user/edit

### 3. 別のブラウザで以下のURLにアクセスして、更新ボタンを押下

http://localhost:8080/user/edit

### 4. 2のブラウザで、更新ボタンを押下

排他制御エラー画面が表示されます。