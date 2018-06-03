起動方法
==================================================
`Spring Boot <https://spring.io/projects/spring-boot>`_\ で作成されたバッチジョブの起動方法について説明します。

アプリケーション起動時に、実行したいジョブ名をパラメータ(--spring.batch.job.names)で指定します。
複数のジョブを実行したい場合には、カンマ(,)区切りで指定します。
なお、パラメータを省略した場合のデフォルト動作では、アプリケーション内でBean登録されたすべてのジョブが実行されます。

起動例
  この例では、batch-application.jarに含まれるjob1とjob2が実行されます。

  .. code-block:: bash

    java -jar batch-application.jar --spring.batch.job.names=job1,job2


実行済みエラーが出る場合の対処方法
--------------------------------------------------
ジョブ実行時に以下のような例外が発生しジョブが実行できない場合があります。
これは、指定されたジョブがすでに正常終了しているため実行できないことを意味しています。

.. code-block:: text

  java.lang.IllegalStateException: Failed to execute CommandLineRunner
    at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:793) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
    at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:774) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:335) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:1246) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:1234) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
    at siosio.springbatchdemo.SpringBatchDemoApplicationKt.main(SpringBatchDemoApplication.kt:15) [classes/:na]
  Caused by: org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException: A job instance already exists and is complete for parameters={-spring.batch.job.names=job2}.  If you want to run this job again, change the parameters.

このような例外が発生した場合には、ジョブ起動時に指定するパラメータを変更する必要があります。

1日に1回だけ実行したいジョブの場合には、以下のように起動時に日付パラメータを設定します。
これにより、日付が変わる前に誤って実行した場合には実行済みエラーとなり、日付が変わった場合には正常に実行できるようになります。

.. code-block:: bash

  # アプリケーション起動時にパラメータにOS日付を設定する
  java -jar batch-application.jar --spring.batch.job.names=job1 date="`date '+%Y%m%d'`"

日付などに関係なく常に実行したい場合には、起動時に一意のパラメータが設定されるようにジョブを構築します。

.. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/config/BonusCalculateJobConfig.java
   :language: java
   :start-after: job-incrementer-start
   :end-before: job-incrementer-end
   :dedent: 4



