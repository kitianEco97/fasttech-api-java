package com.mosainfo.fstechapi.security.jwt;

import com.mosainfo.fstechapi.security.dto.JwtDto;
import com.mosainfo.fstechapi.security.entity.UsuarioPrincipal;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// GENERA EL TOKEN
@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger( JwtProvider.class );

    @Value( "${jwt.secret}" )
    private String secret;

    @Value( "${jwt.expiration}" )
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream().map( GrantedAuthority::getAuthority ).collect( Collectors.toList());
        return Jwts.builder()
                .setSubject( usuarioPrincipal.getUsername())
                .claim( "roles", roles )
                .setIssuedAt( new Date() )
                .setExpiration( new Date(new Date().getTime() + expiration * 180 ) )
                .signWith( SignatureAlgorithm.HS512, secret.getBytes() )
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey( secret.getBytes() ).parseClaimsJws( token ).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey( secret.getBytes() ).parseClaimsJws( token );
            return true;
        } catch (MalformedJwtException e) {
            logger.error( "Token mal formado" );
        } catch (UnsupportedJwtException e) {
            logger.error( "Token no soportado" );
        } catch (ExpiredJwtException e) {
            logger.error( "Token expirado" );
        } catch (IllegalArgumentException e) {
            logger.error( "Token vacio" );
        } catch (SignatureException e) {
            logger.error( "Fail en la firma" );
        }
        return false;
    }

    public String refreshToken(JwtDto jwtDto) throws ParseException {
        JWT jwt = JWTParser.parse( jwtDto.getToken() );
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String nombreUsuario = claims.getSubject();
        List<String> roles = (List<String>) claims.getClaim( "roles" );

        return Jwts.builder()
                .setSubject( nombreUsuario )
                .claim( "roles", roles )
                .setIssuedAt( new Date() )
                .setExpiration( new Date(new Date().getTime() + expiration ) )
                .signWith( SignatureAlgorithm.HS512, secret.getBytes() )
                .compact();
    }
}