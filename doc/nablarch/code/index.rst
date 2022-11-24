Nablarchのコード管理機能を使用する
==================================================
TISでは、豊富な基幹システム構築経験から得られたナレッジを集約したJavaアプリケーション開発/実行基盤として `Nablrch <https://fintan.jp/page/1868/>`_ を提供しています。
SpringからNablarchの機能を利用することで、Springに不足している機能を補うことができます。

この例では、Nablarchの機能である :nablarch-doc:`コード管理 <doc/application_framework/application_framework/libraries/code.html>` を使用して、コード情報を管理する実装方法を説明します。

コード管理機能ではデータベース上でコード情報を管理し、言語毎の名称取得や、コード値をバリデーションすることができます。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`nablarch-code <nablarch/nablarch-code>` を参照してください。

Nablarchを使用するための準備
--------------------------------------------------
pom.xml
  Nablarchのモジュールバージョンを管理するために、dependencyManagementにnablarch-bomを追加します。

  .. literalinclude:: ../../../samples/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

 コード管理機能を使うため、依存ライブラリにnablarch-common-codeとnablarch-common-code-jdbcを追加します。

  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

.. tip::

  Nablarchでは独自のログ出力機能を提供していますが、別のログライブラリを使用するためのアダプタが提供されています。この例では、SLF4Jを使用するためのアダプタである nablarch-slf4j-adaptor を使用しています。
  詳細は :nablarch-doc:`logアダプタ <doc/application_framework/adaptors/log_adaptor.html>` を参照してください。

コード管理で使用するテーブルの作成
--------------------------------------------------
コード管理はデータベースで行うため、コードパターンテーブルとコード名称テーブルを用意します。

この例では、Flywayによるマイグレーションで起動時にデータベースを構築しているため、マイグレーション用のSQLファイルを作成します。
同じコード値でもパターンを切り替えることで表示内容を切り替えることができるように、1つのコード値に対してパターンを2つ用意しています。

テーブル構造の詳細については :nablarch-doc:`コード管理機能を使用する為の初期設定を行う <doc/application_framework/application_framework/libraries/code.html#code-setup-table>` を参照してください。

.. tip::
  テーブル名やカラム名はあくまで一例であり、スキーマ情報を保持するBeanを定義する際に任意のカラム名を設定することができます。

V001__create_code_table.sql

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/resources/db/migration/V001__create_code_table.sql
    :language: sql

データアクセス機能を動作させるための設定
--------------------------------------------------
Nablarchのコード管理機能は、NablarchのDIコンテナ機能とデータアクセス機能を使用して動作するようになっています。Nablarchにはそれらを動作させるための設定が組み込まれていますが、Springからそのまま使用することは出来ないため、Nablarchに組み込まれている設定と同等の設定をSpring側で行います。

データアクセス機能を使用できるようにするため、Springのトランザクション管理機能と統合させるためのクラスを作成します。

UnmanagedSimpleDbTransactionManager

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/db/UnmanagedSimpleDbTransactionManager.java
    :language: java

作成したトランザクション管理のクラスや、その他に必要となるクラスをBean定義します。

DbAccessConfiguration

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/db/DbAccessConfiguration.java
    :language: java

.. tip::

  Nablarchのデータアクセス機能では、データベースによるSQLの違いを吸収するためにダイアレクトを設定する必要があります。
  この例ではデータベースにH2を使用するため、H2用のダイアレクトを設定しています。

コード管理機能を動作させるための設定
--------------------------------------------------
コード管理で使用するために用意したテーブル定義に合わせて、スキーマ情報を保持するBeanを定義します。前述のテーブル定義に合わせて、テーブル名やカラム名を設定します。

CodeManagementConfiguration

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeManagementConfiguration.java
    :language: java
    :start-after: schema-bean-start
    :end-before: schema-bean-end

スキーマ情報のBeanとやデータアクセス機能のBeanを使用して、データベースからコードをロードするための ``BasicCodeLoader`` をBean定義します。 ``BasicCodeLoader`` には初期化するための ``initialize`` メソッドがあるため、Bean初期化時に呼び出すように設定しておきます。

CodeManagementConfiguration

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeManagementConfiguration.java
    :language: java
    :start-after: loader-bean-start
    :end-before: loader-bean-end

コード情報にアクセスする際に使用する ``CodeManager`` をBean定義します。

CodeManagementConfiguration

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeManagementConfiguration.java
    :language: java
    :start-after: manager-bean-start
    :end-before: manager-bean-end

CodeManagerはNablarchの内部でも使用するため、NablarchのDIコンテナであるシステムリポジトリに ``codeManager`` という名前で登録します。

CodeManagementConfiguration

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeManagementConfiguration.java
    :language: java
    :start-after: repository-bean-start
    :end-before: repository-bean-end

CodeManagementSystemRepositoryLoader

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeManagementSystemRepositoryLoader.java
    :language: java

ドメインバリデーションを動作させるための設定
--------------------------------------------------
TODO: ドメインバリデーションページを参照させるため、ドメインバリデーションのPRがマージされた後で対応予定

コード値バリデーションの使用例
--------------------------------------------------
Nablarchが提供するバリデーターでは、メッセージを定義するためプロパティ名がデフォルトで設定されているため、使用するバリデーターに合わせてメッセージを定義します。

Nablarchのデフォルト設定の詳細については :nablarch-doc:`デフォルト設定一覧 <doc/application_framework/application_framework/configuration/index.html>` を参照してください。
（機能名「メッセージ設定」にある ``nablarch.core.validation.ee.xxx.message`` プロパティが該当します）

message.properties

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/resources/messages.properties
    :language: properties

ドメインバリデーションで使用するドメインBeanに、 ``@CodeValue`` アノテーションでコード値のバリデーションを設定します。使用するコード値とパターンを指定し、対応するコード情報を設定します。

DomainBean

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/validation/DomainBean.java
    :language: java
    :start-after: domain-start
    :end-before: domain-end

Controllerで受け取るBeanのプロパティに対して、 ``@Domain`` アノテーションで対応するドメイン名を指定します。Springでバリデーションが実行される際、ドメインBeanに設定したバリデーションルールに従ってバリデーションが実行されます。

CodeManagementForm

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/controller/CodeManagementForm.java
    :language: java
    :start-after: form-start
    :end-before: form-end

画面での使用例
--------------------------------------------------
Bean定義した ``CodeManger`` を使用することでコード情報を取得することができるため、Thymeleafから使用するためのヘルパークラスを作成します。

CodeViewHelper

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/java/keel/nablarch/code/CodeViewHelper.java
    :language: java

画面では、作成したヘルパークラスを使用してコード値と名称を取得し、プルダウンに設定します。

index.html

  .. literalinclude:: ../../../samples/nablarch/nablarch-code/src/main/resources/templates/index.html
    :language: html
    :start-after: select-start
    :end-before: select-end
