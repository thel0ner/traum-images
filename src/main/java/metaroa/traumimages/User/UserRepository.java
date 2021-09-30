package metaroa.traumimages.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findUserByEmail(String email);
//    public User findUserBuUsername(String username);
//    public User findUserByUsernameAndEmail(String username,String email);
}
