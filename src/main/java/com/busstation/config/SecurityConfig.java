package com.busstation.config;

import com.busstation.model.User;
import com.busstation.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User u = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRole().replace("ROLE_", "")) // ROLE_USER → USER
                    .build();
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider(UserDetailsService uds, PasswordEncoder pe) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(pe);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(reg -> reg
                        // Staticki resursi
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Javne rute
                        .requestMatchers("/login", "/register", "/departures", "/search").permitAll()
                        // Admin panel
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Sve ostalo zahteva login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")             // JSP login stranica
                        .permitAll()
                        .defaultSuccessUrl("/home", true) // Nakon logina ide na /home
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // privremeno isključen CSRF za testiranje

        return http.build();
    }
}
