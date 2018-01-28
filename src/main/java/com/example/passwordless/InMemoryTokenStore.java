package com.example.passwordless;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenStore implements TokenStore {
  
  private static final long FIFTEEN_MINS = 15 * 60 * 1000;
  
  private final Map<String, String> store = new SelfExpiringHashMap<>(FIFTEEN_MINS);
  
  @Override
  public String generate (String aUserId) {
    String token = UUID.randomUUID().toString().replace("-", "");
    store.put(aUserId, token);
    return token;
  }

  @Override
  public boolean validate(String aUserId, String aToken) {
    String token = store.get(aUserId);
    return token != null && token.equals(aToken);
  }

}
