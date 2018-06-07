Thymeleafを使用してチェックボックスなどのデフォルト値を送信する
===========================================================================
checkboxや複数選択のselectで1つも選択されなかった場合に、Formのプロパティにデフォルト値を設定する方法について説明します。

.. tip::

  正しい実装を行わないと、未選択状態ではFormのプロパティがnullとなります。
  このため、サーバサイドではnull判定が必要になりプログラムが煩雑になります。

デフォルト値の設定方法
--------------------------------------------------
checkboxやselectでデフォルト値を送信するためには、Thymeleafのth:objectとth:fieldを使用します。
この2つを使用するだけで、checkboxやselectに対応するFormのプロパティにはデフォルト値が設定されます。

詳細は、以下の公式ドキュメントのcheckboxの部分などを参照してください。

* `Thymeleaf + Spring <https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html>`_

.. warning::

  デフォルト値を受け取れる型は限定されています。
  Formのプロパティを誤った型で定義しているとデフォルト値が設定されず、プロパティの値はnullのままとなるため注意してください。

  対応している型については、以下のJavadocを参照してください。

  * `WebDataBinderのJavadoc <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/WebDataBinder.html#getEmptyValue-java.lang.Class->`_


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Form
  上で説明したように、Formのプロパティの型を適切な型で宣言します。

  .. literalinclude:: ../../../samples/web/template-engine-thymeleaf/src/main/java/keel/thymeleaf/DefaultValueSampleController.java
     :language: java
     :start-after: form-start
     :end-before: form-end
     :dedent: 8

View
  上で説明したように、th:objectとth:fieldを使用して入力欄(checkboxやselect)を作成します。

  .. literalinclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/default/input.html
     :language: html
     :start-after: sample-start
     :end-before: sample-end

サンプル全体は :sample-app:`template-engine-thymeleaf-sample <web/template-engine-thymeleaf>` を参照してください。
