package com.example.examentap.databases.dao;

import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Estado;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EstadoDao extends MySQLConnection implements Dao<Estado> {
    Connection conn = getConnection();


    @Override
    public Optional<Estado> findById(int id) {
        Optional<Estado> optionalEstado = Optional.empty();
        String query = "SELECT * FROM estado WHERE id_estado = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Estado edo = new Estado();
                edo.setEstado(rs.getString("estado"));
                edo.setId_estado(rs.getInt("id_estado"));
                optionalEstado = Optional.of(edo);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalEstado;
    }

    @Override
    public List<Estado> findAll() {
        List<Estado> estadoList = FXCollections.observableArrayList();
        String query = "SELECT edo.estado, edo.id_estado " +
                "FROM estado edo";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Estado edo = new Estado();
                edo.setEstado(rs.getString("estado"));
                edo.setId_estado(rs.getInt("id_estado"));
                estadoList.add(edo);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public boolean save(Estado record) {
        return false;
    }

    @Override
    public boolean update(Estado record) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
