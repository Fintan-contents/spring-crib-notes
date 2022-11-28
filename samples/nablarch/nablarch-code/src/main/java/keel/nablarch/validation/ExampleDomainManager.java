package keel.nablarch.validation;

import nablarch.core.validation.ee.DomainManager;

/**
 * DomainManager実装クラス。
 */
public class ExampleDomainManager implements DomainManager<DomainBean> {

    @Override
    public Class<DomainBean> getDomainBean() {
        return DomainBean.class;
    }
}
