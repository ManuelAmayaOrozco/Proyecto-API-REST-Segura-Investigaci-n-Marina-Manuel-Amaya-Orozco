package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Service.PezService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
Clase Controller para los Peces, llama a pezService para realizar las
diferentes acciones de los endpoints establecidos y devuelve una respuesta al usuario.
 */
@RestController
@RequestMapping("/peces")
public class PezController {

    @Autowired
    private PezService pezService;

    /*
    Función encargada de registrar los peces dentro de la base de datos, llamando
    a pezService para que realice la creación en si.
     */
    @PostMapping("/")
    public ResponseEntity<PezDTO> create(
            @RequestBody PezDTO pezDTO
    ) {

        PezDTO p = pezService.create(pezDTO);

        if(p == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar registrar el pez.");

        } else {
            ResponseEntity<PezDTO> respuesta = new ResponseEntity<PezDTO>(
                    p, HttpStatus.CREATED
            );
            return respuesta;
        }

    }

    /*
    Función encargada de obtener uno de los peces buscando por su ID, llamando a
    pezService para que obtenga el pez y sea devuelto junto con la respuesta.
     */
    @GetMapping("/{idPez}")
    public ResponseEntity<PezDTO> getById(
            @PathVariable String idPez
    ) {

        // Compruebo que el id no es null
        if (idPez == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        PezDTO p = pezService.getById(idPez);

        if (p == null) {

            throw new NotFoundException("No se encuentra ningún pez con el ID especificado.");

        } else {

            ResponseEntity<PezDTO> respuesta = new ResponseEntity<PezDTO>(
                    p, HttpStatus.OK
            );
            return respuesta;
        }

    }

    /*
    Función encargada de obtener todos los peces dentro de la base de datos,
    llamando a pezService para que obtenga la lista completa con todos los
    peces registrados y sea devuelta junto con la respuesta.
     */
    @GetMapping("/")
    public ResponseEntity<List<PezDTO>> getAll() {

        List<PezDTO> p = pezService.getAll();

        if(p == null) {

            throw new NotFoundException("No se encuentra ningún pez para mostrar.");

        } else {
            ResponseEntity<List<PezDTO>> respuesta = new ResponseEntity<List<PezDTO>>(
                    p, HttpStatus.OK
            );
            return respuesta;
        }

    }

    /*
    Función encargada de actualizar uno de los peces dentro de la base de datos por ID,
    llamando a pezService para realizar la actualización.
     */
    @PutMapping("/{idPez}")
    public ResponseEntity<PezDTO> update(
            @RequestBody PezDTO pezDTO,
            @PathVariable String idPez
    ) {

        // Compruebo que el id no es null
        if (idPez == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        PezDTO p = pezService.update(idPez, pezDTO);

        if(p == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar actualizar el pez.");

        } else {
            ResponseEntity<PezDTO> respuesta = new ResponseEntity<PezDTO>(
                    p, HttpStatus.OK
            );
            return respuesta;
        }

    }

    /*
    Función encargada de eliminar un pez de la base de datos por ID, llamando
    a pezService para realizar la eliminación.
     */
    @DeleteMapping("/{idPez}")
    public ResponseEntity<PezDTO> delete(
            @PathVariable String idPez
    ) {

        // Compruebo que el id no es null
        if (idPez == null) {

            throw new BadRequestException("El campo ID no tiene un formato válido.");

        }

        PezDTO p = pezService.delete(idPez);

        if(p == null) {

            throw new InternalServerErrorException("Un error inesperado ha ocurrido al intentar eliminar el pez.");

        } else {
            ResponseEntity<PezDTO> respuesta = new ResponseEntity<PezDTO>(
                    p, HttpStatus.NO_CONTENT
            );
            return respuesta;
        }

    }

}
