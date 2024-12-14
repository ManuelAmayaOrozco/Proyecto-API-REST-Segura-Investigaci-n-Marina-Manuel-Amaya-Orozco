package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.Exception.BadRequestException;
import com.es.api_investigacion_marina.Exception.InternalServerErrorException;
import com.es.api_investigacion_marina.Exception.NotFoundException;
import com.es.api_investigacion_marina.Model.Investigacion;
import com.es.api_investigacion_marina.Model.Pez;
import com.es.api_investigacion_marina.Model.Usuario;
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

    public InvestigacionDTO create(InvestigacionDTO investigacionDTO) {

        //Comprobación idInvestigador
        if (investigacionDTO.getIdInvestigador() == null) {

            throw new BadRequestException("El ID del investigador no puede estar vacío.");

        } else {

            //Comprobamos si el usuario existe en la BDD
            boolean existe = false;

            List<Usuario> usuarios = usuarioRepository.findAll();

            for (Usuario u: usuarios) {

                if (u.getId() == investigacionDTO.getIdInvestigador()) {

                    existe = true;

                    break;

                }

            }

            if (!existe) {

                throw new NotFoundException("El ID introducido no coincide con ningún usuario de la base de datos.");

            }

        }

        //Comprobación peces (todos los peces deben existir en la BDD)
        if (!investigacionDTO.getPeces().isEmpty()) {

            List<Long> pecesDTO = investigacionDTO.getPeces();
            List<Pez> peces = pezRepository.findAll();

            for (Long pDTO : pecesDTO) {

                boolean existe = false;

                for (Pez p : peces) {

                    if (p.getIdPez() == pDTO) {

                        existe = true;

                        break;

                    }

                }

                if (!existe) {

                    throw new NotFoundException("No se ha encontrado uno de los peces dentro de la base de datos.");

                }

            }

        }

        //Comprobación titulo
        if (investigacionDTO.getTitulo().isEmpty()) {

            throw new BadRequestException("El título de la investigación no puede estar vacío.");

        }

        //Comprobación resumen
        if (investigacionDTO.getResumen().isEmpty()) {

            throw new BadRequestException("El resumen de la investigación no puede estar vacío.");

        }

        //Comprobación lugar
        if (investigacionDTO.getLugar().isEmpty()) {

            throw new BadRequestException("El lugar de la investigación no puede estar vacío.");

        }

        //Comprobación fecha
        if (investigacionDTO.getFecha() == null) {

            throw new BadRequestException("La fecha de la investigación no puede ser nula.");

        }

        //Comprobación hora
        if (investigacionDTO.getHora() == null) {

            throw new BadRequestException("La hora de la investigación no puede ser nula.");

        }

        Investigacion investigacion = mapToInvestigacion(investigacionDTO);

        investigacion = investigacionRepository.save(investigacion);

        return mapToDTO(investigacion);

    }

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

    public List<InvestigacionDTO> getAll() {

        List<InvestigacionDTO> listaDeDTOs = new ArrayList<>();

        List<Investigacion> listaInvestigacion = investigacionRepository.findAll();

        for (Investigacion i: listaInvestigacion) {

            listaDeDTOs.add(mapToDTO(i));

        }

        return listaDeDTOs;

    }

    public InvestigacionDTO update(String idInvestigacion, InvestigacionDTO investigacionDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idInvestigacion);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        //Comprobación idInvestigador
        if (investigacionDTO.getIdInvestigador() == null) {

            throw new BadRequestException("El ID del investigador no puede estar vacío.");

        } else {

            //Comprobamos si el usuario existe en la BDD
            boolean existe = false;

            List<Usuario> usuarios = usuarioRepository.findAll();

            for (Usuario u: usuarios) {

                if (u.getId() == investigacionDTO.getIdInvestigador()) {

                    existe = true;

                    break;

                }

            }

            if (!existe) {

                throw new NotFoundException("El ID introducido no coincide con ningún usuario de la base de datos.");

            }

        }

        //Comprobación peces (todos los peces deben existir en la BDD)
        if (!investigacionDTO.getPeces().isEmpty()) {

            List<Long> pecesDTO = investigacionDTO.getPeces();
            List<Pez> peces = pezRepository.findAll();

            for (Long pDTO : pecesDTO) {

                boolean existe = false;

                for (Pez p : peces) {

                    if (p.getIdPez() == pDTO) {

                        existe = true;

                        break;

                    }

                }

                if (!existe) {

                    throw new NotFoundException("No se ha encontrado uno de los peces dentro de la base de datos.");

                }

            }

        }

        //Comprobación titulo
        if (investigacionDTO.getTitulo().isEmpty()) {

            throw new BadRequestException("El título de la investigación no puede estar vacío.");

        }

        //Comprobación resumen
        if (investigacionDTO.getResumen().isEmpty()) {

            throw new BadRequestException("El resumen de la investigación no puede estar vacío.");

        }

        //Comprobación lugar
        if (investigacionDTO.getLugar().isEmpty()) {

            throw new BadRequestException("El lugar de la investigación no puede estar vacío.");

        }

        //Comprobación fecha
        if (investigacionDTO.getFecha() == null) {

            throw new BadRequestException("La fecha de la investigación no puede ser nula.");

        }

        //Comprobación hora
        if (investigacionDTO.getHora() == null) {

            throw new BadRequestException("La hora de la investigación no puede ser nula.");

        }

        // Compruebo que la investigación existe en la BDD
        Investigacion i = investigacionRepository.findById(idL).orElse(null);

        if (i == null) {

            return null;

        } else {

            Investigacion newI = mapToInvestigacion(investigacionDTO);

            newI.setIdInvestigacion(i.getIdInvestigacion());

            investigacionRepository.save(newI);

            return mapToDTO(newI);

        }

    }

    public InvestigacionDTO delete(String idInvestigacion) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idInvestigacion);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Investigacion i = investigacionRepository
                            .findById(idL)
                            .orElseThrow(() -> new NotFoundException("Investigación con ID "+idInvestigacion+" no encontrada"));

        if(i == null) {
            throw new NotFoundException("No se encuentra ninguna investigación con el ID especificado.");
        } else {

            InvestigacionDTO investigacionDTO = mapToDTO(i);

            investigacionRepository.delete(i);

            return investigacionDTO;

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

    private Investigacion mapToInvestigacion(InvestigacionDTO investigacionDTO) {

        Investigacion investigacion = new Investigacion();

        investigacion.setIdInvestigacion(investigacionDTO.getIdInvestigacion());
        investigacion.setInvestigador(usuarioRepository.getReferenceById(investigacionDTO.getIdInvestigador()));

        List<Long> pecesDTO = investigacionDTO.getPeces();
        List<Pez> peces = new ArrayList<Pez>();

        for (Long pDTO : pecesDTO) {

            peces.add(pezRepository.getReferenceById(pDTO));

        }

        investigacion.setPeces(peces);
        investigacion.setTitulo(investigacionDTO.getTitulo());
        investigacion.setResumen(investigacionDTO.getResumen());
        investigacion.setLugar(investigacionDTO.getLugar());
        investigacion.setFecha(investigacionDTO.getFecha());
        investigacion.setHora(investigacionDTO.getHora());

        return investigacion;

    }

}
