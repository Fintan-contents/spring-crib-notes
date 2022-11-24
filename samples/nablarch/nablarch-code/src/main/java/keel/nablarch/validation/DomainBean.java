package keel.nablarch.validation;

import nablarch.common.code.validator.ee.CodeValue;

/**
 * ドメインのバリデーションルール。
 */
public class DomainBean {

    // domain-start
    @CodeValue(codeId = "CODE01", pattern = "pattern01")
    public String codePattern01;

    @CodeValue(codeId = "CODE01", pattern = "pattern02")
    public String codePattern02;
    // domain-end
}
