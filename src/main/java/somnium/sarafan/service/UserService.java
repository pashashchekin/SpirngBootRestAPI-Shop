package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import somnium.sarafan.config.jwt.TokenProvider;
import somnium.sarafan.domain.User;
import somnium.sarafan.enums.Role;
import somnium.sarafan.exceptions.NotFoundException;
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

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        user.setActive(false);
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

    public User findById(Long id){
        return userRepo.findById(id).orElseThrow(() -> NotFoundException.forId(id));
    }

    public User getByUserName(String username){
        return userRepo.findAll().stream().filter(u ->
                u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public String authenticate(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return tokenProvider.createToken(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



}
