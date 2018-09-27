.. _database-doma2:

データベースアクセスにDoma2を使用する
==================================================
データベースアクセスライブラリとして、 :doma-doc:`Doma2 <>` を使う方法について説明します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

Doma2を使うための設定例
--------------------------------------------------
pom.xml
  依存ライブラリに\ `doma-spring-boot-starter <https://github.com/domaframework/doma-spring-boot>`_\ を追加することで、Doma2用の設定が自動的に行われます。

  .. literalinclude:: ../../../samples/common/doma2/pom.xml
     :language: xml
     :start-after: doma-starter-start
     :end-before: doma-starter-end
     :dedent: 4

application.properties
  使用するデータベースをDoma2に認識させるための設定を追加します。

  .. literalinclude:: ../../../samples/common/doma2/src/main/resources/application.properties
     :language: properties
     :start-after: doma2-settings-start
     :end-before: doma2-settings-end

サンプル全体は :sample-app:`doma2-sample <common/doma2>` を参照してください。

Doma2用のEntityを生成するための設定例
--------------------------------------------------
:doma-gen-doc:`Doma-Gen <>`\ を使うとデータベースのメタデータからEntityクラスなどを生成できます。

pom.xml
  pom.xmlのプラグインの設定例となります。この設定の場合、mvn generate-resourcesの実行でEntityクラスが生成されます。
  下の例とDoma-Genのドキュメントを参考に必要な設定を行ってください。

  .. literalinclude:: ../../../samples/common/doma2/pom.xml
    :language: xml
    :start-after: doma-gen-start
    :end-before: doma-gen-end
    :dedent: 2

.. tip::

  Entityクラス以外にもDaoやSQLファイルなどの生成もできます。

サンプル全体は :sample-app:`doma2-sample <common/doma2>` を参照してください。

.. _doma2-exception-translator:

Doma2が送出する例外の変換
--------------------------------------------------
Doma2が送出する例外は、 ``doma-spring-boot-starter`` の機能でSpringの例外クラスに変換されます。
そのため、送出される例外を捕捉する場合は、Springの例外クラスを捕捉するようにしてください。
Doma2の例外クラスとSpringの例外クラスのマッピングについては、以下を参照してください。

* :doma-spring-boot-source:`Doma2の例外クラスとSpringの例外クラスのマッピング <doma-spring-boot-autoconfigure/src/main/java/org/seasar/doma/boot/autoconfigure/DomaPersistenceExceptionTranslator.java>`
