package com.es.api_investigacion_marina.Exception;

/*
Usada cuando se intenta buscar un elemento de la base de datos, pero no se encuentran resultados.
 */
public class NotFoundException extends RuntimeException {

    private static final String DESCRIPCION = "Not Found (404)";

    public NotFoundException(String mensaje) {

        super(DESCRIPCION + ". " + mensaje);

    }
}