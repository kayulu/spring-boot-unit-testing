package com.kayulu.springmvc;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.models.HistoryGrade;
import com.kayulu.springmvc.models.MathGrade;
import com.kayulu.springmvc.models.ScienceGrade;
import com.kayulu.springmvc.repository.HistoryGradesDao;
import com.kayulu.springmvc.repository.MathGradesDao;
import com.kayulu.springmvc.repository.ScienceGradesDao;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentDao studentDao;

    @Autowired
    MathGradesDao mathGradesDao;

    @Autowired
    ScienceGradesDao scienceGradesDao;

    @Autowired
    HistoryGradesDao historyGradesDao;

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

    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> students = studentService.getGradeBook();

        List<CollegeStudent> studentList = new ArrayList<>();

        for(CollegeStudent stud : students)
            studentList.add(stud);

        assertEquals(5, studentList.size());
    }

    @Test
    public void createGradeService() {
        // create and save a math-grade for a student
        assertTrue(studentService.createGrade(80.3, 1, "math"));
        assertTrue(studentService.createGrade(80.4, 1, "science"));
        assertTrue(studentService.createGrade(80.5, 1, "history"));
        assertFalse(studentService.createGrade(80.5, 1, "illegal"));

        // get all math-grades of a student
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradesByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradesByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradesByStudentId(1);

        // verify that there is a math-grade
        assertTrue(mathGrades.iterator().hasNext());
        assertTrue(scienceGrades.iterator().hasNext());
        assertTrue(historyGrades.iterator().hasNext());
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
    }
}
