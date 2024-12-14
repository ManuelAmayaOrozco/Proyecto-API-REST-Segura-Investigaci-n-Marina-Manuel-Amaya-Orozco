package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
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

/*
Clase Controller para las Investigaciones, llama a investigacionService para realizar las
diferentes acciones de los endpoints establecidos y devuelve una respuesta al usuario.
 */
@RestController
@RequestMapping("/investigaciones")
public class InvestigacionController {

    @Autowired
    private InvestigacionService investigacionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
    Función encargada de crear las investigaciones dentro de la base de datos, llamando
    a investigacionService para que realice la creación en si.
     */
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

    /*
    Función encargada de obtener una de las investigaciones buscando por su ID, llamando a
    investigacionService para que obtenga la investigación y sea devuelta junto con la
    respuesta.
     */
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

            //Obtengo el usuario al que corresponde el ID de la investigación obtenida para confirmar si es el mismo proveido en el token
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

    /*
    Función encargada de obtener todas las investigaciones dentro de la base de datos,
    llamando a investigacionService para que obtenga la lista completa con todas las
    investigaciones registradas y sea devuelta junto con la respuesta.
     */
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

    /*
    Función encargada de actualizar una de las investigaciones dentro de la base de datos por ID,
    llamando a investigacionService para realizar la actualización si se cumplen los requisitos.
     */
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

        /*
        Primero se realiza un getById para obtener la información de la investigación a
        actualizar, la cual se verá actualizada o no dependiendo de si el token proveido
        cumple los criterios
         */
        InvestigacionDTO i = investigacionService.getById(idInvestigacion);

        if(i == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar actualizar la investigación.");

        } else {

            //Obtengo el usuario al que corresponde el ID de la investigación obtenida para confirmar si es el mismo proveido en el token
            Usuario u = usuarioRepository.getReferenceById(i.getIdInvestigador());

            if(authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(u.getUsername())) {

                //Aquí se realiza la actualización tras haber confirmado que el token de sesión es válido
                InvestigacionDTO newI = investigacionService.update(idInvestigacion, investigacionDTO);

                return new ResponseEntity<>(newI, HttpStatus.OK);
            } else {
                throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
            }

        }

    }

    /*
    Función encargada de eliminar una investigación de la base de datos por ID, llamando
    a investigacionService para realizar la eliminación una vez se confirma que el token
    cumple con todos los requisitos necesarios.
     */
    @DeleteMapping("/{idInvestigacion}")
    public ResponseEntity<InvestigacionDTO> delete(
            @PathVariable String idInvestigacion,
            Authentication authentication,
            Principal principal
    ) {

        // Compruebo que el id no es null
        if (idInvestigacion == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        /*
        Primero se realiza un getById para obtener la información de la investigación a
        eliminar, la cual será eliminada o no dependiendo de si el token proveido
        cumple los criterios
         */
        InvestigacionDTO i = investigacionService.getById(idInvestigacion);

        if(i == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar eliminar la investigación.");

        } else {

            //Obtengo el usuario al que corresponde el ID de la investigación obtenida para confirmar si es el mismo proveido en el token
            Usuario u = usuarioRepository.getReferenceById(i.getIdInvestigador());

            if(authentication.getAuthorities()
                    .stream()
                    .anyMatch(authority -> authority.equals(new SimpleGrantedAuthority("ROLE_ADMIN"))) || authentication.getName().equals(u.getUsername())) {

                //Aquí se realiza la eliminación tras haber confirmado que el token de sesión es válido
                investigacionService.delete(idInvestigacion);

                return new ResponseEntity<>(i, HttpStatus.NO_CONTENT);
            } else {
                throw new NotAuthorizedException("No tienes los permisos para acceder al recurso");
            }

        }

    }

}
