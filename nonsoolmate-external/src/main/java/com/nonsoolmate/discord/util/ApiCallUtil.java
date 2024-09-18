package com.nonsoolmate.discord.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.nonsoolmate.discord.model.JsonObject;

import reactor.core.publisher.Mono;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiCallUtil {

	public static void callDiscordAppenderPostAPI(String urlString, JsonObject json) {
		WebClient webClient = WebClient.create();
		webClient
				.post()
				.uri(urlString)
				.contentType(MediaType.APPLICATION_JSON)
				.header("User-Agent", "Java-DiscordWebhook-BY-Gelox_")
				.body(Mono.just(json.toString()), String.class)
				.retrieve()
				.bodyToMono(Void.class)
				.block();
	}
}
