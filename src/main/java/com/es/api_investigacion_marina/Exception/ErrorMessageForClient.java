package com.es.api_investigacion_marina.Exception;

/*
Estructura genérica usada para los mensajes de error que se muestran al cliente.
 */
public class ErrorMessageForClient {

    private String mensaje;
    private String uri;

    public ErrorMessageForClient(String mensaje, String uri) {
        this.mensaje = mensaje;
        this.uri = uri;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}