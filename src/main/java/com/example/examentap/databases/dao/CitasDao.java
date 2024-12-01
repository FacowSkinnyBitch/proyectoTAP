package com.example.examentap.databases.dao;

import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Datos_Cita;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CitasDao extends MySQLConnection implements Dao<Datos_Cita> {
    Connection conn = getConnection();

    @Override
    public Optional<Datos_Cita> findById(int id) {
        Optional<Datos_Cita> optionalC = Optional.empty();
        String query = "SELECT * FROM datos_cita WHERE id_contacto = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Datos_Cita c = new Datos_Cita();
                c.setId_cita(rs.getInt("id_cita"));
                c.setNombre_completo(rs.getString("nombre_completo"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getInt("telefono"));
                c.setFecha_cita(rs.getDate("fecha_cita"));
                c.setHora_cita(rs.getTime("hora_cita"));
                c.setId_propiedad(rs.getInt("id_propiedad"));
                c.setId_usuario(rs.getInt("id_usuario"));

                optionalC = Optional.of(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalC;
    }

    @Override
    public List<Datos_Cita> findAll() {
        List<Datos_Cita> datosCitaList = FXCollections.observableArrayList();
        String query = "SELECT * FROM datos_cita";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Datos_Cita c = new Datos_Cita();
                c.setId_cita(rs.getInt("id_cita"));
                c.setNombre_completo(rs.getString("nombre_completo"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getInt("telefono"));
                c.setFecha_cita(rs.getDate("fecha_cita"));
                c.setHora_cita(rs.getTime("hora_cita"));
                c.setId_propiedad(rs.getInt("id_propiedad"));
                c.setId_usuario(rs.getInt("id_usuario"));
                datosCitaList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datosCitaList;
    }

    public List<Datos_Cita> findCitaByUser(int id_usuario) {
        List<Datos_Cita> datosCitaList = FXCollections.observableArrayList();
        String query = "SELECT * FROM datos_cita dc " + "JOIN usuario u ON dc.id_usuario = u.id " + "WHERE u.id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id_usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Datos_Cita c = new Datos_Cita();
                c.setId_cita(rs.getInt("id_cita"));
                c.setNombre_completo(rs.getString("nombre_completo"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getInt("telefono"));
                c.setFecha_cita(rs.getDate("fecha_cita"));
                c.setHora_cita(rs.getTime("hora_cita"));
                c.setId_propiedad(rs.getInt("id_propiedad"));
                datosCitaList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return datosCitaList;
    }
    public int countCitaByStatus(String status) {
        String query = "SELECT COUNT(c.status) AS total FROM datos_cita c WHERE status = '" + status + "'";
        int total = 0;

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    @Override
    public boolean save(Datos_Cita c) {
        String query = "INSERT INTO datos_cita (nombre_completo, correo, telefono, fecha_cita, hora_cita, id_propiedad, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, c.getNombre_completo());
            ps.setString(2, c.getCorreo());
            ps.setInt(3, c.getTelefono());
            ps.setDate(4, c.getFecha_cita());
            ps.setTime(5, c.getHora_cita());
            ps.setInt(6, c.getId_propiedad());
            ps.setInt(7, c.getId_usuario());
            ps.execute();
            System.out.println("Información cargada con exito");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Datos_Cita c) {
        String query = "UPDATE datos_cita SET nombre_completo = ?, correo = ?, telefono = ?, fecha_cita = ?, hora_cita = ?, id_propiedad=?, id_usuario WHERE id_cita = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, c.getNombre_completo());
            ps.setString(2, c.getCorreo());
            ps.setInt(3, c.getTelefono());
            ps.setDate(4, c.getFecha_cita());
            ps.setTime(5, c.getHora_cita());
            ps.setInt(6, c.getId_propiedad());
            ps.setInt(7, c.getId_usuario());
            ps.setInt(8, c.getId_cita());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM datos_cita WHERE id_cita = ?";
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

