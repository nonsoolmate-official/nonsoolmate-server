package com.nonsoolmate.examRecord.entity.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO : 데모데이 이후 삭제 필요.
@RequiredArgsConstructor
@Getter
public enum CorrectionFileName {

	KON_KUK("건국대학교", "KonKuk_correction.pdf"),
	KYUNG_HEE("경희대학교", "KyungHee_correction.pdf"),
	CHUNG_ANG("중앙대학교", "ChungAng_correction.pdf"),
	SUNG_KYUN_KWAN("성균관대학교", "Sungkyunkwan_correction.pdf");

	private final String universityName;
	private final String correctionFileName;

	public static String getCorrectionFileName(String universityName){
		return Arrays.stream(CorrectionFileName.values())
			.filter(correction -> correction.universityName.equals(universityName))
			.map(CorrectionFileName::getCorrectionFileName)
			.findAny()
			.orElse(null);
	}

}
