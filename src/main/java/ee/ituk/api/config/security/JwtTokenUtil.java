package ee.ituk.api.config.security;

import ee.ituk.api.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    private static final int SEC_IN_MIN = 60;
    private static final int MILLIS_IN_SEC = 1000;
    private static final int TEMP_TOKEN_DURATION = 5;
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String ISSUER = "http://www.ituk.ee";
    private static final String TYPE_KEY = "type";
    private static final String QR_KEY = "qr";

    @Value("${security.signing.key}")
    private String signingKey;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user);
    }


    private String doGenerateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(AUTHORITIES_KEY, authorities);
        Integer sessionTimeoutMin = getSessionTimeout();

        return buildJwtToken(claims, sessionTimeoutMin);
    }


    private String buildJwtToken(Claims claims, int tokenTimeoutMin) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expireDate = new Date(currentTimeMillis + tokenTimeoutMin * SEC_IN_MIN * MILLIS_IN_SEC);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Integer getSessionTimeout() {
        return 60;
    }

    @Transactional
    public void updateSessionExpiration(User user, Date expireDate) {
    }

}
