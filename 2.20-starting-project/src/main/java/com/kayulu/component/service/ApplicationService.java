package com.kayulu.component.service;

import com.kayulu.component.dao.ApplicationDao;
import com.kayulu.component.models.Student;
import com.kayulu.component.util.StudentGradesUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    public double addGradeResultsForStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return StudentGradesUtils.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults());
    }

    public double findGradePointAverageForStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return StudentGradesUtils.findGradePointAverage(student.getStudentGrades().getMathGradeResults());
    }

    public boolean isNullStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return StudentGradesUtils.checkNull(student);
    }
}
