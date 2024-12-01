
package com.example.examentap.databases.dao;
import com.example.examentap.databases.MySQLConnection;
import com.example.examentap.models.Propiedades;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PropiedadesDao extends MySQLConnection implements Dao<Propiedades> {
    Connection conn = getConnection();
    @Override
    public Optional<Propiedades> findById(int id) {
        Optional<Propiedades> optionalP = Optional.empty();
        String query = "select * from propiedad where id_propiedad = ? ";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if ( rs.next() )
            {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getInt("metros_cuadrados"));
                p.setTipo_propiedad(rs.getString("tipo_propiedad"));
                p.setStatus(rs.getString("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setImagen(rs.getString("imagen"));
                optionalP = Optional.of(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optionalP;
    }
    /*@Override
    public List<Propiedades> findAll() {

        List<Propiedades> propiedadesList = FXCollections.observableArrayList();
        String query = "select * from propiedades";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next())
            {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getInt("metros_cuadrados"));
                p.setTipo_propiedad(rs.getInt("tipo_propiedad"));
                p.setContacto(rs.getInt("contacto"));
                p.setStatus(rs.getBoolean("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setImagen(rs.getString("imagen"));
                propiedadesList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propiedadesList;
    }*/
    @Override
    public List<Propiedades> findAll() {
        List<Propiedades> propiedadesList = FXCollections.observableArrayList();
        String query = "SELECT p.id_propiedad, p.direccion, p.precio, p.descripcion, p.num_cuartos, p.num_bayos, " +
                "p.metros_cuadrados, tp.tipo_propiedad, p.status, p.ayo_construccion, c.ciudad, p.imagen " +
                "FROM propiedad p " +
                "JOIN tipo_propiedad tp ON p.tipo_propiedad = tp.id_tipo_propiedad " +
                "JOIN ciudad c on p.id_ciudad = c.id_ciudad " +
                "order by p.id_propiedad asc ";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDireccion(rs.getString("direccion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getDouble("metros_cuadrados"));
                p.setTipo_propiedad(rs.getString("tipo_propiedad")); // tipo de propiedad
                p.setStatus(rs.getString("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setImagen(rs.getString("imagen"));
                propiedadesList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propiedadesList;
    }

    public List<Propiedades> filterPropByStatus(String status) {
        List<Propiedades> propiedadesList = FXCollections.observableArrayList();
        String query = "select * from propiedad p "+
                "JOIN ciudad c ON p.id_ciudad = c.id_ciudad "+
                "JOIN tipo_propiedad tp on p.tipo_propiedad = tp.id_tipo_propiedad "+
                "where status = '"+status+"' ";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDireccion(rs.getString("direccion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getDouble("metros_cuadrados"));
                p.setTipo_propiedad(rs.getString("tipo_propiedad")); // tipo de propiedad
                p.setStatus(rs.getString("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setImagen(rs.getString("imagen"));
                propiedadesList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propiedadesList;

    }

    public int countPropByStatus(String status) {
        String query = "SELECT COUNT(p.status) AS total FROM propiedad p WHERE status = '" + status + "'";
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


    public List<Propiedades> filterPropByTipoProp(int id) {
        List<Propiedades> propiedadesList = FXCollections.observableArrayList();
        String query = "select * from propiedad p " +
                        "join tipo_propiedad tp on p.tipo_propiedad = tp.id_tipo_propiedad " +
                        "JOIN ciudad c ON p.id_ciudad = c.id_ciudad " +
                        "where tp.id_tipo_propiedad = '"+ id+"' ";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDireccion(rs.getString("direccion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getDouble("metros_cuadrados"));
                p.setTipo_propiedad(rs.getString("tipo_propiedad")); // tipo de propiedad
                p.setStatus(rs.getString("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setImagen(rs.getString("imagen"));
                propiedadesList.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propiedadesList;
        /*
         select*from propiedad p join tipo_propiedad tp on p.tipo_propiedad = tp.id_tipo_propiedad where tp.id_tipo_propiedad=1;
         */
    }
    public Map<String, Integer> countPropsByTipoProp() {
        Map<String, Integer> results = new HashMap<>();
        String query = "SELECT tp.tipo_propiedad AS tipo_propiedad, COUNT(*) AS total " +
                "FROM propiedad p " +
                "JOIN tipo_propiedad tp ON p.tipo_propiedad = tp.id_tipo_propiedad " +
                "GROUP BY tp.tipo_propiedad " +
                "ORDER BY total DESC";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                results.put(rs.getString("tipo_propiedad"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }



    public List<Propiedades> filterPropByCiudad(int id) {
        List<Propiedades> propiedadesList = FXCollections.observableArrayList();
        String query = "Select * From propiedad p " +
                "Join ciudad c on p.id_ciudad = c.id_ciudad " +
                "join tipo_propiedad tp on p.tipo_propiedad = tp.id_tipo_propiedad " +
                "where c.id_ciudad = '"+ id+"' ";
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Propiedades p = new Propiedades();
                p.setId_propiedad(rs.getInt("id_propiedad"));
                p.setDireccion(rs.getString("direccion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setNum_cuartos(rs.getInt("num_cuartos"));
                p.setNum_bayos(rs.getInt("num_bayos"));
                p.setMetros_cuadrados(rs.getDouble("metros_cuadrados"));
                p.setTipo_propiedad(rs.getString("tipo_propiedad")); // tipo de propiedad
                p.setStatus(rs.getString("status"));
                p.setAyo_construccion(rs.getDate("ayo_construccion"));
                p.setCiudad(rs.getString("ciudad"));
                p.setImagen(rs.getString("imagen"));
                propiedadesList.add(p);

            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return propiedadesList;
    }

    @Override
    public boolean save(Propiedades p) {
        String query = "insert into propiedad " +
                        " (direccion, precio, descripcion,num_cuartos,num_bayos,metros_cuadrados,tipo_propiedad,status,ayo_construccion,id_ciudad,imagen)" +
                        " values (?, ?, ?, ?, ?, ?,?,?,?,?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, p.getDireccion());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3,p.getDescripcion());
            ps.setInt(4, p.getNum_cuartos());
            ps.setInt(5, p.getNum_bayos());
            ps.setDouble(6, p.getMetros_cuadrados());
            ps.setString(7, p.getTipo_propiedad());
            ps.setString(8,p.getStatus());
            ps.setDate(9,p.getAyo_construccion());
            ps.setString(10, p.getCiudad());
            ps.setString(11, p.getImagen());
            ps.execute();
            return true;
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean update(Propiedades p) {
        String query = "update propiedad set" +
                "direccion=?, precio=?, descripcion=?,num_cuartos=?,num_bayos=?,metros_cuadrados=?,tipo_propiedad=?,status=?,ayo_construccion=?, id_ciudad = ?,imagen=? " +
                "where id_propiedad = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, p.getDireccion());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3,p.getDescripcion());
            ps.setInt(4, p.getNum_cuartos());
            ps.setInt(5, p.getNum_bayos());
            ps.setDouble(6, p.getMetros_cuadrados());
            ps.setString(7, p.getTipo_propiedad());
            ps.setString(8,p.getStatus());
            ps.setDate(9, p.getAyo_construccion());
            ps.setString(10, p.getCiudad());
            ps.setString(11, p.getImagen());
            ps.setInt(12, p.getId_propiedad());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
@Override
    public boolean delete(int p) {
        String query = "delete from propiedad where id_propiedad = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, p);
            ps.execute();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

