package somnium.sarafan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import somnium.sarafan.config.jwt.TokenFilterConfigure;
import somnium.sarafan.config.jwt.TokenProvider;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF (cross site request forgery)
        httpSecurity.cors().and().csrf().disable();

        // No session will be created or used by spring security
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        httpSecurity.authorizeRequests()//
                .antMatchers("/api/auth").permitAll()//
                .antMatchers("/h2-console/**/**").permitAll()

                .anyRequest().authenticated();

        httpSecurity.apply(new TokenFilterConfigure(tokenProvider));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
