package com.payline.payment.google.pay.utils;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;

import java.security.GeneralSecurityException;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class GooglePayUtils {

    static {
        //GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION.refreshInBackground();
        GooglePaymentsPublicKeysManager.INSTANCE_TEST.refreshInBackground();
    }

    public static String decryptFromGoogle(String encryptedMessage, GooglePaymentsPublicKeysManager googlePaymentsPublicKeysManager) {

        String result = null;

        try {

            result = new PaymentMethodTokenRecipient.Builder()
                    .fetchSenderVerifyingKeysWith(googlePaymentsPublicKeysManager)
                    .recipientId("gateway:"+ JS_PARAM_VALUE_GATEWAY_NAME)
                    // Multiple private keys can be added to support graceful key rotations.
                    .addRecipientPrivateKey(PRIVATE_KEY)
                    .build()
                    .unseal(encryptedMessage);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return result;

    }

}