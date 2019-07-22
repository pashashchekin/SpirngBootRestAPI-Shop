package somnium.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import somnium.sarafan.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByActivationCode(String code);
}
