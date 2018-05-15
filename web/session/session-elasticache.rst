Spring Bootで作られたーアプリケーションののセッション保存先をAWS ElastiCache(Redis)にする
====================================================================================================
メリット
----------------------------------------------------------------------------------------------------
アプリケーションサーバの外にセッション情報が保存されるため、アプリケーションのスケールが可能となる。

.. note::
  Http Sessionを使用した場合、アプリケーションサーバのヒープ上にセッション情報が保存されるため、アプリケーションのスケールが出来ない。

セッションの保存先をAWS ElastiCacheにする方法
----------------------------------------------------------------------------------------------------

依存ライブラリ(pom.xml)
  .. code-block:: xml

    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
    </dependency>

設定例(application.properties)  
  接続情報などの設定値の詳細は `RedisProperties.java <https://github.com/spring-projects/spring-boot/blob/v2.0.2.RELEASE/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/data/redis/RedisProperties.java>`_ を参照すること

  .. code-block:: properties

    # sessionの保存先をredisにする
    spring.session.store-type=redis

    # ElastiCacheのエンドポイントを設定する


