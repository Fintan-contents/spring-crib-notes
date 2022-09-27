FROM sphinxdoc/sphinx

WORKDIR /docs
RUN pip3 install sphinx_rtd_theme
