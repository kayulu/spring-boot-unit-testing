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
        jdbcTemplate.execute("INSERT INTO student(id, firstname, lastname, email_Address)" +
                "VALUES (0, 'Kay', 'Ulu', 'kay.ulu@example.com')");
    }

    @Test
    public void createStudentService() {
        studentService.createNewStudent("Emma", "Johnson", "emma.johnson@example.com");

        CollegeStudent student = studentDao.findByEmailAddress("emma.johnson@example.com");

        System.out.println("Id: " + student.getId());

        assertEquals("emma.johnson@example.com", student.getEmailAddress(), "find by email");
    }

    @Test
    public void checkIfStudentIsNull() {
        assertTrue(studentService.checkIfStudentIsPresent(0), "Student with id 0 should exist");
        assertFalse(studentService.checkIfStudentIsPresent(1), "Student with id 1 should not exist");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
    }
}
