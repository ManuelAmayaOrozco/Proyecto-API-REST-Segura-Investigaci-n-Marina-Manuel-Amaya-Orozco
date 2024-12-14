package com.es.api_investigacion_marina.Security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos "Cross-Site Request Forgery" (CSRF) (No lo trataremos en este ciclo)
                .authorizeHttpRequests(auth -> auth // Filtros para securizar diferentes endpoints de la aplicación
                                .requestMatchers("/usuarios/login", "/usuarios/register").permitAll()
                                .requestMatchers(HttpMethod.GET,"/usuarios/{idUser}").authenticated()
                                .requestMatchers(HttpMethod.GET,"/usuarios/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/usuarios/{idUser}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/usuarios/{idUser}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/peces/{idPez}").authenticated()
                                .requestMatchers(HttpMethod.GET,"/peces/").authenticated()
                                .requestMatchers(HttpMethod.POST,"/peces/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/peces/{idPez}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/peces/{idPez}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/investigaciones/{idInvestigacion}").authenticated()
                                .requestMatchers(HttpMethod.GET,"/investigaciones/").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/investigaciones/").authenticated()

                                .anyRequest().authenticated() // Para el resto de peticiones, el usuario debe estar autenticado
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())) // Establecemos el que el control de autenticación se realice por JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

}
