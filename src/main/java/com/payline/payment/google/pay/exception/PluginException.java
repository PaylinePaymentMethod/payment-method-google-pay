package com.payline.payment.google.pay.exception;

import com.payline.payment.google.pay.utils.PluginUtils;
import com.payline.pmapi.bean.common.FailureCause;

/**
 * Generic exception.
 */
public class PluginException extends RuntimeException {

    public static final int ERROR_CODE_MAX_LENGTH = 50;

    private final String errorCode;
    private final FailureCause failureCause;

    public PluginException(String message) {
        this(message, FailureCause.INTERNAL_ERROR);
    }

    public PluginException(String message, FailureCause failureCause) {
        super(message);
        if( message == null || message.length() == 0 || failureCause == null ){
            throw new IllegalStateException("PluginException must have a non-empty message and a failureCause");
        }
        this.errorCode = PluginUtils.truncate(message, ERROR_CODE_MAX_LENGTH);
        this.failureCause = failureCause;
    }

    public PluginException(String message, Exception cause){
        this(message, FailureCause.INTERNAL_ERROR, cause);
    }

    public PluginException(String message, FailureCause failureCause, Exception cause) {
        super(message, cause);
        if( message == null || message.length() == 0 || failureCause == null) {
            throw new IllegalStateException("PluginException must have a non-empty message and a failureCause");
        }
        this.errorCode = PluginUtils.truncate(message, ERROR_CODE_MAX_LENGTH);
        this.failureCause = failureCause;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public FailureCause getFailureCause() {
        return failureCause;
    }

}