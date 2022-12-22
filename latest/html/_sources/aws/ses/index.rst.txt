Amazon SESを使ってメールを送信する
==================================================
:spring-cloud-aws-doc:`Spring Cloud AWS <reference/html/index.html>` を使用して、Amazon SESでメールを送信する実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-ses <aws/ses>` を参照してください。

AWS SESを使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-starter-awsを追加します。また、Spring Bootでメールを送信するためのライブラリと、
  Amazon SES用のAWS SDKも依存ライブラリに追加します。

  .. literalinclude:: ../../../samples/aws/ses/pom.xml
    :language: xml
    :start-after: cloud-aws-start
    :end-before: cloud-aws-end
    :dedent: 4

application.properties
  AWSのリージョンを実行環境に設定します。
  デフォルトのリージョンはAWS SDKが提供する ``DefaultAwsRegionProviderChain`` により決定され、実行環境に設定されたAWSアカウント情報や、
  EC2のメタデータからリージョンを取得します。

  明示的に指定する場合は、 ``cloud.aws.region.static`` プロパティにリージョンを設定します。

  設定可能な値の詳細については :spring-cloud-aws-doc:`Configuring region <reference/html/index.html#configuring-region>` を参照してください。

AWSアカウントのクレデンシャル情報
  AWSアカウントのクレデンシャル情報を実行環境に設定します。
  クレデンシャル情報の設定方法はいくつかありますが、例えば環境変数を使用する方法であれば、以下の環境変数にクレデンシャル情報を設定します。

  * ``AWS_ACCESS_KEY_ID``
  * ``AWS_SECRET_ACCESS_KEY``

  クレデンシャル情報の設定方法の詳細については :spring-cloud-aws-doc:`SDK credentials configuration <reference/html/index.html#sdk-credentials-configuration>`
  を参照してください。

.. tip::

  IAMによるアクセス制御を行う場合、実行環境には必要な権限を付与する必要があります。
  必要な権限の詳細については :spring-cloud-aws-doc:`IAM Permissions <reference/html/index.html#iam-permissions-7>` を参照してください。

メール送信の実装例
--------------------------------------------------
この例では、メールアドレスが環境によって異なる場合を想定し、プロパティで設定可能にする実装とします。

  .. literalinclude:: ../../../samples/aws/ses/src/main/resources/application-local.properties
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
