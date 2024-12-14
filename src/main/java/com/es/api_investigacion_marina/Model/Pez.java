package com.es.api_investigacion_marina.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "peces")
public class Pez {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pez")
    private Long idPez;

    @ManyToOne
    @JoinColumn(name = "id_investigador")
    private Usuario investigador;

    @ManyToMany(mappedBy = "peces")
    private List<Investigacion> apariciones;

    @Column(name = "nombre_comun")
    private String nombreComun;

    @Column(name = "nombre_cientifico")
    private String nombreCientifico;

    private String especie;

    private String dieta;

    @Column(length = 9000)
    private String descripcion;

    private int ejemplares;

    @Column(name = "tamanio_maximo")
    private double tamMax;

    @Column(name = "peligro_extincion")
    private boolean peligroExtincion;

    // Constructores

    public Pez(Long idPez, Usuario investigador, List<Investigacion> apariciones, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, Double tamMax, boolean peligroExtincion) {
        this.idPez = idPez;
        this.investigador = investigador;
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

    public Pez(Usuario investigador, List<Investigacion> apariciones, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, Double tamMax, boolean peligroExtincion) {
        this.investigador = investigador;
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

    public Pez(Long idPez, Usuario investigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.idPez = idPez;
        this.investigador = investigador;
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.dieta = dieta;
        this.descripcion = descripcion;
        this.ejemplares = ejemplares;
        this.tamMax = tamMax;
        this.peligroExtincion = peligroExtincion;
    }

    public Pez(Usuario investigador, String nombreComun, String nombreCientifico, String especie, String dieta, String descripcion, int ejemplares, double tamMax, boolean peligroExtincion) {
        this.investigador = investigador;
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

    public Usuario getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Usuario investigador) {
        this.investigador = investigador;
    }

    public List<Investigacion> getApariciones() {
        return apariciones;
    }

    public void setApariciones(List<Investigacion> apariciones) {
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
