package keel.nablarch.controller;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

public class CodeManagementForm {

    // form-start
    @Required
    @Domain("codePattern01")
    private String codePattern01;

    @Domain("codePattern02")
    private String codePattern02;
    // form-end

    public String getCodePattern01() {
        return codePattern01;
    }

    public void setCodePattern01(String codePattern01) {
        this.codePattern01 = codePattern01;
    }

    public String getCodePattern02() {
        return codePattern02;
    }

    public void setCodePattern02(String codePattern02) {
        this.codePattern02 = codePattern02;
    }
}
