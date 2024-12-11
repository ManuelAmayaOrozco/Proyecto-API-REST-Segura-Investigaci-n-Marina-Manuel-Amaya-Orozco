package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioLoginDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
import com.es.api_investigacion_marina.Exception.NotAuthorizedException;
import com.es.api_investigacion_marina.Service.CustomUserDetailsService;
import com.es.api_investigacion_marina.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(
            @RequestBody UsuarioLoginDTO usuarioLoginDTO
    ) {

        System.out.println(
                usuarioLoginDTO.getUsername() + " " + usuarioLoginDTO.getPassword()
        );

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword())// modo de autenticación
            );
        } catch (Exception e) {
            System.out.println("Excepcion en authentication");
            e.printStackTrace();
        }

        // Generamos el token
        String token = "";
        try {
            token = tokenService.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("Excepcion en generar token");
            e.printStackTrace();
        }

        // Retornamos el token
        return token;

    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegisterDTO> register(
            @RequestBody UsuarioRegisterDTO usuarioRegisterDTO) {

        System.out.println(
                usuarioRegisterDTO.getPassword()
        );

        customUserDetailsService.registerUser(usuarioRegisterDTO);

        return new ResponseEntity<UsuarioRegisterDTO>(usuarioRegisterDTO, HttpStatus.OK);

    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> getByID(
            @PathVariable String idUser,
            Authentication authentication,
            Principal principal
    ) {

        UsuarioDTO usuarioDTO = customUserDetailsService.getByID(idUser);

        if(authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(usuarioDTO.getId().toString())) {
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        } else {
            throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
        }

    }

}
