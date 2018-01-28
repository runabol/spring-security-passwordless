package com.example.passwordless;

/**
 * <p>An interface for generating and validating temporary login token for users. 
 * 
 * <p>Implementations may (and should) expire tokens.
 * 
 * @author Arik Cohen
 * @since Jan 28, 2018
 */
public interface TokenStore {

  /**
   * Generate a token for the given user id.  
   * 
   * @param aUserId The ID for of the user to generate the token for.
   * @return The generated token
   * @throws IllegalArgumentException if the user id is null.
   */
  String generate (String aUserId);
  
  /**
   * Validates that the given user id has the given token 
   * associated with him and that the token is valid (i.e.
   * not expired).
   * 
   * @param aUserId The id of the user to validate the token for.
   * @param aToken The token to validate.
   * @return <code>true</code> if a token was found for the given user id and is valid. 
   * <code>false</code> otherwise.
   */
  boolean validate (String aUserId, String aToken);
  
}
