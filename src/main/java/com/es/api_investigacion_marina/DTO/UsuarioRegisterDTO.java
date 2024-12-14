package com.es.api_investigacion_marina.DTO;

/*
Clase DTO de Usuario, usada durante la creación de un nuevo usuario, incluye solo el nombre, contraseña y el rol.
 */
public class UsuarioRegisterDTO {

    private String username;
    private String password;
    private String roles;

    // Constructores

    public UsuarioRegisterDTO(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UsuarioRegisterDTO(){}

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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
