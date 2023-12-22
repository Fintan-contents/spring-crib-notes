HTTPレスポンスヘッダ
====================================================
:spring-security-doc:`Spring Security <index.html>` を使用した場合に設定されるHTTPレスポンスヘッダについて説明します。

Spring Securityを使用した場合、デフォルトでは全てのHTTPレスポンスに以下のヘッダが設定されます。詳細は :spring-security-doc:`Default Security Headers <servlet/exploits/headers.html#servlet-headers-default>` を参照してください。

デフォルトで設定されるレスポンスヘッダ
  .. code-block:: YAML

     Cache-Control: no-cache, no-store, max-age=0, must-revalidate
     Pragma: no-cache
     Expires: 0
     X-Content-Type-Options: nosniff
     Strict-Transport-Security: max-age=31536000 ; includeSubDomains # HTTPS通信時のみ設定されます。
     X-Frame-Options: DENY
     X-XSS-Protection: 0

`X-Content-Type-Options: nosniff` が設定されるので、ブラウザはレスポンスのContent-Typeを推測しなくなります。そのため、レスポンスに正しいContent-Typeを設定しないと期待通りの動作をしなくなるので注意してください。Spring Bootのデフォルトでは適切なContent-Typeが設定されるように構成されています。

また、 `X-Frame-Options: DENY` が設定されるため、レスポンスのHTMLを iframe などのフレーム内に表示できなくなります。フレーム内に表示する必要がある場合は、 :spring-security-doc:`X-Frame-Options <servlet/exploits/headers.html#servlet-headers-frame-options>` を参考に SAMEORIGIN を設定してください。

.. tip::

  静的リソースをキャッシュさせたい場合には、 :ref:`web-static-resource-cache` を参考にしてください。
