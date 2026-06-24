package com.generation.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.generation.database.Database;

public class AutoreDao {
    private final Database database;
    private static AutoreDao instance;
    private static final String insert = "INSERT INTO autori (nome, cognome) VALUES (?, ?)";

    private AutoreDao() {
        database = Database.getInstance();
    }

    public static AutoreDao getInstance() {
        if (instance == null) {
            instance = new AutoreDao();
        }
        return instance;
    }

    public void save(Autore autore) {
        database.openConnection();
        if (autore == null) {
            throw new NullPointerException("L'autore non può essere nullo");
        }
        try (PreparedStatement ps = database.getConnection().prepareStatement(insert)) {
            ps.setNString(1, autore.getNome());
            ps.setNString(2, autore.getCognome());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'inserimento dell'autore"+e.getMessage());
        } finally {
            database.closeConnection();
        }

    }

    public void delete(Long id) {
        database.openConnection();
        String delete = "DELETE FROM autori WHERE id = ?";
        try (PreparedStatement ps = database.getConnection().prepareStatement(delete)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nella cancellazione dell'autore"+e.getMessage());
        } finally {
            database.closeConnection();
        }
    }
}
