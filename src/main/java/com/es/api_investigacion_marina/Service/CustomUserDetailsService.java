package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Investigacion;
import com.es.api_investigacion_marina.Model.Pez;
import com.es.api_investigacion_marina.Model.Usuario;
import com.es.api_investigacion_marina.Repository.InvestigacionRepository;
import com.es.api_investigacion_marina.Repository.PezRepository;
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

    @Autowired
    private InvestigacionRepository investigacionRepository;

    @Autowired
    private PezRepository pezRepository;

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

    public UsuarioDTO getByID(String idUser) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idUser);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Usuario u = usuarioRepository
                .findById(idL)
                .orElseThrow(() -> new NotFoundException("Usuario con ID "+idUser+" no encontrado"));

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

    public UsuarioRegisterDTO update(String idUser, UsuarioRegisterDTO usuarioRegisterDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idUser);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        // Compruebo que el usuario existe en la BDD
        Usuario u = usuarioRepository.findById(idL).orElse(null);

        if (u == null) {

            return null;

        } else {

            Usuario newU = mapToUsuario(usuarioRegisterDTO);

            newU.setId(u.getId());

            usuarioRepository.save(newU);

            return mapToRegisterDTO(newU);

        }

    }

    public UsuarioDTO delete(String idUser) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idUser);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Usuario u = usuarioRepository
                .findById(idL)
                .orElseThrow(() -> new NotFoundException("Usuario con ID "+idUser+" no encontrado"));

        if(u == null) {
            throw new NotFoundException("No se encuentra ningún usuario con el ID especificado.");
        } else {

            //Eliminamos todas las investigaciones relacionadas

            List<Investigacion> investigaciones = investigacionRepository.findAll();

            for (Investigacion i: investigaciones) {

                if (i.getInvestigador().getId() == idL) {

                    investigacionRepository.delete(i);

                }

            }

            //Eliminamos todos los peces relacionados

            List<Pez> peces = pezRepository.findAll();

            for (Pez p: peces) {

                if (p.getInvestigador().getId() == idL) {

                    pezRepository.delete(p);

                }

            }

            UsuarioDTO usuarioDTO = mapToDTO(u);

            usuarioRepository.delete(u);

            return usuarioDTO;
        }
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

    private Usuario mapToUsuario(UsuarioRegisterDTO usuarioRegisterDTO) {

        return new Usuario(
                Long.parseLong("0"),
                usuarioRegisterDTO.getUsername(),
                passwordEncoder.encode(usuarioRegisterDTO.getPassword()),
                usuarioRegisterDTO.getRoles()
        );

    }

    private UsuarioRegisterDTO mapToRegisterDTO(Usuario usuario) {

        String roles = Arrays.toString(usuario.getRoles().split(","));

        return new UsuarioRegisterDTO(
                usuario.getUsername(),
                usuario.getPassword(),
                roles
        );

    }

}
