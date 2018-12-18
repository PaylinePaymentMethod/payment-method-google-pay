package com.payline.payment.google.pay.integration;

import com.payline.payment.google.pay.service.impl.PaymentServiceImpl;
import com.payline.payment.google.pay.utils.Utils;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.logging.Level;

import static com.payline.payment.google.pay.utils.GooglePayConstants.PAYMENT_REQUEST_PAYMENT_DATA_KEY;

public class GooglePayIT {
    private PaymentServiceImpl paymentService = new PaymentServiceImpl();


    @Test
    public void fullPaymentTest() {
        // load the payment page
        String paymentData = payOnPartnerWebsite();
        Assert.assertNotNull(paymentData);

        // create PaymentRequest with paymentData
        PaymentRequest paymentRequest = Utils.createCompletePaymentBuilder().build();
        paymentRequest.getPaymentFormContext().getPaymentFormParameter().put(PAYMENT_REQUEST_PAYMENT_DATA_KEY, paymentData);

        // execute payment from payment data
        PaymentResponse response = paymentService.paymentRequest(paymentRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(PaymentResponseDoPayment.class, response.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) response;

    }


    protected String payOnPartnerWebsite() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("disable-web-security");
        options.addArguments("allow-running-insecure-content");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        cap.setJavascriptEnabled(true);
        // Start browser
        WebDriver driver = new ChromeDriver(cap);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {

            ClassLoader classLoader = this.getClass().getClassLoader();
            File keyFile = new File(classLoader.getResource("index.html").getFile());
            // Go to partner's website
            driver.get("file://" + keyFile.getAbsolutePath());


            // pay on google pay webSite


            // Wait for redirection to success or cancel url
            WebDriverWait wait = new WebDriverWait(driver, 60 * 5);
            String paymentDataPath = "//*[@id=\"body\"]/div[2]";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(paymentDataPath)));
            return ((ChromeDriver) driver).findElementByXPath(paymentDataPath).getText();
        } finally {
            driver.quit();
        }
    }
}
