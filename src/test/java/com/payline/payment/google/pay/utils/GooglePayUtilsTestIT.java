package com.payline.payment.google.pay.utils;

import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import org.junit.Assert;
import org.junit.Test;

import java.security.GeneralSecurityException;

public class GooglePayUtilsTestIT {

    @Test
    public void decryptFromGoogleTest() throws GeneralSecurityException {
        String expectedPan = "4111111111111111";

        // this variable expires after few weeks
        String encryptedData = "{\"signature\":\"MEQCIC0g11eOLFWN11I7iv//mUWnOwIxNNJYepmm5qhL4OvTAiBNeDjYNrWRg3Zy6tdMnVYcDSKZlWUpT8erzPeJSBLUkA\\u003d\\u003d\",\"protocolVersion\":\"ECv1\",\"signedMessage\":\"{\\\"encryptedMessage\\\":\\\"0OXR1nFR7W/lhiijdU1anG8Ep5oPSVqaYmSf0USqcCA/2sIKwcaXS9MKQids55xIuBCf0F2YEIAyagc95FuixFRrcUkyYbrNM5HEUjt0JkaDKzHr7RoqmE+f8hmQMOV7g+i1M96fwMhai1KiRbuM5MF4+wKu8EhK3DRB95Zv7G9kn01AHFU1LEyJvRuZivBJiHSShj0hs2+r+HX3tkUat1YVO+Uu/MVvAam1gD0SpWvGbhAkgJu6eAHqd/cu79/UHFDVwuwguOcrw0VonxO4B6w0K4CefUeoQZRLgtto+49XYp9oDbMbZz9ad7k1R2dYkkdulYFLt7bU589XtrY3JJw+z9bTtK9eabvn3T85kg5VrgiR637GEppwLlr2GvDsB++5EcK+hf5+n3vM8b/Ehr4+U5W62JuPt79lMeZ7zAhnRZkq/kMbKhfZGxtyvc9nWppxHZatv7akNcE/\\\",\\\"ephemeralPublicKey\\\":\\\"BBRYW4Nmc1oRQ2kvo/1Rsk0HRvWZ9GFtxqCrHj8NIEahOzK5WiMVCy3UakHuhgnnVzMv3om6dA93U/EKNacU1z0\\\\u003d\\\",\\\"tag\\\":\\\"gIGF1RJSkV+3V+xyB7bbO+/+CNalMGsZt/GT0/wlITM\\\\u003d\\\"}\"}";

        String jsonEncryptedPaymentData = GooglePayUtils.decryptFromGoogle(encryptedData, Utils.TEST_PRIVATE_KEY, null, true);
        DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);

        Assert.assertNotNull(decryptedPaymentData);
        Assert.assertNotNull(decryptedPaymentData.getPaymentMethodDetails());
        Assert.assertEquals(expectedPan, decryptedPaymentData.getPaymentMethodDetails().getPan());
    }
}