package com.payline.payment.google.pay.utils;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class GooglePayUtilsTest {

    @Test
    public void decryptFromGoogleTest() {
        String expectedPan = "4111111111111111";
        String encryptedData = "{\"signature\":\"MEUCIC0q4hi1OtbiUYvw+FotWD5dUGNdUxbaUvHROEPV6ZX8AiEA91a8uBCdwvOpSfd2yeKD9EKaH9cSvJq+kTfzYFTCrlU\\u003d\",\"protocolVersion\":\"ECv1\",\"signedMessage\":\"{\\\"encryptedMessage\\\":\\\"RAdOrfhuZTGxNzjiRdtaBmJsHE7/EjB6kyfj+OjqLCV++E49o3BmzFKUOjtN3LiZsq+aD98ZxZbTpuZyUsH1BNUbqqbuj7ceSlx9Xzb6eThRUK6ywPiJdFHyM4IL79WT0VK4ptesBCHXnGFitxBN3x7P1Muz3K5iLtYgTQaCHHRqaL3MTVJ4eqMc0xFHWxKI6QthmBeWFxCWMpsplKJycL2K1t8QmhTsT41hrDa/QVJxa3xFwVAJEyKgz7/hidf6mMaP3xA7Dq5xq3GTx4EZtoRS03NIZGEgDxNd3ETQcffibezKw+QPYZUkFRExCrZO7EkdXIgmtn1Md9BfEefyh3jjX6IuS9v12P+WXYFzVtlb8Q5VixVM/nexREcPccVBZfvHr9OcHa4cJF8E1uIx2r7EjcexecQx8s2ZrnzuPmq8/Uvn6IrMPrrEjrvEPBbXkYuFxgYz/rCf3+xO\\\",\\\"ephemeralPublicKey\\\":\\\"BNcpODTW3F1F5c5h1ewf3Nohhju+ruYp+mHwM1pcYJe13Nx3TtyKk3K3scuQKlL+hQOFNDGvHjNr8Hq2xaqty1Q\\\\u003d\\\",\\\"tag\\\":\\\"ZbrJBSEZ6ydQcL6wlEoK7Hnez0iGHNUBVrAPlAP9Gew\\\\u003d\\\"}\"}";
        GooglePaymentsPublicKeysManager googlePaymentsPublicKeysManager = GooglePaymentsPublicKeysManager.INSTANCE_TEST;

        String jsonEncryptedPaymentData = GooglePayUtils.decryptFromGoogle(encryptedData, googlePaymentsPublicKeysManager);
        DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);

        Assert.assertTrue(Objects.nonNull(decryptedPaymentData));
        Assert.assertEquals(expectedPan, decryptedPaymentData.getPaymentMethodDetails().getPan());
    }
}