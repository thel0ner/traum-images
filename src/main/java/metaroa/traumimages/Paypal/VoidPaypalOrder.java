package metaroa.traumimages.Paypal;

import metaroa.traumimages.Config.PaypalCredentials;
import metaroa.traumimages.errorHandling.InternalServerError;

public class VoidPaypalOrder {
    private final PaypalCredentials paypalCredentials;

    public void Void(String paymentId){
        try{
            PaypalAssistant.sendData(
                    "https://api-m.paypal.com//v2/payments/authorizations/"+paymentId+"/void/",
                    paypalCredentials.getAccessToken()
            );
        }catch (Exception ex){
            throw new InternalServerError();
        }
    }

    public VoidPaypalOrder(PaypalCredentials paypalCredentials) {
        this.paypalCredentials = paypalCredentials;
    }
}
