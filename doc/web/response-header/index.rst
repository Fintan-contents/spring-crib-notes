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

X-Content-Type-Options: nosniff が設定されるので、ブラウザはレスポンスのContent-Typeを推測しなくなります。そのため、レスポンスに正しいContent-Typeを設定しないと期待通りの動作をしなくなるので注意してください。Spring Bootのデフォルトでは適切なContent-Typeが設定されるように構成されています。

また、 X-Frame-Options: DENY が設定されるため、レスポンスのHTMLを iframe などのフレーム内に表示することができなくなります。フレーム内に表示する必要がある場合は、 :spring-security-doc:`Spring Security Reference 21.1 Default Security Headers <reference/html/headers.html#headers-frame-options>` を参考に SAMEORIGIN を設定してください。

.. tip::

   X-Content-Type-Optionsというヘッダは、もともとはInternet Explorerでのみ採用されていましたが、現在ではChromeやFirefoxでも採用されています。(`X-Content-Type-Options - HTTP | MDN <https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Content-Type-Options>`_)

.. tip::

  Spring Securityを使用しなかった場合は、上記のヘッダ値は全て設定されません。

.. tip::

  静的リソースをキャッシュさせたい場合には、 :ref:`web-static-resource-cache` を参考にしてください。
