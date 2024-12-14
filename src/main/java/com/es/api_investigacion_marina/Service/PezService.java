package com.es.api_investigacion_marina.Service;

import com.es.api_investigacion_marina.DTO.InvestigacionDTO;
import com.es.api_investigacion_marina.DTO.PezDTO;
import com.es.api_investigacion_marina.DTO.UsuarioDTO;
import com.es.api_investigacion_marina.DTO.UsuarioRegisterDTO;
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
import java.util.Objects;

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

            List<Long> investigacionesDTO = pezDTO.getApariciones();
            List<Investigacion> investigaciones = investigacionRepository.findAll();

            for (Long iDTO : investigacionesDTO) {

                boolean existe = false;

                for (Investigacion i : investigaciones) {

                    if (i.getIdInvestigacion() == iDTO) {

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

    public PezDTO getById(String idPez) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idPez);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Pez p = null;
        try {
            p = pezRepository
                    .findById(idL)
                    .orElse(null);
        } catch (Exception e) {
            throw new InternalServerErrorException("Un error inesperado ha ocurrido al buscar el pez por su ID.");
        }

        if(p == null) {
            throw new NotFoundException("No se encuentra ningún pez con el ID especificado.");
        } else {

            return mapToDTO(p);

        }

    }

    public List<PezDTO> getAll() {

        List<PezDTO> listaDeDTOs = new ArrayList<>();

        List<Pez> listaPez = pezRepository.findAll();

        for (Pez p: listaPez) {

            listaDeDTOs.add(mapToDTO(p));

        }

        return listaDeDTOs;

    }

    public PezDTO update(String idPez, PezDTO pezDTO) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idPez);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

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

            List<Long> investigacionesDTO = pezDTO.getApariciones();
            List<Investigacion> investigaciones = investigacionRepository.findAll();

            for (Long iDTO : investigacionesDTO) {

                boolean existe = false;

                for (Investigacion i : investigaciones) {

                    if (i.getIdInvestigacion() == iDTO) {

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

        // Compruebo que el pez existe en la BDD
        Pez p = pezRepository.findById(idL).orElse(null);

        if (p == null) {

            return null;

        } else {

            Pez newP = mapToPez(pezDTO);

            newP.setIdPez(p.getIdPez());

            pezRepository.save(newP);

            return mapToDTO(newP);

        }

    }

    public PezDTO delete(String idPez) {

        // Parsear el id a Long
        Long idL = 0L;
        try {
            idL = Long.parseLong(idPez);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El campo ID no tiene un formato válido.");
        }

        Pez p = pezRepository
                .findById(idL)
                .orElseThrow(() -> new NotFoundException("Pez con ID "+idPez+" no encontrado"));

        if(p == null) {
            throw new NotFoundException("No se encuentra ningún pez con el ID especificado.");
        } else {

            //Actualizamos todas las investigaciones relacionadas con dicho pez

            List<Investigacion> investigacionesP = investigacionRepository.findAll();

            for (Investigacion i: investigacionesP) {

                List<Pez> pecesI = i.getPeces();

                pecesI.removeIf(pezz -> Objects.equals(pezz.getIdPez(), p.getIdPez()));

                i.setPeces(pecesI);

                investigacionRepository.save(i);

            }

            PezDTO pezDTO = mapToDTO(p);

            pezRepository.delete(p);

            return pezDTO;

        }


    }

    private Pez mapToPez(PezDTO pezDTO) {

        Pez pez = new Pez();

        pez.setIdPez(pezDTO.getIdPez());
        pez.setInvestigador(usuarioRepository.getReferenceById(pezDTO.getIdInvestigador()));

        List<Long> investigacionesDTO = pezDTO.getApariciones();
        List<Investigacion> investigaciones = new ArrayList<Investigacion>();

        for (Long iDTO : investigacionesDTO) {

            investigaciones.add(investigacionRepository.getReferenceById(iDTO));

        }

        pez.setApariciones(investigaciones);
        pez.setNombreComun(pezDTO.getNombreComun());
        pez.setNombreCientifico(pezDTO.getNombreCientifico());
        pez.setEspecie(pezDTO.getEspecie());
        pez.setDieta(pezDTO.getDieta());
        pez.setDescripcion(pezDTO.getDescripcion());
        pez.setEjemplares(pezDTO.getEjemplares());
        pez.setTamMax(pezDTO.getTamMax());
        pez.setPeligroExtincion(pezDTO.isPeligroExtincion());

        return pez;

    }

    private PezDTO mapToDTO(Pez pez) {

        PezDTO pezDTO = new PezDTO();

        pezDTO.setIdPez(pez.getIdPez());
        pezDTO.setIdInvestigador(pez.getInvestigador().getId());

        List<Long> investigacionesDTO = new ArrayList<Long>();
        List<Investigacion> investigaciones = pez.getApariciones();

        for (Investigacion i : investigaciones) {

            investigacionesDTO.add(i.getIdInvestigacion());

        }

        pezDTO.setApariciones(investigacionesDTO);
        pezDTO.setNombreComun(pez.getNombreComun());
        pezDTO.setNombreCientifico(pez.getNombreCientifico());
        pezDTO.setEspecie(pez.getEspecie());
        pezDTO.setDieta(pez.getDieta());
        pezDTO.setDescripcion(pez.getDescripcion());
        pezDTO.setEjemplares(pez.getEjemplares());
        pezDTO.setTamMax(pez.getTamMax());
        pezDTO.setPeligroExtincion(pez.isPeligroExtincion());

        return pezDTO;

    }


}
