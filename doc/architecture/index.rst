アプリケーションのスタック
====================================

このドキュメントでは、Springに関する記載を中心に各種OSSのライブラリについても言及しています。
ドキュメント内に記載しているアプリケーションの構成要素は以下となります。

アプリケーションの構成要素
  .. image:: images/application-stack.drawio.png
     :width: 90%

.. list-table:: Web / APIの構成要素の概要
   :widths: 5 20 75
   :header-rows: 1

   * - No
     - 構成要素
     - 概要
   * - 1
     - :spring-framework-doc:`Spring Web MVC <web/webmvc.html>`
     - Springが提供するWeb MVCフレームワークです。
   * - 2
     - `Spring Security <https://spring.io/projects/spring-security>`_
     - Springが提供する認証・認可のフレームワークです。
   * - 3
     - `Thymeleaf <https://www.thymeleaf.org/documentation.html>`_
     - テンプレートエンジンです。画面の作成に使用します。
   * - 4
     - `keel-spring-enhance <https://github.com/Fintan-contents/keel-spring-enhance>`_
     - | Springの不足機能などを補うための機能を提供するライブラリです。
       | 二重送信の防止に利用します。

.. list-table:: Batchの構成要素の概要
   :widths: 5 20 75
   :header-rows: 1

   * - No
     - 構成要素
     - 概要
   * - 1
     - `Spring Batch <https://spring.io/projects/spring-batch>`_
     - Springが提供するBatchフレームワークです。

.. list-table:: AWSの構成要素の概要
   :widths: 5 20 75
   :header-rows: 1

   * - No
     - 構成要素
     - 概要
   * - 1
     - `Spring Cloud AWS <https://awspring.io>`_
     - Spring Cloud傘下のプロジェクトで、Amazon Web ServicesのSDKが提供する機能をSpringに統合します。

.. list-table:: 共通の構成要素の概要
   :widths: 5 20 75
   :header-rows: 1

   * - No
     - 構成要素
     - 概要
   * - 1
     - :doma-doc:`Doma2 <>`
     - 2-way SQL等を特徴としたDBアクセスフレームワークです。
   * - 2
     - `SLF4J <https://www.slf4j.org>`_
     - ロギングシステムのAPI(インターフェース)を提供するライブラリです。
   * - 3
     - `logback <https://logback.qos.ch>`_
     - ロギングシステムのAPIを実装したライブラリです。
   * - 4
     - `Jackson <https://github.com/FasterXML/jackson>`_
     - JSONをパースするライブラリです。
   * - 5
     - `Hibernate Validator <https://hibernate.org/validator/>`_
     - Jakarta Bean Validationを実装した入力値チェック用のライブラリです。
   * - 6
     - `Flyway <https://flywaydb.org>`_
     - DDLの管理等を目的としたマイグレーションツールです。
