package com.shashi.kafka.embedded.model;


import java.util.StringJoiner;

public class Student {
    private String name;
    private String dateOfBirth;

    public Student() {
    }

    public Student(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("dateOfBirth='" + dateOfBirth + "'")
                .toString();
    }
}
