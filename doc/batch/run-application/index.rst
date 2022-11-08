起動方法
==================================================
`Spring Batch <https://spring.io/projects/spring-batch>`_\ で作成されたバッチジョブの起動方法について説明します。

実行するジョブを指定する
--------------------------------------------------

アプリケーションを起動すると、Bean登録されたすべてのジョブが実行されます。

実行するジョブを指定したい場合は、アプリケーション起動時に実行したいジョブ名をパラメータ(--spring.batch.job.names)で指定します。
もし複数のジョブを実行したい場合には、カンマ(,)区切りで指定します。

起動例
  この例では、batch-application.jarに含まれるjob1とjob2が実行されます。

  .. code-block:: bash

    java -jar batch-application.jar --spring.batch.job.names=job1,job2


実行済みエラーが出る場合の対処方法
--------------------------------------------------
Spring Batchでは、JobInstanceという考え方があり、同一のJobInstanceは再実行できないように制御されます。
JobInstanceはジョブ名や起動パラメータから識別されるため、すべて同じ指定でジョブを実行すると以下のような実行時例外が発生し、ジョブが実行できなくなります。

.. code-block:: text

	java.lang.IllegalStateException: Failed to execute ApplicationRunner
		at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:765) ~[spring-boot-2.7.5.jar!/:2.7.5]
		at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:752) ~[spring-boot-2.7.5.jar!/:2.7.5]
		at org.springframework.boot.SpringApplication.run(SpringApplication.java:315) ~[spring-boot-2.7.5.jar!/:2.7.5]
		at org.springframework.boot.SpringApplication.run(SpringApplication.java:1306) ~[spring-boot-2.7.5.jar!/:2.7.5]
		at org.springframework.boot.SpringApplication.run(SpringApplication.java:1295) ~[spring-boot-2.7.5.jar!/:2.7.5]
		at keel.batch.doma2.Doma2SpringBatchApplication.main(Doma2SpringBatchApplication.java:11) ~[classes!/:0.0.1-SNAPSHOT]
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
		at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
		at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49) ~[doma2-spring-batch-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
		at org.springframework.boot.loader.Launcher.launch(Launcher.java:108) ~[doma2-spring-batch-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
		at org.springframework.boot.loader.Launcher.launch(Launcher.java:58) ~[doma2-spring-batch-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
		at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:65) ~[doma2-spring-batch-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
	Caused by: org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException: A job instance already exists and is complete for parameters={date=20221103}.  If you want to run this job again, change the parameters.

このような例外が発生した場合には、ジョブ起動時に指定するパラメータを変更し、異なるJobInstanceにする必要があります。

例えば1日に1回だけ実行したいジョブの場合、以下のように起動時に日付パラメータを指定し、値には現在日付を指定するようにします。
これにより日付が変わる前に誤って実行した場合には実行済みエラーとなり、日付が変わった場合には新たに実行できるようになります。

.. code-block:: bash

  # アプリケーション起動時にパラメータにシステム日付を設定する
  java -jar batch-application.jar --spring.batch.job.names=job1 date="`date '+%Y%m%d'`"

日付などに関係なく常に実行できるようにしたい場合には、``JsrJobParametersConverter`` をBean定義します。
これにより起動時に ``jsr_batch_run_id`` というパラメータが付与され、実行毎に一意なパラメータとなることで、同じジョブを何度でも実行することができます。

.. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/config/BonusCalculateJobConfig.java
   :language: java
   :start-after: job-incrementer-start
   :end-before: job-incrementer-end
   :dedent: 4

.. tip::

  一意なパラメータを付与する設定方法としては、 ``JsrJobParametersConverter`` の他に ``RunIdIncrementer`` があります。
  ``RunIdIncrementer`` では、起動時のパラメータが指定されていなければ前回実行時のパラメータが復元される仕様になっているため、
  特定のケースでのみパラメータを指定するといった運用がある場合には注意する必要があります。
  なお、``JsrJobParametersConverter`` にはパラメータを復元するというような仕様はありませんが、Spring Batch 6.x で削除予定であるため、
  それを踏まえた上で使用する必要があります。
