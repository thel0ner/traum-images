package metaroa.traumimages.Paypal;



import metaroa.traumimages.Config.PaypalCredentials;
import metaroa.traumimages.errorHandling.InternalServerError;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CreatePaypalOrder {
    public static String createOrder(Long amount) throws IOException {
        PaypalCredentials paypalCredentials = new PaypalCredentials();
        URL url = new URL("https://api.sandbox.paypal.com/v1/payments/payment");
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization","Bearer "+ paypalCredentials.getSecret());
        connection.setRequestProperty("Content-Type", "application/json");
        String jsonData = "{ \"intent\":\"sale\",\"redirect_urls\":{\"return_url\":\""
                +paypalCredentials.getReturnURL()+
                "\",\"cancel_url\":\""
                +paypalCredentials.getCancelURL()+
                "\"},\"payer\":{\"payment_method\":\"paypal\"},\"transactions\":[{\"amount\":{\"total\":\""
                +amount.toString()+
                "\",\"currency\":\""
                +paypalCredentials.getCurrency()+
                "\"}}]}";
        try{
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(jsonData);
            output.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            String response = "";
            while((line = reader.readLine()) != null){
                response = response + line;
            }
            reader.close();
            return response;
        }catch(Exception ex){
            throw new InternalServerError();
        }
    }
}
