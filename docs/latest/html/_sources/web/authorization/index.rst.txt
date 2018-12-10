認可
==================================================
Webアプリケーションでの認可の方法について説明します。

認可の考え方や仕組みについては、以下の公式ドキュメントを参照してください。

* `Spring Security <https://projects.spring.io/spring-security/>`_

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

ユーザに対して権限を設定する
--------------------------------------------------
ユーザに対して権限を設定する必要があります。
:doc:`../authentication/table` を使用する場合は、以下のようなテーブルを用意してユーザに対して権限を割り当てます。

.. list-table::
  :header-rows: 1

  * - カラム名
    - 説明
  * - ユーザ名
    - ユーザ名を保持するカラム
  * - 権限(ロール)
    - ユーザに割り当てた権限(ロール)を保持するカラム

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。

認証時にログインしたユーザの権限を保持する
-----------------------------------------------------
認可を正しく行うために認証時にログインしたユーザの権限を保持する必要があります。
データベースで認証・認可の情報を管理している場合には、以下のようにユーザ情報にテーブルから取得した権限の情報を保持します。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/UserService.java
   :language: java
   :start-after: example-start
   :end-before: example-end

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。

エントリポイントごとにアクセス出来る権限を設定する
---------------------------------------------------------------------------
Spring Securityに対してエントリポイントごとにアクセス可能な権限を設定します。
この例では、 ``/admin`` 配下はadmin権限(ロール)を持つユーザだけがアクセスできます。
``/admin`` 以外については、認証済みのユーザであればすべてのユーザがアクセスできます。

.. literalinclude:: ../../../samples/web/table-authentication/src/main/java/keel/tableauth/SecurityConfig.java
   :language: java
   :start-after: role-start
   :end-before: role-end

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。

アクセス権限に応じて画面上のリンクやボタンを非表示にする
-----------------------------------------------------------
:doc:`Thymeleaf <../view/thymeleaf>` を使用している場合で、画面上のリンクやボタンを非表示にする方法を説明します。

ThymeleafのSpring Security用のライブラリ(thymeleaf-extras-springsecurity4)を依存に追加します。

.. literalinclude:: ../../../samples/web/table-authentication/pom.xml
   :language: xml
   :start-after: thymeleaf-security-start
   :end-before: thymeleaf-security-end

thymeleaf-extras-springsecurity4の機能を使って、権限がない場合にはリンクやボタンを非表示にします。
この例では、ログインしているユーザが\ ``GET /admin``\ への権限を保持している場合にリンクが表示されます。

thymeleaf-extras-springsecurity4の機能の詳細には、以下を参照してください。

* `thymeleaf-extras-springsecurity <https://github.com/thymeleaf/thymeleaf-extras-springsecurity>`_

.. literalinclude:: ../../../samples/web/table-authentication/src/main/resources/templates/top.html
   :language: html
   :start-after: example-start
   :end-before: example-end
   
.. tip::

  thymeleaf-extras-springsecurityは、Spring Security 4.Xまでの対応となります。
  このため、Spring Security 5.Xの機能についてはthymeleaf-extras-springsecurity経由では利用できません。

サンプル全体は :sample-app:`table-authentication-sample <web/table-authentication>` を参照してください。




