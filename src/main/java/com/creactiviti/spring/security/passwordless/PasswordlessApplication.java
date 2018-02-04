package com.creactiviti.spring.security.passwordless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

import com.creactiviti.spring.security.passwordless.core.Authenticator;
import com.creactiviti.spring.security.passwordless.core.EmailSender;
import com.creactiviti.spring.security.passwordless.core.InMemoryTokenStore;
import com.creactiviti.spring.security.passwordless.core.Sender;
import com.creactiviti.spring.security.passwordless.core.SpringSecurityAuthenicator;
import com.creactiviti.spring.security.passwordless.core.TokenStore;

@SpringBootApplication
public class PasswordlessApplication {

  public static void main(String[] args) {
    SpringApplication.run(PasswordlessApplication.class, args);
  }
  
  @Bean
  TokenStore tokenStore () {
    return new InMemoryTokenStore();
  }
  
  @Bean
  Sender sender (JavaMailSender aJavaMailSender) {
    return new EmailSender(aJavaMailSender);
  }
  
  @Bean
  Authenticator authenticator () {
    return new SpringSecurityAuthenicator(tokenStore());
  }

}
