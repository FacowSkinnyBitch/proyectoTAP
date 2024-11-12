package com.example.examentap.models;

public class Tipo_Propiedad {
    private int id_tipo_propiedad;
    private String tipo_propiedad;

    public Tipo_Propiedad() {}
    public Tipo_Propiedad(int id_tipo_propiedad, String tipo_propiedad) {
        this.id_tipo_propiedad = id_tipo_propiedad;
        this.tipo_propiedad = tipo_propiedad;
    }

    public int getId_tipo_propiedad() {
        return id_tipo_propiedad;
    }

    public void setId_tipo_propiedad(int id_tipo_propiedad) {
        this.id_tipo_propiedad = id_tipo_propiedad;
    }

    public String getTipo_propiedad() {
        return tipo_propiedad;
    }

    public void setTipo_propiedad(String tipo_propiedad) {
        this.tipo_propiedad = tipo_propiedad;
    }
}
