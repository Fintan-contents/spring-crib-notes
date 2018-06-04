テーブル認証
==================================================
データベース上の認証用テーブルを使って認証処理を行う方法について説明します。

.. tip::

  このサンプルでは、`Spring Security <https://projects.spring.io/spring-security/>`_\ 提供のデータベースを使った認証用クラスではなく、:ref:`Doma2 <database-doma2>` を使用しています。
  Doma2を使用することで、SQLを外部ファイルで管理できるなどのメリットがあります。

認証で使用するテーブルの準備
--------------------------------------------------
データベースのテーブルを使って認証処理を行うため、ユーザの情報(ユーザ名やパスワード)を保持するテーブルを事前に作成します。
この例では、認証処理に最低限必要となるユーザ名とパスワードのみを保持しています。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/db/migration/V001__Create_user_table.sql
   :language: sql

実装例
--------------------------------------------------
Doma2を使用して認証処理を行うためのDao及びServiceを作成します。
作成したServiceを使用して認証処理が行われるよう、Spring Securityに対する設定を行います。

Dao
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserDao.java
     :language: java
     :start-after: example-start
     :end-before: example-end

SQL
  SQLでは、認証用のテーブルからユーザ名に紐づく情報を取得します。

  .. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/META-INF/keel/tableauth/UserDao/loadUserByUserName.sql
     :language: sql

Service
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserService.java
     :language: java
     :start-after: example-start
     :end-before: example-end

Spring Securityに対する設定
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/SecurityConfig.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。
