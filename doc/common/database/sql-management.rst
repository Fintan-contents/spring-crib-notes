DDL / DMLの管理
==================================================

開発時における、DDLやDMLの管理方法について記載します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

DDLの管理
--------------------------------------------------

DDLの管理として、バージョン管理が可能なマイグレーションツールである\ `Flyway <https://flywaydb.org>`_\ について記載します。
Flyway の基本的な使用方法については、以下を参照してください。

* `Flyway Documentation <https://flywaydb.org/documentation/>`_

Spring BootはFlywayをサポートしており、アプリケーションの起動時に `FlywayのMigrateコマンド <https://flywaydb.org/documentation/command/migrate>`_ を自動実行します。
Flywayのその他のコマンドや、 アプリケーションの起動時ではなく任意のタイミングでFlywayのMigrateコマンドを実行したい場合は、`FlywayのMaven Plugin <https://flywaydb.org/documentation/usage/maven/>`_\ を使用する事で実行が可能です。

参考情報

* :spring-boot-doc:`9.5.1. Execute Flyway Database Migrations on Startup <reference/html/howto.html#howto.data-initialization.migration-tool>`
* `Flyway Documentation - Maven Plugin <https://flywaydb.org/documentation/usage/maven/>`_

.. _sql-management-dml:

DMLの管理
--------------------------------------------------

初期データやテストデータ等のDMLの管理として、DDLと同じようにFlywayを使用する方法があります。
ただ、開発者がテスト時に手動や別のツール等でデータを投入するといった運用をする場合、Flywayではデータのみをクリーニングする機能が存在しないので注意が必要になります。

例えば、Flywayで管理しているデータを投入する前に重複するデータを手動で投入してしまっていた場合、FlywayのMigrateコマンドが失敗してしまいます。そういった事態を避けるために、開発者が手動でデータを削除しなければいけない等、運用が煩雑になることがあります。

そのような状況でDMLを管理する方法の1つとして、ここでは任意のディレクトリに格納されているSQLファイルを実行できる `SQL Maven Plugin <http://www.mojohaus.org/sql-maven-plugin/>`_ を使用する方法について記載します。[#document_version]_

.. [#document_version] 本サンプルアプリケーションで使用しているSQL Maven Pluginのバージョンは「1.5」ですが、参考情報のドキュメントはバージョンが「3.0.0-SNAPSHOT」の内容となります。ご注意ください。

.. tip::

  テストデータをFlywayで管理したい場合は、プロファイルを使用する方法等がありますので、 :spring-boot-doc:`9.5.1. Execute Flyway Database Migrations on Startup <reference/html/howto.html#howto.data-initialization.migration-tool>` を参考にしてください。

Flyway Maven Plugin / SQL Maven Plugin の設定例
--------------------------------------------------
pom.xml
  .. literalinclude:: ../../../samples/common/sql-management/pom.xml
     :language: xml
     :start-after: example-start
     :end-before: example-end
     :dedent: 2

サンプル全体は :sample-app:`sql-management-sample <common/sql-management>` を参照してください。
