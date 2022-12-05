二重送信防止
==================================================
Springから提供されている機能だけでは二重送信を防ぐことはできません。
ここでは `keel-spring-enhance <https://github.com/Fintan-contents/keel-spring-enhance>`_ の
`二重送信防止機能 <https://github.com/Fintan-contents/keel-spring-enhance/blob/master/keel-spring-web/keel-transaction-token-check/README.md>`_ を使用して二重送信を防ぐ方法について説明します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

設定例
--------------------------------------------------
pom.xml
  依存ライブラリにkeel-spring-boot-starter-webを追加します。
 
  .. literalinclude:: ../../../samples/pom.xml
     :language: xml
     :start-after: keel-web-start
     :end-before: keel-web-end
     :dedent: 6
 
実装例
--------------------------------------------------
Controller
  サンプルアプリケーションでは、入力画面⇒確認画面⇒登録完了画面の流れで遷移します。トークンの生成やチェックは、以下のタイミングで実施しています。
 
  * 確認画面への遷移リクエスト
 
    * `@TransactionTokenCheck(type = TransactionTokenType.BEGIN)` を使用してトークンを生成します。
 
      * 確認画面のhidden項目にトークンが自動設定されます。
      * サーバ側ではトークンが保持されます。
 
  * 登録処理へのリクエスト
 
    * `@TransactionTokenCheck` を使用して、リクエストで送信されるトークンとサーバ側に保持したトークンをチェックします。
 
      * トークンチェックをパスした場合は、入力情報をデータベースに登録して登録完了画面にリダイレクトします。
      * トークンチェックエラーの場合は、エラー画面を返却します。
 
  .. literalinclude:: ../../../samples/web/double-submission/src/main/java/keel/doublesubmission/UserController.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`double-submission <web/double-submission>` を参照してください。
