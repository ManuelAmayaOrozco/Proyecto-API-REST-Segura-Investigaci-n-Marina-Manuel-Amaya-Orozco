package com.es.api_investigacion_marina.Exception;

/*
Usada cuando un usuario intenta acceder a un endpoint y no posee los criterios necesarios.
 */
public class NotAuthorizedException extends RuntimeException {

    private static final String DESCRIPCION = "Not Authorized (401)";

    public NotAuthorizedException(String mensaje) {

        super(DESCRIPCION +". "+ mensaje);

    }
}
