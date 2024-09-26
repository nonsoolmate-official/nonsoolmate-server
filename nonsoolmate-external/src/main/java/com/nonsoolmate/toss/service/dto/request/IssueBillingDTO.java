package com.nonsoolmate.toss.service.dto.request;

public record IssueBillingDTO(String authKey, String customerKey) {
	public static IssueBillingDTO of(final String authKey, final String customerKey) {
		return new IssueBillingDTO(authKey, customerKey);
	}
}
