package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

    private static int count=0;

    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.school.name}")
    private String schoolName;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach(){
        count=count+1;
        System.out.println("Testing: "+appInfo+" execution of test method "+count);
        student.setFirstname("jitendra");
        student.setLastname("sai");
        student.setEmailAddress("jitendra@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    void basicTest(){

    }

    @DisplayName("Add grade results for student grades")
    @Test
    public void addGradeResults(){
        assertEquals(353.25,studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));

    }

    @DisplayName("add grade results for student grades not equal")
    @Test
    public void addGradeResultsNotEqals(){
        assertNotEquals(0,studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));

    }

    @DisplayName("is grade greater")
    @Test
    public void isGradeGreaterStudentGrades(){
        assertTrue(studentGrades.isGradeGreater(90,75));
    }

    @DisplayName("is grade greater false")
    @Test
    public void isGradeGreaterfalse(){
        assertFalse(studentGrades.isGradeGreater(9,75));
    }

    @DisplayName("create student without grades init")
    @Test
    public void createStudentWithoutGradesInit(){
        CollegeStudent studentTwo=context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("chinna");
        studentTwo.setLastname("balue");
        studentTwo.setEmailAddress("balu@gmail.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("verify students are prtotype")
    @Test
    public void verifyStudentsArePrototype(){
        CollegeStudent studentTwo=context.getBean("collegeStudent", CollegeStudent.class);

        assertNotSame(student,studentTwo);
    }

    @DisplayName("find grade point avg")
    @Test
    public void findGradePointAverage(){
        assertAll("Testing all assertEquals",()->assertEquals(
                353.25,studentGrades.addGradeResultsForSingleClass(
                        student.getStudentGrades().getMathGradeResults())),
                ()->assertEquals(88.31,studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults())));


    }
}
