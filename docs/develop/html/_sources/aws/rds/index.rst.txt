AWS RDSにアクセスする
==================================================
`Spring Cloud AWS <https://cloud.spring.io/spring-cloud-aws/>`_ を使用して、AWS RDSを使用するための実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-rds <aws/rds>` を参照してください。

AWS RDSを使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-starter-awsを追加します。
  
  .. literalinclude:: ../../../samples/aws/rds/pom.xml
    :language: xml
    :start-after: cloud-aws-jdbc-start
    :end-before: cloud-aws-jdbc-end
    :dedent: 4
    
  .. tip::
  
    このサンプルでは、データベースアクセスには :ref:`Doma <database-doma2>` を使用しています。
    Spring BootでDomaを使用する手順などはリンク先を参照してください。

application.properties
  AWSのリージョン名とデータベースの接続先を設定します。
  
  アプリケーションをEC2で動かす場合には、EC2のメタデータからリージョン名が取得できるためcloud.aws.region.autoにはtrueを設定します。
  
  データベースの接続先は、 ``cloud.aws.rds.<RDSインスタンス名>.password`` にパスワードのみを設定します。
  このサンプルでは、RDSのインスタンス名が「keel」であるため、cloud.aws.rds.keel.passwordに対してパスワードを設定しています。
  
  RDSにMySQLを選択している場合、Springのトランザクション管理の仕組みを使用し、かつ読み取り専用トランザクションの場合、参照用SQLの実行をリードレプリカに自動的に振り分ける機能を利用できます。
  (2018/10現在、Spring Cloud AWSのリードレプリカ振り分け機能はAWS RDSのMySQLのみ利用可能となっています。)
  利用する場合には、 ``cloud.aws.rds.<RDSインスタンス名>.readReplicaSupport`` にtrueを設定します。
  
  .. literalinclude:: ../../../samples/aws/rds/src/main/resources/application-ec2.properties
    :language: properties
    :start-after: config-start
    :end-before: config-end
  
  EC2以外(例えば、ローカルの開発環境)で動かす場合には、下の例のようにcloud.aws.region.autoをfalseとしリージョン名を設定します。
  このサンプルでは、ローカルPCに構築したデータベースにアクセスするようにしています。
  
  .. literalinclude:: ../../../samples/aws/rds/src/main/resources/application-local.properties
    :language: properties
    :start-after: config-start
    :end-before: config-end
  
  .. tip::
    
    ローカルの開発環境については、以下の理由によりPC上にデータベース環境を用意することをおすすめします。
    
    * AWS RDSを常時可動した場合コストが非常に高くなる
    * 各開発者が使用しているデータベースのスキーマバージョンが同じとは限らない
    * 開発中は機能のテストなどでデータを自由に変更したい
   
環境変数
  以下の環境変数にAWSアカウントのクレデンシャル情報を設定します。
  
  * AWS_ACCESS_KEY_ID
  * AWS_SECRET_ACCESS_KEY
  
  AWS SDK for Javaが使用するクレデンシャル情報の推奨される設定方法などは、AWS SDK for Javaのドキュメントを参照してください。
  
実装例
--------------------------------------------------
Spring Cloud AWSからAWS RDSを使用するからといって特別な実装を行う必要はありません。
ローカルのデータベースを使用する場合とAWS RDSを使用する場合で、データベースにアクセスするクラスやSQLの実装方法に差異はありません。

.. tip::

  RDSのMySQLを使用している場合で、参照用SQLをリードレプリカに対して発行したい場合には、以下のように読み取り専用トランザクションを設定します。

  .. literalinclude:: ../../../samples/aws/rds/src/main/java/keel/aws/rds/UserService.java
    :language: java
    :start-after: readonly-start
    :end-before: readonly-end
    :dedent: 4
