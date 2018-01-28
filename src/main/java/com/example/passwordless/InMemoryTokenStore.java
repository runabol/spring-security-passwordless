package com.example.passwordless;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 
 * @author Arik Cohen
 * @since Jan 28, 2018
 */
@Component
public class InMemoryTokenStore implements TokenStore {
  
  private static final long FIFTEEN_MINS = 15 * 60 * 1000;
  
  private final Map<String, String> store = new SelfExpiringHashMap<>(FIFTEEN_MINS);
  
  @Override
  public String generate (String aUserId) {
    Assert.notNull(aUserId,"user id can't be null");
    String token = UUID.randomUUID().toString().replace("-", "");
    store.put(aUserId, token);
    return token;
  }

  @Override
  public boolean validate(String aUserId, String aToken) {
    Assert.notNull(aUserId,"user id can't be null");
    Assert.notNull(aToken,"token can't be null");
    String token = store.get(aUserId);
    return token != null && token.equals(aToken);
  }

}
