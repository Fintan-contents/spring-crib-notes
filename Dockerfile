FROM sphinxdoc/sphinx

WORKDIR /repos/doc

RUN pip3 install sphinx_rtd_theme

RUN apt-get update \
    && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

RUN pip3 install setuptools==57.4.0 \
    && pip3 install docutils-ast-writer
