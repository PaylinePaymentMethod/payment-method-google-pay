package com.payline.payment.google.pay.service.impl;

import com.payline.payment.google.pay.utils.GooglePayConstants;
import com.payline.payment.google.pay.utils.Utils;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConfigurationServiceImplTest {
    @InjectMocks
    private ConfigurationServiceImpl service = new ConfigurationServiceImpl();

    @Test
    public void getParameters(){
        List<AbstractParameter> parameters = service.getParameters(Locale.FRANCE);
        Assert.assertTrue(!parameters.isEmpty());
        Assert.assertEquals(20, parameters.size());
    }

    @Test
    public void check(){
        ContractParametersCheckRequest request = Utils.createContractParametersCheckRequest();
        Map<String, String> errors = service.check(request);
        Assert.assertNotNull(errors);
        Assert.assertEquals(0, errors.size());
    }

    @Test
    public void checkBadCountryRestriction(){
        ContractParametersCheckRequest request = Utils.createContractParametersCheckRequest();
        request.getAccountInfo().replace(GooglePayConstants.ALLOWED_COUNTRY_KEY, "1A");
        Map<String, String> errors = service.check(request);
        Assert.assertNotNull(errors);
        Assert.assertEquals(1, errors.size());
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
