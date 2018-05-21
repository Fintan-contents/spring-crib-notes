このドキュメントについて
====================================

このドキュメントでは `Spring <https://spring.io/>`_ を活用して、クラウド上にアプリケーションを構築する際に必要となるノウハウを提供します。

特に、実際にアプリケーションを開発する際に公式ドキュメントやWeb上から実装方法を見つけることが難しく、躓きやすい点についての情報を提供しています。

.. _target-user:

対象読者
------------------------------------

このドキュメントは、ソフトウェア開発経験のあるアーキテクトやプログラマ向けに書かれています。

特に、Springについて、次のような知識・能力を持っていることを前提としています。

* `Spring Boot <https://projects.spring.io/spring-boot/>`_ を利用して、Webアプリケーションを開発したことがあり、基本的な仕組みについて理解している。
* `Spring Framework <https://projects.spring.io/spring-framework/>`_ を利用したアプリケーションの基本的なアーキテクチャについて理解している。
* `Springの公式ドキュメント <https://spring.io/projects>`_ から適切な情報を検索できる。
* その他、次にあげるような技術要素について、基本的な知識を持っている。
    * SQL
    * Maven
    * Amazon Web Services

これからSpringやAmazon Web Servicesを初めて利用するという人は、一般的な書籍やサイトを活用して学習してください。

特に、Springについては、次のような書籍・サイトがあります。

* `Getting Started · Building an Application with Spring Boot <https://spring.io/guides/gs/spring-boot/>`_
* `はじめての Spring Boot[改訂版] <https://www.kohgakusha.co.jp/books/detail/978-4-7775-1969-9>`_
* `Spring徹底入門 Spring FrameworkによるJavaアプリケーション開発（株式会社NTTデータ）｜翔泳社の本 <http://www.shoeisha.co.jp/book/detail/9784798142470>`_

また、このドキュメントではアプリケーションをクラウド上にデプロイすることを想定しています。そういったアプリケーションに必要となるアーキテクチャについて、次にあげるサイトを参照して学習してください。

* `クラウドネイティブなアプリケーション — Macchinetta Server Framework Cloud Extension Development Guideline <https://macchinetta.github.io/cloud-guideline/current/ja/Overview/CloudNativeApplication.html>`_
* `The Twelve-Factor App <https://12factor.net/ja/>`_
* `Beyond the 12 Factor App <https://content.pivotal.io/ebooks/beyond-the-12-factor-app>`_


このドキュメントの使い方
------------------------------------

.. image:: ./how-to-use-this-document.png

基本的な実装方法については、Springの公式ドキュメントを参照してください。

また、実装を進める中でSpringを利用したアプリケーションのアーキテクチャについてより深く理解する必要が出た場合は、まず `Macchinetta Framework <https://macchinetta.github.io>`_ を参照することをおすすめします。
ただし、このドキュメントとMacchinettaでは、利用しているSpring Frameworkのバージョンが異なり、設定の記載方法も異なるため（MacchinettaはXML, このドキュメントではAutoConfigurationとJavaConfig）、具体的な実装例や設定例はそのままでは利用できないと考えてください。

* `Macchinetta Server Framework Development Guideline <https://macchinetta.github.io/server-guideline-thymeleaf/current/ja/>`_
* `Macchinetta Server Framework Cloud Extension Development Guideline <https://macchinetta.github.io/cloud-guideline/current/ja/>`_
* `Macchinetta Batch Framework Development Guideline <https://macchinetta.github.io/batch-guideline/current/ja/>`_
