2重送信防止
==================================================
Springから提供されている機能では、2重送信を防ぐことはできないため
`keel-spring-enhance <https://github.com/Fintan-contents/keel-spring-enhance>`_ の
`2重送信防止機能 <https://github.com/Fintan-contents/keel-spring-enhance/blob/master/README.md>`_ を使用します。

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
 
    * @TransactionTokenCheck(type = TransactionTokenType.BEGIN)を使用してトークンを生成します。
 
      * `keel-transaction-token-check` は、生成したトークンを画面のhidden項目に自動設定します。
      * `keel-transaction-token-check` は、生成したトークンを、サーバ側で保持します。
 
  * 登録処理へのリクエスト
 
    * @TransactionTokenCheckを使用して、リクエストで送信されるトークンと、サーバ側に保持したトークンをチェックします。
 
      * トークンチェックをパスした場合は、入力情報をデータベースに登録して登録完了画面にリダイレクトします。
      * トークンチェックエラーの場合は、エラー画面を返却します。
 
  .. literalinclude:: ../../../samples/web/double-submission/src/main/java/keel/doublesubmission/UserController.java
     :language: java
     :start-after: example-start
     :end-before: example-end
