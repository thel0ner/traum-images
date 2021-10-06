package metaroa.traumimages.Paypal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metaroa.traumimages.Config.PaypalCredentials;
import metaroa.traumimages.errorHandling.InternalServerError;

import java.io.BufferedReader;

public class CheckPaypalOrder {
    private final PaypalCredentials paypalCredentials;
    private PaypalPaymentStatus response;
    public PaypalPaymentStatus checkPayment(String paymentId) {
        try {
            BufferedReader br = new BufferedReader(PaypalAssistant.sendData(
                    "https://api-m.paypal.com/v2/v2/payments/authorizations/"+paymentId,
                    paypalCredentials.getAccessToken()
            ));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();

            switch(jsonObject.get("status").getAsString()){
                case "CREATED":
                    this.response = PaypalPaymentStatus.CREATED;
                    break;
                case "CAPTURED":
                    this.response = PaypalPaymentStatus.CAPTURED;
                    break;
                case "DENIED":
                    this.response = PaypalPaymentStatus.DENIED;
                    break;
                case "EXPIRED":
                    this.response = PaypalPaymentStatus.EXPIRED;
                    break;
                case "PARTIALLY_CAPTURED":
                    this.response = PaypalPaymentStatus.PARTIALLY_CAPTURED;
                    break;
                case "PARTIALLY_CREATED":
                    this.response = PaypalPaymentStatus.PARTIALLY_CREATED;
                    break;
                case "VOIDED":
                    this.response = PaypalPaymentStatus.VOIDED;
                    break;
                case "PENDING":
                    this.response = PaypalPaymentStatus.PENDING;
                    break;
                default:
                    this.response = PaypalPaymentStatus.Unknown;
                    break;
            }
            return this.response;
        } catch (Exception e) {
            throw new InternalServerError();
        }
    }

    public CheckPaypalOrder(){
        this.paypalCredentials = new PaypalCredentials();
    }
}
