package com.payline.payment.google.pay.service.impl;

import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import com.payline.payment.google.pay.bean.DecryptedPaymentMethodDetails;
import com.payline.payment.google.pay.bean.PaymentData;
import com.payline.payment.google.pay.utils.GooglePayUtils;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentData3DS;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.time.YearMonth;

import static com.payline.payment.google.pay.utils.GooglePayConstants.PAYMENTDATA_TOKENDATA;
import static com.payline.payment.google.pay.utils.GooglePayConstants.PAYMENT_REQUEST_PAYMENT_DATA_KEY;
import static com.payline.payment.google.pay.utils.GooglePayConstants.PRIVATE_KEY_PATH;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        // check if the payment is in direct Mode
        String jsonPaymentData;
        if (paymentRequest.getPaymentFormContext().getPaymentFormParameter().get(PAYMENTDATA_TOKENDATA)!= null){
            jsonPaymentData = paymentRequest.getPaymentFormContext().getPaymentFormParameter().get(PAYMENTDATA_TOKENDATA);
        }else{
            jsonPaymentData = paymentRequest.getPartnerConfiguration().getProperty(PAYMENT_REQUEST_PAYMENT_DATA_KEY);
        }

        PaymentData paymentData = new PaymentData.Builder().fromJson(jsonPaymentData);

        try {
            // decrypt data
            String token = paymentData.getPaymentMethodData().getTokenizationData().getToken();
            String privateKey = paymentRequest.getPartnerConfiguration().getProperty(PRIVATE_KEY_PATH);
            String jsonEncryptedPaymentData = getDecryptedData(token, privateKey, paymentRequest.getEnvironment().isSandbox());
            DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);
            DecryptedPaymentMethodDetails paymentDetails = decryptedPaymentData.getPaymentMethodDetails();

            // create card from decrypted data
            Card card = Card.CardBuilder.aCard()
                    .withBrand(paymentData.getPaymentMethodData().getInfo().getCardNetwork())
                    .withHolder(paymentData.getPaymentMethodData().getInfo().getBillingAddress().getName())
                    .withExpirationDate(YearMonth.of(paymentDetails.getExpirationYear(), paymentDetails.getExpirationMonth()))
                    .withPan(paymentDetails.getPan())
                    .build();

            PaymentData3DS paymentData3DS = PaymentData3DS.Data3DSBuilder.aData3DS()
                    .withCavv(paymentDetails.getCryptogram())
                    .withEci(paymentDetails.getEciIndicator())
                    .build();

            PaymentModeCard paymentModeCard = PaymentModeCard.PaymentModeCardBuilder.aPaymentModeCard()
                    .withPaymentDatas3DS(paymentData3DS)
                    .withCard(card)
                    .build();

            return PaymentResponseDoPayment.PaymentResponseDoPaymentBuilder
                    .aPaymentResponseDoPayment()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withPaymentMode(paymentModeCard)
                    .build();
        } catch (GeneralSecurityException e) {
            LOGGER.error("An error occured tring to decrypt data", e);
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }

    }

    public String getDecryptedData(String token, String privateKey, boolean isSandbox) throws GeneralSecurityException {
        return GooglePayUtils.decryptFromGoogle(token, privateKey, isSandbox);
    }

}
