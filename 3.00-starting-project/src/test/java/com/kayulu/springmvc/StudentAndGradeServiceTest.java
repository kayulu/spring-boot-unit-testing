package com.kayulu.springmvc;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentDao studentDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setupDatabase() {
        CollegeStudent testStudent = new CollegeStudent("Liam", "Smith", "liam.smith@example.com");

        studentDao.save(testStudent); // H2 sequences for primary keys start from 1
    }

    @Test
    public void createStudentAndFindByEmailAddress() {
        studentService.createNewStudent("Emma", "Johnson", "emma.johnson@example.com");

        CollegeStudent student = studentDao.findByEmailAddress("emma.johnson@example.com");

        assertEquals("emma.johnson@example.com", student.getEmailAddress(), "find by email");
    }

    @Test
    public void existingStudentRetrievable() {
        assertTrue(studentService.checkIfStudentIsPresent(1), "Student with id 1 should exist");
        assertFalse(studentService.checkIfStudentIsPresent(2), "Student with id 2 should not exist");
    }

    @Test
    public void existingStudentDeletable() {
        assertTrue(studentService.checkIfStudentIsPresent(1), "Student with id 1 should exist");

        studentService.deleteStudentById(1);

        assertFalse(studentService.checkIfStudentIsPresent(1), "Student with id 1 should not exist");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
    }
}
