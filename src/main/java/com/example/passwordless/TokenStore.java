package com.example.passwordless;

public interface TokenStore {

  String generate (String aUserId);
  
  boolean validate (String aUserId, String aToken);
  
}
