package com.payline.payment.google.pay.service.impl;

import com.payline.payment.google.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.configuration.AvailableNetwork;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.InputParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.ListBoxParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.NetworkListBoxParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.service.ConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;
import static com.payline.payment.google.pay.utils.propertiesFilesConstants.ConfigurationConstants.*;

public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger LOGGER = LogManager.getLogger(ConfigurationServiceImpl.class);

    private I18nService i18n = I18nService.getInstance();

    @Override
    public List<AbstractParameter> getParameters(Locale locale) {
        List<AbstractParameter> parameters = new ArrayList<>();

        // parameters used to identify the merchant
        final InputParameter merchantName = new InputParameter();
        merchantName.setKey(MERCHANT_NAME_KEY);
        merchantName.setLabel(this.i18n.getMessage(MERCHANT_NAME_LABEL, locale));
        merchantName.setDescription(this.i18n.getMessage(MERCHANT_NAME_DESCRIPTION, locale));
        merchantName.setRequired(true);
        parameters.add(merchantName);

        final InputParameter merchantId = new InputParameter();
        merchantId.setKey(MERCHANT_ID_KEY);
        merchantId.setLabel(this.i18n.getMessage(MERCHANT_ID_LABEL, locale));
        merchantId.setDescription(this.i18n.getMessage(MERCHANT_ID_DESCRIPTION, locale));
        merchantId.setRequired(true);
        parameters.add(merchantId);

        // parameters used to customize the googlePay pay button
        final ListBoxParameter buttonColor = new ListBoxParameter();
        Map<String, String> colors = new HashMap<>();
        colors.put(COLOR_DEFAULT_KEY, this.i18n.getMessage(COLOR_DEFAULT_VAL, locale));
        colors.put(COLOR_BLACK_KEY, this.i18n.getMessage(COLOR_BLACK_VAL, locale));
        colors.put(COLOR_WHITE_KEY, this.i18n.getMessage(COLOR_WHITE_VAL, locale));
        buttonColor.setList(colors);
        buttonColor.setKey(BUTTON_COLOR_KEY);
        buttonColor.setLabel(this.i18n.getMessage(BUTTON_COLOR_LABEL, locale));
        buttonColor.setDescription(this.i18n.getMessage(BUTTON_COLOR_DESCRIPTION, locale));
        buttonColor.setValue(colors.get(COLOR_WHITE_KEY));
        buttonColor.setRequired(true);
        parameters.add(buttonColor);

        final ListBoxParameter buttonType = new ListBoxParameter();
        Map<String, String> types = new HashMap<>();
        types.put(SIZE_SHORT_KEY, this.i18n.getMessage(SIZE_SHORT_VAL, locale));
        types.put(SIZE_LONG_KEY, this.i18n.getMessage(SIZE_LONG_VAL, locale));
        buttonType.setList(types);
        buttonType.setKey(BUTTON_SIZE_KEY);
        buttonType.setLabel(this.i18n.getMessage(BUTTON_SIZE_LABEL, locale));
        buttonType.setDescription(this.i18n.getMessage(BUTTON_SIZE_DESCRIPTION, locale));
        buttonType.setValue(types.get(SIZE_SHORT_KEY));
        buttonType.setRequired(true);
        parameters.add(buttonType);

        // parameters used to choose payment networks
        final ListBoxParameter allowedAuthMethod = new ListBoxParameter();
        Map<String, String> authMethod = new HashMap<>();
        authMethod.put(METHOD_PANONLY_KEY, this.i18n.getMessage(METHOD_PANONLY_VAL, locale));
        authMethod.put(METHOD_3DS_KEY, this.i18n.getMessage(METHOD_3DS_VAL, locale));
        authMethod.put(METHOD_BOTH_KEY, this.i18n.getMessage(METHOD_BOTH_VAL, locale));
        allowedAuthMethod.setList(authMethod);
        allowedAuthMethod.setKey(ALLOWED_AUTH_METHOD_KEY);
        allowedAuthMethod.setLabel(this.i18n.getMessage(ALLOWED_AUTH_METHOD_LABEL, locale));
        allowedAuthMethod.setDescription(this.i18n.getMessage(ALLOWED_AUTH_METHOD_DESCRIPTION, locale));
        allowedAuthMethod.setValue(authMethod.get(METHOD_BOTH_KEY));
        allowedAuthMethod.setRequired(true);
        parameters.add(allowedAuthMethod);

        Map<String, String> yesNoList = new HashMap<>();
        yesNoList.put(YES_KEY, this.i18n.getMessage(YES_VAL, locale));
        yesNoList.put(NO_KEY, this.i18n.getMessage(NO_VAL, locale));

        final NetworkListBoxParameter networkCb = new NetworkListBoxParameter();
        networkCb.setKey(AvailableNetwork.CB.getKey());
        networkCb.setLabel(this.i18n.getMessage(NETWORK_CB_LABEL, locale));
        networkCb.setDescription(this.i18n.getMessage(NETWORK_CB_DESCRIPTION, locale));
        networkCb.setNetwork(AvailableNetwork.CB);
        parameters.add(networkCb);

        final ListBoxParameter activateNetworkVisa = new ListBoxParameter();
        activateNetworkVisa.setKey(ACTIVATE_NETWORK_VISA_KEY);
        activateNetworkVisa.setLabel(this.i18n.getMessage(ACTIVATE_NETWORK_VISA_LABEL, locale));
        activateNetworkVisa.setDescription(this.i18n.getMessage(ACTIVATE_NETWORK_VISA_DESCRIPTION, locale));
        activateNetworkVisa.setList(yesNoList);
        activateNetworkVisa.setValue(yesNoList.get(YES_KEY));
        activateNetworkVisa.setRequired(true);
        parameters.add(activateNetworkVisa);

        final NetworkListBoxParameter networkVisa = new NetworkListBoxParameter();
        networkVisa.setKey(AvailableNetwork.VISA.getKey());
        networkVisa.setLabel(this.i18n.getMessage(NETWORK_VISA_LABEL, locale));
        networkVisa.setDescription(this.i18n.getMessage(NETWORK_VISA_DESCRIPTION, locale));
        networkVisa.setNetwork(AvailableNetwork.VISA);
        parameters.add(networkVisa);

        final ListBoxParameter activateNetworkMastercard = new ListBoxParameter();
        activateNetworkMastercard.setKey(ACTIVATE_NETWORK_MASTERCARD_KEY);
        activateNetworkMastercard.setLabel(this.i18n.getMessage(ACTIVATE_NETWORK_MASTERCARD_LABEL, locale));
        activateNetworkMastercard.setDescription(this.i18n.getMessage(ACTIVATE_NETWORK_MASTERCARD_DESCRIPTION, locale));
        activateNetworkMastercard.setList(yesNoList);
        activateNetworkMastercard.setValue(yesNoList.get(YES_KEY));
        activateNetworkMastercard.setRequired(true);
        parameters.add(activateNetworkMastercard);

        final NetworkListBoxParameter networkMastercard = new NetworkListBoxParameter();
        networkMastercard.setKey(AvailableNetwork.MASTERCARD.getKey());
        networkMastercard.setLabel(this.i18n.getMessage(NETWORK_MASTERCARD_LABEL, locale));
        networkMastercard.setDescription(this.i18n.getMessage(NETWORK_MASTERCARD_DESCRIPTION, locale));
        networkMastercard.setNetwork(AvailableNetwork.MASTERCARD);
        parameters.add(networkMastercard);

        final ListBoxParameter activateNetworkAmex = new ListBoxParameter();
        activateNetworkAmex.setKey(ACTIVATE_NETWORK_AMEX_KEY);
        activateNetworkAmex.setLabel(this.i18n.getMessage(ACTIVATE_NETWORK_AMEX_LABEL, locale));
        activateNetworkAmex.setDescription(this.i18n.getMessage(ACTIVATE_NETWORK_AMEX_DESCRIPTION, locale));
        activateNetworkAmex.setList(yesNoList);
        activateNetworkAmex.setValue(yesNoList.get(YES_KEY));
        activateNetworkAmex.setRequired(true);
        parameters.add(activateNetworkAmex);

        final NetworkListBoxParameter networkAmex = new NetworkListBoxParameter();
        networkAmex.setKey(AvailableNetwork.AMEX.getKey());
        networkAmex.setLabel(this.i18n.getMessage(NETWORK_AMEX_LABEL, locale));
        networkAmex.setDescription(this.i18n.getMessage(NETWORK_AMEX_DESCRIPTION, locale));
        networkAmex.setNetwork(AvailableNetwork.AMEX);
        parameters.add(networkAmex);

/*        // not available yet
        final ListBoxParameter activateNetworkJCB = new ListBoxParameter();
        activateNetworkJCB.setKey(ACTIVATE_NETWORK_JCB_KEY);
        activateNetworkJCB.setLabel(this.i18n.getMessage(ACTIVATE_NETWORK_JCB_LABEL, locale));
        activateNetworkJCB.setDescription(this.i18n.getMessage(ACTIVATE_NETWORK_JCB_DESCRIPTION, locale));
        activateNetworkJCB.setList(yesNoList);
        activateNetworkJCB.setValue(yesNoList.get(YES_KEY));
        activateNetworkJCB.setRequired(true);
        parameters.add(activateNetworkJCB);

        final NetworkListBoxParameter networkJCB = new NetworkListBoxParameter();
        networkMastercard.setKey(AvailableNetwork.AMEX.getKey());   // todo AvailableNetwork.JCP
        networkMastercard.setLabel(this.i18n.getMessage(NETWORK_JCB_LABEL, locale));
        networkMastercard.setDescription(this.i18n.getMessage(NETWORK_JCB_DESCRIPTION, locale));
        networkMastercard.setNetwork(AvailableNetwork.AMEX);
        parameters.add(networkJCB);

        final ListBoxParameter activateNetworkDiscover = new ListBoxParameter();
        activateNetworkDiscover.setKey(ACTIVATE_NETWORK_DISCOVER_KEY);
        activateNetworkDiscover.setLabel(this.i18n.getMessage(ACTIVATE_NETWORK_DISCOVER_LABEL, locale));
        activateNetworkDiscover.sd(this.i18n.getMessage(ACTIVATE_NETWORK_DISCOVER_DESCRIPTION, locale));
        activateNetworkDiscover.setList(yesNoList);
        activateNetworkDiscover.setValue(yesNoList.get(YES_KEY));
        activateNetworkDiscover.setRequired(true);
        parameters.add(activateNetworkDiscover);

        final NetworkListBoxParameter networkDiscover = new NetworkListBoxParameter();
        networkMastercard.setKey(AvailableNetwork.AMEX.getKey());   // todo AvailableNetwork.DISCOVER
        networkMastercard.setLabel(this.i18n.getMessage(NETWORK_DISCOVER_LABEL, locale));
        networkMastercard.setDescription(this.i18n.getMessage(NETWORK_DISCOVER_DESCRIPTION, locale));
        networkMastercard.setNetwork(AvailableNetwork.AMEX);
        parameters.add(networkDiscover);
        */

        return parameters;
    }

    @Override
    public Map<String, String> check(ContractParametersCheckRequest contractParametersCheckRequest) {
        // Google pay doesn't provide API to verify fields validity
        return new HashMap<>();
    }

    @Override
    public ReleaseInformation getReleaseInformation() {
        Properties props = new Properties();
        try {
            props.load(ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(RELEASE_PROPERTIES));
        } catch (IOException e) {
            final String message = "An error occurred reading the file: release.properties";
            LOGGER.error(message);
            throw new RuntimeException(message, e);
        }

        final LocalDate date = LocalDate.parse(props.getProperty(RELEASE_DATE), DateTimeFormatter.ofPattern(RELEASE_DATE_FORMAT));
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate(date)
                .withVersion(props.getProperty(RELEASE_VERSION))
                .build();
    }

    @Override
    public String getName(Locale locale) {
        return this.i18n.getMessage(PAYMENT_METHOD_NAME, locale);
    }

}