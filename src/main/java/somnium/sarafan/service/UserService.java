package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import somnium.sarafan.config.jwt.TokenProvider;
import somnium.sarafan.domain.ShoppingCart;
import somnium.sarafan.domain.User;
import somnium.sarafan.exceptions.NotFoundException;
import somnium.sarafan.repository.UserRepository;
import somnium.sarafan.utils.CodeConfig;
import somnium.sarafan.utils.MailSender;

import java.util.Calendar;
import java.util.Date;
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
        ShoppingCart shoppingCart = new ShoppingCart();
        Date date = new Date();
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setIsAdmin(false);
        user.setResetPassword(true);
        user.setPasswordResetDate(date);
        user.setShoppingCart(shoppingCart);
        shoppingCart.setUser(user);
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to MyShop. Please, visit next link for activate your account: http://localhost:8080/api/users/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return  userRepo.save(user);
    }

    public User forgetPassword(User user){
        CodeConfig codeConfig = CodeConfig.length(10);
        String newPassword = CodeConfig.generate(codeConfig);
        if (!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Your new password: %s",
                    newPassword
            );
            mailSender.send(user.getEmail(), "New password", message);
            user.setPassword(newPassword);
        }
        return userRepo.save(user);
    }

    public User changePassword(String username, String oldPassword, String newPassword, String confirmPassword){
        User user = userRepo.findByUsername(username);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,7);
        Date resetDate = c.getTime();
        if (user.getResetPassword()){
            if (user.getPassword().equals(oldPassword)){
                if ((!StringUtils.isEmpty(newPassword)) && (newPassword.equals(confirmPassword))){
                    user.setPassword(newPassword);
                    user.setPasswordResetDate(resetDate);
                    user.setResetPassword(false);
                    userRepo.save(user);
                }
            }
        }
        return user;
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
