package com.es.api_investigacion_marina.DTO;

import java.util.List;

public class PezDTO {

    private Long idPez;
    private Long idInvestigador;
    private List<Long> apariciones;
    private String nombreComun;
    private String nombreCientifico;
    private String especie;
    private String dieta;
    private String descripcion;
    private int ejemplares;
    private double tamMax;
    private boolean peligroExtincion;

    // Constructores

    public PezDTO(Long idPez, Long idInvestigador, List<Long> apariciones, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.idPez = idPez;
        this.idInvestigador = idInvestigador;
        this.apariciones = apariciones;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.dieta = dieta;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
        this.tamMax = tamMax;
        this.peligroExtincion = peligroExtincion;
    }

    public PezDTO(Long idInvestigador, List<Long> apariciones, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.idInvestigador = idInvestigador;
        this.apariciones = apariciones;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.dieta = dieta;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
        this.tamMax = tamMax;
        this.peligroExtincion = peligroExtincion;
    }

    public PezDTO(Long idPez, Long idInvestigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.idPez = idPez;
        this.idInvestigador = idInvestigador;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.dieta = dieta;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
        this.tamMax = tamMax;
        this.peligroExtincion = peligroExtincion;
    }

    public PezDTO(Long idInvestigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.idInvestigador = idInvestigador;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.dieta = dieta;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
        this.tamMax = tamMax;
        this.peligroExtincion = peligroExtincion;
    }

    public PezDTO() {}

    // Getters y Setters

    public Long getIdPez() {
        return idPez;
    }

    public void setIdPez(Long idPez) {
        this.idPez = idPez;
    }

    public Long getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(Long idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public List<Long> getApariciones() {
        return apariciones;
    }

    public void setApariciones(List<Long> apariciones) {
        this.apariciones = apariciones;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public double getTamMax() {
        return tamMax;
    }

    public void setTamMax(double tamMax) {
        this.tamMax = tamMax;
    }

    public boolean isPeligroExtincion() {
        return peligroExtincion;
    }

    public void setPeligroExtincion(boolean peligroExtincion) {
        this.peligroExtincion = peligroExtincion;
    }
}
