package com.example.examentap.models;

public class Estado {
    private String estado;
    private int id_estado;

    public Estado() {}
    public Estado(String estado, int id_estado) {
        this.estado = estado;
        this.id_estado = id_estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }
}
