package somnium.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.Role;
import somnium.sarafan.domain.User;
import somnium.sarafan.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());



        return  userRepo.save(user);
    }



}
