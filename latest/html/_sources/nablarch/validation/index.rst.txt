.. _nablarch-validation:

Nablarchのドメインバリデーションを使用して入力値をチェックする
================================================================
TISでは、豊富な基幹システム構築経験から得られたナレッジを集約したJavaアプリケーション開発/実行基盤として `Nablrch <https://fintan.jp/page/1868/>`_ を提供しています。
SpringからNablarchの機能を利用することで、Springに不足している機能を補うことができます。

この例では、Nablarchの機能である :nablarch-doc:`ドメインバリデーション <doc/application_framework/application_framework/libraries/validation/bean_validation.html#bean-validation-domain-validation>` を使用して、入力値をチェックする実装方法を説明します。

ドメインバリデーションを使用することで、Nablarchが提供するバリデーターの使用や、Beanにドメイン名を指定するだけでバリデーションを設定することができます。Beanごとにバリデーションのルールを定義する必要がないため、ルールの変更が容易になります。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`nablarch-validation <nablarch/nablarch-validation>` を参照してください。

Nablarchを使用するための準備
--------------------------------------------------
pom.xml
  Nablarchのモジュールバージョンを管理するために、dependencyManagementにnablarch-bomを追加します。

  .. literalinclude:: ../../../samples/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

 ドメインバリデーションを使うため、依存ライブラリにnablarch-core-validation-eeとnablarch-main-default-configurationを追加します。

  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/pom.xml
    :language: xml
    :start-after: nablarch-start
    :end-before: nablarch-end
    :dedent: 4

.. tip::

  Nablarchでは独自のログ出力機能を提供していますが、別のログライブラリを使用するためのアダプタが提供されています。この例では、SLF4Jを使用するためのアダプタである nablarch-slf4j-adaptor を使用しています。
  詳細は :nablarch-doc:`logアダプタ <doc/application_framework/adaptors/log_adaptor.html>` を参照してください。

バリデーションルールの設定
--------------------------------------------------
ドメイン毎のバリデーションルールを設定するためドメインBeanを作成し、有効化します。

DomainBean
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/DomainBean.java
    :language: java
    :start-after: class-start
    :end-before: class-end

作成したドメインBeanをNablarchで使えるようにするため、 :nablarch-doc:`DomainManager <javadoc/nablarch/core/validation/ee/DomainManager.html>` の実装クラスを作成します。

ExampleDomainManager
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ExampleDomainManager.java
    :language: java
    :start-after: class-start
    :end-before: class-end

ドメインバリデーションを動作させるための設定
--------------------------------------------------
Nablarchのドメインバリデーションは、NablarchのDIコンテナ機能を使用して動作するようになっています。Nablarchにはドメインバリデーションを動作させるための設定が組み込まれていますが、Springからそのまま使用することは出来ないため、Nablarchに組み込まれている設定と同等の設定をSpring側で行います。

Nablarchのドメインバリデーションを動作させるには、NablarchのDIコンテナであるシステムリポジトリを設定する必要があります。バリデーションルールの設定時に作成した DomainManager の実装クラスや、その他に必要なクラスを作成し、システムリポジトリに登録します。

ValidationConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ValidationConfiguration.java
    :language: java
    :start-after: repository-start
    :end-before: repository-end

ValidationSystemRepositoryLoader
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ValidationSystemRepositoryLoader.java
    :language: java
    :start-after: class-start
    :end-before: class-end

ValidatorFactoryBuilderImpl
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ValidatorFactoryBuilderImpl.java
    :language: java
    :start-after: class-start
    :end-before: class-end

サンプルでは文字種バリデーションを使用しますが、Nablarchでは文字種定義をデフォルト設定として提供しています。ここではデフォルト設定を使用するため、対応するプロパティ値をバインドするためのBeanを定義します。

CharsetDefProperties
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/CharsetDefProperties.java
    :language: java
    :start-after: property-start
    :end-before: property-end

ValidationConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ValidationConfiguration.java
    :language: java
    :start-after: charset-props-start
    :end-before: charset-props-end

文字種バリデーションを動作させるため、プロパティ値をバインドしたBeanを使用して必要な設定を行い、Nablarchのシステムリポジトリに登録します。

ValidationConfiguration
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/ValidationConfiguration.java
    :language: java
    :start-after: charset-loader-start
    :end-before: charset-loader-end

CharsetDefSystemRepositoryLoader
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/validation/CharsetDefSystemRepositoryLoader.java
    :language: java
    :start-after: class-start
    :end-before: class-end

ドメインバリデーションの使用例
--------------------------------------------------
Nablarchが提供するバリデーターでは、メッセージを定義するためプロパティ名がデフォルトで設定されているため、使用するバリデーターに合わせてメッセージを定義します。

バリデータで使用するプロパティ名は、Nablarchのデフォルト設定として定義されています。Nablarchのデフォルト設定の詳細については :nablarch-doc:`デフォルト設定一覧 <doc/application_framework/application_framework/configuration/index.html>` を参照してください。

型変換エラー時に表示するメッセージの設定については、 :ref:`入力値の型変換エラー時に入力画面に適切なメッセージを表示する <web-typeMismatch>` を参照してください。

message.properties
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/resources/messages.properties
    :language: properties

Controllerで受け取るBeanのプロパティに対して、 ``@Domain`` アノテーションで対応するドメイン名を指定します。Springでバリデーションが実行される際、ドメインBeanに設定したバリデーションルールに従ってバリデーションが実行されます。

ValidationForm
  .. literalinclude:: ../../../samples/nablarch/nablarch-validation/src/main/java/keel/nablarch/controller/ValidationForm.java
    :language: java
    :start-after: property-start
    :end-before: property-end
