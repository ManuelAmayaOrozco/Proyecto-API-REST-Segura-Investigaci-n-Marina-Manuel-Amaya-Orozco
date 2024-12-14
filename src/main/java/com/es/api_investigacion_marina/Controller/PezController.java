package com.es.api_investigacion_marina.Controller;

import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Service.PezService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peces")
public class PezController {

    @Autowired
    private PezService pezService;

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
