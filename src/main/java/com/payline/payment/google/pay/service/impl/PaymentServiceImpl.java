package com.payline.payment.google.pay.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.GooglePaymentsPublicKeysManager;
import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import com.payline.payment.google.pay.bean.DecryptedPaymentData;
import com.payline.payment.google.pay.bean.DecryptedPaymentMethodDetails;
import com.payline.payment.google.pay.bean.PaymentData;
import com.payline.payment.google.pay.exception.PluginException;
import com.payline.payment.google.pay.utils.GooglePayUtils;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentData3DS;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.Logger;

import java.security.GeneralSecurityException;
import java.time.YearMonth;

import static com.payline.payment.google.pay.bean.CardInfo.MASTERCARD;
import static com.payline.payment.google.pay.bean.CardInfo.VISA;
import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    /** Indique que le numero de carte transité par google pay est un PAN et non un TOKEN PAN */
    private static final String METHOD_PAN_ONLY = "PAN_ONLY";
    private static final String KEY_NAME = "key";
    private static final String OLD_KEY_NAME = "oldKey";

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse;
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
            final String privateKey = paymentRequest.getPartnerConfiguration().getProperty(PRIVATE_KEY_PATH);
            final String privateKeyOld = paymentRequest.getPartnerConfiguration().getProperty(OLD_PRIVATE_KEY_PATH);
            final String jsonEncryptedPaymentData = getDecryptedData(token, privateKey, privateKeyOld, paymentRequest.getEnvironment().isSandbox());
            final DecryptedPaymentData decryptedPaymentData = new DecryptedPaymentData.Builder().fromJson(jsonEncryptedPaymentData);
            final DecryptedPaymentMethodDetails paymentDetails = decryptedPaymentData.getPaymentMethodDetails();

            // create card from decrypted data
            final Card card = Card.CardBuilder.aCard()
                    .withBrand(brand)
                    .withHolder(holder)
                    .withExpirationDate(YearMonth.of(paymentDetails.getExpirationYear(), paymentDetails.getExpirationMonth()))
                    .withPan(paymentDetails.getPan())
                    .withPanType(METHOD_PAN_ONLY.equals(paymentDetails.getAuthMethod()) ? Card.PanType.CARD_PAN : Card.PanType.TOKEN_PAN)
                    .build();

            final String eci = computeEciIndicator(brand, paymentDetails);

            final PaymentData3DS paymentData3DS = PaymentData3DS.Data3DSBuilder.aData3DS()
                    .withCavv(paymentDetails.getCryptogram())
                    .withEci(eci)
                    .build();

            final PaymentModeCard paymentModeCard = PaymentModeCard.PaymentModeCardBuilder.aPaymentModeCard()
                    .withPaymentDatas3DS(paymentData3DS)
                    .withCard(card)
                    .build();

            paymentResponse =  PaymentResponseDoPayment.PaymentResponseDoPaymentBuilder
                    .aPaymentResponseDoPayment()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withPaymentMode(paymentModeCard)
                    .build();
        }
        catch (final PluginException e) {
            LOGGER.error(e.getMessage());
            paymentResponse =  PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withErrorCode(e.getMessage())
                    .withFailureCause(e.getFailureCause())
                    .build();
        }
        catch (final GeneralSecurityException e) {
            LOGGER.error("An error occured tring to decrypt data", e);
            paymentResponse =  PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withPartnerTransactionId(paymentRequest.getTransactionId())
                    .withErrorCode("Unable to decrypt data")
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }

        return paymentResponse;
    }

    public String getDecryptedData(String token, String privateKey, String privateKeyOld, boolean isSandbox) throws GeneralSecurityException {
        final GooglePaymentsPublicKeysManager keysManager = isSandbox ? GooglePaymentsPublicKeysManager.INSTANCE_TEST : GooglePaymentsPublicKeysManager.INSTANCE_PRODUCTION;
        keysManager.refreshInBackground();
        PaymentMethodTokenRecipient.Builder builder = new PaymentMethodTokenRecipient.Builder()
                .fetchSenderVerifyingKeysWith(keysManager)
                .recipientId("gateway:" + JS_PARAM_VALUE_GATEWAY_NAME)
                .protocolVersion("ECv2");

        // Multiple private keys can be added to support graceful key rotations.
        boolean hasAValidKey = addPrivateKey(builder, KEY_NAME, privateKey) || addPrivateKey(builder, OLD_KEY_NAME, privateKeyOld);
        if (!hasAValidKey) {
            throw new PluginException("No valid key to communicate with GooglePay");
        }
        return builder.build().unseal(token);
    }

    /**
     * Méthode permettant d'ajouter une clé privée pour tenter de déchiffrer le message GooglePay.
     * @param builder
     *          Builder google utilisé pour le déchiffrement.
     * @param name
     *          Nom de la clé.
     * @param privateKey
     *          Clé version texte.
     * @return
     *          True si la clé a été ajouté false sinon.
     */
    protected boolean addPrivateKey(PaymentMethodTokenRecipient.Builder builder, final String name, final String privateKey) {
        boolean validKey = false;
        try{
            if (!GooglePayUtils.isEmpty(privateKey)) {
                builder.addRecipientPrivateKey(privateKey);
                validKey = true;
            }
        }
        catch (final GeneralSecurityException exception) {
            LOGGER.error("Impossible d'ajouter la clé {}", name, exception);
        }
        return validKey;
    }

    /**
     * Method used to compute EciIndicator.
     * @param brand
     *          card brand.
     * @param paymentDetails
     *          payment details.
     * @return
     *          Electronic challenge indicator.
     */
    private String computeEciIndicator(final String brand, final DecryptedPaymentMethodDetails paymentDetails) {
        // set the right value to the ECI (see PAYLAPMEXT257)
        String eci = paymentDetails.getEciIndicator();
        if (VISA.equalsIgnoreCase(brand) && GooglePayUtils.isEmpty(eci)) {
            eci = "05";
        } else if (MASTERCARD.equalsIgnoreCase(brand)
                && (GooglePayUtils.isEmpty(eci) || Integer.parseInt(eci) == 5)) {
            eci = "02";
        }
        return eci;
    }
}
