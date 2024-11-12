package com.example.examentap.models;

import java.util.Date;

public class Usuario {
    private int id;
    private String user;
    private String nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String email;
    private String contraseya;
    private String telefono;
    private String direccion;
    private String genero;
    private Date nacimiento;
    private String role;

    public Usuario() {}

    public Usuario(int id, String user, String nombre, String primer_apellido, String segundo_apellido, String email, String contraseya, String telefono, String direccion, String genero, Date nacimiento, String role) {
        this.id = id;
        this.user = user;
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.email = email;
        this.contraseya = contraseya;
        this.telefono = telefono;
        this.direccion = direccion;
        this.genero = genero;
        this.nacimiento = nacimiento;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseya() {
        return contraseya;
    }

    public void setContraseya(String contraseya) {
        this.contraseya = contraseya;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public java.sql.Date getNacimiento() {
        return (java.sql.Date) nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
