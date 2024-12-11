package com.es.api_investigacion_marina.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "investigaciones")
public class Investigacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_investigacion")
    private Long idInvestigacion;

    @ManyToOne
    @JoinColumn(name = "id_investigador")
    private Usuario investigador;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "investigaciones_peces",
            joinColumns = @JoinColumn(name = "id_investigacion"),
            inverseJoinColumns = @JoinColumn(name = "id_pez"))
    private List<Pez> peces;

    private String titulo;

    @Column(length = 9000)
    private String resumen;

    private String lugar;

    private LocalDate fecha;

    private LocalTime hora;

    // Constructores

    public Investigacion(Long idInvestigacion, Usuario investigador, List<Pez> peces, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.idInvestigacion = idInvestigacion;
        this.investigador = investigador;
        this.peces = peces;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Investigacion(Usuario investigador, List<Pez> peces, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.investigador = investigador;
        this.peces = peces;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Investigacion(Usuario investigador, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora, Long idInvestigacion) {
        this.investigador = investigador;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
        this.idInvestigacion = idInvestigacion;
    }

    public Investigacion(Usuario investigador, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.investigador = investigador;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Investigacion() {}

    // Getters y Setters

    public Long getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(Long idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public Usuario getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Usuario investigador) {
        this.investigador = investigador;
    }

    public List<Pez> getPeces() {
        return peces;
    }

    public void setPeces(List<Pez> peces) {
        this.peces = peces;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
