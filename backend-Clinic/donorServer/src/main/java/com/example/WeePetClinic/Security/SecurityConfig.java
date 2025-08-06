//package com.example.WeePetClinic.Security;
//
//import com.example.WeePetClinic.Components.Service.ServiceUserDefaultLogin;
//import com.example.WeePetClinic.Components.Service.ServiceUserLogin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.SecurityFilterChain;
//
//import lombok.AllArgsConstructor;
//@Configuration
//@AllArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//
//  @Autowired
//  private final ServiceUserDefaultLogin logInService;
//
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return logInService;
//  }
//
//  @Bean
//  public AuthenticationProvider authenticationProvider() {
//    // one
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setUserDetailsService(logInService);
//    return provider;
//  }
//
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//      return httpSecurity
//              .csrf(AbstractHttpConfigurer::disable)
//              .formLogin(
//              httpForm -> {
//                httpForm.loginPage("/login").permitAll();
//              })
//              .authorizeHttpRequests(
//                      registry -> {
//                        registry.requestMatchers("/req/signup", "/js/**", "/css/**").permitAll();
//                        registry.anyRequest().authenticated();
//                      })
//              .build();
//  }
//}
