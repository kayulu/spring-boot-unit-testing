package com.kayulu.springmvc;

import com.kayulu.springmvc.models.CollegeStudent;
import com.kayulu.springmvc.repository.StudentDao;
import com.kayulu.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@SpringBootTest
@AutoConfigureMockMvc
class GradebookControllerTest {
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentService;

    @BeforeEach
    public void setUp() {
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

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
    }
}