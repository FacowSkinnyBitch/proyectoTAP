package com.example.examentap.models;

public class Ciudad {
    private String ciudad;
    private int id_ciudad;
    private String estado;

    public Ciudad() {}
    public Ciudad(String ciudad, int id_Ciudad, String estado) {
        this.ciudad = ciudad;
        this.id_ciudad = id_Ciudad;
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getId_Ciudad() {
        return id_ciudad;
    }

    public void setId_Ciudad(int id_Ciudad) {
        this.id_ciudad = id_Ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
