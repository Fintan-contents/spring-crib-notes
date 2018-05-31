DDL / DMLの管理
==================================================

.. warning::
     本節の内容は、現在検討・検証中です。今後、内容が大きく変更する可能性があります。
     その点につきましてはご留意頂くようお願いします。

本節では、開発時における、DDLやDMLの管理方法について記載します。

DDLの管理
--------------------------------------------------

DDLの管理として、バージョン管理が可能なマイグレーションツールである\ `Flyway <https://flywaydb.org>`_\ について記載します。
Flyway の基本的な使用方法については、以下を参照してください。

* `Flyway Documentation <https://flywaydb.org/documentation/>`_

Spring BootはFlywayをサポートしており、アプリケーションの起動時に `FlywayのMigrateコマンド <https://flywaydb.org/documentation/command/migrate>`_ を自動実行します。
Flywayのその他のコマンドや、 アプリケーションの起動時ではなく任意のタイミングでFlywayのMigrateコマンドを実行したい場合は、`FlywayのMaven Plugin <https://flywaydb.org/documentation/maven/>`_\ を使用する事で実行が可能です。

参考情報

* `Spring Boot Reference Guide - 81.5.1 Execute Flyway Database Migrations on Startup <https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-execute-flyway-database-migrations-on-startup>`_
* `Flyway Documentation - Maven Plugin <https://flywaydb.org/documentation/maven/>`_

なお、Flywayを使用して、初期データやテストデータ等のDMLを管理する事も可能です。
但し、データのみをクリーニングする機能が存在しないため、開発者がテスト時にデータを投入したり更新するようなテーブルのDMLは、Flywayで管理しない事をお薦めします。
例えば、Flywayで管理しているデータを投入する前に、開発者が同一レコードのデータを手動や別のツールで投入すると、一意制約違反が発生し、FlywayのMigrateコマンドが失敗します。
対応するには、開発者が手動でレコードを削除しなければいけない等、運用が煩雑になることが予想されます。
そのため、このようなデータを管理する場合は、 :ref:`sql-management-dml` を参考にしてください。

.. _sql-management-dml:

DMLの管理
--------------------------------------------------
初期データやテストデータ等のDMLの管理として、任意のディレクトリに格納されているSQLファイルを実行可能な `SQL Maven Plugin <http://www.mojohaus.org/sql-maven-plugin/>`_ の使用を推奨します。[#document_version]_ 

.. [#document_version] 本サンプルアプリケーションで使用しているSQL Maven Pluginのバージョンは「1.5」ですが、参考情報のドキュメントはバージョンが「3.0.0-SNAPSHOT」の内容となります。ご注意ください。

Flyway Maven Plugin / SQL Maven Plugin の設定例
--------------------------------------------------
pom.xml
  .. literalinclude:: ../../../samples/common/sql-management/pom.xml
     :language: xml
     :start-after: example-start
     :end-before: example-end
     :dedent: 2

サンプル全体は :sample-app:`sql-management-sample <common/sql-management>` を参照してください。
