package com.goldfrosch.domain.User.application;

import com.goldfrosch.domain.User.domain.User;
import com.goldfrosch.domain.User.entity.dao.RegisterDAO;
import com.goldfrosch.domain.User.entity.dto.UserDTO;
import com.goldfrosch.domain.User.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다"));
  }

  @Transactional
  public UserDTO getProfile(User user) {
    UserDTO profile = new UserDTO();
    profile.setEmail(user.getEmail());
    profile.setNickName(user.getNickName());

    return profile;
  }

  //Optional 을 줌으로써 NullPointerException 을 굳이 처리 안해도 되는 장점이 있다 칸다. 쩌는듯?
  @Transactional
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional(rollbackFor = Exception.class)
  public void registerUser(RegisterDAO user) {
    userRepository.save(User.builder()
      .email(user.getEmail())
      .password(user.getPassword())
      .nickName(user.getNickName())
      .roles(Collections.singletonList("ROLE_USER"))
      .build()
    );
  }
}
