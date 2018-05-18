このドキュメントについて
====================================

このドキュメントではSpringを活用してアプリケーションを構築する際に必要となるノウハウを提供します。

特に、実際にアプリケーションを開発する際に公式ドキュメントやWeb上から実装方法を見つけることが難しく、躓きやすい点についての情報を提供しています。


対象読者
------------------------------------

このドキュメントは、ソフトウェア開発経験のあるアーキテクトやプログラマ向けに書かれています。

特に、Spring Frameworkについて、次のような知識・能力を持っていることを前提としています。

* Spring Bootを利用して、Webアプリケーションを開発したことがあり、基本的な仕組みについて理解している。
* Spring Frameworkを利用したアプリケーションの基本的なアーキテクチャについて理解している。
* Spring BootやSpring Frameworkの公式ドキュメントから適切な情報を検索できる。
* その他、次にあげるような技術要素について、基本的な知識を持っている。
    * SQL
    * Maven
    * Amazon Web Services

これからSpring BootやAmazon Web Servicesを初めて利用するという人に向けたドキュメントとはなっていません。一般的な書籍やサイトを活用して学習してください。

特に、Spring Bootについては、次のような書籍・サイトがあります。

  * `Getting Started · Building an Application with Spring Boot <https://spring.io/guides/gs/spring-boot/>`_
  * `はじめての Spring Boot[改訂版] <https://www.kohgakusha.co.jp/books/detail/978-4-7775-1969-9>`_
  * `Spring徹底入門 Spring FrameworkによるJavaアプリケーション開発（株式会社NTTデータ）｜翔泳社の本 <http://www.shoeisha.co.jp/book/detail/9784798142470>`_


このドキュメントの使い方
------------------------------------

.. image:: ./how-to-use-this-document.png

Spring Frameworkを利用したWebアプリケーションのアーキテクチャについては、`Macchinetta Framework <https://macchinetta.github.io>`_ のドキュメントに分かりやすく記載されているので、参考にしてください。
ただし、このドキュメントとMacchinettaでは、利用しているSpring Frameworkのバージョンが異なり、設定の記載方法も異なるため（MacchinettaはXML, このドキュメントではAutoConfigurationとJavaConfig）、具体的な実装例や設定例はそのままでは利用できないと考えてください。

* `Macchinetta Server Framework Development Guideline <https://macchinetta.github.io/server-guideline-thymeleaf/current/ja/>`_
* `Macchinetta Batch Framework Development Guideline <https://macchinetta.github.io/batch-guideline/current/ja/>`_

詳細な実装方法については、Springの公式ドキュメントを参考にしてください。
