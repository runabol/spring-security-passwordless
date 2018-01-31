package com.creactiviti.spring.security.passwordless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.creactiviti.spring.security.passwordless.store.InMemoryTokenStore;

@SpringBootApplication
public class PasswordlessApplication {

  public static void main(String[] args) {
    SpringApplication.run(PasswordlessApplication.class, args);
  }
  
  @Bean
  InMemoryTokenStore tokenStore () {
    return new InMemoryTokenStore();
  }

}
