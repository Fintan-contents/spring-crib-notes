# 複数のジョブを定義したサンプルアプリケーション

## 実行手順

### 1 DBの起動

サンプルアプリケーションではPostgreSQLを使用するため、Dockerを使用して実行前に起動します。

```
$ docker run --rm --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=postgres postgres:16
```

### 1. アプリケーションの起動

複数のジョブを定義しており、実行するジョブを指定する必要があるため、以下のように実行します。

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.name=job1"
```

`package`で作成したJARファイルから起動する場合は、以下のように実行します。

```bash
java -jar multiple-jobs-batch-0.0.1-SNAPSHOT.jar --spring.batch.job.name=job1
```

`JobInstanceAlreadyCompleteException` を発生させる場合は、以下のように任意のパラメータを付与し、同じパラメータで複数回実行します。

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.name=job1 date="`date '+%Y%m%d'`""
```

### 2. ログを確認

指定したジョブの実行結果が以下のように表示され、正常に完了します。

```
Job: [SimpleJob: [name=job1]] completed with the following parameters: [{}] and the following status: [COMPLETED] in 41ms
```
