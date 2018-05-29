画面やAPIでTomcatのアクセスログを出力する
==================================================
Spring Bootのデフォルト構成ではEmbedded Tomcatで起動しているアプリケーションのアクセスログが出力されません。
アクセスログが出力されないと、サーバ側の処理時間や返却するHTTPステータスコードが分からないため、アクセスログを出力するように構成することをおすすめします。

アクセスログを出力するための設定例
--------------------------------------------------
pom.xml
  アクセスログを `logback <https://logback.qos.ch/>`_ を使用してログ出力するライブラリを追加します。

  .. literalinclude:: ../../../samples/web/tomcat-access-log/pom.xml
      :language: xml
      :start-after: example-start
      :end-before: example-end

.. tip::
  ログのフォーマットの変更(例えば、ログの可視化用にLTSV形式への変更)などを行う場合には、
  `logback-access-spring-boot-starter <https://github.com/akihyro/logback-access-spring-boot-starter>`_ を参考にしてください。

  例えば、LTSVでログを出力する場合は下の内容をもつ ``logback-access.xml`` を作成して ``src/main/resources`` 配下に配置します。

  .. literalinclude:: ../../../samples/web/tomcat-access-log/src/main/resources/logback-access.xml
    :language: xml
    :start-after: ltsv-start
    :end-before: ltsv-end

サンプル全体は :sample-app:`tomcat-access-log-sample <web/tomcat-access-log>` を参照してください。
