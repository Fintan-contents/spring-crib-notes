Amazon RDSにアクセスする
==================================================

.. important::

  :spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` では、2.x までRDSへアクセスする機能が提供されていましたが、3.x から削除されています。

  代替として、主にAurora向けではありますがAWSから提供されているJDBCの機能を拡張した `AWS JDBC Driver <https://github.com/awslabs/aws-advanced-jdbc-wrapper>`_ が挙げられます。フェイルオーバー処理やリードレプリカへのアクセス切り替えが実現できるため、Auroraを使用される場合は参考にしてください。
  （ `Spring Cloud AWSのIssue <https://github.com/awspring/spring-cloud-aws/issues/322>`_ でも言及されております）
