package com.github.jjcdutra2015.security.jwt;

import com.github.jjcdutra2015.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expiracaoLong = Long.valueOf(expiracao);
        LocalDateTime dataHoraExp = LocalDateTime.now().plusMinutes(expiracaoLong);
        Instant instant = dataHoraExp.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date expiration = claims.getExpiration();
            LocalDateTime dateTime = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(dateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLogin(String token) {
        return (String) obterClaims(token).getSubject();
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
//        JwtService jwtService = context.getBean(JwtService.class);
//        String token = jwtService.gerarToken(Usuario.builder().login("fulano").build());
//        System.out.println(token);
//
//        boolean isTokenValido = jwtService.tokenValido(token);
//        System.out.println("Token est?? v??lido? " + isTokenValido);
//
//        System.out.println(jwtService.obterLogin(token));
//    }
}
