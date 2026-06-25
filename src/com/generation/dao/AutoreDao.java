package com.generation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.generation.database.Database;
import com.generation.models.Autore;

public class AutoreDao {

   
    private final Database database;
    private static AutoreDao instance;
    private static String insert = "insert into autori(nome,cognome) values (?,?)";


    private AutoreDao(){
       database = Database.getInstance();
    }

    public static AutoreDao getInstance(){
        if(instance == null){
            instance = new AutoreDao();
        }
        return instance;
    }

    
    public void save(Autore autore){
        database.openConnection();
        if(autore == null){
            throw new NullPointerException("L'autore non può essere null");
        }

        Connection connection = database.getConnection();

        try(PreparedStatement ps = connection.prepareStatement(insert)){
      
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());

      
            int nRows = ps.executeUpdate();
            System.out.println("Numero di righe manipolate: " + nRows);
        }catch(SQLException e){
            System.out.println("errore nella insert di AutoreDao: " + e.getMessage());
        }finally{
            database.closeConnection();
        }

    }

    public Map<Long,Autore> readAll(){
        Map<Long,Autore> result = new HashMap<>();
        database.openConnection();
        String selectAll = "SELECT * FROM autori";
        Connection connection = database.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(selectAll);
        
            ResultSet rs = ps.executeQuery()){
  

       
        while(rs.next()){ 
        

            Long id = rs.getLong(1); 
            String nome = rs.getString("nome");
            String cognome = rs.getString(3);

            Autore autore = new Autore(id, nome, cognome);
            result.put(id, autore);

        }

        }catch(SQLException e){
            System.out.println("Errore nella lettura della tabella autori in AutoreDao: " + e.getMessage());
        }
        return result;
    }


    
    public void update (Autore autore) {
        database.openConnection();
        String update = "UPDATE autori SET nome = ?, cognome = ? WHERE id = ?";
        Connection c = database.getConnection();
         try (PreparedStatement ps = c.prepareStatement(update)) {
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());
            ps.setLong(3, autore.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'update in AutoreDao: "+e.getMessage());
        } finally {
            database.closeConnection();
        }
    } 
    
    public void delete(Long id){
        database.openConnection();
        String delete = "delete from autori where id = ?";
        Connection c = database.getConnection();

        try(PreparedStatement ps = c.prepareStatement(delete)){
            ps.setLong(1, id); //se ad esempio id = 12L
            
        
            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println("Errore nella delete in AutoreDao: " + e.getMessage());
        }finally{
            database.closeConnection();
        }

    }


    
}
