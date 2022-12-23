Amazon RDSにアクセスする
==================================================
:spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` を使用して、Amazon RDSを使用するための実装方法を説明します。

.. tip::

  Spring Bootのデータソース設定に接続情報を設定するだけでもAmazon RDSへ接続することができますが、
  Spring Cloud AWSを使用することで、リードレプリカの自動検出やフェイルオーバー中の再試行といった機能を使用することができます。
  詳細については :spring-cloud-aws-doc:`JDBC によるデータアクセス <reference/html/index.html>` を参照してください。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-rds <aws/rds>` を参照してください。

Amazon RDSを使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリに spring-cloud-starter-aws-jdbc を追加します。
  
  .. literalinclude:: ../../../samples/aws/rds/pom.xml
    :language: xml
    :start-after: cloud-aws-jdbc-start
    :end-before: cloud-aws-jdbc-end
    :dedent: 4
    
  .. tip::
  
    このサンプルでは、データベースアクセスには :doma-doc:`Doma2 <>` を使用しています。
    Spring BootでDoma2を使用する実装例については :ref:`データベースアクセスにDoma2を使用する <database-doma2>` を参照してください。

application.properties
  AWSのリージョン名とデータベースの接続先を設定します。

  デフォルトのリージョンはAWS SDKが提供する ``DefaultAwsRegionProviderChain`` により決定され、実行環境に設定されたAWSアカウント情報や、EC2のメタデータからリージョンを取得します。
  明示的に指定する場合は、 ``cloud.aws.region.static`` プロパティにリージョンを設定します。

  接続先の情報は ``cloud.aws.rds.instances[x]`` プロパティに設定します。
  接続先は複数設定することができるため、 ``cloud.aws.rds.instances[0]`` のようにインデックスを指定します。
  リードレプリカがサポートされているデータベースを使用する場合は、 ``cloud.aws.rds.instances[x].readReplicaSupport`` の値を ``true`` に設定することで、読み取り専用トランザクションでのアクセス先をリードレプリカに向けることができます。

  .. literalinclude:: ../../../samples/aws/rds/src/main/resources/application-ec2.properties
    :language: properties
    :start-after: datasource-start
    :end-before: datasource-end

  なお、このサンプルではローカル開発環境からはRDSではなく別途用意したデータベースに接続する実装としています。
  コストやテスト容易性を考慮してローカル開発環境ではRDSを使用しない場合も多いと考えられるため、このような実装としています。
  そのため、 ``application-local.properties`` では ``cloud.aws.rds.instances[x]`` プロパティを使用せず、
  Spring Bootのデータソース設定である ``spring.datasource`` プロパティを使用しています。

  .. literalinclude:: ../../../samples/aws/rds/src/main/resources/application-local.properties
    :language: properties
    :start-after: config-start
    :end-before: config-end

AWSアカウントのクレデンシャル情報
  AWSアカウントのクレデンシャル情報を実行環境に設定します。
  クレデンシャル情報の設定方法はいくつかありますが、例えば環境変数を使用する方法であれば、以下の環境変数にクレデンシャル情報を設定します。

  * ``AWS_ACCESS_KEY_ID``
  * ``AWS_SECRET_ACCESS_KEY``

  クレデンシャル情報の設定方法の詳細については :spring-cloud-aws-doc:`SDK credentials configuration <reference/html/index.html#sdk-credentials-configuration>`
  を参照してください。

.. tip::

  IAMによるアクセス制御を行う場合、実行環境には必要な権限を付与する必要があります。
  必要な権限の詳細については :spring-cloud-aws-doc:`IAM Permissions <reference/html/index.html#iam-permissions-6>` を参照してください。

実装例
--------------------------------------------------
Spring Cloud AWSを使用していても、前述のプロパティ以外で特別な実装はありません。
一般的なデータベースにアクセスする際のクラスやSQLの実装方法に差異はありません。

.. tip::

  リードレプリカに対してSQLを発行したい場合には、以下のように読み取り専用トランザクションを設定します。

  .. literalinclude:: ../../../samples/aws/rds/src/main/java/keel/aws/rds/UserService.java
    :language: java
    :start-after: readonly-start
    :end-before: readonly-end
    :dedent: 4
