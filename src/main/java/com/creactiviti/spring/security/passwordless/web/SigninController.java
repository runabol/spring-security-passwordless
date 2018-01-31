package com.creactiviti.spring.security.passwordless.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.creactiviti.spring.security.passwordless.core.Sender;
import com.creactiviti.spring.security.passwordless.core.TokenStore;

@Controller
public class SigninController {
  
  private final TokenStore tokenStore;
  
  private final Sender sender;
  
  public SigninController (TokenStore aTokenStore, Sender aSender){
    tokenStore = aTokenStore;
    sender = aSender;
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
    String token = tokenStore.create(aEmail);
    sender.send(aEmail, token);
    
    return "login_link_sent";
  }
  
  @GetMapping("/signin/{token}")
  public String signin (@RequestParam("uid") String aUid, @PathVariable("token") String aToken) {
    String token = tokenStore.get(aUid);
    if(aToken.equals(token)) {
      Authentication authentication = new UsernamePasswordAuthenticationToken(aUid, null,AuthorityUtils.createAuthorityList("ROLE_USER"));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return "redirect:/";
    }
    else {
      return "invalid_login_link";
    }
  }
  	
}
