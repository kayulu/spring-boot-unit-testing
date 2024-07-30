package com.kayulu.component;

import com.kayulu.component.dao.ApplicationDao;
import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.StudentGrades;
import com.kayulu.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockitoExampleTest {
    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    ApplicationDao dao; // this bean will be in the application context and can be autowired by other beans

    @Autowired
    ApplicationService service; // the ApplicationDao dependency will be autowired when this bean is created

    @BeforeEach
    public void setUp() {
        studentOne.setFirstname("Kay");
        studentOne.setLastname("Ulu");
        studentOne.setEmailAddress("kay.ulu@example.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsAddGradeResultsForSingleClass() {
        // Set Up: set up expectations with mock responses
        when(dao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.0);

        // Execute: call the method you want to test
        // Assert: check result
        assertEquals(100.0, service.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));

        // Verify: verify that dao.addGradeResultsForSingleClass() method was called
        verify(dao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
        verify(dao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("When & Verify anyList")
    public void assertEqualsAddGradeResultsForSingleClassAnyList() {
        // use of anyList()-Matcher
        when(dao.addGradeResultsForSingleClass(anyList())).thenReturn(100.0);

        List<Double> grades = Arrays.asList(234.0, 23443.0); // arbitrary List<Double>

        assertEquals(100.0, service.addGradeResultsForSingleClass(grades));
    }

    @Test
    @DisplayName("Find Gpa")
    public void assertEqualsTestFindGpa() {
        when(dao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(90.0);

        assertEquals(90.0, dao.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));

        verify(dao).findGradePointAverage(studentGrades.getMathGradeResults());
    }

    @Test
    @DisplayName("Check null")
    public void assertCheckNull() {
        when(dao.checkNull(studentOne.getStudentGrades().getMathGradeResults())).thenReturn(true);

        assertNotNull(service.checkNull(studentOne.getStudentGrades().getMathGradeResults()));
    }
}
