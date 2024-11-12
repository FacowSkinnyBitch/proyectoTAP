package com.example.examentap.models;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Contacto {
    private int id_contacto;
    private String nombre_completo;
    private String correo;
    private int telefono;
    private Date fecha_cita;
    private Time hora_cita;
    private int propiedad;

    public Contacto() {

    }
    public Contacto(int id_contacto,String nombre_completo, String correo, int telefono, Date fecha_cita, Time hora_cita, int propiedad) {
        this.id_contacto = id_contacto;
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.propiedad = propiedad;
    }

    public Contacto(String nombre_completo, String correo, int telefono, Date fecha_cita, Time hora_cita, int propiedad) {
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.propiedad = propiedad;
    }

    public int getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(int id_contacto) {
        this.id_contacto = id_contacto;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public java.sql.Date getFecha_cita() {
        return (java.sql.Date) fecha_cita;
    }

    public void setFecha_cita(Date fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public Time getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(Time hora_cita) {
        this.hora_cita = hora_cita;
    }
    public int getPropiedad() {
        return propiedad;
    }
    public void setPropiedad(int propiedad) {
        this.propiedad = propiedad;
    }
}
