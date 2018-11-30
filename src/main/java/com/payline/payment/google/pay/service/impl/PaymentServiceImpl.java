package com.payline.payment.google.pay.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import com.payline.payment.google.pay.bean.PaymentData;
import com.payline.payment.google.pay.utils.GooglePayUtils;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.YearMonth;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LogManager.getLogger( PaymentServiceImpl.class );

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {

        String jsonPaymentData = paymentRequest.getPaymentFormContext().getPaymentFormParameter().get(PAYMENT_REQUEST_PAYMENT_DATA_KEY);
        this.logger.debug("JSON PaymentData :");
        this.logger.debug(jsonPaymentData);

        PaymentData paymentData = new PaymentData.Builder().fromJson(jsonPaymentData);
        this.logger.debug("Object PaymentData :");
        this.logger.debug(paymentData.toString());

        this.logger.debug("Encrypted token : " + paymentData.getPaymentMethodData().getTokenizationData().getToken());


        GooglePaymentsPublicKeysManager googlePaymentsPublicKeysManager = GooglePaymentsPublicKeysManager.INSTANCE_TEST;

        if (!paymentRequest.getEnvironment().isSandbox()) {
            googlePaymentsPublicKeysManager = GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION;
        }

        String jsonEncryptedPaymentData = GooglePayUtils.decryptFromGoogle(
                paymentData.getPaymentMethodData().getTokenizationData().getToken(),
                googlePaymentsPublicKeysManager
        );
        this.logger.debug("JSON decrypted token :");
        this.logger.debug(jsonEncryptedPaymentData);

        DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);
        this.logger.debug("OBJECT decrypted token :");
        this.logger.debug(decryptedPaymentData.toString());

        Card card = Card.CardBuilder.aCard()
                //.withBrand()
                //.withHolder()
                .withExpirationDate(YearMonth.of(decryptedPaymentData.getPaymentMethodDetails().getExpirationYear(), decryptedPaymentData.getPaymentMethodDetails().getExpirationMonth()))
                .withPan(decryptedPaymentData.getPaymentMethodDetails().getPan())
                .build();

        PaymentModeCard paymentModeCard = PaymentModeCard.PaymentModeCardBuilder.aPaymentModeCard()
                //.withFavoriteNetwork()
                //.withPaymentDatas3DS()
                //.withPaymentOption()
                .withCard(card)
                .build();

        return PaymentResponseDoPayment.PaymentResponseDoPaymentBuilder.aPaymentResponseDoPayment()
                .withPartnerTransactionId("0123456789")
                .withPaymentMode(paymentModeCard)
                .build();

    }

}
