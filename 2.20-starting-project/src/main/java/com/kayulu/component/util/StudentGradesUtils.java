package com.kayulu.component.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class StudentGradesUtils {
    public static double addGradeResultsForSingleClass(List<Double> grades) {
        return grades.stream()
                .mapToDouble(Double::doubleValue).sum();
    }

    public static double findGradePointAverage (List<Double> grades ) {
        double result = grades.stream()
                .collect(Collectors.averagingDouble(Double::doubleValue));

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, RoundingMode.HALF_UP);
        return resultRound.doubleValue();

    }

    public static boolean isGradeGreater(double gradeOne, double gradeTwo) {
        return gradeOne > gradeTwo;
    }

    public static boolean checkNull(Object obj) {
        return obj == null;
    }
}
