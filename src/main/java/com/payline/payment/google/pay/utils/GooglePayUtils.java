package com.payline.payment.google.pay.utils;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;

import java.io.*;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

import static com.payline.payment.google.pay.utils.GooglePayConstants.JS_PARAM_VALUE_GATEWAY_NAME;

public class GooglePayUtils {

    public static String decryptFromGoogle(String encryptedMessage, String privateKey, boolean isSandbox) throws GeneralSecurityException {
        GooglePaymentsPublicKeysManager keysManager = isSandbox ? GooglePaymentsPublicKeysManager.INSTANCE_TEST : GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION;
        keysManager.refreshInBackground();

        return new PaymentMethodTokenRecipient.Builder()
                .fetchSenderVerifyingKeysWith(keysManager)
                .recipientId("gateway:" + JS_PARAM_VALUE_GATEWAY_NAME)
                // Multiple private keys can be added to support graceful key rotations.
                .addRecipientPrivateKey(privateKey)
                .build()
                .unseal(encryptedMessage);
    }


    public static String createStringAmount(BigInteger amount) {
        StringBuilder sb = new StringBuilder();
        sb.append(amount);

        for (int i = sb.length(); i < 3; i++) {
            sb.insert(0, "0");
        }

        sb.insert(sb.length() - 2, ".");
        return sb.toString();
    }

    public static String ConvertInputStreamToString(InputStream is) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(is, "UTF-8");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}