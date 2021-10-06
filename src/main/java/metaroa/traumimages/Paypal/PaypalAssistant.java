package metaroa.traumimages.Paypal;

import metaroa.traumimages.errorHandling.InternalServerError;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class PaypalAssistant {
    public static String preparePayload(String currency_code,String amount){
        return "{\"intent\": \"CAPTURE\",\"purchase_units\": [\n" +
                "    {\n" +
                "      \"amount\": {\n" +
                "        \"currency_code\": \""+currency_code+"\",\n" +
                "        \"value\": \""+amount+"\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]}";
    }

    public static InputStreamReader sendData(String target,String auth,String payload){
        try{
            URL url = new URL(target);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Authorization", "Basic "+auth);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes(payload);
            writer.flush();
            writer.close();
            return new InputStreamReader(conn.getInputStream(), "utf-8");
        }catch(Exception ex){
            throw new InternalServerError();
        }
    }

    public static InputStreamReader sendData(String target,String auth){
        try{
            URL url = new URL(target);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Authorization", "Basic "+auth);
            conn.setRequestMethod("GET");
            return new InputStreamReader(conn.getInputStream(), "utf-8");
        }catch (Exception ex){
            throw new InternalServerError();
        }
    }
}
