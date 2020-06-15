package com.payline.payment.google.pay.service.impl;

import com.google.api.client.util.Base64;
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
import com.payline.pmapi.logger.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.time.YearMonth;
import java.util.Optional;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    /** Indique que le numero de carte transité par google pay est un PAN et non un TOKEN PAN */
    private static final String METHOD_PAN_ONLY = "PAN_ONLY";

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        try {
            String token;
            String brand = null;
            String holder = null;
            // check if the payment is in direct Mode
            if (paymentRequest.getPaymentFormContext().getPaymentFormParameter().containsKey(PAYMENTDATA_TOKENDATA)){
                token = paymentRequest.getPaymentFormContext().getPaymentFormParameter().get(PAYMENTDATA_TOKENDATA);
            }else{
                String jsonPaymentData = paymentRequest.getPaymentFormContext().getPaymentFormParameter().get(PAYMENT_REQUEST_PAYMENT_DATA_KEY);
                PaymentData paymentData = new PaymentData.Builder().fromJson(jsonPaymentData);
                token = paymentData.getPaymentMethodData().getTokenizationData().getToken();
                brand = paymentData.getPaymentMethodData().getInfo().getCardNetwork();
                holder = paymentData.getPaymentMethodData().getInfo().getBillingAddress().getName();
                if(holder == null){
                    holder = "";
                }
            }

            // decrypt token
            String privateKey = paymentRequest.getPartnerConfiguration().getProperty(PRIVATE_KEY_PATH);
            String privateKeyOld = paymentRequest.getPartnerConfiguration().getProperty(OLD_PRIVATE_KEY_PATH);
            String jsonEncryptedPaymentData = getDecryptedData(token, privateKey, privateKeyOld, paymentRequest.getEnvironment().isSandbox());
            DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);
            DecryptedPaymentMethodDetails paymentDetails = decryptedPaymentData.getPaymentMethodDetails();

            // create card from decrypted data
            Card card = Card.CardBuilder.aCard()
                    .withBrand(brand)
                    .withHolder(holder)
                    .withExpirationDate(YearMonth.of(paymentDetails.getExpirationYear(), paymentDetails.getExpirationMonth()))
                    .withPan(paymentDetails.getPan())
                    .withPanType(METHOD_PAN_ONLY.equals(paymentDetails.getAuthMethod()) ? Card.PanType.CARD_PAN : Card.PanType.TOKEN_PAN)
                    .build();

            // set the right value to the ECI (see PAYLAPMEXT242)
            String eci = paymentDetails.getEciIndicator();
            if (GooglePayUtils.isEmpty(eci) || "5".equalsIgnoreCase(eci)){
                eci = "02";
            }

            PaymentData3DS paymentData3DS = PaymentData3DS.Data3DSBuilder.aData3DS()
                    .withCavv(paymentDetails.getCryptogram())
                    .withEci(eci)
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
                    .withErrorCode("expired payload")
                    .withFailureCause(FailureCause.SESSION_EXPIRED)
                    .build();
        }

    }

    public String getDecryptedData(String token, String privateKey, String privateKeyOld, boolean isSandbox) throws GeneralSecurityException {
        return GooglePayUtils.decryptFromGoogle(token, privateKey, privateKeyOld, isSandbox);
    }

}
