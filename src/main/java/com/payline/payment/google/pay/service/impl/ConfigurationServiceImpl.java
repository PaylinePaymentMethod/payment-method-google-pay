package com.payline.payment.google.pay.service.impl;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

import com.payline.payment.google.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.configuration.AvailableNetwork;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.NetworkListBoxParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.service.ConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LogManager.getLogger( ConfigurationServiceImpl.class );

    private I18nService i18n = I18nService.getInstance();

    @Override
    public List<AbstractParameter> getParameters(Locale locale) {
        List<AbstractParameter> parameters = new ArrayList<>();

        // TODO

        final NetworkListBoxParameter networkCb = new NetworkListBoxParameter();
        networkCb.setKey(AvailableNetwork.CB.getKey());
        networkCb.setLabel(this.i18n.getMessage(NETWORK_CB_LABEL, locale));
        networkCb.setDescription(this.i18n.getMessage(NETWORK_CB_DESCRIPTION, locale));
        networkCb.setNetwork(AvailableNetwork.CB);

        final NetworkListBoxParameter networkVisa = new NetworkListBoxParameter();
        networkVisa.setKey(AvailableNetwork.VISA.getKey());
        networkVisa.setLabel(this.i18n.getMessage(NETWORK_VISA_LABEL, locale));
        networkVisa.setDescription(this.i18n.getMessage(NETWORK_VISA_DESCRIPTION, locale));
        networkVisa.setNetwork(AvailableNetwork.VISA);

        final NetworkListBoxParameter networkMastercard = new NetworkListBoxParameter();
        networkMastercard.setKey(AvailableNetwork.MASTERCARD.getKey());
        networkMastercard.setLabel(this.i18n.getMessage(NETWORK_MASTERCARD_LABEL, locale));
        networkMastercard.setDescription(this.i18n.getMessage(NETWORK_MASTERCARD_DESCRIPTION, locale));
        networkMastercard.setNetwork(AvailableNetwork.MASTERCARD);

        parameters.add(networkCb);
        parameters.add(networkVisa);
        parameters.add(networkMastercard);

        return parameters;
    }

    @Override
    public Map<String, String> check(ContractParametersCheckRequest contractParametersCheckRequest) {
        Locale locale = contractParametersCheckRequest.getLocale();
        final Map<String, String> errors = new HashMap<>();

        // TODO

        // No need to go forward if there is an error at this point
        if( errors.size() > 0 ){
            return errors;
        }

        return errors;
    }

    @Override
    public ReleaseInformation getReleaseInformation() {
        Properties props = new Properties();
        try {
            props.load( ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream( RELEASE_PROPERTIES ) );
        } catch( IOException e ){
            this.logger.error("An error occurred reading the file: " + RELEASE_PROPERTIES );
            props.setProperty( RELEASE_VERSION, "unknown" );
            props.setProperty( RELEASE_DATE, "01/01/1900" );
        }

        LocalDate date = LocalDate.parse( props.getProperty( RELEASE_DATE ), DateTimeFormatter.ofPattern( RELEASE_DATE_FORMAT ) );
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate( date )
                .withVersion( props.getProperty( RELEASE_VERSION ) )
                .build();
    }

    @Override
    public String getName(Locale locale) {
        return this.i18n.getMessage( PAYMENT_METHOD_NAME, locale );
    }

}