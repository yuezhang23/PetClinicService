package com.example.WeePetClinic;


import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
        import org.springframework.web.servlet.config.annotation.CorsRegistry;
        import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow CORS for all paths
                .allowedOrigins("http://localhost:3000")  // Allow the origin of your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these methods
                .allowedHeaders("*")
                .allowCredentials(true);
      }
    };
  }
}
