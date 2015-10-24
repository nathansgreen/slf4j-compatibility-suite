package com.inco5.slf4j.retrolambda;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class InvokeTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"a"}, {"b"}, {"c"}});
    }

    private String name;

    public InvokeTest(String name) {
        this.name = name;
    }

    @Test
    public void testOneArg() {
        Logger logger = LoggerFactory.getLogger(InvokeTest.class);
        logger.warn("{}", () -> name);
    }

    @Test
    public void testTwoArg() {
        Logger logger = LoggerFactory.getLogger(InvokeTest.class);
        logger.warn("{}/{}", name, () -> name);
        logger.warn("{}/{}", ()-> name, () -> name);
    }

    @Test
    public void testNArg() {
        Logger logger = LoggerFactory.getLogger(InvokeTest.class);
        logger.warn("{}/{}/{}", ()-> name, () -> name, () -> name);
        logger.warn("{}/{}/{}", ()-> name, () -> name, null);
    }

    @Test
    @Ignore("currently allowing lambdas to blow up default implementation")
    public void testThrow() {
        Logger logger = LoggerFactory.getLogger(InvokeTest.class);
        logger.warn("{}", () -> {
            throw new AssertionError("uncaught exception in lambda");
        });
    }
}
