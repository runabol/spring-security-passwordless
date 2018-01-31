package com.creactiviti.spring.security.passwordless.core;

import java.security.Principal;

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
    return null;
  }

}
