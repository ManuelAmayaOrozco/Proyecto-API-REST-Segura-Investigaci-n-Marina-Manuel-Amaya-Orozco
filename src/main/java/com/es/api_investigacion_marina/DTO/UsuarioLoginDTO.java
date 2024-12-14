package com.es.api_investigacion_marina.DTO;

/*
Clase DTO de Usuario, usada durante el login, que solo requiere de usuario y contraseña.
 */
public class UsuarioLoginDTO {

    private String username;
    private String password;

    // Constructores

    public UsuarioLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsuarioLoginDTO(){}

    // Getters y Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
