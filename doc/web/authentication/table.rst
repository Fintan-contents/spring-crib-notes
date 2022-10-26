テーブル認証
==================================================
データベース上のユーザー情報を使用して認証処理を行う方法について説明します。

認証処理の実装には `Spring Security <https://projects.spring.io/spring-security/>`_ を使用します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

.. tip::

  このサンプルでは、データベースへのアクセスに :ref:`Doma2 <database-doma2>` を使用しています。
  Doma2を使用することで、SQLを外部ファイルで管理できるなどのメリットがあります。

認証で使用するテーブルの準備
--------------------------------------------------
データベースのテーブルを使って認証処理を行うため、ユーザ情報(ユーザ名やパスワード)を保持するテーブルを事前に作成します。
この例では、認証処理に最低限必要となるユーザ名とパスワードのみを保持しています。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/db/migration/V001__Create_user_table.sql
   :language: sql

実装例
--------------------------------------------------
Spring Securityの :spring-security-doc:`Form Login <servlet/authentication/passwords/form.html>` および :spring-security-doc:`Logout <servlet/authentication/logout.html>` を参考に、作成したログインページを表示するように設定します。

Spring Securityに対する設定
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/SecurityConfig.java
     :language: java
     :start-after: example-start
     :end-before: example-end

ユーザ情報を取得するため、Doma2を使用してユーザ情報を取得するためのDaoとServiceを作成します。
ServiceではSpring Securityが提供している :spring-security-doc:`UserDetailService <servlet/authentication/passwords/user-details-service.html#servlet-authentication-userdetailsservice>` を実装し、Bean定義することで、Spring Securityから呼び出されます。

Dao
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserDao.java
     :language: java
     :start-after: example-start
     :end-before: example-end

SQL
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/META-INF/keel/tableauth/UserDao/loadUserByUserName.sql
     :language: sql

Service
  .. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserService.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。
