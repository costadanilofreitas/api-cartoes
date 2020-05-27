package com.br.api.cartao.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTUtilTest {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void deveGerarEValidarToken() {
        String token = jwtUtil.generateToken("teste");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(jwtUtil.tokenValido(token));
    }

    @Test
    public void deveGerarUserName() {
        String token = jwtUtil.generateToken("teste");
        String nome = jwtUtil.getUsername(token);
        Assertions.assertNotNull(nome);
        Assertions.assertEquals("teste", nome);
    }

    @Test
    public void deveInvalidarTokenCasoTokenSejaNulo() {
        Assertions.assertFalse(jwtUtil.tokenValido(null));
    }

    @Test
    public void deveInvalidarTokenCasoTokenSejaUmTokenInvalido() {
        Assertions.assertFalse(jwtUtil.tokenValido("qualquecoisa"));
    }

}
