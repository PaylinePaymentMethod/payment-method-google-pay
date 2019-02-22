package com.payline.payment.google.pay.utils;

import com.payline.pmapi.bean.common.FailureCause;

public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public FailureCause getFailureCause() {
        return FailureCause.INVALID_DATA;
    }

}
