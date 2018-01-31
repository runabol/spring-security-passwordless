package com.creactiviti.spring.security.passwordless.core;

import java.security.Principal;

/**
 * Authenticates a User ID / Token combination.
 * 
 * @author Arik Cohen
 * @since Jan 30, 2018
 */
public interface Authenticator {

  Principal authenticate (String aUserId, String aToken);
  
}
