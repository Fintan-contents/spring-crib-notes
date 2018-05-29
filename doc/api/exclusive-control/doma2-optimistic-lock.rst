Doma2の楽観ロックで排他制御する
====================================================================================================

Doma2の ``@Version`` を使用した楽観ロック方式で実現します。

:doc:`Webの場合 </web/exclusive-control/doma2-optimistic-lock>` は更新前のバージョン番号をセッションに保存しますが、APIの場合はリクエストボディに含まれているバージョン番号がデータベースと一致しているかどうかで排他制御します。
そのため、APIを呼び出すクライアントで、変更しようとしているエンティティのバージョンを保持する必要があります。

参考情報
    * `Doma2 - 更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
    * `Doma2 - バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Controllerでの ``userService.update(User)`` の実行時にDoma2の楽観排他制御が行われます。楽観排他に失敗した場合は、Doma2から ``OptimisticLockException`` が送出されます。

送出された ``OptimisticLockException`` はアプリケーション全体で横断的に処理することで、個別の機能で例外処理する必要がなくなります。詳細は、 :doc:`/api/error-handling/index` を参照してください。

Controller

  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/controller/UsersController.java
    :language: java
    :linenos:
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end

Dao

  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/dao/UserDao.java
    :language: java
    :linenos:
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end

Entity

  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/entity/User.java
    :language: java
    :linenos:
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end

GlobalExceptionHandler: サンプルアプリケーションで、アプリケーション全体の例外を横断的にハンドリングしているクラスです。
  .. literalInclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/GlobalExceptionHandler.java
    :language: java
    :linenos:
    :start-after: optimistic-lock-example-start
    :end-before: optimistic-lock-example-end


サンプル全体は :sample-app:`doma2-optimistic-lock-sample <web/doma2-optimistic-lock>` を参照してください。