package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import somnium.sarafan.domain.Role;
import somnium.sarafan.domain.User;
import somnium.sarafan.repository.UserRepository;
import somnium.sarafan.utils.MailSender;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MailSender mailSender;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to MyShop. Please, visit next link for activate your account: http://localhost:8080/account/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return  userRepo.save(user);
    }

    public User activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        user.setActivationCode(null);
        user.setActive(true);
        return userRepo.save(user);
    }


}
