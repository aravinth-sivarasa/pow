package io.pow.web.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebConfig {

    String baseURL= "http://localhost:8080";
    @Bean
    UserDetailsPasswordService userDetailsPasswordService(UserDetailsManager detailsManager) {
        return new UserDetailsPasswordService() {
            @Override
            public UserDetails updatePassword(UserDetails user, String newPassword) {
                var updated = User.withUserDetails(user).password(newPassword).build();
                detailsManager.updateUser(updated);
                return updated;
            }

        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .oneTimeTokenLogin(
                        configure -> configure.tokenGenerationSuccessHandler((request, response, oneTimeToken) -> {
                            var msg = "Please click the link below, "+baseURL+"/login/ott?token="
                                    + oneTimeToken.getTokenValue();
                            System.out.println(msg);
                            response.setContentType(org.springframework.http.MediaType.TEXT_HTML_VALUE);
                            response.getWriter().write("Please check the console");

                        }))
                .formLogin(Customizer.withDefaults())
                .webAuthn((webAuthn) -> webAuthn
                        .rpName("Spring Security Relying Party")
                        .rpId("localhost")
                        .allowedOrigins(baseURL))
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/admin")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())

                .build();

    }

    // SecurityFilterChain filterOTTlogin(HttpSecurity http){
    //     return http.build();
    // }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

}
