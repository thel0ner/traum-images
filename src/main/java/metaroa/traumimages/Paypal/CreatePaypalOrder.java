package metaroa.traumimages.Paypal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metaroa.traumimages.Config.PaypalCredentials;
import metaroa.traumimages.dto.PaypalResponseDTO;
import metaroa.traumimages.errorHandling.InternalServerError;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class CreatePaypalOrder {
    private String baseURL = "https://api-m.paypal.com/v2/";
    private String redirectURL = "https://www.paypal.com/checkoutnow?token=";
    private final PaypalCredentials paypalCredentials;
    private String preparePayload(String currency_code,String amount){
        return "{\"intent\": \"CAPTURE\",\"purchase_units\": [\n" +
                "    {\n" +
                "      \"amount\": {\n" +
                "        \"currency_code\": \""+currency_code+"\",\n" +
                "        \"value\": \""+amount+"\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]}";
    }
    public PaypalResponseDTO createOrder(Long amount) throws IOException,MalformedURLException {
        URL url = new URL(baseURL+"checkout/orders");
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestProperty("Authorization", "Basic "+this.paypalCredentials.getAccessToken());
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
        writer.writeBytes(preparePayload(this.paypalCredentials.getCurrency(),amount.toString()));
        writer.flush();
        writer.close();
        PaypalResponseDTO paypalResponseDTO = new PaypalResponseDTO();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        try{
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            paypalResponseDTO.setId(jsonObject.get("id").getAsString());
            paypalResponseDTO.setAmount(amount);
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
