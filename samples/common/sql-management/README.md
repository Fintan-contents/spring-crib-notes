# DDL / DMLの管理のサンプルアプリケーション

### １．Flyway Maven Pluginの実行

以下コマンドを実行する事で、`src/main/resources/db/migration`配下のsqlが実行され、「USERS」テーブルが作成されます。

```bash
mvn generate-resources
```

### ２．SQL Maven Pluginの実行

以下コマンドを実行する事で、`src/test/data`配下のsqlが実行され、「USERS」テーブルにデータが投入されます。

```bash
mvn process-test-resources
```