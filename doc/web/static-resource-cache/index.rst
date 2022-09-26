.. _web-static-resource-cache:

画像やcssなどの静的なリソースをキャッシュする
====================================================================================================
静的リソースをブラウザなどにキャッシュする方法について説明します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

設定例
-------------------------------------------------
application.propertiesにmax-age等のキャッシュに関する設定を追加することで対応できます。

application.properties
  .. literalinclude:: ../../../samples/web/spring-security-http-response-header/src/main/resources/application.properties
     :language: properties
     :start-after: example-start
     :end-before: example-end


.. tip::

  Cache-Controlには、max-age以外にもいくつかの設定可能なディレクティブがあります。
  Spring Bootで設定可能なディレクティブについては、以下を参照してください。

  * :spring-boot-doc:`Common Application Properties <reference/html/application-properties.>`

  ※ ``spring.resources.cache.cachecontrol.xxx`` となっている項目が、Cache-Controlに設定可能なディレクティブです。

.. tip::

  Cache-Controlに設定するディレクティブを検討する際は、以下が参考になります。

  * `rfc7234 section 5.2.2 <https://tools.ietf.org/html/rfc7234#section-5.2.2>`_
  * `HTTP caching - Google developers reference <https://developers.google.com/web/fundamentals/performance/optimizing-content-efficiency/http-caching>`_

サンプル全体は :sample-app:`spring-security-http-response-header-sample <web/spring-security-http-response-header>` を参照してください。
