package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotAuthorizedException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Usuario;
import com.es.api_investigacion_marina.Repository.UsuarioRepository;
import com.es.api_investigacion_marina.Service.InvestigacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/investigaciones")
public class InvestigacionController {

    @Autowired
    private InvestigacionService investigacionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity<InvestigacionDTO> create(
            @RequestBody InvestigacionDTO investigacionDTO
    ) {

        InvestigacionDTO i = investigacionService.create(investigacionDTO);

        if(i == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar registrar la investigación.");

        } else {
            ResponseEntity<InvestigacionDTO> respuesta = new ResponseEntity<InvestigacionDTO>(
                    i, HttpStatus.CREATED
            );
            return respuesta;
        }

    }

    @GetMapping("/{idInvestigacion}")
    public ResponseEntity<InvestigacionDTO> getById(
            @PathVariable String idInvestigacion,
            Authentication authentication,
            Principal principal
    ) {

        // Compruebo que el id no es null
        if (idInvestigacion == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        InvestigacionDTO i = investigacionService.getById(idInvestigacion);

        if (i == null) {

            throw new NotFoundException("No se encuentra ninguna investigación con el ID especificado.");

        } else {

            Usuario u = usuarioRepository.getReferenceById(i.getIdInvestigador());

            if(authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(u.getUsername())) {
                return new ResponseEntity<>(i, HttpStatus.OK);
            } else {
                throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
            }

        }

    }

    @GetMapping("/")
    public ResponseEntity<List<InvestigacionDTO>> getAll() {

        List<InvestigacionDTO> i = investigacionService.getAll();

        if(i == null) {

            throw new NotFoundException("No se encuentra ninguna investigación para mostrar.");

        } else {
            ResponseEntity<List<InvestigacionDTO>> respuesta = new ResponseEntity<List<InvestigacionDTO>>(
                    i, HttpStatus.OK
            );
            return respuesta;
        }

    }

    @PutMapping("/{idInvestigacion}")
    public ResponseEntity<InvestigacionDTO> update(
            @RequestBody InvestigacionDTO investigacionDTO,
            @PathVariable String idInvestigacion,
            Authentication authentication,
            Principal principal
    ) {

        // Compruebo que el id no es null
        if (idInvestigacion == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        InvestigacionDTO i = investigacionService.update(idInvestigacion, investigacionDTO);

        if(i == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar actualizar la investigación.");

        } else {

            Usuario u = usuarioRepository.getReferenceById(i.getIdInvestigador());

            if(authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(u.getUsername())) {
                return new ResponseEntity<>(i, HttpStatus.OK);
            } else {
                throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
            }

        }

    }

}
