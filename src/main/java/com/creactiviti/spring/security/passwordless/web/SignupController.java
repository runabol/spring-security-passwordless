package com.creactiviti.spring.security.passwordless.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creactiviti.spring.security.passwordless.sender.Sender;
import com.creactiviti.spring.security.passwordless.store.TokenStore;

@Controller
public class SignupController {
  
  private final TokenStore tokenStore;
  
  private final Sender sender;
  
  public SignupController (TokenStore aTokenStore, Sender aSender){
    tokenStore = aTokenStore;
    sender = aSender;
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
    String token = tokenStore.create(aEmail);
    
    sender.send(aEmail, token);
    
    return "login_link_sent";
  }
  
	
}
