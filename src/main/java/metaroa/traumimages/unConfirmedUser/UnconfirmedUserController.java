package metaroa.traumimages.unConfirmedUser;

import metaroa.traumimages.Config.WebsiteInfo;
import metaroa.traumimages.Mailer.MailingMechanism;
import metaroa.traumimages.User.RegisterUser;
import metaroa.traumimages.User.UserRepository;
import metaroa.traumimages.assistants.Assistant;
import metaroa.traumimages.dto.SimpleMessageResponse;
import metaroa.traumimages.dto.UnconfirmedUserDTO;
import metaroa.traumimages.dto.UserConfirmDTO;
import metaroa.traumimages.errorHandling.*;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;

@RestController
public class UnconfirmedUserController {
    private final UnconfirmedUserRepository repository;
    private final UserRepository userRepository;
    private boolean checkInputs(UnconfirmedUserDTO payload){
        return payload.getEmail() != null && payload.getUsername() != null;
    }
    public UnconfirmedUserController(UnconfirmedUserRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    SimpleMessageResponse newUnconfirmedUser(@RequestBody UnconfirmedUserDTO unconfirmedUser){
        UnConfirmedUser checkUsername = repository.findUnconfirmedUserByUsername(unconfirmedUser.getUsername());
        UnConfirmedUser checkEmail = repository.findUnconfirmedUserByEmail(unconfirmedUser.getEmail());
        if(checkEmail != null || checkUsername != null){
            throw new EntityAlreadyExists();
        }
        boolean isEmailValid = EmailValidator.getInstance().isValid(unconfirmedUser.getEmail());
        UnConfirmedUser newUser = new UnConfirmedUser();
        newUser.setUsername(unconfirmedUser.getUsername());
        if(checkInputs(unconfirmedUser)){
            if(isEmailValid) {
                newUser.setEmail(unconfirmedUser.getEmail());
                newUser.setConfirmCode(Assistant.generateConfirmCode());
                newUser.setSubmissionDate(Assistant.getCurrentDateAndTime());
                try{
                    WebsiteInfo websiteInfo = new WebsiteInfo();
                    String content =  MailingMechanism.generateMessageBodyForEmailConfirm(
                            newUser.getConfirmCode(),
                            newUser.getUsername(),
                            newUser.getEmail(),
                            websiteInfo.getDomain(),
                            websiteInfo.getTitle()
                    );
                    MailingMechanism.sendMai(
                            newUser.getEmail(),
                            "Please confirm your email",
                            content,
                            content
                    );
                }catch (Exception e){
                    throw new InternalServerError();
                }finally {
                    repository.save(newUser);
                    SimpleMessageResponse message = new SimpleMessageResponse("please check your email box");
                    return message;
                }

            }else{
                throw new InValidEmailAddressException();
            }
        }else{
            throw new IncompletePayloadException();
        }
    }

    @PostMapping("/confirm")
    SimpleMessageResponse confirmUser(@RequestBody UserConfirmDTO payload) {
        UnConfirmedUser toCheck = repository.findByConfirmCode(payload.getConfirmCode());
        if (toCheck == null) {
            throw new NotFoundException();
        } else {
            if (payload.getPassword().equals(payload.getConfirmPassword())) {
                RegisterUser registerUser = new RegisterUser(this.userRepository);
                boolean check = registerUser.registerUser(toCheck,payload.getPassword());
                if(check == true){
                    UnConfirmedUser toDelete = repository.findByConfirmCode(payload.getConfirmCode());
                    System.out.println(toDelete);
                    repository.delete(toDelete);
                    SimpleMessageResponse response = new SimpleMessageResponse("User confirmed successfully");
                    return response;
                }else{
                    throw new InternalServerError();
                }
            } else {
                throw new PasswordMisMatchError();
            }
        }
    }
}
