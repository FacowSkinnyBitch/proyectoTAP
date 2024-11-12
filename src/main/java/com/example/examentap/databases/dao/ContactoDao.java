package com.example.examentap.databases.dao;

import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Contacto;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ContactoDao extends MySQLConnection implements Dao<Contacto> {
    Connection conn = getConnection();

    @Override
    public Optional<Contacto> findById(int id) {
        Optional<Contacto> optionalC = Optional.empty();
        String query = "SELECT * FROM contacto WHERE id_contacto = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Contacto c = new Contacto();
                c.setId_contacto(rs.getInt("id_contacto"));
                c.setNombre_completo(rs.getString("nombre_completo"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getInt("telefono"));
                c.setFecha_cita(rs.getDate("fecha_cita"));
                c.setHora_cita(rs.getTime("hora_cita"));
                c.setPropiedad(rs.getInt("propiedad"));

                optionalC = Optional.of(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalC;
    }

    @Override
    public List<Contacto> findAll() {
        List<Contacto> contactoList = FXCollections.observableArrayList();
        String query = "SELECT * FROM contacto";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Contacto c = new Contacto();
                c.setId_contacto(rs.getInt("id_contacto"));
                c.setNombre_completo(rs.getString("nombre_completo"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getInt("telefono"));
                c.setFecha_cita(rs.getDate("fecha_cita"));
                c.setHora_cita(rs.getTime("hora_cita"));
                c.setPropiedad(rs.getInt("propiedad"));

                contactoList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactoList;
    }

    @Override
    public boolean save(Contacto c) {
        String query = "INSERT INTO contacto (nombre_completo, correo, telefono, fecha_cita, hora_cita, propiedad) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, c.getNombre_completo());
            ps.setString(2, c.getCorreo());
            ps.setInt(3, c.getTelefono());
            ps.setDate(4, c.getFecha_cita());
            ps.setTime(5, c.getHora_cita());
            ps.setInt(6, c.getPropiedad());
            ps.execute();
            System.out.println("Información cargada con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Contacto c) {
        String query = "UPDATE contacto SET nombre_completo = ?, correo = ?, telefono = ?, fecha_cita = ?, hora_cita = ?, propiedad=? WHERE id_contacto = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, c.getNombre_completo());
            ps.setString(2, c.getCorreo());
            ps.setInt(3, c.getTelefono());
            ps.setDate(4, c.getFecha_cita());
            ps.setTime(5, c.getHora_cita());
            ps.setInt(6, c.getPropiedad());
            ps.setInt(7, c.getId_contacto());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM contacto WHERE id_contacto = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

