package com.example.application.security;

import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorize access to /images/ without authentication
        http.authorizeRequests().antMatchers("/images/**").permitAll();
        // Set default security policy that permits Vaadin internal requests and
        // denies all other
        super.configure(http);
        setLoginView(http, LoginView.class, "/logout");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new CrmInMemoryUserDetailsManager();
    }

    private static class CrmInMemoryUserDetailsManager extends InMemoryUserDetailsManager {

        public CrmInMemoryUserDetailsManager() {
            createUser(new User("mufaddal",
                    "{noop}example",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));
        }
    }
}
