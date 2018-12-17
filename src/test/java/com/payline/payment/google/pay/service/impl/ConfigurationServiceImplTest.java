package com.payline.payment.google.pay.service.impl;

import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Locale;

public class ConfigurationServiceImplTest {
    @InjectMocks
    private ConfigurationServiceImpl service = new ConfigurationServiceImpl();

    @Test
    public void getParameters(){
        List<AbstractParameter> parameters = service.getParameters(Locale.FRANCE);
        Assert.assertTrue(!parameters.isEmpty());
    }

    @Test
    public void check(){
        // todo!!
    }


    @Test
    public void getReleaseInformation() {
        ReleaseInformation information = service.getReleaseInformation();
        Assert.assertFalse("01/01/1900".equals(information.getDate()));
        Assert.assertFalse("unknown".equals(information.getVersion()));
    }

    @Test
    public void getName(){
        String name = service.getName(Locale.FRANCE);
        Assert.assertEquals("Google Pay", name);
    }

}
