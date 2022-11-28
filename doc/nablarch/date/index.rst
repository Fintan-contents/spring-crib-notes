Nablarchの日付管理機能を使用する
==================================================
TISでは、豊富な基幹システム構築経験から得られたナレッジを集約したJavaアプリケーション開発/実行基盤として `Nablrch <https://fintan.jp/page/1868/>`_ を提供しています。
SpringからNablarchの機能を利用することで、Springに不足している機能を補うことができます。

この例では、Nablarchの機能である :nablarch-doc:`日付管理 <doc/application_framework/application_framework/libraries/date.html>` を使用して、システム日時と業務日付を管理する実装方法を説明します。

日付管理機能では、データベース上で区分ごとに業務日付を管理することができます。また、システム日時の取得では、テスト実行時などに任意の日時に切り替えることができます。

サンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

サンプル全体は :sample-app:`nablarch-code <nablarch/nablarch-date>` を参照してください。
