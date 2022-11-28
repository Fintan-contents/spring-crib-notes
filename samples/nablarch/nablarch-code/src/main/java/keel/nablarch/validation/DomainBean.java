package keel.nablarch.validation;

import nablarch.common.code.validator.ee.CodeValue;

// class-start
/**
 * ドメインのバリデーションルール。
 */
public class DomainBean {

    @CodeValue(codeId = "CODE01", pattern = "pattern01")
    public String codePattern01;

    @CodeValue(codeId = "CODE01", pattern = "pattern02")
    public String codePattern02;
}
// class-end
