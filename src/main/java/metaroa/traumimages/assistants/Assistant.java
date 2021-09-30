package metaroa.traumimages.assistants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Assistant {
    public static String getCurrentDateAndTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String generateConfirmCode(){
        return RandomString.generateString();
    }

    public static String oneWayPasswordEncryption(String password){
        Password passwordMaker = new Password();
        return passwordMaker.encode(password);
    }
}
