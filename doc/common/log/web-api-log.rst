HTTPリクエストおよびレスポンスをログに出力する
==================================================
HTTPリクエストおよびレスポンスをログに出力するため、 `Logbook <https://github.com/zalando/logbook>`_ を使う方法について説明します。

Logbookは、HTTPリクエストおよびレスポンスをログに出力するためのライブラリです。
様々なメッセージ形式や出力方法に対応していますが、このページでは以下の実装について説明します。

* 実装したREST APIへのリクエストを受信したら、リクエストおよびレスポンスのログを出力する。
* 外部REST APIへのリクエストを送信したら、リクエストおよびレスポンスのログを出力する。
  * リクエスト送信にはRestTemplateを使用し、メッセージの形式にはJSONを使用する
* `JsonPath <https://github.com/json-path/JsonPath>`_ を使用して、JSON形式の特定項目に対してマスク処理を行う

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

Logbookを使うための設定例
--------------------------------------------------
pom.xml
  依存ライブラリに `logbook-spring-boot-starter <https://github.com/zalando/logbook/tree/main/logbook-spring-boot-starter>`_ を追加します。
  これによりLogbookが自動で構成され、デフォルト設定が有効になります。

  .. literalinclude:: ../../../samples/pom.xml
      :language: xml
      :start-after: logbook-start
      :end-before: logbook-end
      :dedent: 6

Bean定義



Amazon S3にファイルをアップロードする実装例
--------------------------------------------------


.. tip::
  ログのフォーマットの変更(例えば、ログの可視化用にLTSV形式への変更)などを行う場合には、
  `logback-access-spring-boot-starter <https://github.com/akkinoc/logback-access-spring-boot-starter>`_ を参考にしてください。

  例えば、LTSVでログを出力する場合は下の内容をもつ logback-access.xmlを作成してsrc/main/resources配下に配置します。

  .. literalinclude:: ../../../samples/web/tomcat-access-log/src/main/resources/logback-access.xml
    :language: xml
    :start-after: ltsv-start
    :end-before: ltsv-end

サンプル全体は :sample-app:`tomcat-access-log-sample <web/tomcat-access-log>` を参照してください。
