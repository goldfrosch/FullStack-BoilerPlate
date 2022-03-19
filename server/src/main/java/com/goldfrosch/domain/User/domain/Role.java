package com.goldfrosch.domain.User.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN");

  private final String role;
}
