package com.example.examentap.databases.dao;

import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Ciudad;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CiudadDao extends MySQLConnection implements Dao<Ciudad> {
    Connection conn = getConnection();
    @Override
    public Optional<Ciudad> findById(int id) {
        Optional<Ciudad> optionalCiudad = Optional.empty();
        String query = "select * FROM ciudad WHERE id_ciudad = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if( rs.next() ){
                Ciudad c = new Ciudad();
                c.setId_Ciudad(rs.getInt("id_ciudad"));
                c.setCiudad(rs.getString("ciudad"));
                c.setEstado(rs.getString("estado"));
                optionalCiudad = Optional.of(c);

            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optionalCiudad;
    }

    @Override
    public List<Ciudad> findAll() {
        List<Ciudad> ciudadesList = FXCollections.observableArrayList();
        String query = "SELECT c.id_ciudad, c.ciudad, c.id_estado " +
                "FROM ciudad c" +
                "JOIN estado edo on c.id_estado = edo.id_estado" +
                "ORDER BY c.id_ciudad asc";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Ciudad c = new Ciudad();
                c.setId_Ciudad(rs.getInt("id_ciudad"));
                c.setCiudad(rs.getString("ciudad"));
                c.setEstado(rs.getString("estado"));
                ciudadesList.add(c);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return List.of();
    }

    @Override
    public boolean save(Ciudad record) {

        return false;
    }

    @Override
    public boolean update(Ciudad record) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
