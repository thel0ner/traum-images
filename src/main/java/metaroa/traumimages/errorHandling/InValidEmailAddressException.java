package metaroa.traumimages.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid email address")
public class InValidEmailAddressException extends RuntimeException{
}
