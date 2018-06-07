二重送信防止
==================================================
Springから提供されている機能では二重送信を防止できないため、
`TERASOLUNA <http://terasolunaorg.github.io/>`_ のトランザクショントークンチェックの機能を利用して二重送信を防止します。
詳細は、以下を参照してください。
 
* :macchinetta-server-guideline-thymeleaf-doc:`Macchinetta Server Framework Development Guideline - 4.6. 二重送信防止 <ArchitectureInDetail/WebApplicationDetail/DoubleSubmitProtection.html>`
 
設定例
--------------------------------------------------
pom.xml
  依存ライブラリに\ `terasoluna-gfw-web <https://github.com/terasolunaorg/terasoluna-gfw/tree/master/terasoluna-gfw-common-libraries/terasoluna-gfw-web>`_ を追加します。
 
  .. literalinclude:: ../../../samples/web/double-submission/pom.xml
     :language: xml
     :start-after: example-start
     :end-before: example-end
     :dedent: 4
 
実装例
--------------------------------------------------
Configuration
  トランザクショントークンチェックを実施するための定義をします。
 
  .. literalinclude:: ../../../samples/web/double-submission/src/main/java/keel/doublesubmission/WebConfig.java
     :language: java
     :start-after: example-start
     :end-before: example-end
 
Controller
  サンプルアプリケーションでは、入力画面⇒確認画面⇒登録完了画面の流れで遷移します。トークンの生成やチェックは、以下のタイミングで実施しています。
 
  * 確認画面への遷移リクエスト
 
    * @TransactionTokenCheck(type = TransactionTokenType.BEGIN)を使用してトークンを生成します。
 
      * `terasoluna-gfw-web` は、生成したトークンを画面のhidden項目に自動設定します。
      * `terasoluna-gfw-web` は、生成したトークンを、サーバ側で保持します。
 
  * 登録処理へのリクエスト
 
    * @TransactionTokenCheckを使用して、リクエストで送信されるトークンと、サーバ側に保持したトークンをチェックします。
 
      * トークンチェックをパスした場合は、入力情報をデータベースに登録して登録完了画面にリダイレクトします。
      * トークンチェックエラーの場合は、エラー画面を返却します。
 
  .. literalinclude:: ../../../samples/web/double-submission/src/main/java/keel/doublesubmission/UserController.java
     :language: java
     :start-after: example-start
     :end-before: example-end