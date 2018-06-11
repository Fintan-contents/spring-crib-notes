Thymeleafを使用して各画面で共通のレイアウトを利用する
====================================================================================================
`Thymeleaf <https://www.thymeleaf.org/documentation.html>`_ を使用して、共通レイアウトを作成する方法について記載します。

Thymeleafの基本的な使用方法については、以下のドキュメントを参照してください。

* :thymeleaf-tutorials-doc:`Tutorial: Using Thymeleaf <usingthymeleaf_ja.html>`
* :thymeleaf-tutorials-doc:`Tutorial: Thymeleaf + Spring <thymeleafspring.html>`
* :thymeleaf-tutorials-doc:`Tutorial: Extending Thymeleaf <extendingthymeleaf.html>`


実装例
-----------------------------------------------
共通レイアウトは、:thymeleaf-tutorials-doc:`ThymeleafのFragments <usingthymeleaf_ja.html#フラグメント>` を使用して実現します。

例えば、

* <head>内の定義
* ヘッダーのレイアウト
* フッターのレイアウト

を共通化したい場合は以下のような実装になります。

<head>を定義したフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/head.html
    :language: html

ヘッダーのフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/header.html
    :language: html

フッターのフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/footer.html
    :language: html

ユーザ画面
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/user.html
    :language: html

.. tip::
  Fragmentsを読込む方法として、以下の方法があります。

    * th:insert
    * th:replace
    * th:include

  上記の使い分けについては、 :thymeleaf-tutorials-doc:`Tutorial: Using Thymeleaf -  Including template fragments <usingthymeleaf_ja.html#テンプレートフラグメントのインクルード>` を参照してください。

サンプル全体は :sample-app:`template-engine-thymeleaf-sample <web/template-engine-thymeleaf>` を参照してください。

