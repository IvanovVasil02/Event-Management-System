package ivanovvasil.u5d5w3Project.security;

import ivanovvasil.u5d5w3Project.exceptions.ExceptionsHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  @Autowired
  JWTAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  ExceptionsHandlerFilter exceptionsHandlerFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.formLogin(AbstractHttpConfigurer::disable);

    httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.addFilterBefore(exceptionsHandlerFilter, JWTAuthenticationFilter.class);

    httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());
    return httpSecurity.build();
  }

  @Bean
  PasswordEncoder encodePassword() {
    return new BCryptPasswordEncoder(11);
  }
}
