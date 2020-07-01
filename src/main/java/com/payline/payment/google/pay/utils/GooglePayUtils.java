package com.payline.payment.google.pay.utils;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Locale;

import static com.payline.payment.google.pay.utils.GooglePayConstants.JS_PARAM_VALUE_GATEWAY_NAME;

public class GooglePayUtils {

    public static String decryptFromGoogle(String encryptedMessage, String privateKey, String privateKeyOld, boolean isSandbox) throws GeneralSecurityException {
        GooglePaymentsPublicKeysManager keysManager = isSandbox ? GooglePaymentsPublicKeysManager.INSTANCE_TEST : GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION;
        keysManager.refreshInBackground();

        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder()
                .fetchSenderVerifyingKeysWith(keysManager)
                .recipientId("gateway:" + JS_PARAM_VALUE_GATEWAY_NAME)
                // Multiple private keys can be added to support graceful key rotations.
                .protocolVersion("ECv2")
                .addRecipientPrivateKey(privateKey);

        if (!GooglePayUtils.isEmpty(privateKeyOld)){
                builder = builder.addRecipientPrivateKey(privateKeyOld);
        }

        return builder
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

    /**
     * check if a String is null or empty
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * check if a String respect ISO-3166 rules
     *
     * @param countryCode the code to compare
     * @return true if countryCode is in ISO-3166 list, else return false
     */
    public static boolean isISO3166(String countryCode) {
        return Arrays.asList(Locale.getISOCountries()).contains(countryCode);
    }
}