package com.nonsoolmate.exception.aws;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AWSExceptionType implements ExceptionType {
  DELETE_FILE_AWS_S3_FAIL(HttpStatus.BAD_REQUEST, "s3에 해당 파일 삭제에 실패했습니다."),
  NOT_FOUND_AWS_PRIVATE_KEY(HttpStatus.NOT_FOUND, "해당 AWS Private Key를 찾을 수 없습니다"),
  NOT_FOUND_SHEET_FILE_AWS_S3(HttpStatus.NOT_FOUND, "해당 답안지 파일 이름을 찾을 수 없습니다"),
  GET_PRESIGNED_URL_AWS_CLOUDFRONT_FAIL(HttpStatus.BAD_REQUEST, "Cloud PreSignedUrl 발급에 실패했습니다"),

  FAIL_ENCODING_FILE_NAME(HttpStatus.BAD_REQUEST, "파일 이름 인코딩에 실패했습니다"),
  AWS_S3_SERVICE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "S3 서비스 에러");

  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus status() {
    return this.status;
  }

  @Override
  public String message() {
    return this.message;
  }
}
