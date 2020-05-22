package com.br.api.cartao.configurations;

import com.br.api.cartao.security.FiltroAutenticacaoJWT;
import com.br.api.cartao.security.FiltroAutorizacaoJWT;
import com.br.api.cartao.security.JWTUtil;
import com.br.api.cartao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/**/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/usuarios/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Desliga esquema de criar sessões
        http.cors().and().csrf().disable();
        //Libera acessos da aplicação
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();
        // avisa ao spring que não precisa criar esquema de sessões já que a aplicação é stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new FiltroAutenticacaoJWT(jwtUtil, authenticationManager()));
        http.addFilter(new FiltroAutorizacaoJWT(authenticationManager(), usuarioService, jwtUtil));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
