Doma2の楽観ロックで排他制御する
====================================================================================================
Doma2の\ :doma-java-doc:`@Version <org/seasar/doma/Version.html>`\ を使用した楽観ロック方式で実現します。

Doma2の楽観ロックについては、以下の公式ドキュメントを参照してください。

* :doma-doc:`更新 <query/update/>`
* :doma-doc:`バッチ更新 <query/batch-update/>`

処理フロー
-----------------------------------------------
* 初期表示

  * 画面表示するデータをDBから取得します。
  * 取得したデータをセッションに格納します。

* 更新

  * 画面の入力値をバリデーションします。
  * バリデーション後の入力値とセッションに格納したバージョン番号を使用して、DBのデータを更新します。

    * データの更新に成功した場合は、初期表示にリダイレクトします。
    * 楽観ロック例外が発生した場合は、HTTPステータスコードに409を設定して、エラーページを表示します。


実装例
-----------------------------------------------
Controllerでの ``userService.update(User)`` の実行時にDoma2の楽観排他制御が行われます。楽観排他に失敗した場合は、Doma2がOptimisticLockExceptionを送出します。
OptimisticLockExceptionは、 ``doma-spring-boot-starter`` がSpringのOptimisticLockingFailureExceptionに変換して再送出します。
詳細は、以下を参照してください。

* :ref:`Doma2が送出する例外の変換 <doma2-exception-translator>`

送出されたOptimisticLockingFailureExceptionはアプリケーション全体で横断的に処理することで、個別の機能で例外処理する必要がなくなります。詳細は、 :doc:`/web/error-handling/index` を参照してください。

Controller
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/controller/UserUpdateController.java
    :language: java
    :start-after: example-start
    :end-before: example-end


Dao
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/domain/repository/UserDao.java
    :language: java
    :start-after: example-start
    :end-before: example-end
    :dedent: 4


Entity
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/entity/User.java
    :language: java
    :start-after: example-start
    :end-before: example-end
    :dedent: 4


ControllerAdvice
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/controller/ErrorControllerAdvice.java
    :language: java
    :start-after: example-start
    :end-before: example-end


サンプル全体は :sample-app:`doma2-optimistic-lock-sample <web/doma2-optimistic-lock>` を参照してください。
