package com.kayulu.component.models;

public interface Student {

    String studentInformation();

    String getFullName();

    StudentGrades getStudentGrades();

    void setStudentGrades(StudentGrades grades);
}
