セッションの情報をRedisに保存する
====================================================================================================
セッション情報を `Redis <https://redis.io/>`_ に保存する方法について説明します。

セッション情報をRedisに保存することで、アプリケーションのスケールが可能となります。
Redisへのセッション情報の保存や取得は、 `Spring Session <https://projects.spring.io/spring-session/>`_ が行ってくれるので、アプリケーション開発者が意識する必要はありません。

.. warning::

  セッション情報をhttp session上に保存した場合は、基本的にはアプリケーションのヒープ上にセッション情報が保存されます。
  このためアプリケーションをスケールした場合にセッション情報が引き継がれない等の問題が発生します。

セッションの保存先をRedisにする
----------------------------------------------------------------------------------------------------
:spring-session-doc:`Spring Session - Spring Boot <reference/html5/guides/boot-redis.html>` を参考にライブラリの追加と設定の追加を行います。

ローカル開発環境について
----------------------------------------------------------------------------------------------------
Dockerの `Redis公式イメージ <https://hub.docker.com/_/redis/>`_ を使用することで簡単にRedis環境を構築出来ます。
この場合は、ローカル開発環境とテストや本番環境でRedisの接続先の設定が変わるので、:spring-boot-doc:`profile <reference/html/boot-features-external-config.html#boot-features-external-config-profile-specific-properties>`\ で設定値を切り替える等の対応が必要となります。

例えば、本番と開発PCで設定を切り替える場合には以下のような構成になります。
接続先の設定については、 :spring-session-doc:`Configuring the Redis Connection <reference/html5/guides/boot-redis.html#boot-redis-configuration>` を参照してください。

application.properties(本番用)
  .. code-block:: properties
  
    spring.redis.host=redis.server
  
application-dev.properties(開発用)
  .. code-block:: properties
  
    spring.redis.host=localhost


.. tip::

  Redis環境を開発者全員に構築させるのが面倒な場合には、下のようにSpring Session機能を無効化することで対応も可能です。

  .. code-block:: properties

    spring.session.store-type=none
