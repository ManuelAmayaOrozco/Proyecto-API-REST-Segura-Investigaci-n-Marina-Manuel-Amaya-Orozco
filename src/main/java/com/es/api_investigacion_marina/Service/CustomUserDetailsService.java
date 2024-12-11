package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Usuario;
import com.es.api_investigacion_marina.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // BUSCO EL USUARIO POR SU NOMBRE EN LA BDD
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));

        // RETORNAMOS UN USERDETAILS
        UserDetails userDetails = User // User pertenece a SpringSecurity
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().split(","))
                .build();

        return userDetails;

    }

    public UsuarioRegisterDTO registerUser(UsuarioRegisterDTO usuarioRegisterDTO) {

        // Comprobamos que el usuario no existe en la base de datos
        if (usuarioRepository.findByUsername(usuarioRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        // Creamos la instancia de
        Usuario newUsuario = new Usuario();

        newUsuario.setPassword(passwordEncoder.encode(usuarioRegisterDTO.getPassword())); // Hashear la contraseña
        newUsuario.setUsername(usuarioRegisterDTO.getUsername());
        newUsuario.setRoles(usuarioRegisterDTO.getRoles());

        // Guardamos el newUsuario en la base de datos
        usuarioRepository.save(newUsuario);

        return usuarioRegisterDTO;
    }

    public UsuarioDTO getByID(String id) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Usuario u = usuarioRepository
                .findById(idL)
                .orElseThrow(() -> new NotFoundException("Usuario con ID "+id+" no encontrado"));

        return mapToDTO(u);

    }

    public List<UsuarioDTO> getAll() {

        List<UsuarioDTO> listaDeDTOs = new ArrayList<>();

        List<Usuario> listaUser = usuarioRepository.findAll();

        for (Usuario u: listaUser) {

            listaDeDTOs.add(mapToDTO(u));

        }

        return listaDeDTOs;

    }

    private UsuarioDTO mapToDTO(Usuario usuario) {

        String roles = Arrays.toString(usuario.getRoles().split(","));

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getPassword(),
                roles
        );

    }

}
