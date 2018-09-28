AWS S3にファイルをアップロード&S3からファイルをダウンロードする
================================================================================
`Spring Cloud AWS <https://cloud.spring.io/spring-cloud-aws/>`_ を使用して、AWS S3にファイルをアップロード及びAWS S3からファイルをダウンロードする実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-s3 <aws/s3>` を参照してください。

AWS S3を使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-starter-awsを追加する。
  
  .. literalinclude:: ../../../samples/aws/s3/pom.xml
    :language: xml
    :start-after: cloud-aws-start
    :end-before: cloud-aws-end
    
application.properties
  AWSのリージョン名を設定します。
  
  アプリケーションをEC2で動かす場合には、EC2のメタデータからリージョン名が取得できるためcloud.aws.region.autoにはtrueを設定します。
  
  .. literalinclude:: ../../../samples/aws/s3/src/main/resources/application-ec2.properties
    :language: properties
    :start-after: start
    :end-before: end
  
  EC2以外(例えば、ローカルの開発環境)で動かす場合には、下の例のようにcloud.aws.region.autoをfalseとしリージョン名を設定します。
  
  .. literalinclude:: ../../../samples/aws/s3/src/main/resources/application-local.properties
    :language: properties
    :start-after: start
    :end-before: end

環境変数
  以下の環境変数にAWSアカウントのクレデンシャル情報を設定します。
  
  * AWS_ACCESS_KEY_ID
  * AWS_SECRET_ACCESS_KEY
  
  AWS SDK for Javaが使用するクレデンシャル情報の推奨される設定方法などは、AWS SDK for Javaのドキュメントを参照してください。
  
AWS S3にファイルをアップロードする実装例
--------------------------------------------------
* S3にファイルをアップロードする際に使用するTransferManagerをBeanとして登録します

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Configuration.java
    :language: java
    :start-after: transferManager-start
    :end-before: transferManager-end
    
* S3のバケット名を設定するBeanを作成します

  使用するバケット名は環境によって異なることが想定されます。このため、プロパティファイルなどでバケット名を設定できるBeanを作成します。
  
  この例の場合には、プロパティファイルに ``s3.bucket-name=test-bucket`` のようにバケット名が設定できます。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Properties.java
    :language: java


* AWS S3にファイルをアップロードするBeanにTransferManagerをインジェクションします

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Service.java
    :language: java
    :start-after: injection-start
    :end-before: injection-end

* TransferManagerを使用してファイルをアップロードします

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Service.java
    :language: java
    :start-after: upload-start
    :end-before: upload-end
    
AWS S3からファイルをダウンロードする実装例
--------------------------------------------------
* S3にファイルをアップロードする際に使用するTransferManagerをBeanとして登録します

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Configuration.java
    :language: java
    :start-after: transferManager-start
    :end-before: transferManager-end

* AWS S3にファイルをアップロードするBeanにTransferManagerをインジェクションします

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Service.java
    :language: java
    :start-after: injection-start
    :end-before: injection-end

* TransferManagerを使用してファイルをダウンロードします

  この例では、S3オブジェクトの内容をローカルストレージ上に保存します。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Service.java
    :language: java
    :start-after: download-start
    :end-before: download-end
    
* TransferManagerを使用してファイルをダウンロードします

  この例では、S3オブジェクトの内容をOutputStreamに移送します。
  TransferManagerでは直接ローカルストレージのファイルに書き込むことしかできないため、ストリームに流し込みたい場合は、
  この実装例のようにAmazonS3を使用する必要があります。

  .. literalinclude:: ../../../samples/aws/s3/src/main/java/keel/aws/s3/AwsS3Service.java
    :language: java
    :start-after: download2-start
    :end-before: download2-end
    

