package metaroa.traumimages.User;

import lombok.Data;
import lombok.NoArgsConstructor;
import metaroa.traumimages.Plan;
import metaroa.traumimages.Roles.Roles;
import metaroa.traumimages.Website;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="website_id")
    private Website website;

    @OneToOne(mappedBy = "user")
    private Plan plan;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date registerDate;

    @Column(nullable = false)
    private Date modificationDate;

    @Column(nullable = true)
    private Roles.RolesList roles = Roles.RolesList.OrdinaryUser;

    @Column(nullable = true)
    private boolean isLoggedIn;

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setIsLoggedIn(boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }
}
