HTTPレスポンスヘッダ
====================================================
`Spring Security <https://docs.spring.io/spring-security/site/docs/current/reference/html/>`_ を使用した場合に設定されるHTTPレスポンスヘッダについて記載します。

Spring Securityを使用した場合、デフォルトでは全てのHTTPレスポンスに以下のヘッダが設定されます。詳細は、以下の公式ドキュメントを参照してください。

* `Spring Security Reference 21.1 Default Security Headers <https://docs.spring.io/spring-security/site/docs/current/reference/html/headers.html#default-security-headers>`_

.. code-block:: YAML

   Cache-Control: no-cache, no-store, max-age=0, must-revalidate
   Pragma: no-cache
   Expires: 0
   X-Content-Type-Options: nosniff
   Strict-Transport-Security: max-age=31536000 ; includeSubDomains # HTTPS通信時のみ設定されます。
   X-Frame-Options: DENY
   X-XSS-Protection: 1; mode=block


.. tip::

  Spring Securityを使用しなかった場合は、上記のヘッダ値は全て設定されません。


静的コンテンツをキャッシュする
-------------------------------------------------
アプリケーションによっては、静的コンテンツをブラウザやキャッシュサーバに、キャッシュさせたい場合もあるかと思います。

その場合は、application.propertiesにmax-age等を設定する事で実現が可能です。

設定例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
application.properties
  .. literalinclude:: ../../../samples/web/spring-security-http-response-header/src/main/resources/application.properties
     :language: properties
     :start-after: example-start
     :end-before: example-end


.. tip::

  Cache-Controlには、max-age以外にもいくつかの設定可能なディレクティブがあります。
  Spring Bootで設定可能なディレクティブについては、以下を参照してください。

  * `Spring Boot Reference Guide Appendix A. Common application properties <https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#common-application-properties>`_

  ※ ``spring.resources.cache.cachecontrol.xxx`` となっている項目が、Cache-Controlに設定可能なディレクティブです。

.. tip::

  Cache-Controlに設定するディレクティブを検討する際は、以下が参考になります。

  * `rfc7234 section 5.2.2 <https://tools.ietf.org/html/rfc7234#section-5.2.2>`_
  * `HTTP caching - Google developers reference <https://developers.google.com/web/fundamentals/performance/optimizing-content-efficiency/http-caching>`_

サンプル全体は :sample-app:`spring-security-http-response-header-sample <web/spring-security-http-response-header>` を参照してください。
