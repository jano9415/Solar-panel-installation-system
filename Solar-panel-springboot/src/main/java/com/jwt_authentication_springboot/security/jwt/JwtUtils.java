package com.jwt_authentication_springboot.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jwt_authentication_springboot.security.service.UserDetailsImpl;

import io.jsonwebtoken.*;



@Component
public class JwtUtils {
	
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  //Jwt token titkosítása. Ez egy általam kreált szöveg, ami az application.properties-ban van.
  @Value("${jwtauth.app.jwtSecret}")
  private String jwtSecret;

  //Jwt token lejárati ideje. Ez egy általam megadott érték milisecundum-ban, ami az application.properties-ban van.
  @Value("${jwtauth.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  //Jwt token generálása
  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    //A jwt token tartalmazza: felhasználói név, mostani dátum, token lejárati dátuma.
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  //Felhasználói név a jwt token-ből.
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  //Jwt token validációja.
  //Hiba ha, - nem megfelelő a token formátuma
  //		 - a token már lejárt
  //		 - a token nem támogatott
  //		 - ha a bejövő string üres
  //Ha minden oké, akkor a token valid és igazzal térek vissza.
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}