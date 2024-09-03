package com.nonsoolmate.nonsoolmateServer.domain.payment.controller.dto.response;

public record CustomerInfoDTO(String customerKey, String customerName, String customerEmail) {
	public static CustomerInfoDTO of(final String customerKey, final String customerName,
		final String customerEmail) {
		return new CustomerInfoDTO(customerKey, customerName, customerEmail);
	}
}
