package com.goldfrosch.domain.User.api;

import com.goldfrosch.domain.User.application.UserService;
import com.goldfrosch.domain.User.domain.User;
import com.goldfrosch.domain.User.entity.dao.RegisterDAO;
import com.goldfrosch.domain.User.entity.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserRestController {
  private final UserService userService;

  @GetMapping("/profile")
  public UserDTO getProfile(@ApiIgnore @AuthenticationPrincipal User user) {
    return userService.getProfile(user);
  }

  @PostMapping("/register")
  public void registerUser(@RequestBody RegisterDAO newUser) {
    userService.registerUser(newUser);
  }

  @GetMapping("/find/email")
  public User findProfileByEmail(@RequestParam String email) {
    return userService.findByEmail(email);
  }
}
