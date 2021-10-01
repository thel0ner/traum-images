package metaroa.traumimages.User;

import metaroa.traumimages.Roles.Roles;
import metaroa.traumimages.assistants.Assistant;
import metaroa.traumimages.assistants.Password;
import metaroa.traumimages.dto.*;
import metaroa.traumimages.errorHandling.IncorrectCredentials;
import metaroa.traumimages.errorHandling.InternalServerError;
import metaroa.traumimages.errorHandling.NotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserRepository repository;
    private final String secretKey = "SomeKeyKavehTaheKiwyReyhanehTaher1321584554484&^^THHHHKavehtaherHomayunTaher878786757uy7ytiuyiuytwiuyiuyrgiuuowy_+";

    private String getJWTToken(String username, Roles.RolesList role){
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role.toString());
        String token = Jwts.builder()
                .setId("traum-images")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                        )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes()).compact();
        return token;
    }

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    @PostMapping("/login")
    public UserAuthDTO login(@RequestBody LoginDTO payload){
        String email = payload.getEmail();
        User toCheck = repository.findUserByEmail(email);
        if(toCheck != null){
            Password password = new Password();
            if(password.matches(payload.getPassword(),toCheck.getPassword())){
                String token = getJWTToken(toCheck.getUsername(),toCheck.getRoles());
                 UserAuthDTO userAuthDTO = new UserAuthDTO();
                 userAuthDTO.setUsername(toCheck.getUsername());
                 userAuthDTO.setToken(token);
                 toCheck.setIsLoggedIn(true);
                 repository.save(toCheck);
                 return userAuthDTO;
            }else{
                throw new IncorrectCredentials();
            }
        }else{
            throw new NotFoundException();
        }
    }

    @PostMapping("/logUserOut")
    public SimpleMessageResponse logout(@RequestBody LogOutDTO payload){
        User toLogOut = repository.findUserByEmail(payload.getEmail());
        SimpleMessageResponse message = new SimpleMessageResponse("logged out successfully");
        if(toLogOut != null){
            toLogOut.setIsLoggedIn(false);
            repository.save(toLogOut);
        }
        return message;
    }

    @PostMapping("/assignRole")
    public SimpleMessageResponse assignRole(@RequestBody AssignRoleDTO payload){
        Optional<User> check = repository.findById(payload.getUserId());
        if(check.isPresent()){
            User toEffect = check.get();
            toEffect.setRoles(payload.getRole());
            try{
                repository.save(toEffect);
                SimpleMessageResponse message = new SimpleMessageResponse("role assigned successfully");
                return message;
            }catch (Exception ex){
                throw new InternalServerError();
            }
        }else{
            throw new NotFoundException();
        }
    }
}
