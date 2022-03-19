package com.goldfrosch.global.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
  private static final long serialVersionUID = -2550185165626007488L;
  public static final long JWT_TOKEN_VALIDITY = 5 * 24 * 60 * 60;

  @Value("${jwt.secret}")
  private String secret;
  
  //토큰에 저장된 모든 정보 body 를 가져오기
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }
  
  //토큰의 모든 정보를 이용해 T 타입의 객체값을 return
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }
  
  //유저 이름 가져오기
  public String getUsernameFromToken(String token) {
    //Claims::getSubject 는 Claims JWT 에서 제목을 가져옴
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    //Claims::getExpiration 은 만료시간을 return
    return getClaimFromToken(token, Claims::getExpiration);
  }

  //토큰 유효시간 검사에 사용
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    
    //현재 시간을 기점으로 만료시간 이전인지 확인
    return expiration.before(new Date());
  }

  //토큰 생성시의 함수 (내부에서만 작동할것이라 외부에 참조할 필요가 없다)
  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        //key-value 의 저장할 객체와
        .setClaims(claims)
        //토큰 제목을 지정 generateToken 에서는 username 을 제목으로 지정
        .setSubject(subject)
        //System.currentTimeMillis()로 현재 타임을 불러옴
        .setIssuedAt(new Date(System.currentTimeMillis()))
        //TOKEN_VALIDITY 의 초 값에 millimeter 값이라 1000을 곱해서 사용
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        //암호 키 값과 암호화 방식을 지정
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  //username 을 기반으로 토큰 생성
  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, username);
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    //토큰 값에서 키 값인 유저 이름을 가져옴
    final String username = getUsernameFromToken(token);
    //유저이름 같은 지와 만료 시간을 확인해 사용 가능한 토큰인지 비교함
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
