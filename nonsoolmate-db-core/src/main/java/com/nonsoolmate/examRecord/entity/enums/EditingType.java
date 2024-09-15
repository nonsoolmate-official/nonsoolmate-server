package com.nonsoolmate.examRecord.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EditingType {
	EDITING("첨삭"),
	REVISION("재첨삭");

	private final String type;
}
