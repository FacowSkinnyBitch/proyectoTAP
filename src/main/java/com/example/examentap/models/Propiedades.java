package com.example.examentap.models;

import java.util.Date;

public class Propiedades {
    private int id_propiedad;
    private String direccion;
    private double precio;
    private String descripcion;
    private int num_cuartos;
    private int num_bayos;
    private double metros_cuadrados;
    private String tipo_propiedad;
    private String status;
    private Date ayo_construccion;
    private String ciudad;
    private String imagen;
    public Propiedades() {}

    public Propiedades(int id_propiedad, String direccion, double precio, String descripcion, int num_cuartos, int num_bayos, double metros_cuadrados, String tipo_propiedad, String status, Date ayo_construccion, String ciudad, String imagen) {
        this.id_propiedad = id_propiedad;
        this.direccion = direccion;
        this.precio = precio;
        this.descripcion = descripcion;
        this.num_cuartos = num_cuartos;
        this.num_bayos = num_bayos;
        this.metros_cuadrados = metros_cuadrados;
        this.tipo_propiedad = tipo_propiedad;
        this.status = status;
        this.ayo_construccion = ayo_construccion;
        this.ciudad = ciudad;
        this.imagen = imagen;
    }

    public int getId_propiedad() {
        return id_propiedad;
    }

    public void setId_propiedad(int id_propiedad) {
        this.id_propiedad = id_propiedad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNum_cuartos() {
        return num_cuartos;
    }

    public void setNum_cuartos(int num_cuartos) {
        this.num_cuartos = num_cuartos;
    }

    public int getNum_bayos() {
        return num_bayos;
    }

    public void setNum_bayos(int num_bayos) {
        this.num_bayos = num_bayos;
    }

    public double getMetros_cuadrados() {
        return metros_cuadrados;
    }

    public void setMetros_cuadrados(double metros_cuadrados) {
        this.metros_cuadrados = metros_cuadrados;
    }

    public String getTipo_propiedad() {
        return tipo_propiedad;
    }

    public void setTipo_propiedad(String tipo_propiedad) {
        this.tipo_propiedad = tipo_propiedad;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.sql.Date getAyo_construccion() {
        return (java.sql.Date) ayo_construccion;
    }

    public void setAyo_construccion(Date ayo_construccion) {
        this.ayo_construccion = ayo_construccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    @Override
    public String toString() {
        return "Descripción general de la propiedad" +
                "\nID Propiedad: " + id_propiedad +
                "\nDireccion: " + direccion+
                "\nPrecio: " + precio +
                "\nDescripcion: " + descripcion+
                "\nNúmero de cuartos: " + num_cuartos +
                "\nNúmero de baños: " + num_bayos +
                "\nMetros cuadrados: " + metros_cuadrados +
                "\nTipo propiedad: " + tipo_propiedad +
                "\nStatus: " + status +
                "\nAño de construccion: " + ayo_construccion +
                "\nCiudad: " + ciudad;

    }
    public String showID() {
        return "ID: " + id_propiedad; // Cambia según tus necesidades
    }



}
