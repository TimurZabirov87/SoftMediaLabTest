package com.example.softmedialabtest.model.grade;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StudentGradeEnum {
    UNSATISFACTORILY("неуд"),
    SATISFACTORILY("уд"),
    GOOD("хор"),
    EXCELLENT("отл");

    private String translation;

    StudentGradeEnum(String translation) {
        this.translation = translation;
    }

    public String getTranslation(){
        return translation;
    }

    public List<String> getTranslations() {
        return Arrays.stream(StudentGradeEnum.values()).toList().stream().map(StudentGradeEnum::getTranslation).collect(Collectors.toList());
    }
}
