package com.nonsoolmate.global.dto;

import com.nonsoolmate.tag.entity.Tag;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TagDTO", description = "태그 DTO")
public record TagDTO(
    @Schema(description = "태그 id", example = "1") Long tagId,
    @Schema(description = "태그 이름", example = "총 경력 8년") String tagName,
    @Schema(description = "태그 이미지 url", example = "[url 형식]") String tagImageUrl) {

  public static TagDTO of(Tag tag) {
    return new TagDTO(tag.getTagId(), tag.getTagName(), tag.getTagImageUrl());
  }
}
