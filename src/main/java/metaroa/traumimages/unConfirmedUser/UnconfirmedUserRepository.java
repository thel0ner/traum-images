package metaroa.traumimages.unConfirmedUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnconfirmedUserRepository extends JpaRepository<UnConfirmedUser,Long> {
    public UnConfirmedUser findUnconfirmedUserByEmailAndUsername(String email,String username);
    public UnConfirmedUser findByConfirmCode(String confirmCode);
    public UnConfirmedUser findUnconfirmedUserByEmail(String email);
    public UnConfirmedUser findUnconfirmedUserByUsername(String username);
}
