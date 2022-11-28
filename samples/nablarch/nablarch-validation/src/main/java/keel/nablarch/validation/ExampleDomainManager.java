package keel.nablarch.validation;

import nablarch.core.validation.ee.DomainManager;

// class-start
/**
 * DomainManager実装クラス。
 */
public class ExampleDomainManager implements DomainManager<DomainBean> {

    @Override
    public Class<DomainBean> getDomainBean() {
        return DomainBean.class;
    }
}
// class-end
