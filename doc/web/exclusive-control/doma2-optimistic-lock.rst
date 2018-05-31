Doma2の楽観ロックで排他制御する
====================================================================================================
Doma2の\ `@Version <http://static.javadoc.io/org.seasar.doma/doma/2.19.2/org/seasar/doma/Version.html>`_\ を使用した楽観ロック方式で実現します。

Doma2の楽観ロックについては、以下の公式ドキュメントを参照してください。

* `更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
* `バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_

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
