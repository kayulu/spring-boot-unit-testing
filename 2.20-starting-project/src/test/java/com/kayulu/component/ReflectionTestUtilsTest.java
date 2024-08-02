package com.kayulu.component;

import com.kayulu.component.models.CollegeStudent;
import com.kayulu.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReflectionTestUtilsTest {
    private CollegeStudent collegeStudent;

    @BeforeEach
    public void studentBeforeEach() {
        collegeStudent = new CollegeStudent("Kay", "Ulu", "kay.ulu@example.com");

        // this will not invoke public setter, it will directly set the private field 'id' using reflection
        ReflectionTestUtils.setField(collegeStudent, "id", 1);

        ReflectionTestUtils.setField(collegeStudent, "studentGrades", // use ReflectionTestUtils to set any field
                new StudentGrades(Arrays.asList(55.55, 66.66, 88.88)));
    }

    @Test
    @DisplayName("Test private field Id was set")
    public void testPrivateFieldIdWasSet() {
        assertEquals(1, collegeStudent.getId(), "Student Id should be 1");
    }

    @Test
    @DisplayName("Demo ReflectionTestUtils to invoke private method")
    public void demoReflectionTestUtilsToInvokePrivateMethod() {
        assertEquals("Kay 1", ReflectionTestUtils.invokeMethod(collegeStudent, "getFirstNameAndId"));
    }
}
