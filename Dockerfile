FROM sphinxdoc/sphinx

WORKDIR /repos/doc
RUN pip3 install sphinx_rtd_theme
