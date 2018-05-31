Bean Validationのエラーメッセージをmessages.propertiesに定義する
====================================================================================================
Spring Bootでは、入力値のチェックとしてBean Validationをサポートしており、実装ライブラリとして `Hibernate Validator <https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/>`_ を採用しています。
デフォルトでは、Hibernate Validatorで定義されているValidationMessages.propertiesからエラーメッセージが生成されます。

本節では、エラーメッセージをValidationMessages.propertiesではなく、Spring BootのMessageSourceがロードするmessages.propertiesから取得する方法を記載します。
メッセージの定義をmessages.propertiesに集約する事で、開発者はメッセージをどのファイルに定義するべきかを意識する必要がなくなります。

実装例（Web）
-----------------------------------------------

Configuration
  .. literalinclude:: ../../../samples/common/web-bean-validation-messages/src/main/java/keel/config/MessageConfig.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`web-bean-validation-messages-sample <common/web-bean-validation-messages>` を参照してください。

実装例（Batch）
-----------------------------------------------
Configuration
  .. literalinclude:: ../../../samples/common/batch-bean-validation-messages/src/main/java/keel/config/BatchConfig.java
     :language: java
     :start-after: example-start
     :end-before: example-end

.. tip::

  messages.propertiesに集約した場合も、ValidationMessages.propertiesはロードされています。

  そのため、Hibernate Validatorが必要としているメッセージキーがmessages.propertiesに定義されていなくても、
  Hibernate Validatorに定義されているメッセージが取得できます。

  例えば、@NotEmptyが付与された項目の入力値がブランクの場合はjavax.validation.constraints.NotEmpty.messageというキーでメッセージを定義する必要がありますが、
  messages.propertiesに定義していなくても、ValidationMessages.propertiesに定義されているmust not be emptyがメッセージとして取得できます。

  なお、デフォルトで定義されているメッセージは以下を参照して下さい。

  * `ValidationMessages.properties <https://github.com/hibernate/hibernate-validator/blob/master/engine/src/main/resources/org/hibernate/validator/ValidationMessages.properties>`_

サンプル全体は :sample-app:`batch-bean-validation-messages-sample <common/batch-bean-validation-messages>` を参照してください。
