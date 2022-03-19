package com.goldfrosch.domain.User.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false, unique = true)
  private String email;

  @Column(length = 300)
  private String password;

  @Column(length = 100, unique = true)
  private String nickname;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AuthType authType;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;
}
