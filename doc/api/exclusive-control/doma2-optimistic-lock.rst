Doma2の楽観ロックで排他制御する
====================================================================================================
Doma2の\ `@Version <http://static.javadoc.io/org.seasar.doma/doma/2.19.2/org/seasar/doma/Version.html>`_\ を使用した楽観ロック方式で実現します。

:doc:`Webの場合 </web/exclusive-control/doma2-optimistic-lock>` は更新前のバージョン番号をセッションに保存しますが、APIの場合はリクエストボディに含まれているバージョン番号がデータベースと一致しているかどうかで排他制御します。
そのため、APIを呼び出すクライアントで、変更しようとしているエンティティのバージョンを保持する必要があります。

Doma2の楽観ロックについては、以下の公式ドキュメントを参照してください。

* `更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
* `バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Controllerでの ``userService.update(User)`` の実行時にDoma2の楽観排他制御が行われます。楽観排他に失敗した場合は、Doma2がOptimisticLockExceptionを送出します。
OptimisticLockExceptionは、 ``doma-spring-boot-starter`` がSpringのOptimisticLockingFailureExceptionに変換して再送出します。
詳細は、以下を参照してください。

* :ref:`Doma2が送出する例外の変換 <doma2-exception-translator>`

送出されたOptimisticLockingFailureExceptionはアプリケーション全体で横断的に処理することで、個別の機能で例外処理する必要がなくなります。詳細は、 :doc:`/api/error-handling/index` を参照してください。

Controller
  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/controller/UsersController.java
    :language: java
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end
    :dedent: 4

Dao
  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/dao/UserDao.java
    :language: java
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end
    :dedent: 4

Entity
  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/entity/User.java
    :language: java
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end
    :dedent: 4

GlobalExceptionHandler
  サンプルアプリケーションで、アプリケーション全体の例外を横断的にハンドリングしているクラスです。
  
  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/GlobalExceptionHandler.java
    :language: java
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end
    :dedent: 4

サンプル全体は :sample-app:`doma2-optimistic-lock-sample <web/doma2-optimistic-lock>` を参照してください。
