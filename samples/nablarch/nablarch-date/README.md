# Nablarchの日付管理機能で日付を取得するサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn spring-boot:run
```

### 2. ログを確認

起動すると、業務日付の更新処理が実行されます。
更新処理前後のシステム日付、および業務日付がログに出力されます。

### 業務日付を上書きして起動する場合

業務日付を上書きする場合、起動時にプロパティを設定します。

Mavenから実行する場合は、以下のように実行します。

```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DbusinessDate.fixed.00=20220131"
```

Jarファイルから起動する場合は、以下のように実行します。

```
java -jar nablarch-date-0.0.1-SNAPSHOT.jar --businessDate.fixed.00=20220131
```
