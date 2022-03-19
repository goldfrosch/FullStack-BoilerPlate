package com.goldfrosch.domain.User.domain;

import lombok.Getter;

@Getter
public enum AuthType {
  DEFAULT,
  KAKAO,
  GOOGLE,
  NAVER,
}
