セッションの情報をredisに保存する
====================================================================================================
セッション情報を `redis <https://redis.io/>`_ に保存することで、アプリケーションのスケールが可能となります。
redisへのセッション情報は、 `Spring Session <https://projects.spring.io/spring-session/>`_ が行ってくれるので、アプリケーション開発者が意識する必要はありません。

.. tip::

  セッション情報をhttp session上に保存した場合は、基本的にはアプリケーションのヒープ上にセッション情報が保存されます。
  このためアプリケーションをスケールした場合にセッション情報が引き継がれない等の問題が発生します。

参考情報
----------------------------------------------------------------------------------------------------
* `Spring Session - Spring Boot <https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html>`_

セッションの保存先をredisにする
----------------------------------------------------------------------------------------------------
`Spring Session - Spring Boot <https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html>`_ を参考にライブラリの追加と設定の追加を行います。

ローカル開発環境について
----------------------------------------------------------------------------------------------------
Dockerの `redis公式イメージ <https://hub.docker.com/_/redis/>`_ を使用することで簡単にredis環境を構築出来ます。
この場合は、ローカル開発環境とテストや本番環境でredisの接続先の設定が変わるので、profileで設定値を切り替える等の対応が必要となります。

.. tip::

  redis環境を開発者全員に構築させるのが面倒な場合には、下のようにSpring session機能を無効化することで対応も可能です。

  .. code-block:: properties

    spring.session.store-type=none
