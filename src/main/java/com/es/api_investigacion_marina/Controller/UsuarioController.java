package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioLoginDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotAuthorizedException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
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
import java.util.List;

/*
Clase Controller para los Usuarios, llama a usuarioService para realizar las
diferentes acciones de los endpoints establecidos y devuelve una respuesta al usuario.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenService tokenService;

    /*
    Función encargada de logear al usuario, devolviendo un token de sesión si se introducen
    los datos correctamente.
     */
    @PostMapping("/login")
    public String login(
            @RequestBody UsuarioLoginDTO usuarioLoginDTO
    ) {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword()) // modo de autenticación
            );
        } catch (Exception e) {
            System.out.println("Excepción en authentication");
            e.printStackTrace();
        }

        // Generamos el token
        String token = "";
        try {
            token = tokenService.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("Excepción en generar token");
            e.printStackTrace();
        }

        // Retornamos el token
        return token;

    }

    /*
    Función encargada de registrar un usuario nuevo en la base de datos, devolviendo ese mismo
    usuario creado.
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioRegisterDTO> register(
            @RequestBody UsuarioRegisterDTO usuarioRegisterDTO) {

        customUserDetailsService.registerUser(usuarioRegisterDTO);

        return new ResponseEntity<UsuarioRegisterDTO>(usuarioRegisterDTO, HttpStatus.OK);

    }

    /*
    Función encargada de devolver la información de un usuario existente, buscando por su ID.
     */
    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> getByID(
            @PathVariable String idUser,
            Authentication authentication,
            Principal principal
    ) {

        // Compruebo que el id no es null
        if (idUser == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        UsuarioDTO usuarioDTO = customUserDetailsService.getByID(idUser);

        if (usuarioDTO == null) {

            throw new NotFoundException("No se encuentra ningún usuario con el ID especificado.");

        } else {

            if (authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(usuarioDTO.getUsername())) {
                return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
            } else {
                throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
            }

        }

    }

    /*
    Función encargada de devolver una lista completa de todos los usuarios de la base de datos.
     */
    @GetMapping("/")
    public ResponseEntity<List<UsuarioDTO>> getAll() {

        List<UsuarioDTO> u = customUserDetailsService.getAll();

        if(u == null) {

            throw new NotFoundException("No se encuentra ningún seguro para mostrar.");

        } else {
            ResponseEntity<List<UsuarioDTO>> respuesta = new ResponseEntity<List<UsuarioDTO>>(
                    u, HttpStatus.OK
            );
            return respuesta;
        }

    }

    /*
    Función encargada de actualizar un usuario de la base de datos, devolviendo el usuario
    actualizado junto con la respuesta.
     */
    @PutMapping("/{idUser}")
    public ResponseEntity<UsuarioRegisterDTO> update(
            @RequestBody UsuarioRegisterDTO usuarioRegisterDTO,
            @PathVariable String idUser
    ) {

        // Compruebo que el id no es null
        if (idUser == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        UsuarioRegisterDTO u = customUserDetailsService.update(idUser, usuarioRegisterDTO);

        if(u == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar actualizar el usuario.");

        } else {
            ResponseEntity<UsuarioRegisterDTO> respuesta = new ResponseEntity<UsuarioRegisterDTO>(
                    u, HttpStatus.OK
            );
            return respuesta;
        }

    }

    /*
    Función encargada de eliminar un usuario de la base de datos, buscándolo por su ID.
     */
    @DeleteMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> delete(
            @PathVariable String idUser
    ) {

        // Compruebo que el id no es null
        if (idUser == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        UsuarioDTO u = customUserDetailsService.delete(idUser);

        if(u == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar eliminar el usuario.");

        } else {
            ResponseEntity<UsuarioDTO> respuesta = new ResponseEntity<UsuarioDTO>(
                    u, HttpStatus.NO_CONTENT
            );
            return respuesta;
        }

    }

}
