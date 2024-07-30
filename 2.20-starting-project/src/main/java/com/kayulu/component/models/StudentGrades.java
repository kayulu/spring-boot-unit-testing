package com.kayulu.component.models;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class StudentGrades {

    List<Double> mathGradeResults;

    public StudentGrades() {
    }

    public StudentGrades(List<Double> mathGradeResults) {
        this.mathGradeResults = mathGradeResults;
    }

    public List<Double> getMathGradeResults() {
        return mathGradeResults;
    }

    public void setMathGradeResults(List<Double> mathGradeResults) {
        this.mathGradeResults = mathGradeResults;
    }

    @Override
    public String toString() {
        return "StudentGrades{" +
                "mathGradeResults=" + mathGradeResults +
                '}';
    }
}
