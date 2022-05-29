package com.sbnz.gleficu.model.enums;

public enum AgeRange {
    YOUNG("Less than 25 years old."),
    MIDDLE("Between 25 and 50 years old."),
    OLDER("More than 50 years old.");

    final String age;

    private AgeRange(String age){
        this.age = age;
    }

    public String getAge() {
        return this.age;
    }
}
