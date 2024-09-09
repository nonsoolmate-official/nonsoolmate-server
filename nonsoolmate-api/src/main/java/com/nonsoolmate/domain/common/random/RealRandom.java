package com.nonsoolmate.domain.common.random;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev", "prod"})
public class RealRandom implements CustomRandom {
	@Override
	public String generateRandomValue() {
		return UUID.randomUUID().toString().substring(0, 13);
	}
}
