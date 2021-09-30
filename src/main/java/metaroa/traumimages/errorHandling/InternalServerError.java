package metaroa.traumimages.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "something went wrong")

public class InternalServerError extends RuntimeException{
}
