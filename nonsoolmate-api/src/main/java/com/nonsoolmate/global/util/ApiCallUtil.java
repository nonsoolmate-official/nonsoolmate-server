package com.nonsoolmate.global.util;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.nonsoolmate.external.discord.model.JsonObject;

import io.jsonwebtoken.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiCallUtil {

	public static void callDiscordAppenderPostAPI(String urlString, JsonObject json) {
		WebClient webClient = WebClient.create();
		try {
			webClient.post()
				.uri(urlString)
				.contentType(MediaType.APPLICATION_JSON)
				.header("User-Agent", "Java-DiscordWebhook-BY-Gelox_")
				.body(Mono.just(json.toString()), String.class)
				.retrieve()
				.bodyToMono(Void.class)
				.block();
		} catch (IOException e) {
			log.error("Discord Appender API 호출 실패", e.getMessage());
			throw e;
		}
	}
}

