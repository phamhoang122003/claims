package com.spring.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {
    private static final String[] PERMIT_ALL_LINK = {
            "/login",
            "/css/**",
            "/js/**",
            "/rest/statistics/**"
    };

    private static final String[] ADMIN_PERMIT_LINK = {
            "/project/**",
            "/staff/**",
            "/rest/statistics/**"
    };



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PERMIT_ALL_LINK).permitAll()
                        .requestMatchers(ADMIN_PERMIT_LINK).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(
                        loginForm -> loginForm.loginPage("/login")
                                .loginProcessingUrl("/loginCheck")
                                .failureUrl("/login?error=true")
                                .usernameParameter("email")
                                .passwordParameter("password")
                )
                .logout(
                        logout -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                );
        return http.build();
    }

    @Autowired
    UserDetailsService userDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

}
