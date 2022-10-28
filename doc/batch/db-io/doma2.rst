Doma2でデータベースから読み込み・書き出しする
==================================================

Spring Batchで :doma-doc:`Doma2 <>` を利用してデータを読み込み/書き出しする方法について説明します。

データベースアクセスにDoma2を利用するための基本的な設定については、 :doc:`/common/database/doma2` を参照してください。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

データベースからの読み込み
--------------------------------------------------

バッチ処理では一般的に、処理対象データをすべて読み込んでヒープ上に展開することは推奨されません。そのため、少量ずつ読み込みながら順次ヒープ上に展開するための実装が必要になります。

データ読み込みに利用するDaoのメソッドは、Streamを返すように実装します。詳細については、以下の公式ドキュメントを参照してください。

* :doma-doc:`ストリーム検索 - 戻り値で戻す方法 <query/select/#return-the-return-value>`

ItemReaderのopenではDaoからStreamを取得し、Stream自身とそのStreamから取得したIteratorをインスタンス変数として保持しておきます。 readでは保持しているIteratorの次の要素を返すようにし、 closeでは保持しているStreamを閉じるようにします。

実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Dao (Reader)
  .. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/dao/EmployeeBonusDao.java
    :language: java
    :start-after: doma2-spring-batch-example-start
    :end-before: doma2-spring-batch-example-end

ItemStreamReader
  .. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/reader/EmployeeBonusReader.java
    :language: java
    :start-after: doma2-spring-batch-example-start
    :end-before: doma2-spring-batch-example-end

サンプル全体は :sample-app:`doma2-spring-batch-sample <batch/doma2-spring-batch>` を参照してください。

.. warning::

  PostgreSQLでは、トランザクションが終了すると自動的にカーソルが閉じられます [#f1]_ 。そのため、Spring BatchのChunkのように一定間隔ごとにcommitされるような場合には、1つめのチャンクは正常に完了しますが、以降のチャンクは処理できなくなってしまいます。

  このような事象を回避するために、データベースにアクセスするItemReaderの実装クラスは、 :spring-batch-doc:`ItemStreamReader <api/org/springframework/batch/item/ItemStreamReader.html>` を実装したクラスにしてください。サンプルでは、ItemStreamReaderを実装した :spring-batch-doc:`AbstractItemCountingItemStreamItemReader <api/org/springframework/batch/item/support/AbstractItemCountingItemStreamItemReader.html>` を継承しています。

  ItemStreamReaderでは、openメソッドで利用されるコネクションとwriterで利用されるコネクションとは別のトランザクションに属するようになるため、上記の問題を踏まないようになっています。

.. [#f1]

  `Issuing a Query and Processing the Result の Getting results based on a cursor <https://jdbc.postgresql.org/documentation/query/#getting-results-based-on-a-cursor>`_ に下記の記載があります。

    The backend closes cursors at the end of transactions, so in autocommit mode the backend will have closed the cursor before anything can be fetched from it.


データベースへの書き出し
--------------------------------------------------

ItemWriterではDoma2の@BatchInsertを利用して、一括でインサートします。詳細については、以下の公式ドキュメントを参照してください。

* :doma-doc:`バッチ挿入 <query/batch-insert/>`

実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Dao (Writer)
  .. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/dao/BonusDao.java
      :language: java
      :start-after: doma2-spring-batch-example-start
      :end-before: doma2-spring-batch-example-end

ItemWriter
   .. literalinclude:: ../../../samples/batch/doma2-spring-batch/src/main/java/keel/batch/doma2/writer/BonusWriter.java
       :language: java
       :start-after: doma2-spring-batch-example-start
       :end-before: doma2-spring-batch-example-end

サンプル全体は :sample-app:`doma2-spring-batch-sample <batch/doma2-spring-batch>` を参照してください。
