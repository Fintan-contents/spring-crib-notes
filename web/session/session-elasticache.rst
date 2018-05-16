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
依存ライブラリ
  ``pom.xml`` (Mavenプロジェクトの場合)に以下のライブラリを追加します。

  .. code-block:: xml

    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
    </dependency>

設定例
  ``application.properties`` (propertiesを使う場合)に、セッションの保存先をredisにするための設定を追加します。
  なお、redisのホストやポートの情報は環境にあった値を設定してください。

  .. code-block:: properties

    # sessionの保存先をredisにします
    spring.session.store-type=redis

    # redisの接続情報を設定します
    spring.redis.host=localhost
    spring.redis.port=6379

    # その他は要件に合わせて設定してください。

ローカル開発環境について
----------------------------------------------------------------------------------------------------
Dockerの `公式イメージ <https://hub.docker.com/_/redis/>`_ を使用することで簡単にredis環境を構築出来ます。
この場合は、ローカル開発環境とテストや本番環境でredisの接続先の設定が変わるので、profileで設定値を切り替える等の対応が必要となります。

.. tip::

  redis環境を開発者全員に構築させるのが面倒な場合には、下のようにSpring session機能を無効化することで対応も可能です。

  .. code-block:: properties

    spring.session.store-type=none
