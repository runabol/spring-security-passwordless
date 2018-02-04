package com.creactiviti.spring.security.passwordless.core;

import java.security.Principal;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Arik Cohen
 * @since Jan 30, 2018
 */
public class SpringSecurityAuthenicator implements Authenticator {
  
  private final TokenStore tokenStore;
  
  public SpringSecurityAuthenicator(TokenStore aTokenStore) {
    tokenStore = aTokenStore;
  }

  @Override
  public Principal authenticate (String aUserId, String aToken) {
    String token = tokenStore.get(aUserId);
    if(aToken.equals(token)) {
      Authentication authentication = new UsernamePasswordAuthenticationToken(aUserId, null,AuthorityUtils.createAuthorityList("ROLE_USER"));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return authentication;
    }
    throw new BadCredentialsException("Invalud auth token for user: " + aUserId);
  }

}
