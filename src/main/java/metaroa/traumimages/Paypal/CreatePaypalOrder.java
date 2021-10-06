package metaroa.traumimages.Paypal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metaroa.traumimages.Config.PaypalCredentials;
import metaroa.traumimages.dto.PaypalResponseDTO;
import metaroa.traumimages.errorHandling.InternalServerError;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class CreatePaypalOrder {
    private String baseURL = "https://api-m.paypal.com/v2/";
    private String redirectURL = "https://www.paypal.com/checkoutnow?token=";
    private final PaypalCredentials paypalCredentials;
    public PaypalResponseDTO createOrder(Long amount) throws IOException,MalformedURLException {
        try{
            PaypalResponseDTO paypalResponseDTO = new PaypalResponseDTO();
            BufferedReader br = new BufferedReader(PaypalAssistant.sendData(
                    baseURL+"checkout/orders",
                    this.paypalCredentials.getAccessToken(),
                    PaypalAssistant.preparePayload(
                            this.paypalCredentials.getCurrency(),amount.toString())
            ));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            paypalResponseDTO.setId(jsonObject.get("id").getAsString());
            paypalResponseDTO.setAmount(amount);
            paypalResponseDTO.setStatus(PaypalPaymentStatus.CREATED);
            paypalResponseDTO.setCurrencyCode(this.paypalCredentials.getCurrency());
            return paypalResponseDTO;
        }catch (Exception ex){
            throw new InternalServerError();
        }
    }

    public CreatePaypalOrder(){
        this.paypalCredentials = new PaypalCredentials();
    }
}
