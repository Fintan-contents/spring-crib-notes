Bean Validationのエラーメッセージをmessages.propertiesに定義する方法
====================================================================================================

Spring Bootでは、入力値のチェックとして ``Bean Validation`` をサポートしており、実装ライブラリとして `Hibernate Validator <https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/>`_ を採用しています。
デフォルトでは、 `Hibernate Validator <https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/>`_ で定義されている ``ValidationMessages.properties`` からエラーメッセージが生成されます。

本節では、エラーメッセージを ``ValidationMessages.properties`` ではなく、Spring Bootの ``MessageSource`` がロードする ``messages.properties`` から取得する方法を記載します。
メッセージの定義を ``messages.properties`` に集約する事で、開発者はメッセージをどのファイルに定義するべきかを意識する必要がなくなります。

実装例（Web）
-----------------------------------------------

Configuration
  .. literalinclude:: ../../../samples/common/web-bean-validation-messages/src/main/java/keel/config/MessageConfig.java
     :language: java
     :linenos:
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`web-bean-validation-messages-sample <common/web-bean-validation-messages>` を参照してください。

実装例（Batch）
-----------------------------------------------

Configuration
  .. literalinclude:: ../../../samples/common/batch-bean-validation-messages/src/main/java/keel/config/BatchConfig.java
     :language: java
     :linenos:
     :start-after: example-start
     :end-before: example-end

.. tip::

  ``messages.properties`` に集約した場合も、 ``ValidationMessages.properties`` はロードされています。

  そのため、 ``Hibernate Validator`` が必要としているメッセージキーが ``messages.properties`` に定義されていなくても、
  `Hibernate Validator <https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/>`_ に定義されているメッセージが取得できます。

  例えば、 ``@NotEmpty`` が付与された項目の入力値がブランクの場合は ``javax.validation.constraints.NotEmpty.message`` というキーでメッセージを定義する必要がありますが、
  ``messages.properties`` に定義していなくても、 ``ValidationMessages.properties`` に定義されている ``must not be empty`` がメッセージとして取得できます。

  なお、デフォルトで定義されているメッセージは以下を参照して下さい。

  ValidationMessages.properties_

  .. _ValidationMessages.properties: https://github.com/hibernate/hibernate-validator/blob/master/engine/src/main/resources/org/hibernate/validator/ValidationMessages.properties

サンプル全体は :sample-app:`batch-bean-validation-messages-sample <common/batch-bean-validation-messages>` を参照してください。
