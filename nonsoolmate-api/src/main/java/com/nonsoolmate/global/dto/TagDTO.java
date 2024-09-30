package com.nonsoolmate.global.dto;

import com.nonsoolmate.tag.entity.Tag;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TagDTO", description = "태그 DTO")
public record TagDTO(
		@Schema(name = "태그 id", description = "1") Long tagId,
		@Schema(name = "태그 이름", description = "총 경력 8년") String tagName,
		@Schema(name = "태그 이미지 url", description = "[url 형식]") String tagImageUrl) {

	public static TagDTO of(Tag tag) {
		return new TagDTO(tag.getTagId(), tag.getTagName(), tag.getTagImageUrl());
	}
}
