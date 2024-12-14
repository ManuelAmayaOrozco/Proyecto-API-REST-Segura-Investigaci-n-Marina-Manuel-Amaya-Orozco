package com.es.api_investigacion_marina.Exception;

/*
Usada cuando una petición viene con sintaxis o información correcta.
 */
public class BadRequestException extends RuntimeException {

    private static final String DESCRIPCION = "Bad Request (400)";

    public BadRequestException(String mensaje) {

        super(DESCRIPCION + ". " + mensaje);

    }

}