/* Copyright (c) 2015 Nathan Green
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.inco5.slf4j.it;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.Assert.assertEquals;

/**
 * Test use of lambda-based logging methods against legacy SLF4J implementations
 * in order to ensure the default methods work correctly
 */
public class LambdaLoggingTest {

    @Test
    public void testError() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        synchronized (System.out) {
            final PrintStream stdout = System.out;
            try {
                System.setOut(new PrintStream(out, true, "US-ASCII"));
                Logger logger = LoggerFactory.getLogger(LambdaLoggingTest.class);
                logger.error("one {} two {} three {}", () -> new Object[]{"uno", "dos", "tres"});
                System.out.flush();
                String result = new String(out.toByteArray(), US_ASCII);
                result = result.contains(" one ") ? result.substring(result.indexOf("one ")).trim() : result;
                assertEquals("one uno two dos three tres", result);
            } finally {
                System.setOut(stdout);
            }
        }
    }

}
