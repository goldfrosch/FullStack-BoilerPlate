package com.goldfrosch.domain.User.application;

import com.goldfrosch.domain.User.domain.User;
import com.goldfrosch.domain.User.persistance.UserRepository;
import com.goldfrosch.global.component.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(username);

    if(user != null) return new JwtUserDetails(user);

    throw new UsernameNotFoundException("Could not find user with email : " + username);
  }
}
