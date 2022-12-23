package keel.nablarch.controller;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

// property-start
public class ValidationForm {

    @Required
    @Domain("id")
    private Integer id;

    @Required
    @Domain("name")
    private String name;

    @Required
    @Domain("age")
    private Integer age;

    @Domain("note")
    private String note;

    // （アクセサの記載は省略します）
    // property-end

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
