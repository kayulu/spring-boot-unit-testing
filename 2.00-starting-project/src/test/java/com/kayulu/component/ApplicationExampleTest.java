package com.kayulu.component;

import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationExampleTest {

    private static int count = 0;

    @Value("${info.school.name}")
    private String schoolName;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CollegeStudent student;

    @Autowired
    private StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        count++;
        System.out.println("Testing: " + appInfo + " which is " + appDescription +
                "   Version: " + appVersion + ". Execution of test method " + count);

        student.setFirstname("Kayhan");
        student.setLastname("Ulu");
        student.setEmailAddress("kay.ulu@kayulu.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 88.0, 91.5, 97.0)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Test app properties")
    void testAppProperties() {
        assertEquals("kayulu", schoolName);
        assertEquals("My Super Cool Gradebook", appInfo);
        assertEquals("a fun way to track student grades!", appDescription);
        assertEquals("1.0.0", appVersion);
    }

    @Test
    @DisplayName("Add grade results for student grades")
    public void addGradeResultsForStudentGrades() {
        assertEquals(376.5, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Add grade results for student grades NOT equals")
    public void addGradeResultsForStudentGradesAssertNotEquals() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Is grade greater")
    public void isGradeGreater() {
        assertTrue(studentGrades.isGradeGreater(90, 89),
                "failure - should be true");
    }

    @Test
    @DisplayName("Is grade greater false")
    public void isGradeGreaterFalse() {
        assertFalse(studentGrades.isGradeGreater(20, 89),
                "failure - should be false");
    }

    @Test
    @DisplayName("Check null for student grades")
    public void checkNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(student.getStudentGrades()),
                "object should not be null");
    }

    @Test
    @DisplayName("Create student without grades init")
    public void createStudentsWithoutGradesInit() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()), "Grades should be null");
    }

    @Test
    @DisplayName("Verify students are prototypes")
    public void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student, studentTwo);
        assertTrue(context.isPrototype("collegeStudent"));
    }

    @Test
    @DisplayName("Find grade point average")
    public void findGradePointAverage() {

        assertAll("Testing all assertEquals",
                () -> assertEquals(376.5, studentGrades.addGradeResultsForSingleClass(
                        student.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(94.13, studentGrades.findGradePointAverage(
                        student.getStudentGrades().getMathGradeResults())));
    }
}
