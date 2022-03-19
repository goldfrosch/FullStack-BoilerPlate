package com.goldfrosch.domain.User.persistance;

import com.goldfrosch.domain.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
  //<T>의 객체를 포장해주는 Wrapper Class! -> Optional
  //Wrapper class 는 기본 자료타입들을 객체처럼 다루기 위해 사용한다(int -> Integer etc...)
  User findByEmail(String email);
}
