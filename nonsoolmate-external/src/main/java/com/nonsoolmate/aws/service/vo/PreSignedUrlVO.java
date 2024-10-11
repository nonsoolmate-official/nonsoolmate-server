package com.nonsoolmate.aws.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreSignedUrlVO {
  private String fileName;
  private String url;

  public static PreSignedUrlVO of(final String fileName, final String url) {
    return new PreSignedUrlVO(fileName, url);
  }
}
