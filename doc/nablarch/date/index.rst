Nablarchの日付管理機能を使用する
==================================================
TISでは、豊富な基幹システム構築経験から得られたナレッジを集約したJavaアプリケーション開発/実行基盤として `Nablrch <https://fintan.jp/page/1868/>`_ を提供しています。
SpringからNablarchの機能を利用することで、Springに不足している機能を補うことができます。

この例では、Nablarchの機能である :nablarch-doc:`日付管理 <doc/application_framework/application_framework/libraries/date.html>` を使用して、システム日時と業務日付を管理する実装方法を説明します。

日付管理機能では、データベース上で区分ごとに業務日付を管理することができます。また、システム日時の取得では、テスト実行時などに任意の日時に切り替えることができます。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`nablarch-date <nablarch/nablarch-date>` を参照してください。

Nablarchを使用するための準備
--------------------------------------------------
pom.xml
  Nablarchのモジュールバージョンを管理するために、dependencyManagementにnablarch-bomを追加します。

  .. literalinclude:: ../../../samples/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

 日付管理機能を使うため、依存ライブラリにnablarch-coreとnablarch-common-code-jdbcを追加します。

  .. literalinclude:: ../../../samples/nablarch/nablarch-date/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

.. tip::

  Nablarchでは独自のログ出力機能を提供していますが、別のログライブラリを使用するためのアダプタが提供されています。この例では、SLF4Jを使用するためのアダプタである nablarch-slf4j-adaptor を使用しています。
  詳細は :nablarch-doc:`logアダプタ <doc/application_framework/adaptors/log_adaptor.html>` を参照してください。

業務日付管理で使用するテーブルの作成
--------------------------------------------------
業務日付管理はデータベースで行うため、テーブルを用意します。

この例では、Flywayによるマイグレーションで起動時にデータベースを構築しているため、マイグレーション用のSQLファイルを作成します。
区分ごとに異なる業務日付を取得できるように、区分を2つ用意しています。

テーブル構造の詳細については :nablarch-doc:`業務日付管理機能を使うための設定を行う <doc/application_framework/application_framework/libraries/date.html#date-business-date-settings>` を参照してください。

.. tip::
  テーブル名やカラム名はあくまで一例であり、スキーマ情報を保持するBeanを定義する際に任意のカラム名を設定することができます。

V001__create_code_table.sql
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/resources/db/migration/V001__create_date_table.sql
    :language: sql

データアクセス機能を動作させるための設定
--------------------------------------------------
Nablarchの業務日付管理機能は、NablarchのDIコンテナ機能とデータアクセス機能を使用して動作するようになっています。Nablarchにはそれらを動作させるための設定が組み込まれていますが、Springからそのまま使用することは出来ないため、Nablarchに組み込まれている設定と同等の設定をSpring側で行います。

データアクセス機能を使用できるようにするため、Springのトランザクション管理機能と統合させるためのクラスを作成します。

UnmanagedSimpleDbTransactionManager
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/db/UnmanagedSimpleDbTransactionManager.java
    :language: java
    :start-after: class-start
    :end-before: class-end

作成したトランザクション管理のクラスや、その他に必要となるクラスをBean定義します。

DbAccessConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/db/DbAccessConfiguration.java
    :language: java
    :start-after: class-start
    :end-before: class-end

.. tip::

  Nablarchのデータアクセス機能では、データベースによるSQLの違いを吸収するためにダイアレクトを設定する必要があります。
  この例ではデータベースにH2を使用するため、H2用のダイアレクトを設定しています。

業務日付管理機能を動作させるための設定
--------------------------------------------------
業務日付管理では、業務日付をシステムプロパティや環境変数で上書きするための機能が提供されていますが、Springからそのまま使用することができません。
そのため、Spring側で同等のプロパティを用意し、プロパティ値をNablarchのDIコンテナであるシステムリポジトリに登録することで上書きできるように設定します。

プロパティ値をバインドするためのBeanを定義します。

FixedBusinessDateProperties
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/FixedBusinessDateProperties.java
    :language: java
    :start-after: class-start
    :end-before: class-end

スキーマ情報等の設定を保持する :nablarch-doc:`BasicBusinessDateProvider <javadoc/nablarch/core/date/BasicBusinessDateProvider.html>` のBeanを定義します。
スキーマ情報の他、デフォルト区分やキャッシュ使用有無など、使用する環境に合わせて設定します。
また、BasicBusinessDateProviderには初期化用のメソッドがあるため、Bean初期化時に呼び出すように設定しておきます。

BusinessDateConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/BusinessDateConfiguration.java
    :language: java
    :start-after: provider-start
    :end-before: provider-end

Bean定義したBasicBusinessDateProviderはNablarchの内部で使用するため、NablarchのDIコンテナであるシステムリポジトリに登録します。
また、業務日付を上書きするためのプロパティが設定されている場合、区分ごとの業務日付をシステムリポジトリに登録します。

BusinessDateConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/BusinessDateConfiguration.java
    :language: java
    :start-after: repository-bean-start
    :end-before: repository-bean-end

BusinessDateSystemRepositoryLoader
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/BusinessDateSystemRepositoryLoader.java
    :language: java
    :start-after: class-start
    :end-before: class-end

システム日付管理機能を動作させるための設定
--------------------------------------------------
:nablarch-doc:`BasicSystemTimeProvider <javadoc/nablarch/core/date/BasicSystemTimeProvider.html>` のBeanを定義します。

SystemTimeConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/SystemTimeConfiguration.java
    :language: java
    :start-after: provider-start
    :end-before: provider-end

Bean定義したBasicSystemTimeProviderはNablarchの内部で使用するため、NablarchのDIコンテナであるシステムリポジトリに登録します。

SystemTimeConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/SystemTimeConfiguration.java
    :language: java
    :start-after: repository-bean-start
    :end-before: repository-bean-end

SystemTimeSystemRepositoryLoader
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/date/SystemTimeSystemRepositoryLoader.java
    :language: java
    :start-after: class-start
    :end-before: class-end

システム日付の使用例
--------------------------------------------------
システム日付を取得するには、 :nablarch-doc:`SystemTimeUtil <javadoc/nablarch/core/date/SystemTimeUtil.html>` を使用します。

DateService
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/DateService.java
    :language: java
    :start-after: sysdate-start
    :end-before: sysdate-end

.. tip::

  テスト等でシステム日付を切り替えたい場合は、:nablarch-doc:`SystemTimeProvider <javadoc/nablarch/core/date/SystemTimeProvider.html>` を実装したクラスを作成し、Bean定義した :nablarch-doc:`BasicSystemTimeProvider <javadoc/nablarch/core/date/BasicSystemTimeProvider.html>` を使用しないように差し替えます。

業務日付の使用例
--------------------------------------------------
業務日付情報にアクセスするには、Bean定義した :nablarch-doc:`BasicBusinessDateProvider <javadoc/nablarch/core/date/BasicBusinessDateProvider.html>` を使用します。

デフォルト区分の業務日付は、区分を指定せずに取得することができます。

DateService
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/DateService.java
    :language: java
    :start-after: bizdate-00-start
    :end-before: bizdate-00-end

区分を指定することで、指定した区分の業務日付を取得することができます。

DateService
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/DateService.java
    :language: java
    :start-after: bizdate-01-start
    :end-before: bizdate-01-end

業務日付を更新するには、対象の区分と日付を指定します。

DateService
  .. literalinclude:: ../../../samples/nablarch/nablarch-date/src/main/java/keel/nablarch/DateService.java
    :language: java
    :start-after: bizdate-update-start
    :end-before: bizdate-update-end

業務日付を上書きする場合は、``business-date.fixed.[区分]`` プロパティを設定します。
例えば、バッチ実行時に業務区分 ``00`` の業務日付を上書きしたいといった場合、以下のように起動します。

.. code-block:: bash

  java -jar nablarch-date-0.0.1-SNAPSHOT.jar --businessDate.fixed.00=20220131

.. tip::

  :nablarch-doc:`BasicBusinessDateProvider <javadoc/nablarch/core/date/BasicBusinessDateProvider.html>` では業務日付を ``String`` 型で扱います。
  ``LocalDate`` 型等で日付を扱いたいような場合には、 BasicBusinessDateProviderをラップして相互変換を実装したクラスを用意して、業務日付へのアクセスにはそのクラスを使用するといった方法があります。
