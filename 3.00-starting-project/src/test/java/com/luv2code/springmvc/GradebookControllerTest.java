package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {
    @Autowired
    private JdbcTemplate jdbc;

    private static MockHttpServletRequest request;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentDao studentDao;
    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    @BeforeAll
    public static void setUp(){
        request=new MockHttpServletRequest();
        request.setParameter("firstName","chin");
        request.setParameter("lastName","bal");
        request.setParameter("emailAddress","chin@gmail.com");

    }

    @BeforeEach
    public void beforeEach(){
        jdbc.execute("insert into student(id,firstName,lastName,email_address) " +
                "values (1,'jitee','saiii','jitee@email.com')");

    }

    @Test
    public void getStudentHttpRequest() throws Exception{
        CollegeStudent studentOne=new GradebookCollegeStudent("jit","sai","jit@email");
        CollegeStudent studentTwo=new GradebookCollegeStudent("vara","pra","var@email");
        List<CollegeStudent>collegeStudentList=new ArrayList<>(Arrays.asList(studentOne,studentTwo));
        when(studentCreateServiceMock.getGradebook()).thenReturn(collegeStudentList);
        assertIterableEquals(collegeStudentList,studentCreateServiceMock.getGradebook());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");
    }

    @Test
    public void createStudentHttpReq() throws Exception {

        CollegeStudent studentOne=new CollegeStudent("jitee","saiii","jitee@email.com");
        List<CollegeStudent>collegeStudentList=new ArrayList<>(Arrays.asList(studentOne));
        when(studentCreateServiceMock.getGradebook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList,studentCreateServiceMock.getGradebook());

        MvcResult mvcResult=this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName",request.getParameterValues("firstName"))
                .param("lastName",request.getParameterValues("lastName"))
                .param("emailAddress",request.getParameterValues("emailAddress")))
        .andExpect(status().isOk()).andReturn();

        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");

        CollegeStudent verifyStudent=studentDao.findByEmailAddress("chin@gmail.com");

        assertNotNull(verifyStudent,"student should be found");

    }

    @Test
    public void deleteStudentHtppRequest() throws Exception{
        assertTrue(studentDao.findById(1).isPresent());

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}",1))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"index");

        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    public void deleteStudentErrorPage() throws Exception{
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}",0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav=mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav,"error");
    }

    @AfterEach
    public void setupAfterTrasaction(){
        jdbc.execute("delete from student");
    }


}
