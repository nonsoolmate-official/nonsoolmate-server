package com.nonsoolmate.common.random;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MockRandom implements CustomRandom {

  @Override
  public String generateRandomValue() {
    return "test-random-value";
  }
}
