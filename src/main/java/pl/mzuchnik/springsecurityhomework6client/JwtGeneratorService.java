package pl.mzuchnik.springsecurityhomework6client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Date;

@Service
public class JwtGeneratorService {

    public String generateSimpleJWT() {
        //Pobieranie kluczy
        PrivateKey privateKey = RSAKeyUtil.getPrivateKey();
        PublicKey publicKey = RSAKeyUtil.getPublicKey();

        //Tworzenie tokenu JWT
        String jwt = JWT.create()
                .withSubject("1234567890")
                .withClaim("name", "Mateusz")
                .withClaim("role", "ADMIN")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.RSA512((RSAPublicKey) publicKey,(RSAPrivateKey) privateKey));

        return "Bearer " + jwt;
    }
}
