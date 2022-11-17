Amazon S3にファイルをアップロード及びAmazon S3からファイルをダウンロードする
================================================================================
:spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` を使用して、Amazon S3にファイルをアップロード及びAmazon S3からファイルをダウンロードする実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-s3 <aws/s3>` を参照してください。

Amazon S3を使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-starter-awsを追加します。
  また、依存しているAWS SDKではJAXBが使用されますが、JDK 11からは標準ライブラリとして含まれていないため、依存ライブラリに追加します。
  
  .. literalinclude:: ../../../samples/aws/s3/pom.xml
    :language: xml
    :start-after: spring-cloud-aws-start
    :end-before: spring-cloud-aws-end

.. tip::

  ``spring-cloud-starter-aws`` の依存関係定義で ``version`` を指定していませんが、
  サンプルではdependencyManagementに ``spring-cloud-aws-dependencies`` を指定して
  Spring Cloud AWS関連ライブラリのバージョンを制御しています。

application.properties
  AWSのリージョンを設定します。
  実行環境がEC2の場合、EC2のメタデータからリージョンが取得できるためを設定する必要はありません。
  ローカル開発環境など実行環境がEC2以外の場合、 ``cloud.aws.region.static`` にリージョンを設定します。
  （サンプルでは ``application-local.properties`` にのみ指定しています）

  設定可能な値の詳細については :spring-cloud-aws-doc:`Configuring region <reference/html/index.html#configuring-region>` を参照してください。
  
  .. literalinclude:: ../../../samples/aws/s3/src/main/resources/application-local.properties
    :language: properties
    :start-after: region-start
    :end-before: region-end

AWSアカウントのクレデンシャル情報
  AWSアカウントのクレデンシャル情報を実行環境に設定します。
  クレデンシャル情報の設定方法はいくつかありますが、例えば環境変数を使用する方法であれば、以下の環境変数にクレデンシャル情報を設定します。
  
  * ``AWS_ACCESS_KEY_ID``
  * ``AWS_SECRET_ACCESS_KEY``

  クレデンシャル情報の設定方法の詳細については :spring-cloud-aws-doc:`SDK credentials configuration <reference/html/index.html#sdk-credentials-configuration>`
  を参照してください。

.. _s3-upload:

Amazon S3にファイルをアップロードする実装例
--------------------------------------------------
この例では、Springが提供する ``ResourceLoader`` を使用して指定したファイルをアップロードします。
リソースのロケーションに ``s3`` プロトコルを使用することで、Amazon S3のリソースを扱うことができます。

アップロード先のバケット名については環境によって異なることが想定されるため、ここではプロパティファイルで設定可能にします。

  .. literalinclude:: ../../../samples/aws/s3/src/main/resources/application-local.properties
    :language: properties
    :start-after: bucket-start
    :end-before: bucket-end

プロパティファイルに合わせて、プロパティ値をバインドするためのBeanを定義します。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Properties.java
    :language: java

アップロード先とするAmazon S3上のオブジェクトは、 ``ResourceLoader`` のリソースとして ``s3://<バケット名>/<オブジェクトキー名>`` の形式で指定します。
（例えば ``s3://keel-s3-bucket-test/upload/upload.txt`` のようになります）

指定したリソースに対して書き込むことで、Amazon S3にファイルをアップロードします。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3UploadService.java
    :language: java
    :start-after: upload-start
    :end-before: upload-end

.. tip::

  ``ResourceLoader`` を使用したアップロードでは、マルチパートアップロードを使用することができません。
  巨大なファイルをアップロードする等の理由でマルチパートアップロードを使用したい場合、AWS SDKから提供されている
  ``TransferManager`` を使用する等の方法で実装することができます。
  詳細については :spring-cloud-aws-doc:`Uploading with the TransferManager <reference/html/index.html#uploading-with-the-transfermanager>` を参照してください。

Amazon S3からファイルをダウンロードする実装例
--------------------------------------------------
この例では、Springが提供する ``ResourceLoader`` を使用して指定したファイルをダウンロードします。

``ResourceLoader`` でのリソースの指定方法やバケット名の設定については、 :ref:`Amazon S3へのアップロード実装例 <s3-upload>` と同じになります。
Amazon S3上のオブジェクトを表すリソースを指定し、リソースに対して読み込むことで、Amazon S3からファイルをダウンロードします。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3DownloadService.java
    :language: java
    :start-after: download-start
    :end-before: download-end
