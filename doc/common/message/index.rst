メッセージ管理
====================================================================================================
このページでは、メッセージの管理方法について記載します。
メッセージは、Jakarta Bean Validationを使った入力値チェックで使用するエラーメッセージと、それ以外のメッセージで管理方法が異なります。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

Jakarta Bean Validationを使った入力値チェックのエラーメッセージ
---------------------------------------------------------
Spring Bootでは、入力値のチェックとしてJakarta Bean Validationをサポートしており、実装ライブラリとして :hibernate-validator-doc:`Hibernate Validator <reference/en-US/html_single/>` を採用しています。
デフォルトでは、Hibernate Validatorで定義されているロケール毎のValidationMessages.properties（例えば :hibernate-validator-github:`ValidationMessages_ja.properties </engine/src/main/resources/org/hibernate/validator/ValidationMessages_ja.properties>` ）からエラーメッセージが生成されます。

デフォルトのエラーメッセージを変更したい場合は、以下のどちらかのファイルに、デフォルトから変更したいエラーメッセージを定義します。

* Springのメッセージ定義ファイル（クラスパス直下のmessages.properties）
* Hibernate Validatorのメッセージ定義ファイル（クラスパス直下のValidationMessages.propertiesやValidationMessages_ja.properties等）

エラーメッセージの解決では、まずSpringのメッセージ定義ファイルが使用されます。（エラーメッセージ解決の詳細については :spring-framework-doc:`エラーメッセージの解決 <core/validation/conversion.html>` を参考にしてください）

Springのメッセージ定義ファイルにエラーメッセージが定義されていなければ、Hibernate Validatorのメッセージ定義ファイルが使用されます。（Hibernate Validatorのエラーメッセージ解決の詳細については :hibernate-validator-doc:`エラーメッセージの補完 <reference/en-US/html_single/#chapter-message-interpolation>` を参考にしてください）

エラーメッセージには、Jakarta Bean ValidationやHibernate Validatorの入力チェックアノテーションで定義されている属性名を、プレースホルダとして定義できます。
例えば、 :hibernate-validator-doc:`@Length <api/org/hibernate/validator/constraints/Length.html>` を用いて入力値チェックを実施した場合は、プレースホルダとして `{min}、{max}` が使用できます。

実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
ValidationMessages.properties
  .. literalinclude:: ../../../samples/web/validation/src/main/resources/ValidationMessages.properties
     :language: properties

サンプル全体は :sample-app:`validation-sample <web/validation>` を参照してください。

その他のメッセージ
-----------------------------------------------
Jakarta Bean Validationを使った入力値チェック以外で使用するメッセージは、Springのメッセージ定義ファイル（クラスパス直下のmessages.properties）にメッセージを定義します。
例えば、データベースを使用した入力値のチェックや入力値の型変換エラーが発生した場合、登録処理の完了時に使用するメッセージ等はmessages.propertiesに定義します。
messages.propertiesに定義したメッセージは、SpringのMessageSourceを使用して取得します。

messages.propertiesでは、プレースホルダを `{0}、{1}` のような連番で定義します。
プレースホルダに値を埋め込みたい場合は、 `MessageSource#getMessage` の第二引数に埋め込む値を設定します。

実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
messages.properties
  .. literalinclude:: ../../../samples/web/validation/src/main/resources/messages.properties
     :language: properties

MessageSourceを使用したメッセージの取得
  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/controller/AddUserController.java
     :language: java
     :start-after: message-source-injection-start
     :end-before: message-source-injection-end
     :dedent: 4

  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/controller/AddUserController.java
     :language: java
     :start-after: message-source-start
     :end-before: message-source-end
     :dedent: 8

サンプル全体は :sample-app:`validation-sample <web/validation>` を参照してください。
