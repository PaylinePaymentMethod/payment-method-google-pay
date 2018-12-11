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
import com.payline.pmapi.bean.paymentform.response.logo.impl.PaymentFormLogoResponseFile;
import com.payline.pmapi.service.PaymentFormConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class PaymentFormConfigurationServiceImpl implements PaymentFormConfigurationService {

    private static final String LOGO_CONTENT_TYPE = "image/png";
    private static final int LOGO_HEIGHT = 256;
    private static final int LOGO_WIDTH = 256;

    private static final Logger LOGGER = LogManager.getLogger( PaymentFormConfigurationServiceImpl.class );

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
                .withDescription("DESC") //TODO description obligatoire a ajouter
                .withDisplayButton(true).withButtonText("HACK") //TODO ligne temporaire le temps qu'un bug du widget soit corrige
                .build();

        return PaymentFormConfigurationResponseSpecific
                .PaymentFormConfigurationResponseSpecificBuilder
                .aPaymentFormConfigurationResponseSpecific()
                .withPaymentForm(partnerWidgetForm)
                .build();

    }

    @Override
    public PaymentFormLogoResponse getPaymentFormLogo(PaymentFormLogoRequest paymentFormLogoRequest) {
        return PaymentFormLogoResponseFile.PaymentFormLogoResponseFileBuilder.aPaymentFormLogoResponseFile()
                .withHeight(LOGO_HEIGHT)
                .withWidth(LOGO_WIDTH)
                .withTitle("Title") //TODO
                .withAlt("Alt") //TODO
                .build();
    }

    @Override
    public PaymentFormLogo getLogo(String paymentMethodIdentifier, Locale locale) {
        try {
            // Read logo file
            InputStream input = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream("logo.png");
            BufferedImage logo = ImageIO.read(input);

            // Recover byte array from image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(logo, "png", baos);

            return PaymentFormLogo.PaymentFormLogoBuilder.aPaymentFormLogo()
                    .withFile(baos.toByteArray())
                    .withContentType(LOGO_CONTENT_TYPE)
                    .build();

        } catch (IOException e) {
            LOGGER.error("unable to load the logo: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to load logo");
        }
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
            this.LOGGER.error(e.getMessage());
        }

        return url;

    }

    /**
     *
     * @return
     */
    private String getInitPaymentJavaScript() {

        String rawScriptInitPaymentContent = "";
        String scriptInitPaymentContent = "";

        try {

            rawScriptInitPaymentContent = CharStreams.toString(
                    new InputStreamReader(
                            PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(JS_RES_INIT_PAYMENT)
                    )
            );

        } catch (IOException e) {
            this.LOGGER.error(e.getMessage()); //TODO mieux gerer cette erreur qui doit etre bloquante non ?
        }

        scriptInitPaymentContent = rawScriptInitPaymentContent
                .replace(JS_PARAM_TAG__TYPE, JS_PARAM_VALUE__TYPE)
                .replace(JS_PARAM_TAG__GATEWAY, JS_PARAM_VALUE__GATEWAY_NAME)
                .replace(JS_PARAM_TAG__GATEWAY_MERCHAND_ID, "gatewayMerchantId")
                .replace(JS_PARAM_TAG__CONTAINER, JS_PARAM_VALUE__CONTAINER)
                .replace(JS_PARAM_TAG__CALLBACK, JS_PARAM_VALUE__CALLBACK);

        this.LOGGER.debug(scriptInitPaymentContent);

        return scriptInitPaymentContent;

    }

}