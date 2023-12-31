package com.example.TMS.security.jwt;

import com.example.TMS.repository.UsersRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfig {

    UsersRepository userRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("пользователь не найден"));
    }

    /**
     * конфигурация для аутентификации пользователей
     * используется DaoAuthenticationProvider, который использует UserDetailsService для загрузки информации о пользователе
     * PasswordEncoder для проверки пароля
     * Отвечает за проверку подлинности пользователей
     *
     * @return autProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider();
        autProvider.setUserDetailsService(userDetailsService());
        autProvider.setPasswordEncoder(passwordEncoder());
        return autProvider;
    }

    /**
     * управляет проуессом аутентификации
     *
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
