起動方法
==================================================
`Spring Batch <https://spring.io/projects/spring-batch>`_\ で作成されたバッチジョブの起動方法について説明します。

実行するジョブを指定する
--------------------------------------------------

アプリケーションを起動すると、Bean登録されたジョブが実行されます。

  .. code-block:: bash

    java -jar multiple-jobs-batch-0.0.1-SNAPSHOT.jar

もし複数のジョブを定義している場合、実行するジョブ名を指定しなければ以下のような実行時例外が発生します。

.. code-block:: text
    java.lang.IllegalArgumentException: Job name must be specified in case of multiple jobs

そのため、実行したいジョブ名をパラメータ( ``--spring.batch.job.name`` )で指定する必要があります。

起動例
  この例では、multiple-jobs-batch-0.0.1-SNAPSHOT.jarに含まれるjob1が実行されます。

  .. code-block:: bash

    java -jar multiple-jobs-batch-0.0.1-SNAPSHOT.jar --spring.batch.job.name=job1


実行済みエラーが出る場合の対処方法
--------------------------------------------------
Spring Batchでは、JobInstanceという考え方があり、同一のJobInstanceは再実行できないように制御されます。
JobInstanceはジョブ名や起動パラメータから識別されるため、すべて同じ指定でジョブを実行すると以下のような実行時例外が発生し、ジョブが実行できなくなります。

.. code-block:: text

	org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException: A job instance already exists and is complete for identifying parameters={'date':'{value=20231213, type=class java.lang.String, identifying=true}'}.  If you want to run this job again, change the parameters.

このような例外が発生した場合には、ジョブ起動時に指定するパラメータを変更し、異なるJobInstanceにする必要があります。

例えば1日に1回だけ実行したいジョブの場合、以下のように起動時に日付パラメータを指定し、値には現在日付を指定するようにします。
これにより日付が変わる前に誤って実行した場合には実行済みエラーとなり、日付が変わった場合には新たに実行できるようになります。

.. code-block:: bash

  # アプリケーション起動時にパラメータにシステム日付を設定する
  java -jar multiple-jobs-batch-0.0.1-SNAPSHOT.jar --spring.batch.job.name=job1 date="`date '+%Y%m%d'`"

日付などに関係なく常に実行できるようにしたい場合には、``JsrJobParametersConverter`` をBean定義します。
これにより起動時に ``jsr_batch_run_id`` というパラメータが付与され、実行毎に一意なパラメータとなることで、同じジョブを何度でも実行することができます。

.. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/config/BonusCalculateJobConfig.java
   :language: java
   :start-after: job-incrementer-start
   :end-before: job-incrementer-end
   :dedent: 4

.. tip::

  一意なパラメータを付与する設定方法としては、 ``RunIdIncrementer`` やSpring Batch 5.x から削除された ``JsrJobParametersConverter`` があります。
  ``RunIdIncrementer`` は前回起動時のパラメータを復元した上で新しいパラメータを設定するため、前回起動時に指定したパラメータを
  指定しなかった場合、そのパラメータは前回起動時の値で付与されることになります。
  そのため、特定ケースでのみパラメータを指定する等、状況によりパラメータ指定有無が変わるような状況では注意して使用する必要があります。
  ``JsrJobParametersConverter`` にはパラメータを復元するというような仕様がないため、サンプルでは Spring Batch 5.x から同クラスを流用して使用しています。
