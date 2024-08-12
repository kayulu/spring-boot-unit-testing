package com.kayulu.springmvc;

import com.kayulu.springmvc.models.*;
import com.kayulu.springmvc.repository.HistoryGradesDao;
import com.kayulu.springmvc.repository.MathGradesDao;
import com.kayulu.springmvc.repository.ScienceGradesDao;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestPropertySource("classpath:test-application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {
    @Value("${sql.scripts.create.student}")
    String createStudent;

    @Value("${sql.scripts.create.math_grade}")
    String createMathGrade;

    @Value("${sql.scripts.create.science_grade}")
    String createScienceGrade;

    @Value("${sql.scripts.create.history_grade}")
    String createHistoryGrade;

    @Value("${sql.script.delete.student}")
    String deleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String deleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String deleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String deleteHistoryGrade;

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
        jdbcTemplate.execute(createStudent);

        jdbcTemplate.execute(createMathGrade);
        jdbcTemplate.execute(createScienceGrade);
        jdbcTemplate.execute(createHistoryGrade);
    }

    @Test
    public void createStudentAndFindByEmailAddress() {
        studentService.createNewStudent("Emma", "Johnson", "emma.johnson@example.com");

        CollegeStudent student = studentDao.findByEmailAddress("emma.johnson@example.com");

        assertEquals("emma.johnson@example.com", student.getEmailAddress(), "find by email");
    }

    @Test
    public void retrieveStudentService() {
        assertTrue(studentService.checkIfStudentIsPresent(1), "Student with id 1 should exist");
        assertFalse(studentService.checkIfStudentIsPresent(2), "Student with id 2 should not exist");
    }

    @Test
    public void deleteStudentService() {
        assertTrue(studentDao.findById(1).isPresent(), "Student with id 1 should exist");
        assertTrue(mathGradesDao.findById(1).isPresent(), "Grade with id 1 should exist");
        assertTrue(scienceGradesDao.findById(1).isPresent(), "Grade with id 1 should exist");
        assertTrue(historyGradesDao.findById(1).isPresent(), "Grade with id 1 should exist");

        studentService.deleteStudentById(1);

        assertFalse(studentService.checkIfStudentIsPresent(1), "Student with id 1 should not exist");
        assertFalse(mathGradesDao.findById(1).isPresent(), "Grade with id 1 should not exist");
        assertFalse(scienceGradesDao.findById(1).isPresent(), "Grade with id 1 should not exist");
        assertFalse(historyGradesDao.findById(1).isPresent(), "Grade with id 1 should not exist");
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

        // get all math-grades of a student
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradesByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradesByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradesByStudentId(1);

        // verify that there is a math-grade
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size());
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size());
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size());
    }

    @Test
    public void createGradeServiceForInvalidInput() {
        // invalid grade: grade < 0
        assertFalse(studentService.createGrade(-100.0, 1, "math"));

        // invalid grade: grade > 100
        assertFalse(studentService.createGrade(100.5, 1, "math"));

        // invalid studentId: 2
        assertFalse(studentService.createGrade(80.5, 2, "math"));

        // invalid grade-type
        assertFalse(studentService.createGrade(80.5, 1, "invalid"));
    }

    @Test
    public void deleteGradeService() {
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Deletion of grade with grade-id 1 should return student-id 1");
        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Deletion of grade with grade-id 1 should return student-id 1");
        assertEquals(1, studentService.deleteGrade(1, "HISTORY"),
                "Deletion of grade with grade-id 1 should return student-id 1");
    }

    @Test
    public void deleteGradeServiceForInvalidInput() {
        assertNotEquals(1, studentService.deleteGrade(2, "math"),
                "Deletion of grade with grade-id 2 should not return student-id 1");
        assertNotEquals(1, studentService.deleteGrade(2, "science"),
                "Deletion of grade with grade-id 2 should not return student-id 1");
        assertNotEquals(1, studentService.deleteGrade(2, "HISTORY"),
                "Deletion of grade with grade-id 2 should not return student-id 1");

        //invalid subject
        assertNotEquals(1, studentService.deleteGrade(1, "literature"),
                "Deletion of grade with grade-id 1 and subject 'literature' should not return student-id 1");
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent student = studentService.getStudentInformation(1);

        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("Liam", student.getFirstname());
        assertEquals("Smith", student.getLastname());
        assertEquals("Liam Smith", student.getFullName());
        assertEquals("liam.smith@example.com", student.getEmailAddress());
        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());
        assertEquals(1, student.getStudentGrades().getScienceGradeResults().size());
        assertEquals(1, student.getStudentGrades().getHistoryGradeResults().size());
    }

    @Test
    public void studentInformationReturnNull() {
        GradebookCollegeStudent student = studentService.getStudentInformation(0);
        assertNull(student);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(deleteStudent);
        jdbcTemplate.execute(deleteMathGrade);
        jdbcTemplate.execute(deleteScienceGrade);
        jdbcTemplate.execute(deleteHistoryGrade);
    }
}
