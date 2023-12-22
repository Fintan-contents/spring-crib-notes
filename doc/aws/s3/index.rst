Amazon S3にファイルをアップロード及びAmazon S3からファイルをダウンロードする
================================================================================
:spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` を使用して、Amazon S3にファイルをアップロード及びAmazon S3からファイルをダウンロードする実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-s3 <aws/s3>` を参照してください。

Amazon S3を使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-aws-starter-s3を追加します。
  
  .. literalinclude:: ../../../samples/aws/s3/pom.xml
    :language: xml
    :start-after: spring-cloud-aws-start
    :end-before: spring-cloud-aws-end

.. tip::

  ``spring-cloud-aws-starter-s3`` の ``version`` を指定していませんが、
  サンプルでは :spring-cloud-aws-doc:`Spring Cloud AWSが提供するBOM <reference/html/index.html#bill-of-materials>` を使用してSpring Cloud AWS関連ライブラリのバージョンを制御しています。

AWSのリージョン
  AWSのリージョンを実行環境に設定します。
  デフォルトのリージョンはAWS SDKが提供する ``DefaultAwsRegionProviderChain`` により決定され、実行環境に設定されたAWSアカウント情報や、EC2のメタデータからリージョンを取得します。

  明示的に指定する場合は、 ``spring.cloud.aws.region.static`` 、または ``spring.cloud.aws.s3.region`` プロパティにリージョンを設定します。

  設定可能な値の詳細については :spring-cloud-aws-doc:`Spring Cloud AWS Core - Region <reference/html/index.html#region>` および :spring-cloud-aws-doc:`S3 Integration - Configuration <reference/html/index.html#configuration-2>` を参照してください。

AWSアカウントのクレデンシャル情報
  AWSアカウントのクレデンシャル情報を実行環境に設定します。
  クレデンシャル情報の設定方法はいくつかありますが、例えば環境変数を使用する方法であれば、以下の環境変数にクレデンシャル情報を設定します。
  
  * ``AWS_ACCESS_KEY_ID``
  * ``AWS_SECRET_ACCESS_KEY``

  クレデンシャル情報の設定方法の詳細については :spring-cloud-aws-doc:`Spring Cloud AWS Core - Credentials <reference/html/index.html#credentials>` を参照してください。

.. tip::

  IAMによるアクセス制御を行う場合、実行環境には必要な権限を付与する必要があります。
  必要な権限の詳細については :spring-cloud-aws-doc:`SES Integration - IAM Permissions <reference/html/index.html#iam-permissions-2>` を参照してください。

.. _s3-upload:

Amazon S3にファイルをアップロードする実装例
--------------------------------------------------
この例では、 :spring-cloud-aws-doc:`Spring Cloud AWSが提供する S3Template <reference/html/index.html#using-s3template>`  を使用して指定したファイルをアップロードします。

アップロード先のバケット名については環境によって異なることが想定されるため、ここではプロパティファイルで設定可能にします。

  .. literalinclude:: ../../../samples/aws/s3/src/main/resources/application.properties
    :language: properties
    :start-after: bucket-start
    :end-before: bucket-end

プロパティファイルに合わせて、プロパティ値をバインドするためのBeanを定義します。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Properties.java
    :language: java


``S3Template`` を使用してAmazon S3にファイルをアップロードします。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3UploadService.java
    :language: java
    :start-after: upload-start
    :end-before: upload-end

Amazon S3からファイルをダウンロードする実装例
--------------------------------------------------
この例では、Spring Cloud AWSが提供する ``S3Template`` を使用して指定したファイルをダウンロードします。

バケット名の設定については、 :ref:`Amazon S3へのアップロード実装例 <s3-upload>` と同じになります。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3DownloadService.java
    :language: java
    :start-after: download-start
    :end-before: download-end
