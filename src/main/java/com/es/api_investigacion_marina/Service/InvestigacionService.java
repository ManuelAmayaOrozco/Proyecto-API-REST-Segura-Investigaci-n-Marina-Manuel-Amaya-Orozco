package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Investigacion;
import com.es.api_investigacion_marina.Model.Pez;
import com.es.api_investigacion_marina.Repository.InvestigacionRepository;
import com.es.api_investigacion_marina.Repository.PezRepository;
import com.es.api_investigacion_marina.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvestigacionService {

    @Autowired
    private InvestigacionRepository investigacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PezRepository pezRepository;

    public InvestigacionDTO getById(String idInvestigacion) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idInvestigacion);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Investigacion i = null;
        try {
            i = investigacionRepository
                    .findById(idL)
                    .orElse(null);
        } catch (Exception e) {
            throw new InternalServerErrorException("Un error inesperado ha ocurrido al buscar la investigación por su ID.");
        }

        if(i == null) {
            throw new NotFoundException("No se encuentra ninguna investigación con el ID especificado.");
        } else {

            return mapToDTO(i);

        }

    }

    private InvestigacionDTO mapToDTO(Investigacion investigacion) {

        InvestigacionDTO investigacionDTO = new InvestigacionDTO();

        investigacionDTO.setIdInvestigacion(investigacion.getIdInvestigacion());
        investigacionDTO.setIdInvestigador(investigacion.getInvestigador().getId());

        List<Long> pecesDTO = new ArrayList<Long>();
        List<Pez> peces = investigacion.getPeces();

        for (Pez p : peces) {

            pecesDTO.add(p.getIdPez());

        }

        investigacionDTO.setPeces(pecesDTO);
        investigacionDTO.setTitulo(investigacion.getTitulo());
        investigacionDTO.setResumen(investigacion.getResumen());
        investigacionDTO.setLugar(investigacion.getLugar());
        investigacionDTO.setFecha(investigacion.getFecha());
        investigacionDTO.setHora(investigacion.getHora());

        return investigacionDTO;

    }

}
