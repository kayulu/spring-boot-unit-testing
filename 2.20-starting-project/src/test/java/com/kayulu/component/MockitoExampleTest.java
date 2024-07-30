package com.kayulu.component;

import com.kayulu.component.dao.ApplicationDao;
import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.Student;
import com.kayulu.component.models.StudentGrades;
import com.kayulu.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockitoExampleTest {
    private static List<Student> collegeStudents;

    @Autowired
    ApplicationContext context;

    @MockBean
    ApplicationDao dao; // this bean will be in the application context and can be autowired by other beans

    @Autowired
    ApplicationService service; // the ApplicationDao dependency will be autowired when this bean is created

    @BeforeAll
    public static void setUp() {
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

        collegeStudents = Arrays.asList(student1, student2, student3, student4, student5);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsAddGradeResultsForSingleClass() {
        // Set Up: set up expectations with mock responses
        when(dao.getStudentByEmail("kay.ulu@example.com")).thenReturn(collegeStudents.getFirst());

        // Execute: call the method you want to test
        // Assert: check result
        assertEquals(46.2, service.addGradeResultsForStudent("kay.ulu@example.com"));

        // Verify: verify that dao.addGradeResultsForSingleClass() method was called
        verify(dao).getStudentByEmail("kay.ulu@example.com");
        verify(dao, times(1)).getStudentByEmail("kay.ulu@example.com");
    }

    @Test
    @DisplayName("When & Verify anyList")
    public void assertEqualsAddGradeResultsForSingleClassAnyList() {
        // use of anyList()-Matcher
        when(dao.getStudentByEmail(anyString())).thenReturn(collegeStudents.getLast());

        List<Double> grades = Arrays.asList(234.0, 23443.0); // arbitrary email

        assertEquals(154.8, service.addGradeResultsForStudent("blabla"));
    }

    @Test
    @DisplayName("Find Gpa")
    public void assertEqualsTestFindGpa() {
        when(dao.getStudentByEmail("john.mayer@example.com")).thenReturn(collegeStudents.get(1));

        assertEquals(83.4, service.findGradePointAverageForStudent("john.mayer@example.com"));

        verify(dao).getStudentByEmail("john.mayer@example.com");
    }

    @Test
    @DisplayName("Check null")
    public void assertCheckNull() {
        when(dao.getStudentByEmail("john.mayer@example.com")).thenReturn(collegeStudents.get(1));

        assertFalse(service.isNullStudent("john.mayer@example.com"));
    }
}
