HTTPレスポンスヘッダ
====================================================
:spring-security-doc:`Spring Security <reference/html/>` を使用した場合に設定されるHTTPレスポンスヘッダについて記載します。

Spring Securityを使用した場合、デフォルトでは全てのHTTPレスポンスに以下のヘッダが設定されます。詳細は、以下の公式ドキュメントを参照してください。

* :spring-security-doc:`Spring Security Reference 21.1 Default Security Headers <reference/html/headers.html#default-security-headers>`

デフォルトで設定されるレスポンスヘッダ
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

.. tip::

  静的リソースをキャッシュさせたい場合には、 :ref:`web-static-resource-cache` を参考にしてください。
