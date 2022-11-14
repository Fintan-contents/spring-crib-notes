.. _database-doma2:

データベースアクセスにMyBatisを使用する
==================================================
データベースアクセスライブラリとして `MyBatis <https://mybatis.org/mybatis-3/ja/>`_ を使う方法について説明します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

MyBatisを使うための設定例
--------------------------------------------------
pom.xml
  依存ライブラリに `mybatis-spring-boot-starter <http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/>`_ を追加します。
  これによりMyBatisとSpringを連携させる `mybatis-spring <https://mybatis.org/spring/ja/index.html>`_ が自動で構成され、MyBatis用の設定が自動的に行われます。

  .. literalinclude:: ../../../samples/pom.xml
     :language: xml
     :start-after: mybatis-start
     :end-before: mybatis-end
     :dedent: 6

サンプル全体は :sample-app:`mybatis-sample <common/mybatis>` を参照してください。
