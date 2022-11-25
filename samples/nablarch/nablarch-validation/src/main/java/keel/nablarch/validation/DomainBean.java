package keel.nablarch.validation;

import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;

// class-start
/**
 * ドメインのバリデーションルール。
 */
public class DomainBean {

    /** ID **/
    @Digits(integer = 4, fraction = 0)
    public String id;

    /** 名前 **/
    @Length(max = 10)
    @SystemChar(charsetDef = "全角文字")
    public String name;

    /** 年齢 **/
    @NumberRange(min = 0, max = 100)
    public String age;

    /** 備考 **/
    @Length(max = 10)
    @SystemChar(charsetDef = "システム許容文字")
    public String note;
}
// class-end
