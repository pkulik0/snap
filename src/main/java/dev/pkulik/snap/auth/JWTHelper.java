package dev.pkulik.snap.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.Date;

public class JWTHelper {
    private static final String issuer = "Snap";

    private static final Algorithm algorithm = Algorithm.HMAC512(System.getenv("SNAP_SECRET"));
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    public static String createToken(org.springframework.security.core.userdetails.User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .sign(algorithm);
    }

    public static UsernamePasswordAuthenticationToken verifyToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}
