package com.kayulu.springmvc;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@SpringBootTest
@AutoConfigureMockMvc
class GradebookControllerTest {
    private static MockHttpServletRequest request;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @Mock
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
        CollegeStudent testStudent = new CollegeStudent("Kay", "Ulu", "kay.ulu@example.com");
        studentDao.save(testStudent); // H2 sequences for primary keys start from 1
    }

    @Test
    public void getStudentsHttpRequest() throws Exception {
        CollegeStudent student1 = new CollegeStudent("Emma", "Johnson", "emma.johnson@example.com");
        CollegeStudent student2 = new CollegeStudent("Liam", "Smith", "liam.smith@example.com");
        List<CollegeStudent> students = new ArrayList<>(Arrays.asList(student1, student2));

        when(studentService.getGradeBook()).thenReturn(students);

        assertIterableEquals(students, studentService.getGradeBook());

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

        when(studentService.getGradeBook()).thenReturn(students);

        assertEquals(students, studentService.getGradeBook());

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

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
    }
}