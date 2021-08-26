package com.payline.payment.google.pay.exception;


import com.payline.pmapi.bean.common.FailureCause;
import org.junit.Before;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PluginExceptionTest {

    private static final Exception CAUSE = new Exception("this is the cause");
    private static final String LONG_MESSAGE = "This message is longer than the max length authorized for an errorCode";

    private PluginException exceptionWithSimpleMessage;
    private PluginException exceptionWithMessageAndFailureCause;
    private PluginException exceptionWithMessageAndCause;
    private PluginException exceptionWithMessageAndFailureCauseAndCause;

    @Before
    public void setup(){
        this.exceptionWithSimpleMessage = new PluginException(LONG_MESSAGE);
        this.exceptionWithMessageAndFailureCause = new PluginException(LONG_MESSAGE, FailureCause.COMMUNICATION_ERROR);
        this.exceptionWithMessageAndCause = new PluginException(LONG_MESSAGE, CAUSE);
        this.exceptionWithMessageAndFailureCauseAndCause = new PluginException(LONG_MESSAGE, FailureCause.COMMUNICATION_ERROR, CAUSE);
    
    }

    @Test
    public void constructors() {
        // Assert the exception has a message
        assertNotNull(exceptionWithSimpleMessage.getMessage());
        assertNotNull(exceptionWithMessageAndFailureCause.getMessage());
        assertNotNull(exceptionWithMessageAndCause.getMessage());
        assertNotNull(exceptionWithMessageAndFailureCauseAndCause.getMessage());

        // Assert the exception has a failure cause and an error code
        assertNotNull(exceptionWithSimpleMessage.getErrorCode());
        assertNotNull(exceptionWithMessageAndFailureCause.getErrorCode());
        assertNotNull(exceptionWithMessageAndCause.getErrorCode());
        assertNotNull(exceptionWithMessageAndFailureCauseAndCause.getErrorCode());

        assertNotNull(exceptionWithMessageAndFailureCause.getFailureCause());
        assertNotNull(exceptionWithMessageAndFailureCauseAndCause.getFailureCause());

        // Assert the error code is no longer than the maximum authorized length (@see development best practices on Confluence)
        assertTrue( exceptionWithMessageAndFailureCause.getErrorCode().length() <= PluginException.ERROR_CODE_MAX_LENGTH);
        assertTrue( exceptionWithMessageAndFailureCauseAndCause.getErrorCode().length() <= PluginException.ERROR_CODE_MAX_LENGTH);

    }

    @Test
     public void constructorExceptions() {
        // Expected an exception when calling the constructor with a null message or failure cause
        assertThrows( IllegalStateException.class, () -> new PluginException(null));
        assertThrows( IllegalStateException.class, () -> new PluginException(null, FailureCause.COMMUNICATION_ERROR));
        assertThrows( IllegalStateException.class, () -> new PluginException(LONG_MESSAGE, (FailureCause) null));
        assertThrows( IllegalStateException.class, () -> new PluginException(null, CAUSE ));
        assertThrows( IllegalStateException.class, () -> new PluginException(null, FailureCause.COMMUNICATION_ERROR, CAUSE));
        assertThrows( IllegalStateException.class, () -> new PluginException(LONG_MESSAGE, null, CAUSE));
    }

}
