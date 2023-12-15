HTTPリクエストおよびレスポンスをログに出力する
==================================================
HTTPリクエストおよびレスポンスをログに出力するため、 `Logbook <https://github.com/zalando/logbook>`_ を使う方法について説明します。

Logbookは、HTTPリクエストおよびレスポンスをログに出力するためのライブラリです。
様々なメッセージ形式や出力方法に対応していますが、このページでは以下の実装について説明します。

* 実装したAPIへのリクエストを受信したら、リクエストおよびレスポンスのログを出力する
* 外部のAPIへリクエストを送信したら、リクエストおよびレスポンスのログを出力する
  * リクエスト送信にはRestTemplateを使用し、メッセージの形式にはJSONを使用する
* `JsonPath <https://github.com/json-path/JsonPath>`_ を使用して、JSON形式の特定項目に対してマスク処理を行う

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

Logbookを使うための設定例
--------------------------------------------------
pom.xml
  依存ライブラリに `logbook-spring-boot-starter <https://github.com/zalando/logbook/tree/main/logbook-spring-boot-starter>`_ を追加します。
  これによりLogbookが自動で構成され、よく使用される設定がデフォルトで有効になります。
  詳細は `LogbookのREADME <https://github.com/zalando/logbook?tab=readme-ov-file#spring-boot-starter>`_ を参照してください。

  .. literalinclude:: ../../../samples/pom.xml
      :language: xml
      :start-after: logbook-start
      :end-before: logbook-end
      :dedent: 6

application.properties
  LogbookのログはTRACEレベルで出力されるため、ログレベルを設定します。

  .. literalinclude:: ../../../samples/common/api-logging/src/main/resources/application.properties
     :language: properties
     :start-after: logbook-start
     :end-before: logbook-end

Bean定義
  Logbookはデフォルト設定で有効化されていますが、Bean定義することでカスタマイズすることができます。
  ここでは、リクエスト/レスポンスボディの ``id`` 項目をマスクする設定に変更します。

  .. literalinclude:: ../../../samples/common/api-logging/src/main/java/keel/apilogging/ApiLoggingApp.java
      :language: java
      :start-after: incoming-start
      :end-before: incoming-end

実装したAPIへのリクエストおよびレスポンスをログ出力する実装例
---------------------------------------------------------------------------

前述の設定例に沿って設定すると、実装したAPIへのリクエストおよびレスポンスの内容がログ出力されるようになります。
例えば、GETリクエストを受信してレスポンスを返却した場合は、以下のようなログが出力されます。

リクエストのログ

.. code-block:: text

  2023-12-14T17:18:31.790+09:00 TRACE 27798 --- [o-auto-1-exec-2] org.zalando.logbook.Logbook              : Incoming Request: 8a56e7263eb07a9e
  Remote: 127.0.0.1
  GET http://localhost:52116/users HTTP/1.1
  accept: application/json, application/*+json
  connection: keep-alive
  host: localhost:52116
  user-agent: Java/17.0.8

レスポンスのログ（設定に従って ``id`` 項目の値はマスクして出力される）

.. code-block:: text

  2023-12-14T17:18:31.793+09:00 TRACE 27798 --- [o-auto-1-exec-2] org.zalando.logbook.Logbook              : Incoming Response: be742b576f2c3a04
  Duration: 1 ms
  HTTP/1.1 200 OK
  connection: keep-alive
  content-length: 40
  Content-Type: application/json; charset=utf-8
  {"id":"***","username":"hoge"}

外部のAPIへのリクエストおよびレスポンスをログ出力する実装例
---------------------------------------------------------------------------

Logbookでは、様々なクライアントに登録するためのクラスが提供されています。
ここではRestTemplate用に提供されている ``LogbookClientHttpRequestInterceptor`` クラスを使用します。

Bean定義
  ここではRestTemplateを使用するため、Bean定義されているLogbookを元に ``LogbookClientHttpRequestInterceptor`` を生成し、RestTemplateにインターセプターとして登録します。

  .. literalinclude:: ../../../samples/common/api-logging/src/main/java/keel/apilogging/ApiLoggingApp.java
      :language: java
      :start-after: outgoing-start
      :end-before: outgoing-end

Controller
  Logbookを設定したRestTemplateを使用して、外部のAPIへリクエストを送信します。

  .. literalinclude:: ../../../samples/common/api-logging/src/main/java/keel/apilogging/ApiLoggingController.java
     :language: java
     :dedent: 4
     :start-after: logbook-start
     :end-before: logbook-end

外部のAPIへGETリクエストを送信した場合は、以下のようなログが出力されます。

リクエストのログ

.. code-block:: text

  2023-12-14T17:18:31.791+09:00 TRACE 27798 --- [o-auto-1-exec-2] org.zalando.logbook.Logbook              : Outgoing Request: be742b576f2c3a04
  Remote: localhost
  GET http://localhost:52110/users HTTP/1.1
  Accept: application/json, application/*+json
  Content-Length: 0


レスポンスのログ（設定に従って ``id`` 項目の値はマスクして出力される）

.. code-block:: text

  2023-12-14T17:18:31.794+09:00 TRACE 27798 --- [o-auto-1-exec-2] org.zalando.logbook.Logbook              : Outgoing Response: 8a56e7263eb07a9e
  Duration: 3 ms
  HTTP/1.1 200 OK
  Connection: keep-alive
  Content-Type: application/json
  Date: Thu, 14 Dec 2023 08:18:31 GMT
  Keep-Alive: timeout=60
  Transfer-Encoding: chunked
  {"id":"***","username":"hoge"}

サンプル全体は :sample-app:`api-logging-sample <common/api-logging>` を参照してください。
