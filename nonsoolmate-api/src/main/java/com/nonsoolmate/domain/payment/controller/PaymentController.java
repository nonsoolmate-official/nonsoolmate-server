package com.nonsoolmate.domain.payment.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonsoolmate.domain.member.service.MemberService;
import com.nonsoolmate.domain.payment.controller.dto.response.CustomerInfoDTO;
import com.nonsoolmate.global.security.AuthMember;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
	@Value("${payment.toss.widget-secret-key}")
	private String widgetSecretKey;
	private final MemberService memberService;

	@RequestMapping(value = "/confirm")
	public ResponseEntity<JsonNode> confirmPayment(@RequestBody String jsonBody) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String orderId;
		String amount;
		String paymentKey;

		try {
			// 클라이언트에서 받은 JSON 요청 바디입니다.
			JsonNode requestData = objectMapper.readTree(jsonBody);
			paymentKey = requestData.get("paymentKey").asText();
			orderId = requestData.get("orderId").asText();
			amount = requestData.get("amount").asText();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		JsonNode obj = objectMapper.createObjectNode()
			.put("orderId", orderId)
			.put("amount", amount)
			.put("paymentKey", paymentKey);

		// 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
		// 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.

		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
		String authorizations = "Basic " + new String(encodedBytes);

		// 결제를 승인하면 결제수단에서 금액이 차감돼요.
		URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("Authorization", authorizations);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream()) {
			outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
		}

		int code = connection.getResponseCode();
		boolean isSuccess = code == 200;

		InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
		JsonNode jsonObject;

		try (Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8)) {
			jsonObject = objectMapper.readTree(reader);
		}

		return ResponseEntity.status(code).body(jsonObject);
	}

	@GetMapping("/customer/info")
	public ResponseEntity<CustomerInfoDTO> getCustomerInfo(@AuthMember String memberId) {
		return ResponseEntity.ok().body(memberService.getCustomerInfo(memberId));
	}
}
