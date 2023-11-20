認可
==================================================
Webアプリケーションでの認可の方法について説明します。

認可処理の実装には `Spring Security <https://projects.spring.io/spring-security/>`_ を使用します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

ユーザに対して権限を設定する
--------------------------------------------------
この例では :doc:`../authentication/table` を使用するため、同じようにユーザ名や権限（ロール）を保持するテーブルを事前に作成します。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/db/migration/V001__Create_user_table.sql
   :language: sql

認証時にログインしたユーザの権限を保持する
-----------------------------------------------------
認可を正しく行うために、認証時にログインしたユーザの権限を保持する必要があります。
この例ではデータベースで認証・認可情報を管理しているため、ユーザ情報を取得する際に権限情報も取得し、ユーザ情報に設定します。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserService.java
   :language: java
   :start-after: example-start
   :end-before: example-end

URLパターンごとにアクセスに必要な権限を設定する
---------------------------------------------------------------------------
URLパターンごとにアクセスに必要な権限を設定します。
この例では、 ``/admin`` 配下はadmin権限を持つユーザだけがアクセスできるように設定します。
``/admin`` 以外については、認証済みのユーザであればすべてのユーザがアクセスできるように設定します。

また、admin権限についてはuser権限を含めた権限にするため、:spring-security-doc:`Hierarchical Roles <servlet/authorization/architecture.html#authz-hierarchical-roles>` を使用して階層構造を設定します。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/SecurityConfig.java
   :language: java
   :start-after: role-start
   :end-before: role-end

アクセス権限に応じて画面上のリンクやボタンを非表示にする
-----------------------------------------------------------
:doc:`Thymeleaf <../view/thymeleaf>` を使用している場合に、権限に応じて画面上にあるリンクやボタンの表示を制御する方法を説明します。

ThymeleafのSpring Security用ライブラリである `thymeleaf-extras-springsecurity <https://github.com/thymeleaf/thymeleaf-extras-springsecurity>`_ を依存関係に追加します。

.. literalinclude:: ../../../samples/web/table-authentication/pom.xml
   :language: xml
   :start-after: thymeleaf-security-start
   :end-before: thymeleaf-security-end

.. tip::

  thymeleaf-extras-springsecurityは、Spring Securityのバージョンによってライブラリ名（artifactId）が異なります。例えば、Spring Security 6.Xを使用している場合には、thymeleaf-extras-springsecurity6を使用します。

thymeleaf-extras-springsecurityの機能を使って、権限がない場合にはリンクやボタンを非表示にします。
この例では、ログインしているユーザが\ ``GET /admin``\ への権限を保持している場合にリンクが表示されます。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/templates/top.html
   :language: html
   :start-after: example-start
   :end-before: example-end

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。
