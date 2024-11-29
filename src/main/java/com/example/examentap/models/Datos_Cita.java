package com.example.examentap.models;

import java.sql.Time;
import java.util.Date;

public class Datos_Cita {
    private int id_cita;
    private String nombre_completo;
    private String correo;
    private int telefono;
    private Date fecha_cita;
    private Time hora_cita;
    private int id_propiedad;
    private int id_usuario;

    public Datos_Cita() {

    }
    public Datos_Cita(int id_cita, String nombre_completo, String correo, int telefono, Date fecha_cita, Time hora_cita, int id_propiedad, int id_usuario) {
        this.id_cita = id_cita;
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.id_propiedad = id_propiedad;
        this.id_usuario = id_usuario;
    }

    public Datos_Cita(String nombre_completo, String correo, int telefono, Date fecha_cita, Time hora_cita, int id_propiedad, int id_usuario) {
        this.nombre_completo = nombre_completo;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.id_propiedad = id_propiedad;
        this.id_usuario = id_usuario;
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_contacto) {
        this.id_cita = id_contacto;
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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setHora_cita(Time hora_cita) {
        this.hora_cita = hora_cita;
    }

    public int getId_propiedad() {
        return id_propiedad;
    }

    public void setId_propiedad(int id_propiedad) {
        this.id_propiedad = id_propiedad;
    }
}
