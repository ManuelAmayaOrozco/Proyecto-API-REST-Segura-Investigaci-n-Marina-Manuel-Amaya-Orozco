package com.es.api_investigacion_marina.DTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InvestigacionDTO {

    private Long idInvestigacion;
    private Long idInvestigador;
    private List<Long> peces;
    private String titulo;
    private String resumen;
    private String lugar;
    private LocalDate fecha;
    private LocalTime hora;

    // Constructores

    public InvestigacionDTO(Long idInvestigacion, Long idInvestigador, List<Long> peces, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.idInvestigacion = idInvestigacion;
        this.idInvestigador = idInvestigador;
        this.peces = peces;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public InvestigacionDTO(Long idInvestigador, List<Long> peces, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.idInvestigador = idInvestigador;
        this.peces = peces;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public InvestigacionDTO(Long idInvestigacion, Long idInvestigador, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.idInvestigacion = idInvestigacion;
        this.idInvestigador = idInvestigador;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public InvestigacionDTO(Long idInvestigador, String titulo, String resumen, String lugar, LocalDate fecha, LocalTime hora) {
        this.idInvestigador = idInvestigador;
        this.titulo = titulo;
        this.resumen = resumen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public InvestigacionDTO() {}

    // Getters y Setters

    public Long getIdInvestigacion() {
        return idInvestigacion;
    }

    public void setIdInvestigacion(Long idInvestigacion) {
        this.idInvestigacion = idInvestigacion;
    }

    public Long getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(Long idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public List<Long> getPeces() {
        return peces;
    }

    public void setPeces(List<Long> peces) {
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
