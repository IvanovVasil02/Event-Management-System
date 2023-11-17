package ivanovvasil.u5d5w3Project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ivanovvasil.u5d5w3Project.entities.User;
import ivanovvasil.u5d5w3Project.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTools {
  @Value("${spring.jwt.secret}")
  private String secret;

  public String createToken(User user) {
    return Jwts.builder().setSubject(String.valueOf(user.getId()))
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
            .compact();

  }

  public String extractIdFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
            .build().parseClaimsJws(token).getBody().getSubject();
  }

  public void verifyToken(String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
              .build().parse(token);
    } catch (Exception e) {
      throw new UnauthorizedException("Invalid acces token!");

    }
  }
}
