package com.generation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.generation.database.Database;
import com.generation.models.Libro;

public class LibroDao {

    private final Database database;
    private static LibroDao instance;

    private LibroDao() {
        database = Database.getInstance();
    }

    public static LibroDao getInstance() {
        if (instance == null) {
            instance = new LibroDao();
        }
        return instance;
    }

    // CREATE
    public void save(Libro libro) {
        database.openConnection();
        if (libro == null) {
            throw new NullPointerException("Il libro non può essere null");
        }

        String insert = "INSERT INTO libri (titolo, pagine, id_autore) VALUES (?, ?, ?)";
        Connection connection = database.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(insert)) {
            ps.setString(1, libro.getTitolo());
            ps.setInt(2, libro.getPagine());
            ps.setLong(3, libro.getIdAutore());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nella insert di LibroDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }
    }

    // READ
    public Map<Long, Libro> readAll() {
        Map<Long, Libro> result = new HashMap<>();
        database.openConnection();
        String selectAll = "SELECT * FROM libri";
        Connection connection = database.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(selectAll);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String titolo = rs.getString("titolo");
                int pagine = rs.getInt("pagine");
                Long idAutore = rs.getLong("id_autore");

                Libro libro = new Libro(id, titolo, pagine, idAutore);
                result.put(id, libro);
            }
        } catch (SQLException e) {
            System.out.println("Errore nella readAll di LibroDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }
        return result;
    }

    // UPDATE
    public void update(Libro libro) {
        database.openConnection();
        String update = "UPDATE libri SET titolo = ?, pagine = ?, id_autore = ? WHERE id = ?";
        Connection connection = database.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(update)) {
            ps.setString(1, libro.getTitolo());
            ps.setInt(2, libro.getPagine());
            ps.setLong(3, libro.getIdAutore());
            ps.setLong(4, libro.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'update di LibroDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }
    }

    // DELETE
    public void delete(Long id) {
        database.openConnection();
        String delete = "DELETE FROM libri WHERE id = ?";
        Connection connection = database.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(delete)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nella delete di LibroDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }
    }
}