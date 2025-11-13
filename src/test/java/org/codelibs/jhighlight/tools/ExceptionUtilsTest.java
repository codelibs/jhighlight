package org.codelibs.jhighlight.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExceptionUtilsTest {

    @Test
    public void testGetExceptionStackTrace_BasicException() {
        Exception exception = new RuntimeException("Test exception");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("RuntimeException"));
        assertTrue(stackTrace.contains("Test exception"));
    }

    @Test
    public void testGetExceptionStackTrace_WithCause() {
        Exception cause = new IllegalArgumentException("Root cause");
        Exception exception = new RuntimeException("Test exception", cause);

        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("RuntimeException"));
        assertTrue(stackTrace.contains("Test exception"));
        assertTrue(stackTrace.contains("Caused by"));
        assertTrue(stackTrace.contains("IllegalArgumentException"));
        assertTrue(stackTrace.contains("Root cause"));
    }

    @Test
    public void testGetExceptionStackTrace_ContainsMethodNames() {
        Exception exception = new RuntimeException("Test");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        // Should contain method name from test
        assertTrue(stackTrace.contains("testGetExceptionStackTrace_ContainsMethodNames"));
    }

    @Test
    public void testGetExceptionStackTrace_IOException() {
        Exception exception = new java.io.IOException("File not found");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("IOException"));
        assertTrue(stackTrace.contains("File not found"));
    }

    @Test
    public void testGetExceptionStackTrace_NullPointerException() {
        Exception exception = new NullPointerException();
        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("NullPointerException"));
    }

    @Test
    public void testGetExceptionStackTrace_CustomException() {
        Exception exception = new CustomTestException("Custom message");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(exception);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("CustomTestException"));
        assertTrue(stackTrace.contains("Custom message"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExceptionStackTrace_NullException() {
        ExceptionUtils.getExceptionStackTrace(null);
    }

    @Test
    public void testGetExceptionStackTrace_NestedCauses() {
        Exception rootCause = new IllegalStateException("Root");
        Exception middleCause = new IllegalArgumentException("Middle", rootCause);
        Exception topException = new RuntimeException("Top", middleCause);

        String stackTrace = ExceptionUtils.getExceptionStackTrace(topException);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("RuntimeException"));
        assertTrue(stackTrace.contains("Top"));
        assertTrue(stackTrace.contains("IllegalArgumentException"));
        assertTrue(stackTrace.contains("Middle"));
        assertTrue(stackTrace.contains("IllegalStateException"));
        assertTrue(stackTrace.contains("Root"));
    }

    @Test
    public void testGetExceptionStackTrace_Error() {
        Error error = new AssertionError("Assertion failed");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(error);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("AssertionError"));
        assertTrue(stackTrace.contains("Assertion failed"));
    }

    @Test
    public void testGetExceptionStackTrace_Throwable() {
        Throwable throwable = new Throwable("Generic throwable");
        String stackTrace = ExceptionUtils.getExceptionStackTrace(throwable);

        assertNotNull(stackTrace);
        assertTrue(stackTrace.contains("Throwable"));
        assertTrue(stackTrace.contains("Generic throwable"));
    }

    // Custom exception class for testing
    private static class CustomTestException extends Exception {
        public CustomTestException(String message) {
            super(message);
        }
    }
}
