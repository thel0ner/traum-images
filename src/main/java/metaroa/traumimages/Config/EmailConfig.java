package metaroa.traumimages.Config;

public class EmailConfig {
    private final String smtpAuth = "true";
    private final String startTlsEnabled = "true";
    private final String host = "smtp.gmail.com";
    private final String port = "587";
    private final String email = "kavehtahernam@gmail.com";
    private final String password = "samplePassword";

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public String getStartTlsEnabled() {
        return startTlsEnabled;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
