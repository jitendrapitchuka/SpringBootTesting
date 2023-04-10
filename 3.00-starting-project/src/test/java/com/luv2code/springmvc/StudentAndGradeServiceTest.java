package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;
    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;



    @BeforeEach
    public void setUpDb(){

        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddHistoryGrade);
        jdbc.execute(sqlAddScienceGrade);
    }
    @Test
    public void createStudentService(){

        studentService.createStudent("jit","sai","jit@email.com");
        CollegeStudent student=studentDao.findByEmailAddress("jit@email.com");
        assertEquals("jit@email.com",student.getEmailAddress(),"find by email");
    }

    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService(){
        Optional<CollegeStudent> deletedCollegeStudent=studentDao.findById(1);
        Optional<MathGrade> deletedMathGrade=mathGradeDao.findById(1);
        Optional<HistoryGrade>deletedHistoryGrade=historyGradeDao.findById(1);
        Optional<ScienceGrade>deletedScienceGrade=scienceGradeDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(),"return true");
        assertTrue(deletedMathGrade.isPresent(),"return true");
        assertTrue(deletedScienceGrade.isPresent(),"return true");
        assertTrue(deletedHistoryGrade.isPresent(),"return true");

        studentService.deletedService(1);
        deletedCollegeStudent=studentDao.findById(1);
        deletedMathGrade=mathGradeDao.findById(1);
        deletedHistoryGrade=historyGradeDao.findById(1);
        deletedScienceGrade=scienceGradeDao.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(),"return false");
        assertFalse(deletedMathGrade.isPresent(),"return false");
        assertFalse(deletedScienceGrade.isPresent(),"return false");
        assertFalse(deletedHistoryGrade.isPresent(),"return false");

    }
    @Sql("/insertData.sql")
    @Test
    public void getGradebookService(){
        Iterable<CollegeStudent> iterableCollegeStudents=studentService.getGradebook();
        List<CollegeStudent>collegeStudents=new ArrayList<>();

        for(CollegeStudent collegeStudent:iterableCollegeStudents){
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5,collegeStudents.size());
    }

    @Test
    public void createGradeService(){
        assertTrue(studentService.createGrade(80.5,1,"math"));
        assertTrue(studentService.createGrade(80.5,1,"science"));
        assertTrue(studentService.createGrade(80.5,1,"history"));


        Iterable<MathGrade>mathGrades=mathGradeDao.findGradeByStudentId(1);
        Iterable<ScienceGrade>scienceGrades=scienceGradeDao.findGradeByStudentId(1);
        Iterable<HistoryGrade>historyGrades=historyGradeDao.findGradeByStudentId(1);

        assertTrue(((Collection<MathGrade>)mathGrades).size()==2,"student has 2 math grades");
        assertTrue(((Collection<ScienceGrade>)scienceGrades).size()==2,"student has 2 science grades");
        assertTrue(((Collection<HistoryGrade>)historyGrades).size()==2,"student has 2 history grades");
    }

    @Test
    public void createGradeServiceReturnFalse(){
        assertFalse(studentService.createGrade(105,1,"math"));
        assertFalse(studentService.createGrade(-5,1,"math"));
        assertFalse(studentService.createGrade(80.5,2,"math"));
        assertFalse(studentService.createGrade(80.5,1,"biology"));
    }

    @Test
    public void deleteGradeservice(){
        assertEquals(1,studentService.deleteGrade(1,"math"),"Return student id after delete");
        assertEquals(1,studentService.deleteGrade(1,"history"),"Return student id after delete");
        assertEquals(1,studentService.deleteGrade(1,"science"),"Return student id after delete");


    }

    @Test
    public void deleteGradeServiceReturnStudentIdZero(){
        assertEquals(0,studentService.deleteGrade(0,"science"),"no studenet should have 0 id");
        assertEquals(0,studentService.deleteGrade(1,"biology"),"no studenet should have bilogy  class");


    }

    @Test
    public void studentInformation(){
        GradebookCollegeStudent gradebookCollegeStudent=studentService.studentInformation(1);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(1,gradebookCollegeStudent.getId());
        assertEquals("jitee",gradebookCollegeStudent.getFirstname());
        assertEquals("saiii",gradebookCollegeStudent.getLastname());
        assertEquals("jitee@email.com",gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size()==1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size()==1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size()==1);
    }

    @Test
    public void studentInfoReturnNull(){
        GradebookCollegeStudent gradebookCollegeStudent=studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }
    @AfterEach
    public void setupAfterTrasaction(){
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
        jdbc.execute(sqlDeleteScienceGrade);
    }

}
