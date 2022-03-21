package com.rpis82.scalc.security.jwt;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.rpis82.scalc.entity.UserState;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Класс для предъявления новых токенов и проверки старых
@Component
public class JwtTokenProvider {
	
	@Value("${jwt.token.secret}")
	private String secret;
	
	@Value("${jwt.token.expired}")
	private long validityInMilliseconds;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
	
	// Шифрование секретного слова перед созданием компонента JwtTokenProvider
	@PostConstruct
	protected void init() {
	    secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	// Создание токена
	public String createToken(String username, UserState userState) {
	
	    Claims claims = Jwts.claims().setSubject(username);
	    claims.put("roles", getStateName(userState));
	
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + validityInMilliseconds);
	
	    return Jwts.builder()//
	            .setClaims(claims)//
	            .setIssuedAt(now)//
	            .setExpiration(validity)//
	            .signWith(SignatureAlgorithm.HS256, secret)//
	            .compact();
	}
	
	// Получение деталей о пользователе на основе пришедшего токена
	public Authentication getAuthentication(String token) {
	    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUsername(String token) {
	    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Выделение токена из заголовка "Authorization" пришедшего HttpServletRequest запроса
	public String resolveToken(HttpServletRequest req) {
	    String bearerToken = req.getHeader("Authorization");
	    if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
	        return bearerToken.substring(7, bearerToken.length());
	    }
	    return null;
	}
	
	// Проверка действительности текущего токена
	public boolean validateToken(String token) {
	    try {
	        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	
	        if (claims.getBody().getExpiration().before(new Date())) {
	            return false;
	        }
	
	        return true;
	    } catch (JwtException | IllegalArgumentException e) {
	        throw new JwtAuthenticationException("JWT token is expired or invalid");
	    }
	}
	
	private String getStateName(UserState userState) {
	    return userState.getName();
	}
}
