package com.nonsoolmate.tag.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;

	@NotNull private String tagName;

	private String tagImageUrl;

	/**
	 * @implNote : 모든 테그 데이터가 해당 테이블에서 관리된다. 특정 도메인의 태그 데이터는 해당 컬럼이 null 이 아닌 row 를 조회한다.
	 */
	private Long teacherId;
}
