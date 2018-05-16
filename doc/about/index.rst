このドキュメントについて
====================================

このドキュメントではSpring Framework、Spring Boot、Thymeleaf、DomaといったOSSを活用してアプリケーションを構築する際に必要となるノウハウを提供します。

特に、実際にアプリケーションを開発する際に公式ドキュメントやWeb上から実装方法を見つけることが難しく、躓きやすい点についての情報を提供しています。


対象読者
------------------------------------

このドキュメントは、ソフトウェア開発経験のあるアーキテクトやプログラマ向けに書かれています。

特に、Spring Frameworkについて、次のような知識・能力を持っていることを前提としています。

* Spring Bootを利用して、Webアプリケーションを開発したことがあり、基本的な仕組みについて理解している。
* Spring Frameworkを利用したアプリケーションの基本的なアーキテクチャについて理解している。
* Spring BootやSpring Frameworkの公式ドキュメントから適切な情報を検索できる。

これからSpring Bootを利用し始めるという人に向けたドキュメントとはなっていません。Spring Bootを初めて利用する方は、次にあげる書籍・サイトを参考にするなどして情報を補う必要があります。

  * `Getting Started · Building an Application with Spring Boot <https://spring.io/guides/gs/spring-boot/>`_
  * `はじめての Spring Boot[改訂版] <https://www.kohgakusha.co.jp/books/detail/978-4-7775-1969-9>`_
  * `Spring徹底入門 Spring FrameworkによるJavaアプリケーション開発（株式会社NTTデータ）｜翔泳社の本 <http://www.shoeisha.co.jp/book/detail/9784798142470>`_

また、Spring Frameworkを利用したWebアプリケーションのアーキテクチャについては、`Macchinetta Framework <https://macchinetta.github.io>`_ のドキュメントに分かりやすく記載されているので、参考にしてください。
ただし、このドキュメントとMacchinettaでは、利用しているSpring Frameworkのバージョンが異なり、設定の記載方法も異なるため（MacchinettaはXML, このドキュメントではJavaConfig）、具体的な実装例や設定例はそのままでは利用できないと考えてください。

* `Macchinetta Server Framework Development Guideline <https://macchinetta.github.io/server-guideline-thymeleaf/current/ja/>`_
* `Macchinetta Batch Framework Development Guideline <https://macchinetta.github.io/batch-guideline/current/ja/>`_

Spring Framework以外にも、次にあげるような技術要素を利用していますが、それらについての入門情報も記載していません。

* SQL
* Maven
* Amazon Web Services
