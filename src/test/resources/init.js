const baseRequest = {
  apiVersion: 2,
  apiVersionMinor: 0,
  emailRequired: true,
  shippingAddressRequired : true,
  shippingAddressParameters: {

    phoneNumberRequired: true
  }
};

const allowedCardNetworks = ["MASTERCARD", "AMEX", "VISA"];
const allowedCardAuthMethods = ["PAN_ONLY", "CRYPTOGRAM_3DS"];

const tokenizationSpecification = {
  type: 'PAYMENT_GATEWAY',
  parameters: {
    'gateway': 'monext',
    'gatewayMerchantId': 'gatewayMerchantId'
  }
};

const baseCardPaymentMethod = {
  type: 'CARD',
  parameters: {
    allowedAuthMethods: allowedCardAuthMethods,
    allowedCardNetworks: allowedCardNetworks,
    billingAddressRequired: true,
    billingAddressParameters: {
        format: 'FULL',
        phoneNumberRequired: true
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
     merchantId: 'gatewayMerchantId',
    merchantName: 'monext'
  };
  return paymentDataRequest;
}

function getGooglePaymentsClient() {
  if ( paymentsClient === null ) {
    paymentsClient = new google.payments.api.PaymentsClient({environment: 'TEST'});
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
        } else {
            console.warn("No compatible card for Google Pay available");
            addNoCompatibleCardMessage();
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
        buttonColor: "default",
        buttonType: "long",
        onClick: onGooglePaymentButtonClicked
      });
  document.getElementById('paylineGooglePayBtnDivContainer').appendChild(button);
}

function addNoCompatibleCardMessage() {
    const div = document.createElement("div").appendChild(document.createTextNode("Google Pay 3D Secure ne peut être utilisé car la carte stockée dans le wallet n'est pas compatible ou le paiement n'est pas effectué depuis un mobile"));
    document.getElementById('paylineGooglePayBtnDivContainer').appendChild(div);
}

function getGoogleTransactionInfo() {
  return {
    currencyCode: 'EUR',
    totalPriceStatus: 'FINAL',
    // set to cart total
    totalPrice: '1.00'
  };
}

function prefetchGooglePaymentData() {
  const paymentDataRequest = getGooglePaymentDataRequest();
  // transactionInfo must be set but does not affect cache
  paymentDataRequest.transactionInfo = {
    totalPriceStatus: 'NOT_CURRENTLY_KNOWN',
    currencyCode: 'EUR'
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
  paylineProcessPaymentCallback(paymentData);
}