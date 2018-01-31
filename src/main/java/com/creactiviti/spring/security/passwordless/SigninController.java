package com.creactiviti.spring.security.passwordless;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SigninController {
  
  private final TokenStore tokenStore;
  
  private final JavaMailSender mailSender;
  
  @Value("${passwordless.email.from}")
  private String from;
  
  public SigninController (TokenStore aTokenStore, JavaMailSender aJavaMailSender){
    tokenStore = aTokenStore;
    mailSender = aJavaMailSender;
  }

  @GetMapping("/signin")
  public String signin () {
    return "signin";
  }
  
  @PostMapping("/signin")
  public String signin (@RequestParam("email") String aEmail) {
    
    // verify that the user is in the database.
    // ...
    
    // send sign-in email
    String token = tokenStore.generate(aEmail);
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(from);
    mailMessage.setTo(aEmail);
    mailMessage.setSubject("Your signin link");
    mailMessage.setText(String.format("Hello!\nAccess your account here: http://localhost:8080/signin/%s?uid=%s",token,aEmail));
    mailSender.send(mailMessage);
    
    return "login_link_sent";
  }
  
  @GetMapping("/signin/{token}")
  public String signin (@RequestParam("uid") String aUid, @PathVariable("token") String aToken) {
    boolean valid = tokenStore.validate(aUid, aToken);
    if(valid) {
      Authentication authentication = new UsernamePasswordAuthenticationToken(aUid, null,AuthorityUtils.createAuthorityList("ROLE_USER"));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return "redirect:/";
    }
    else {
      return "invalid_login_link";
    }
  }
  	
}
