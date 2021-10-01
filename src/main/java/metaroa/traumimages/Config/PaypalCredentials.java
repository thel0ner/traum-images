package metaroa.traumimages.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalCredentials {
    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String secret = "CLIENT-SECRET";

    @Value("${paypal.client.return_url}")
    private String returnURL;

    @Value("${paypal.client.cancel_url}")
    private String cancelURL;

    @Value("$(paypal.client.currency)")
    private String currency;

    public String getSecret() {
        return secret;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCancelURL() {
        return cancelURL;
    }

    public String getClientId() {
        return clientId;
    }
}