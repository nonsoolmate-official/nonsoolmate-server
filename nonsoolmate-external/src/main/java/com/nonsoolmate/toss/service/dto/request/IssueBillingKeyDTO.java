package com.nonsoolmate.toss.service.dto.request;

public record IssueBillingKeyDTO(String authKey, String customerKey) {
	public static IssueBillingKeyDTO of(final String authKey, final String customerKey) {
		return new IssueBillingKeyDTO(authKey, customerKey);
	}
}
