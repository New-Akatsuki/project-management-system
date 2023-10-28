package org.blank.projectmanagementsystem.config;

import lombok.RequiredArgsConstructor;
import org.blank.projectmanagementsystem.repository.UserRepository;
import org.blank.projectmanagementsystem.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.*;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final String MY_KEY = "akatsuki-abcdEFGH1234-5678IJKLmnopqrstuvwxYZ";
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(
                                        "/resources/**",
                                        "/js/**",
                                        "/css/**",
                                        "/images/**",
                                        "/vendor/**",
                                        "/fragments/**",
                                        "/report-file/**",
                                        "/skins/**",
                                        "/static/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        (formLogin) -> formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/process-login")
                                .successHandler((request, response, authentication) -> {
                                    response.sendRedirect("/");
                                })
                                .permitAll()
                )
                .logout(
                        (logout) -> logout
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    response.sendRedirect("/login");
                                })
                                .permitAll()
                )
                .exceptionHandling(
                        (exceptionHandling) -> exceptionHandling
                                .accessDeniedPage("/access-denied"))
                .rememberMe(remember -> remember
                        .rememberMeParameter("remember-me")
                        .rememberMeServices(rememberMeServices())
                        .tokenValiditySeconds(60 * 60 * 24)//1 day
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        if (tokenRepository.getJdbcTemplate() == null) {
            tokenRepository.setJdbcTemplate(new JdbcTemplate(dataSource));
        }
        tokenRepository.getJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS persistent_logins (" +
                "username VARCHAR(64) NOT NULL," +
                "series VARCHAR(64) PRIMARY KEY," +
                "token VARCHAR(64) NOT NULL," +
                "last_used TIMESTAMP NOT NULL" +
                ")");

        return tokenRepository;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
        return new PersistentTokenBasedRememberMeServices(
                MY_KEY, userDetailsService(), persistentTokenRepository()
        );
    }
}

