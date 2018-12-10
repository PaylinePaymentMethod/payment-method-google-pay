package com.payline.payment.google.pay.service.impl;

import com.google.common.io.CharStreams;
import com.payline.pmapi.bean.paymentform.bean.PaymentFormLogo;
import com.payline.pmapi.bean.paymentform.bean.form.PartnerWidgetForm;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetContainerTargetDivId;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetOnPayCallBack;
import com.payline.pmapi.bean.paymentform.bean.form.partnerwidget.PartnerWidgetScriptImport;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import com.payline.pmapi.bean.paymentform.response.logo.PaymentFormLogoResponse;
import com.payline.pmapi.service.PaymentFormConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentFormConfigurationServiceImpl implements PaymentFormConfigurationService {

    private static final Logger logger = LogManager.getLogger( PaymentFormConfigurationServiceImpl.class );

    @Override
    public PaymentFormConfigurationResponse getPaymentFormConfiguration(PaymentFormConfigurationRequest paymentFormConfigurationRequest) {

        URL googlePayScriptUrl = this.getGooglePayScriptUrl();
        String scriptInitPaymentContent = this.getInitPaymentJavaScript();

        // Define the JS script to load
        PartnerWidgetScriptImport partnerWidgetScriptImport = PartnerWidgetScriptImport
                .WidgetPartnerScriptImportBuilder
                .aWidgetPartnerScriptImport()
                .withUrl(googlePayScriptUrl)
                .build();

        // Define the Payline callback
        PartnerWidgetOnPayCallBack partnerWidgetOnPayCallBack = PartnerWidgetOnPayCallBack
                .WidgetContainerOnPayCallBackBuilder
                .aWidgetContainerOnPayCallBack()
                .withName(JS_PARAM_VALUE__CALLBACK)
                .build();

        // Define the GooglePay button target div id
        PartnerWidgetContainerTargetDivId partnerWidgetContainerTargetDivId = PartnerWidgetContainerTargetDivId
                .WidgetPartnerContainerTargetDivIdBuilder
                .aWidgetPartnerContainerTargetDivId()
                .withId(JS_PARAM_VALUE__CONTAINER)
                .build();

        PartnerWidgetForm partnerWidgetForm = PartnerWidgetForm
                .WidgetPartnerFormBuilder
                .aWidgetPartnerForm()
                // Le script JS de pilotage de paiement a pre-charger sous forme de String
                .withLoadingScriptBeforeImport(scriptInitPaymentContent)
                // Le script JS GooglePay a charger depuis une URL
                .withScriptImport(partnerWidgetScriptImport)
                // Apres chargement du script JS GooglePay, la methode a appeler sur le script de pilotage pre-charge
                .withLoadingScriptAfterImport(JS_START_METHOD_NAME)
                // L'id de div dans laquelle sera charge et affiche le bouton GooglePay
                .withContainer(partnerWidgetContainerTargetDivId)
                // La callback de retour Payline pour proceder a la payment request qui devra suivre
                .withOnPay(partnerWidgetOnPayCallBack)
                .build();

        return PaymentFormConfigurationResponseSpecific
                .PaymentFormConfigurationResponseSpecificBuilder
                .aPaymentFormConfigurationResponseSpecific()
                .withPaymentForm(partnerWidgetForm)
                .build();

    }

    @Override
    public PaymentFormLogoResponse getPaymentFormLogo(PaymentFormLogoRequest paymentFormLogoRequest) {
        return null;
    }

    @Override
    public PaymentFormLogo getLogo(String s, Locale locale) {
        return null;
    }

    /**
     *
     * @return
     */
    private URL getGooglePayScriptUrl() {

        URL url = null;

        try {

            url = new URL(JS_URL_GOOGLE_PAY);

        } catch (MalformedURLException e) {
            this.logger.error(e.getMessage());
        }

        return url;

    }

    /**
     *
     * @return
     */
    private String getInitPaymentJavaScript() {

        String rawScriptInitPaymentContent = StringUtils.EMPTY;
        String scriptInitPaymentContent = StringUtils.EMPTY;

        try {

            rawScriptInitPaymentContent = CharStreams.toString(
                    new InputStreamReader(
                            PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(JS_RES_INIT_PAYMENT)
                    )
            );

        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }

        scriptInitPaymentContent = rawScriptInitPaymentContent
                .replace(JS_PARAM_TAG__TYPE, JS_PARAM_VALUE__TYPE)
                .replace(JS_PARAM_TAG__GATEWAY, JS_PARAM_VALUE__GATEWAY_NAME)
                .replace(JS_PARAM_TAG__GATEWAY_MERCHAND_ID, "gatewayMerchantId")
                .replace(JS_PARAM_TAG__CONTAINER, JS_PARAM_VALUE__CONTAINER)
                .replace(JS_PARAM_TAG__CALLBACK, JS_PARAM_VALUE__CALLBACK);

        this.logger.debug(scriptInitPaymentContent);

        return scriptInitPaymentContent;

    }

}