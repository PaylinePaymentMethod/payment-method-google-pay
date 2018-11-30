package com.payline.payment.google.pay.utils.i18n;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.payline.payment.google.pay.utils.GooglePayConstants.*;

public class I18nService {

    private static final Logger logger = LogManager.getLogger( I18nService.class );

    /**
     * Private constructor
     */
    private I18nService(){
        Locale.setDefault( new Locale( I18N_SERVICE_DEFAULT_LOCALE ) );
    }

    /**
     * Holder
     */
    private static class SingletonHolder {
        /**
         * Unique instance, not preinitializes
         */
        private static final I18nService instance = new I18nService();
    }

    /**
     * Unique access point for the singleton instance
     */
    public static I18nService getInstance() {
        return SingletonHolder.instance;
    }

    public String getMessage( final String key, final Locale locale ){
        ResourceBundle messages = ResourceBundle.getBundle( RESOURCE_BUNDLE_BASE_NAME, locale );
        try {
            return messages.getString( key );
        }
        catch( MissingResourceException e ){
            logger.error( "Trying to get a message with a key that does not exist: " + key + " (language: " + locale.getLanguage() + ")" );
            return "???" + locale + "." + key + "???";
        }
    }

    // If ever needed, implement getMessage( String, Locale, String... ) to insert values into the translation messages
}
