Amazon SESを使ってメールを送信する
==================================================
:spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` を使用して、Amazon SESでメールを送信する実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-ses <aws/ses>` を参照してください。

AWS SESを使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-aws-starter-sesを追加します。また、Spring Bootでメールを送信するためのライブラリも依存ライブラリに追加します。

  .. literalinclude:: ../../../samples/aws/ses/pom.xml
    :language: xml
    :start-after: cloud-aws-start
    :end-before: cloud-aws-end
    :dedent: 4

.. tip::

  ``spring-cloud-aws-starter-s3`` の ``version`` を指定していませんが、
  サンプルでは :spring-cloud-aws-doc:`Spring Cloud AWSが提供するBOM <reference/html/index.html#bill-of-materials>` を使用してSpring Cloud AWS関連ライブラリのバージョンを制御しています。

AWSのリージョン
  AWSのリージョンを実行環境に設定します。
  デフォルトのリージョンはAWS SDKが提供する ``DefaultAwsRegionProviderChain`` により決定され、実行環境に設定されたAWSアカウント情報や、EC2のメタデータからリージョンを取得します。

  明示的に指定する場合は、 ``spring.cloud.aws.region.static`` 、または ``spring.cloud.aws.ses.region`` プロパティにリージョンを設定します。

  設定可能な値の詳細については :spring-cloud-aws-doc:`Spring Cloud AWS Core - Region <reference/html/index.html#region>` および :spring-cloud-aws-doc:`SES Integration - Configuration <reference/html/index.html#configuration-3>` を参照してください。

AWSアカウントのクレデンシャル情報
  AWSアカウントのクレデンシャル情報を実行環境に設定します。
  クレデンシャル情報の設定方法はいくつかありますが、例えば環境変数を使用する方法であれば、以下の環境変数にクレデンシャル情報を設定します。

  * ``AWS_ACCESS_KEY_ID``
  * ``AWS_SECRET_ACCESS_KEY``

  クレデンシャル情報の設定方法の詳細については :spring-cloud-aws-doc:`Spring Cloud AWS Core - Credentials <reference/html/index.html#credentials>` を参照してください。

.. tip::

  IAMによるアクセス制御を行う場合、実行環境には必要な権限を付与する必要があります。
  必要な権限の詳細については :spring-cloud-aws-doc:`SES Integration - IAM Permissions <reference/html/index.html#iam-permissions-3>` を参照してください。

メール送信の実装例
--------------------------------------------------
この例では、メールアドレスが環境によって異なる場合を想定し、プロパティで設定可能にする実装とします。

  .. literalinclude:: ../../../samples/aws/ses/src/main/resources/application.properties
    :language: properties
    :start-after: mail-start
    :end-before: mail-end

プロパティファイルに合わせて、プロパティ値をバインドするためのBeanを定義します。

  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/MailProperties.java
    :language: java

ファイルを添付しないような単純なメールを送信する場合には、Springが提供する ``MailSender`` を使用して簡潔に実装できます。

  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/SimpleMailService.java
    :language: java

ファイルを添付する場合は、Springが提供する ``JavaMailSender`` を使用して実装できます。
ファイルの添付が必要ない場合でも、 ``MailSender`` ではなく ``JavaMailSender`` を使用することで詳細な設定を行うことができます。

  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/AttachmentMailService.java
    :language: java
