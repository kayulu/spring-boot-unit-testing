package com.kayulu.junitdemo;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

class ConditionalTest {
    @Test
    @Disabled("Don't run this test until fix JIRA #123 is resolved")
    void basicTest() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowsOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testForLinuxOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    void testForMaxAndWindowsOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testOnlyForJava17() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JRE.JAVA_21)
    void testOnlyForJava21() {
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_13, max = JRE.JAVA_17)
    void testOnlyForJava13ToJava17() {
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_13)
    void testOnlyForJava13Min() {
        // execute method and perform asserts
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "KAYULU_ENV", matches = "DEV")
    void testOnlyIfEnvVarSet() {
        // execute method and perform asserts
    }

    @Test
    @EnabledIfSystemProperty(named = "KAYULU_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testOnlyForSysProp() {
        // execute method and perform asserts
    }
}
