Spring Boot + Doma2を使用した楽観ロックの検証
====================================================================================================

Doma2の `@Version` を使用した楽観ロック方式で実現する。

参考情報
    * `Doma2 - 更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
    * `Doma2 - バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_

処理フロー
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
* 初期表示

  * 画面表示するデータをDBから取得
  * 取得したデータをセッションに格納

* 更新

  * 画面の入力値を精査
  * 精査後の入力値とセッションに格納したバージョン番号を使用して、DBのデータを更新

    * データの更新に成功した場合は、初期表示にリダイレクト
    * 楽観ロック例外が発生した場合は、HTTPステータスコードに409を設定して、エラーページを表示


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Controller
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/jp/co/tis/keel/controller/UserUpdateController.java
    :language: java


Dao
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/jp/co/tis/keel/domain/repository/UserDao.java
    :language: java


Entity
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/jp/co/tis/keel/entity/User.java
    :language: java


ControllerAdvice
  .. literalInclude:: ../../../samples/web/doma2-optimistic-lock/src/main/java/jp/co/tis/keel/controller/ErrorControllerAdvice.java
    :language: java
