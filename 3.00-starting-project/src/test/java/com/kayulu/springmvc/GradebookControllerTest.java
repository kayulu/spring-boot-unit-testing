package com.kayulu.springmvc;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.models.GradebookCollegeStudent;
import com.kayulu.springmvc.models.MathGrade;
import com.kayulu.springmvc.repository.MathGradesDao;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/test-application.properties")
@SpringBootTest
@AutoConfigureMockMvc
class GradebookControllerTest {
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

    private static MockHttpServletRequest request;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentServiceMock;

    @Autowired
    private StudentAndGradeService studentService;

    @BeforeAll
    public static void beforeAll() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Emma");
        request.setParameter("lastname", "Johnson");
        request.setParameter("emailAddress", "emma.johnson@example.com");
    }

    @BeforeEach
    public void beforeEach() {
        jdbcTemplate.execute(createStudent);

        jdbcTemplate.execute(createMathGrade);
        jdbcTemplate.execute(createScienceGrade);
        jdbcTemplate.execute(createHistoryGrade);
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Emma", "Johnson", "emma.johnson@example.com");
        CollegeStudent student2 = new CollegeStudent("Liam", "Smith", "liam.smith@example.com");
        List<CollegeStudent> students = new ArrayList<>(Arrays.asList(student1, student2));

        when(studentServiceMock.getGradeBook()).thenReturn(students);

        assertIterableEquals(students, studentServiceMock.getGradeBook());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        
        ModelAndViewAssert.assertViewName(modelAndView, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        CollegeStudent student = new CollegeStudent(
                "Emma", "Johnson", "emma.johnson@example.com");

        List<CollegeStudent> students = new ArrayList<>(List.of(student));

        when(studentServiceMock.getGradeBook()).thenReturn(students);

        assertEquals(students, studentServiceMock.getGradeBook());

        MvcResult result = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstname", request.getParameter("firstname"))
                .param("lastname", request.getParameter("lastname"))
                .param("emailAddress", request.getParameter("emailAddress")))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndViewAssert.assertViewName(result.getModelAndView(), "index");

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("emma.johnson@example.com");

        assertNotNull(verifyStudent, "Student should be found in DB");
    }

    @Test
    public void deleteStudentHttpRequest() throws Exception {
        assertNotNull(studentDao.findById(1));

        MvcResult result = mockMvc.perform(get("/delete/student/{id}", 1))
                        .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(result.getModelAndView(), "index");

        assertTrue(studentDao.findById(1).isEmpty());
    }

    @Test
    public void deleteStudentErrorPage() throws Exception {
        assertTrue(studentDao.findById(0).isEmpty());

        MvcResult result = mockMvc.perform(get("/delete/student/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(result.getModelAndView(), "error");
    }

    @Test
    public void studentInformationHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult result = mockMvc.perform(get("/studentInformation/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                Objects.requireNonNull(result.getModelAndView()), "studentInformation");
    }

    @Test
    public void studentInformationHttpRequestError() throws Exception {
        assertTrue(studentDao.findById(0).isEmpty());

        MvcResult result = mockMvc.perform(get("/studentInformation/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                Objects.requireNonNull(result.getModelAndView()), "error");
    }

    @Test
    public void createValidGradeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        GradebookCollegeStudent student = studentService.getStudentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        MvcResult result = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("studentId", "1")
                .param("gradeType", "math")
                .param("grade", "99.0"))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "studentInformation");

        student = studentService.getStudentInformation(1);

        assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void createInvalidGradeTypeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        GradebookCollegeStudent student = studentService.getStudentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        // perform invalid gradeType
        MvcResult result = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("studentId", "1")
                .param("gradeType", "literature")   // invalid grade-type
                .param("grade", "99.0"))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "error");

        student = studentService.getStudentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size()); // has not changed
    }

    @Test
    public void createInvalidGradeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());

        GradebookCollegeStudent student = studentService.getStudentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        // perform invalid grade
        MvcResult result = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("studentId", "1")
                        .param("gradeType", "math")
                        .param("grade", "101.0"))   // invalid grade
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "error");

        student = studentService.getStudentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size()); // has not changed
    }

    @Test
    public void createInvalidStudentIdRequest() throws Exception {
        assertTrue(studentDao.findById(0).isEmpty());

        GradebookCollegeStudent student = studentService.getStudentInformation(0);

        assertNull(student);

        // perform invalid id
        MvcResult result = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("studentId", "0")    // invalid student-id
                        .param("gradeType", "math")
                        .param("grade", "88.0"))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "error");
    }

    @Test
    public void deleteValidGradeHttpRequest() throws Exception {
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);

        assertTrue(mathGrade.isPresent());

        MvcResult result = mockMvc.perform(get("/grades/1/math")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "studentInformation");

        assertTrue(mathGradesDao.findById(1).isEmpty());
    }

    @Test
    public void deleteInvalidGradeHttpRequest() throws Exception {
        Optional<MathGrade> mathGrade = mathGradesDao.findById(0);

        assertFalse(mathGrade.isPresent());

        MvcResult result = mockMvc.perform(get("/grades/0/math")    // invalid grade-id
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "error");
    }

    @Test
    public void deleteInvalidGradeTypeHttpRequest() throws Exception {
        MvcResult result = mockMvc.perform(get("/grades/1/literature")  // invalid grade-type
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk()).andReturn();

        ModelAndViewAssert.assertViewName(
                result.getModelAndView(), "error");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(deleteStudent);
        jdbcTemplate.execute(deleteMathGrade);
        jdbcTemplate.execute(deleteScienceGrade);
        jdbcTemplate.execute(deleteHistoryGrade);
    }
}