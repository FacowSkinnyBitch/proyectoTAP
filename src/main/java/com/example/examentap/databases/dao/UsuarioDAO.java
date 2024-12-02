package com.example.examentap.databases.dao;

import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Usuario;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO extends MySQLConnection implements Dao<Usuario> {
    Connection conn = getConnection();

    @Override
    public Optional<Usuario> findById(int id) {
        Optional<Usuario> optionalU = Optional.empty();
        String query = "SELECT * FROM usuario WHERE id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUser(rs.getString("user"));
                u.setNombre(rs.getString("nombre"));
                u.setPrimer_apellido(rs.getString("primer_apellido"));
                u.setSegundo_apellido(rs.getString("segundo_apellido"));
                u.setEmail(rs.getString("email"));
                u.setContraseya(rs.getString("contraseya"));
                u.setTelefono(rs.getString("telefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setNacimiento(rs.getDate("nacimiento"));
                u.setRole(rs.getString("role"));

                optionalU = Optional.of(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionalU;
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> userList = FXCollections.observableArrayList();
        String query = "SELECT * FROM usuario";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUser(rs.getString("user"));
                u.setNombre(rs.getString("nombre"));
                u.setPrimer_apellido(rs.getString("primer_apellido"));
                u.setSegundo_apellido(rs.getString("segundo_apellido"));
                u.setEmail(rs.getString("email"));
                u.setContraseya(rs.getString("contraseya"));
                u.setTelefono(rs.getString("telefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setGenero(rs.getString("genero"));
                u.setNacimiento(rs.getDate("nacimiento"));
                u.setRole(rs.getString("role"));

                userList.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public boolean save(Usuario usuario) {
        String query = "INSERT INTO usuario (user, nombre, primer_apellido, segundo_apellido, email, contraseya, telefono, direccion, genero, nacimiento, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, usuario.getUser());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getPrimer_apellido());
            statement.setString(4, usuario.getSegundo_apellido());
            statement.setString(5, usuario.getEmail());
            statement.setString(6, usuario.getContraseya());
            statement.setString(7, usuario.getTelefono());
            statement.setString(8, usuario.getDireccion());
            statement.setString(9, usuario.getGenero());
            statement.setDate(10, usuario.getNacimiento());
            statement.setString(11, usuario.getRole());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Usuario u) {
        String query = "UPDATE usuario SET " +
                "user=?, nombre=?, primer_apellido=?, segundo_apellido=?, email=?, contraseya=?, " +
                "telefono=?, direccion=?, genero=?, nacimiento=?, role=? WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, u.getUser());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getPrimer_apellido());
            ps.setString(4, u.getSegundo_apellido());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getContraseya());
            ps.setString(7, u.getTelefono());
            ps.setString(8, u.getDireccion());
            ps.setString(9, u.getGenero());
            ps.setDate(10, u.getNacimiento());
            ps.setString(11, u.getRole());
            ps.setInt(12, u.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        String deleteCitasQuery = "DELETE FROM datos_cita WHERE id_usuario = ?";
        String deleteUserQuery = "DELETE FROM usuario WHERE id = ?";

        try {
            // Primero, eliminamos las citas relacionadas con el usuario
            PreparedStatement ps1 = conn.prepareStatement(deleteCitasQuery);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            // Luego, eliminamos el usuario
            PreparedStatement ps2 = conn.prepareStatement(deleteUserQuery);
            ps2.setInt(1, id);
            ps2.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

