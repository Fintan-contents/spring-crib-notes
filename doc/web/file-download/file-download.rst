ファイルダウンロード
==================================================
このページでは、ファイルをダウンロードする方法について説明します。

ファイルダウンロードは、 :spring-framework-doc:`BeanNameViewResolver <javadoc-api/org/springframework/web/servlet/view/BeanNameViewResolver.html>` の機能を利用して実施します。
BeanNameViewResolverは、SpringのDIコンテナに登録されたBeanから :spring-framework-doc:`View <javadoc-api/org/springframework/web/servlet/View.html>` を継承したBeanを取得し、HTTPレスポンスへの書込み処理を取得したBeanに委譲します。