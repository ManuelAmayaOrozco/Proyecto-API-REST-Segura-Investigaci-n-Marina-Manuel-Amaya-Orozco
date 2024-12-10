package com.es.api_investigacion_marina.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "peces")
public class Pez {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pez")
    private Long idPez;

    @ManyToOne
    @JoinColumn(name = "id_investigador")
    private Long idInvestigador;

    @Column(name = "nombre_comun")
    private String nombreComun;

    @Column(name = "nombre_cientifico")
    private String nombreCientifico;

    private String especie;

    private String dieta;

    private String descripcion;

    private int ejemplares;

    @Column(name = "tamaño_maximo")
    private Double tamMax;

    @Column(name = "peligro_extincion")
    private boolean peligroExtincion;

    // Constructores

    public Pez(Long idPez, Long idInvestigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, Double tamMax, boolean peligroExtincion) {
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

    public Pez(Long idInvestigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, Double tamMax, boolean peligroExtincion) {
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

    public Pez() {}

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

    public Double getTamMax() {
        return tamMax;
    }

    public void setTamMax(Double tamMax) {
        this.tamMax = tamMax;
    }

    public boolean isPeligroExtincion() {
        return peligroExtincion;
    }

    public void setPeligroExtincion(boolean peligroExtincion) {
        this.peligroExtincion = peligroExtincion;
    }
}
