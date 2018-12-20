package com.payline.payment.google.pay.utils;

import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Objects;

import static com.payline.payment.google.pay.utils.GooglePayUtils.createStringAmount;

public class GooglePayUtilsTest {

    @Test
    public void decryptFromGoogleTest() throws GeneralSecurityException {
        String expectedPan = "4111111111111111";
        String encryptedData = "{\"signature\":\"MEUCIC0q4hi1OtbiUYvw+FotWD5dUGNdUxbaUvHROEPV6ZX8AiEA91a8uBCdwvOpSfd2yeKD9EKaH9cSvJq+kTfzYFTCrlU\\u003d\",\"protocolVersion\":\"ECv1\",\"signedMessage\":\"{\\\"encryptedMessage\\\":\\\"RAdOrfhuZTGxNzjiRdtaBmJsHE7/EjB6kyfj+OjqLCV++E49o3BmzFKUOjtN3LiZsq+aD98ZxZbTpuZyUsH1BNUbqqbuj7ceSlx9Xzb6eThRUK6ywPiJdFHyM4IL79WT0VK4ptesBCHXnGFitxBN3x7P1Muz3K5iLtYgTQaCHHRqaL3MTVJ4eqMc0xFHWxKI6QthmBeWFxCWMpsplKJycL2K1t8QmhTsT41hrDa/QVJxa3xFwVAJEyKgz7/hidf6mMaP3xA7Dq5xq3GTx4EZtoRS03NIZGEgDxNd3ETQcffibezKw+QPYZUkFRExCrZO7EkdXIgmtn1Md9BfEefyh3jjX6IuS9v12P+WXYFzVtlb8Q5VixVM/nexREcPccVBZfvHr9OcHa4cJF8E1uIx2r7EjcexecQx8s2ZrnzuPmq8/Uvn6IrMPrrEjrvEPBbXkYuFxgYz/rCf3+xO\\\",\\\"ephemeralPublicKey\\\":\\\"BNcpODTW3F1F5c5h1ewf3Nohhju+ruYp+mHwM1pcYJe13Nx3TtyKk3K3scuQKlL+hQOFNDGvHjNr8Hq2xaqty1Q\\\\u003d\\\",\\\"tag\\\":\\\"ZbrJBSEZ6ydQcL6wlEoK7Hnez0iGHNUBVrAPlAP9Gew\\\\u003d\\\"}\"}";

        String jsonEncryptedPaymentData = GooglePayUtils.decryptFromGoogle(encryptedData, Utils.PRIVATE_KEY, true);
        DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);

        Assert.assertTrue(Objects.nonNull(decryptedPaymentData));
        Assert.assertEquals(expectedPan, decryptedPaymentData.getPaymentMethodDetails().getPan());
    }



    @Test
    public void createStringAmont(){
        BigInteger int1 = BigInteger.ZERO;
        BigInteger int2 = BigInteger.ONE;
        BigInteger int3 = BigInteger.TEN;
        BigInteger int4 = BigInteger.valueOf(100);
        BigInteger int5 = BigInteger.valueOf(1000);

        Assert.assertEquals("0.00", createStringAmount(int1));
        Assert.assertEquals("0.01", createStringAmount(int2));
        Assert.assertEquals("0.10", createStringAmount(int3));
        Assert.assertEquals("1.00", createStringAmount(int4));
        Assert.assertEquals("10.00", createStringAmount(int5));
    }
}