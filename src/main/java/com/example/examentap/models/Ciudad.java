package com.example.examentap.models;

public class Ciudad {
    private String ciudad;
    private int id_ciudad;
    private int id_estado;

    public Ciudad() {}
    public Ciudad(String ciudad, int id_Ciudad, int id_estado) {
        this.ciudad = ciudad;
        this.id_ciudad = id_Ciudad;
        this.id_estado = id_estado;
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

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }
}
