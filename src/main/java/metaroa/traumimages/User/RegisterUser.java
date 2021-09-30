package metaroa.traumimages.User;

import metaroa.traumimages.Roles.Roles;
import metaroa.traumimages.assistants.Password;
import metaroa.traumimages.unConfirmedUser.UnConfirmedUser;

import java.util.Date;

public class RegisterUser {
    private final UserRepository repository;
    public RegisterUser(UserRepository userRepository){
        this.repository = userRepository;
    }

    public boolean registerUser(UnConfirmedUser payload, String password){
        Password passwordManager = new Password();
        User user = new User();
        user.setEmail(payload.getEmail());
        user.setUsername(payload.getUsername());
        user.setPassword(passwordManager.encode(password));
        user.setRegisterDate(new Date());
        user.setModificationDate(new Date());
        user.setPlan(null);
        user.setRoles(Roles.RolesList.OrdinaryUser);
        try{
            repository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
