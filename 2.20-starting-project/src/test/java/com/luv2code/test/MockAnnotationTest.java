package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;
//    @Mock
//    private ApplicationDao applicationDao;
    @MockBean
    private ApplicationDao applicationDao;

    //@InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforEach(){
        studentOne.setFirstname("jithu");
        studentOne.setLastname("sai");
        studentOne.setEmailAddress("jite@gmail.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("when a verify")
    @Test
    public void assertEqualsTestAddGrades(){
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);
        assertEquals(100,applicationService.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()));

        verify(applicationDao,times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
    }

    @DisplayName("finding gpa")
    @Test
    public void findGpa(){
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31,applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));

    }

    @DisplayName("not null")
    @Test
    public void notNull(){
        when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);
        assertNotNull(applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("throw run time exc")
    @Test
    public void throwRunTimeExc(){

        CollegeStudent nullStudent=(CollegeStudent) context.getBean("collegeStudent");

        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class,()->{applicationService.checkNull(nullStudent);});

        verify(applicationDao,times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple stubbing")
    @Test
    public void consectiveCalls(){

        CollegeStudent nullStudent=(CollegeStudent) context.getBean("collegeStudent");
        when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException()).thenReturn("Do not throw Exception " +
                "second time");
        assertThrows(RuntimeException.class,()->{applicationService.checkNull(nullStudent);});

        assertEquals("Do not throw Exception second time",applicationService.checkNull(nullStudent));

        verify(applicationDao,times(2)).checkNull(nullStudent);

    }
}
