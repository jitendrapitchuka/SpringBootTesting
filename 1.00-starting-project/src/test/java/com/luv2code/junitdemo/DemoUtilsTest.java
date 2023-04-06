package com.luv2code.junitdemo;


import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class DemoUtilsTest {

    DemoUtils demoUtils;

//    @BeforeAll
//    static void beforeall(){
//        System.out.println("Before all>>>");
//    }
//
//    @AfterAll
//    static void afterall(){
//        System.out.println("after all<<<");
//    }
//
    @BeforeEach
    void setupBeforeEach(){
        demoUtils=new DemoUtils();

    }

//    @AfterEach

//    void setupAftereach(){
//        System.out.println("After each---");
//    }


    @Test
     @Order(0)
//    @DisplayName("test1")
    void testequalsAndNotEquals(){

        assertEquals(6,demoUtils.add(4,2),"4+2 is 6");
        assertNotEquals(8,demoUtils.add(4,2),"4+2 must not 8");

    }
    @Test
//    @DisplayName("test2")
    void testNullOrNotNull(){

        String str1=null;
        String str2="hi";
        assertNull(demoUtils.checkNull(str1),"obj should be null");
        assertNotNull(demoUtils.checkNull(str2),"obj should be not null");
    }

    @Test
    void testMultiply(){
        assertEquals(12,demoUtils.multiply(4,3),"4,3 should be 12");
    }
    @Test
    void testSameandNotSame(){
        String str="hi";
        assertSame(demoUtils.getAcademy(),demoUtils.getAcademyDuplicate(),"objects should refer to same");
        assertNotSame(str,demoUtils.getAcademyDuplicate(),"objects should not refer to same");

    }
@Order(2)
    @Test
    void trueandFalse(){
        int num1=10;
        int num2=5;
        assertTrue(demoUtils.isGreater(num1,num2),"true");
        assertFalse(demoUtils.isGreater(num2,num1),"false");


    }

    @Test
    void testArrayEquals(){
        String arr[]={"A","B","C"};
        assertArrayEquals(arr,demoUtils.getFirstThreeLettersOfAlphabet(),"array should be Equal");
    }

    @Test
    void testItrableEquals(){
        List<String>l= List.of("luv", "2", "code");
        assertIterableEquals(l,demoUtils.getAcademyInList(),"list should be qeual");
    }

    @Test
    void TestLineMatch(){
        List<String>l= List.of("luv", "2", "code");
        assertLinesMatch(l,demoUtils.getAcademyInList(),"lines should be match");
    }

    @Test
    void testThrows(){

        assertThrows(Exception.class,()->{demoUtils.throwException(-1);},"should throw an excep");
        assertDoesNotThrow(()->{demoUtils.throwException(1);},"should not throw an excep");

    }

    @Test
    void testTimeOut(){
        assertTimeoutPreemptively(Duration.ofSeconds(3),()->{demoUtils.checkTimeout();},"mthod should exec in 3 sec");
    }

}

