package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Investigacion;
import com.es.api_investigacion_marina.Model.Pez;
import com.es.api_investigacion_marina.Model.Usuario;
import com.es.api_investigacion_marina.Repository.InvestigacionRepository;
import com.es.api_investigacion_marina.Repository.PezRepository;
import com.es.api_investigacion_marina.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PezService {

    @Autowired
    private PezRepository pezRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InvestigacionRepository investigacionRepository;

    public PezDTO create(PezDTO pezDTO) {

        //Comprobación idInvestigador
        if (pezDTO.getIdInvestigador() == null) {

            throw new BadRequestException("El ID del investigador no puede estar vacío.");

        } else {

            //Comprobamos si el usuario existe en la BDD
            boolean existe = false;

            List<Usuario> usuarios = usuarioRepository.findAll();

            for (Usuario u: usuarios) {

                if (u.getId() == pezDTO.getIdInvestigador()) {

                    existe = true;

                    break;

                }

            }

            if (!existe) {

                throw new NotFoundException("El ID introducido no coincide con ningún usuario de la base de datos.");

            }

        }

        //Comprobación apariciones (todas las investigaciones deben existir en la BDD)
        if (!pezDTO.getApariciones().isEmpty()) {

            List<InvestigacionDTO> investigacionesDTO = pezDTO.getApariciones();
            List<Investigacion> investigaciones = investigacionRepository.findAll();

            for (InvestigacionDTO iDTO : investigacionesDTO) {

                boolean existe = false;

                for (Investigacion i : investigaciones) {

                    if (i.getIdInvestigacion() == iDTO.getIdInvestigacion()) {

                        existe = true;

                        break;

                    }

                }

                if (!existe) {

                    throw new NotFoundException("No se ha encontrado una de las investigaciones dentro de la base de datos.");

                }

            }

        }

        //Comprobación nombreComun
        if (pezDTO.getNombreComun().isEmpty()) {

            throw new BadRequestException("El nombre común del pez no puede estar vacío.");

        }

        //Comprobación nombreCientifico
        if (pezDTO.getNombreCientifico().isEmpty()) {

            throw new BadRequestException("El nombre científico del pez no puede estar vacío.");

        }

        //Comprobación especie
        if (pezDTO.getEspecie().isEmpty()) {

            throw new BadRequestException("La especie del pez no puede estar vacía.");

        }

        //Comprobación dieta
        if (pezDTO.getDieta().isEmpty()) {

            throw new BadRequestException("La dieta del pez no puede estar vacía.");

        }

        //Comprobación descripcion
        if (pezDTO.getDescripcion().isEmpty()) {

            throw new BadRequestException("La descripción del pez no puede estar vacía.");

        }

        //Comprobación ejemplares
        if (pezDTO.getEjemplares() < 0) {

            throw new BadRequestException("El número de ejemplares del pez ha de ser mayor que 0.");

        }

        //Comprobación tamMax
        if (pezDTO.getTamMax() < 0) {

            throw new BadRequestException("El tamaño máximo del pez ha de ser mayor que 0.");

        }

        //Comprobación peligroExtincion
        try {

            String test1 = Boolean.toString(pezDTO.isPeligroExtincion());

            Boolean test2 = Boolean.parseBoolean(test1);

        } catch (Exception e) {

            throw new BadRequestException("El campo peligroExtincion no puede ser null.");

        }

        Pez pez = mapToPez(pezDTO);

        pez = pezRepository.save(pez);

        return mapToDTO(pez);

    }

    private Pez mapToPez(PezDTO pezDTO) {
        Pez pez = new Pez();

        pez.setIdPez(pezDTO.getIdPez());
        pez.setApariciones(pezDTO.getApariciones());

    }

}
