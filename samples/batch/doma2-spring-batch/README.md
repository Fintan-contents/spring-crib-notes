# Doma2でデータベースから読み込み・書き出しするサンプルアプリケーション

## 実行手順

### 1. アプリケーションの起動

```bash
mvn spring-boot:run
```

賞与計算ジョブが実行されます。このジョブは、 `employee` テーブルから社員情報を取得し、賞与を計算して `bonus` テーブルに登録する、Chunk形式のバッチです。

スキーマと初期データは `src/main/resources/schema-all.sql`, `src/main/resources/data-all.sql` を参照してください。

### 2. ログを確認

`BUILD SUCCESS` が表示され、正常に完了します。

途中、Doma2が実行した`insert`文がログ出力されています。
