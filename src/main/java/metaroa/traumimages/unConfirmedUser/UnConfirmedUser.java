package metaroa.traumimages.unConfirmedUser;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name="unconfirmedusers")
public class UnConfirmedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="unconfirmeduderid")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private String confirmCode;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(nullable = false)
    private String submissionDate;

    public Long getId() {
        return id;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
