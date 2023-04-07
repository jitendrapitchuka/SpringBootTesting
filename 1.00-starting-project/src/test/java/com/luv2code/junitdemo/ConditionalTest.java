package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {

    @Test
    @Disabled("Dont run this until we fix")
    void basicTest(){

    }


    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForwindowa(){

    }


    @Test
    @EnabledOnOs(OS.LINUX)
    void testforLinux(){

    }


    @Test
    @EnabledOnOs({OS.MAC,OS.WINDOWS})
    void testForMacWindows(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForJava17(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void testForJava13(){

    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9,max = JRE.JAVA_19)
    void testFor9TO19(){

    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_9)
    void testForFromJava9(){

    }



}
