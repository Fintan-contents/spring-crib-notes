Doma2の楽観ロックで排他制御する
====================================================================================================

Doma2の ``@Version`` を使用した楽観ロック方式で実現します。

参考情報
    * `Doma2 - 更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
    * `Doma2 - バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_

処理フロー
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
* 初期表示

  * 画面表示するデータをDBから取得します。
  * 取得したデータをセッションに格納します。

* 更新

  * 画面の入力値をバリデーションします。
  * バリデーション後の入力値とセッションに格納したバージョン番号を使用して、DBのデータを更新します。

    * データの更新に成功した場合は、初期表示にリダイレクトします。
    * 楽観ロック例外が発生した場合は、HTTPステータスコードに409を設定して、エラーページを表示します。


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Controller
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/controller/UserUpdateController.java
    :language: java
    :linenos:
    :start-after: example-start
    :end-before: example-end


Dao
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/domain/repository/UserDao.java
    :language: java
    :linenos:
    :start-after: example-start
    :end-before: example-end


Entity
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/entity/User.java
    :language: java
    :linenos:
    :start-after: example-start
    :end-before: example-end


ControllerAdvice
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/keel/controller/ErrorControllerAdvice.java
    :language: java
    :linenos:
    :start-after: example-start
    :end-before: example-end


サンプル全体は :sample-app:`doma2-optimistic-lock-sample <web/doma2-optimistic-lock>` を参照してください。
