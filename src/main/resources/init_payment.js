const baseRequest = {
  apiVersion: 2,
  apiVersionMinor: 0,
  emailRequired: ${emailRequired},
  shippingAddressRequired : ${shippingAddressRequired},
  shippingAddressParameters: {
${allowedCountryCode}
    phoneNumberRequired: ${shippingPhoneNumberRequired}
  }
};

const allowedCardNetworks = ${allowedCardNetworks};
const allowedCardAuthMethods = ${allowedCardAuthMethods};

const tokenizationSpecification = {
  type: '${type}',
  parameters: {
    'gateway': '${gateway}',
    'gatewayMerchantId': '${gatewayMerchantId}'
  }
};

const baseCardPaymentMethod = {
  type: '${paymentMethodType}',
  parameters: {
    allowedAuthMethods: allowedCardAuthMethods,
    allowedCardNetworks: allowedCardNetworks,
    billingAddressRequired: ${billingAddressRequired},
    billingAddressParameters: {
        format: '${billingAddressFormat}',
        phoneNumberRequired: ${billingPhoneNumberRequired}
    }
  }
};

const cardPaymentMethod = Object.assign(
  {},
  baseCardPaymentMethod,
  {
    tokenizationSpecification: tokenizationSpecification
  }
);

let paymentsClient = null;

function getGoogleIsReadyToPayRequest() {
  return Object.assign(
      {},
      baseRequest,
      {
        allowedPaymentMethods: [baseCardPaymentMethod]
      }
  );
}

function getGooglePaymentDataRequest() {
  const paymentDataRequest = Object.assign({}, baseRequest);
  paymentDataRequest.allowedPaymentMethods = [cardPaymentMethod];
  paymentDataRequest.transactionInfo = getGoogleTransactionInfo();
  paymentDataRequest.merchantInfo = {
     merchantId: '${merchantId}',
    merchantName: '${merchantName}'
  };
  return paymentDataRequest;
}

function getGooglePaymentsClient() {
  if ( paymentsClient === null ) {
    paymentsClient = new google.payments.api.PaymentsClient({environment: '${environement}'});
  }
  return paymentsClient;
}

function onGooglePayLoaded() {
  const paymentsClient = getGooglePaymentsClient();
  paymentsClient.isReadyToPay(getGoogleIsReadyToPayRequest())
      .then(function(response) {
        if (response.result) {
          addGooglePayButton();
           prefetchGooglePaymentData();
        }
      })
      .catch(function(err) {
        // show error in developer console for debugging
        console.error(err);
      });
}

function addGooglePayButton() {
  const paymentsClient = getGooglePaymentsClient();
  const button =
      paymentsClient.createButton(
      {
        buttonColor: "${buttonColor}",
        buttonType: "${buttonType}",
        onClick: onGooglePaymentButtonClicked
      });
  document.getElementById('${container}').appendChild(button);
}

function getGoogleTransactionInfo() {
  return {
    currencyCode: '${currency}',
    totalPriceStatus: 'FINAL',
    // set to cart total
    totalPrice: '${totalPrice}'
  };
}

function prefetchGooglePaymentData() {
  const paymentDataRequest = getGooglePaymentDataRequest();
  // transactionInfo must be set but does not affect cache
  paymentDataRequest.transactionInfo = {
    totalPriceStatus: 'NOT_CURRENTLY_KNOWN',
    currencyCode: '${currency}'
  };
  const paymentsClient = getGooglePaymentsClient();
  paymentsClient.prefetchPaymentData(paymentDataRequest);
}

function onGooglePaymentButtonClicked() {
  const paymentDataRequest = getGooglePaymentDataRequest();
  paymentDataRequest.transactionInfo = getGoogleTransactionInfo();

  const paymentsClient = getGooglePaymentsClient();
  paymentsClient.loadPaymentData(paymentDataRequest)
      .then(function(paymentData) {
        // handle the response
        processPayment(paymentData);
      })
      .catch(function(err) {
        // show error in developer console for debugging
        console.error(err);
      });
}

function processPayment(paymentData) {
  // show returned data in developer console for debugging
  // pass payment data response to your gateway to process payment
  ${callback}(paymentData);
}