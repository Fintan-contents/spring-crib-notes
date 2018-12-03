AWS SESを使ってメールを送信する
==================================================
`Spring Cloud AWS <https://cloud.spring.io/spring-cloud-aws/>`_ を使用して、AWS SESでメールを送信する実装方法を説明します。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`aws-ses <aws/ses>` を参照してください。

AWS SESを使用するための設定例
--------------------------------------------------
pom.xml
  依存ライブラリにspring-cloud-starter-awsを追加します。
  
  .. literalinclude:: ../../../samples/aws/ses/pom.xml
    :language: xml
    :start-after: cloud-aws-start
    :end-before: cloud-aws-end
    :dedent: 4
    
  メールを送信するために必要となるライブラリを追加します。
  
  .. literalinclude:: ../../../samples/aws/ses/pom.xml
    :language: xml
    :start-after: mail-start
    :end-before: mail-end
    :dedent: 4
    
application.properties
  AWSのリージョン名を設定します。
  
  アプリケーションをEC2で動かす場合には、EC2のメタデータからリージョン名が取得できるためcloud.aws.region.autoにはtrueを設定します。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/resources/application-ec2.properties
    :language: properties
    :start-after: start
    :end-before: end
  
  EC2以外(例えば、ローカルの開発環境)で動かす場合には、下の例のようにcloud.aws.region.autoをfalseとしリージョン名を設定します。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/resources/application-local.properties
    :language: properties
    :start-after: start
    :end-before: end

環境変数
  以下の環境変数にAWSアカウントのクレデンシャル情報を設定します。
  
  * AWS_ACCESS_KEY_ID
  * AWS_SECRET_ACCESS_KEY
  
  AWS SDK for Javaが使用するクレデンシャル情報の推奨される設定方法などは、AWS SDK for Javaのドキュメントを参照してください。
  
AWS SESのみ異なるリージョンに構築した場合のカスタマイズ実装を追加する
----------------------------------------------------------------------
AWS SESは東京リージョンで利用することができないため(2018/10現在)、単一リージョンしか想定されていないSpring Cloud AWSをそのまま利用することができません。
このため、AWS SESのみ異なるリージョンで実行できるようにするためのBeanを登録するなどの対応が必要となります。

Beanの定義
  AWS SES用のConfigurationクラスを定義して、AWS SES専用のリージョンを指定できるようにします。
  リージョン名は、@Valueで受け取りプロパティファイルなどで指定できるようにします。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/MailConfiguration.java
    :language: java
    
application.properties
  上記のConfigurationクラスが受け取るリージョン名を設定します。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/resources/application-local.properties
    :language: properties
    :start-after: ses-start
    :end-before: ses-end
    
メール送信の実装例
--------------------------------------------------
メール送信用の設定を持つBeanを作成します
  Fromアドレスに設定するメールアドレスなどは環境によって異なる可能性があります。
  このため、プロパティファイルなどで環境ごと異なる値を設定できるBeanを作成します。
  
  このサンプルでは、FromとToのメールアドレスを設定できるBeanを作成しています。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/MailProperties.java

メール送信の実装例
  ファイルを添付する場合には、JavaMailSenderを使用してメールを送信します。
  ファイルを添付する必要がない場合には、addAttachmentの呼び出しが不要となります。
  
  .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/AttachmentMailService.java
    :language: java
    
  .. tip::
    
    ファイル添付を必要としない場合には、以下の実装例のようにMailSenderを使用することで、実装が少しだけ簡潔になります。
  
    .. literalinclude:: ../../../samples/aws/ses/src/main/java/keel/aws/ses/SimpleMailService.java
      :language: java
    
