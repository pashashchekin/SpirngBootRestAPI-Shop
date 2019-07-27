package somnium.sarafan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import somnium.sarafan.domain.User;
import somnium.sarafan.service.UserService;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.getByUserName(username);
        BCryptPasswordEncoder encoder = passwordEncoder();
        if (user.getIsAdmin()){
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),encoder.encode(user.getPassword()), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        }
        return  new org.springframework.security.core.userdetails.User(
                user.getUsername(),encoder.encode(user.getPassword()), AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
