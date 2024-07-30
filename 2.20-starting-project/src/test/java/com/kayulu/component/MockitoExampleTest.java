package com.kayulu.component;

import com.kayulu.component.dao.ApplicationDao;
import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.StudentGrades;
import com.kayulu.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    ApplicationDao dao;

    @InjectMocks
    ApplicationService service;

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
}
