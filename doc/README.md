## sphinx_rtd_themeのインストール

pipで `sphinx_rtd_theme` をインストールします。

```sh
pip install sphinx_rtd_theme
```

## textlintの実行方法
### 環境構築
#### Node.js
Node.jsをインストールします。

#### npm install
npmで依存ライブラリをインストールします。

```sh
npm install
```

#### docutils-ast-writer
[textlint-plugin-rst](https://github.com/jimo1001/textlint-plugin-rst)の依存ライブラリである
docutils-ast-writerをインストールします。

```sh
pip install docutils-ast-writer
```

### 実行

対象ディレクトリを指定してtextlintを起動します。

```sh
./node_modules/.bin/textlint development_tools
```

### 設定ファイル

| ファイル               | 説明           |
|------------------------|----------------|
| .textlintrc            | textlintの設定 |
| .textlint/prh.yml      | 辞書的なやつ   |
