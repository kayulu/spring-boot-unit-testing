package com.kayulu.component.dao;

import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.Student;
import com.kayulu.component.models.StudentGrades;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ApplicationDao {
    private final List<Student> collegeStudents;

    public ApplicationDao() {
        collegeStudents = new ArrayList<>();
    }

    @PostConstruct
    private void initializeStudents() {
        CollegeStudent student1 = new CollegeStudent("Kay", "Ulu", "kay.ulu@example.com");
        student1.setStudentGrades(new StudentGrades(Arrays.asList(10.0, 32.2, 4.0)));

        CollegeStudent student2 = new CollegeStudent("John", "Mayer", "john.mayer@example.com");
        student2.setStudentGrades(new StudentGrades(Arrays.asList(100.0, 62.2, 88.0)));

        CollegeStudent student3 = new CollegeStudent("Emma", "Johnson", "emma.johnson@example.com");
        student3.setStudentGrades(new StudentGrades(Arrays.asList(70.4, 42.4, 34.0)));

        CollegeStudent student4 = new CollegeStudent("Liam", "Smith", "liam.smith@example.com");
        student4.setStudentGrades(new StudentGrades(Arrays.asList(16.2, 32.23, 35.0)));

        CollegeStudent student5 = new CollegeStudent("Ava", "Wilson", "ava.wilson@example.com");
        student5.setStudentGrades(new StudentGrades(Arrays.asList(66.6, 34.2, 54.0)));

        collegeStudents.add(student1);
        collegeStudents.add(student2);
        collegeStudents.add(student3);
        collegeStudents.add(student4);
        collegeStudents.add(student5);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(collegeStudents);
    }

    public Student getStudentByEmail(String email) {
        return collegeStudents.stream()
                .filter(student -> {
                    if(student instanceof CollegeStudent cs)
                        return cs.getEmailAddress().equals(email);
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    public void addStudent(Student student) {
        collegeStudents.add(student);
    }
}
