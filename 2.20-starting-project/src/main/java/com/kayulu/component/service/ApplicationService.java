package com.kayulu.component.service;

import com.kayulu.component.models.Student;
import org.springframework.beans.factory.annotation.Autowired;

import com.kayulu.component.dao.ApplicationDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    public double addGradeResultsForStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults());
    }

    public double findGradePointAverageForStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return findGradePointAverage(student.getStudentGrades().getMathGradeResults());
    }

    public boolean isNullStudent(String email) {
        Student student = applicationDao.getStudentByEmail(email);

        return checkNull(student);
    }

    private double addGradeResultsForSingleClass(List<Double> grades) {
        return grades.stream()
                .mapToDouble(Double::doubleValue).sum();
    }

    private double findGradePointAverage (List<Double> grades ) {
        double result = grades.stream()
                .collect(Collectors.averagingDouble(Double::doubleValue));

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();

    }

    private boolean isGradeGreater(double gradeOne, double gradeTwo) {
        return gradeOne > gradeTwo;
    }

    private boolean checkNull(Object obj) {
        return obj == null;
    }
}
