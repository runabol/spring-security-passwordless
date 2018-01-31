package com.creactiviti.spring.security.passwordless.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creactiviti.spring.security.passwordless.store.TokenStore;

@Controller
public class SignupController {
  
  private final JavaMailSender mailSender;
  
  private final TokenStore tokenStore;
  
  @Value("${passwordless.email.from}")
  private String from;
  
  public SignupController (JavaMailSender aMailSender, TokenStore aTokenStore){
    mailSender = aMailSender;
    tokenStore = aTokenStore;
  }

  @GetMapping("/signup")
  public String signup () {
    return "signup";
  }
    
  @PostMapping("/signup")
  public String signup (@RequestParam("email") String aEmail) {
    
    // add new user to the database
    // ...
    
    // send sign-in email
    String token = tokenStore.generate(aEmail);
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(from);
    mailMessage.setTo(aEmail);
    mailMessage.setSubject("Your login link");
    mailMessage.setText(String.format("Hello!\nAccess your account here: http://localhost:8080/signin/%s?uid=%s",token,aEmail));
    mailSender.send(mailMessage);
    
    return "login_link_sent";
  }
  
	
}
