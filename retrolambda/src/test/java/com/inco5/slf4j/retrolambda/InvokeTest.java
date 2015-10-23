package com.inco5.slf4j.retrolambda;

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
    public void testErr() {
        Logger logger = LoggerFactory.getLogger(InvokeTest.class);
        logger.error("{}", () -> name);
        logger.error("{}/{}", name, () -> name);
        logger.error("{}/{}", ()-> name, () -> name);
        logger.error("{}/{}/{}", ()-> name, () -> name, () -> name);
        logger.error("{}/{}/{}", ()-> name, () -> name, null);
        logger.error("{}", () -> {
            throw new AssertionError("uncaught exception in lambda");
        });
    }

}
