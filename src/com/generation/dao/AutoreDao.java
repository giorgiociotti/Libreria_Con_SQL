package com.generation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.generation.database.Database;
import com.generation.models.Autore;

//DAO: Data Access Object -> Design Pattern architetturale per la gestione
//della persistenza di uno specifico dato, in questo caso degli autori
//conterrà i metodi CRUD: create, read, update e delete relativi agli autori
public class AutoreDao {

    //dipendenza di AutoreDao
    //è un oggetto di tipo Database per poter aprire e chiudere la connessione
    //un oggetto di tipo AutoreDao per poter funzionare 
    //ha bisogno di un oggetto di tipo Database per sfruttare e usare i suoi metodi
    //questo un uso di un oggetto come COMPOSIZIONE
    //la proprietà è final perché così si garantisce l'esistenza di questo oggetto
    //infatti o lo inizializzo qui dove lo dichiaro oppure nel costruttore ma sono
    //obbligato a farlo dal compilatore stesso, altrimenti mi segnala errore
    //sono quindi certa che questa proprietà non sarà mai null ma veerà sempre creata e inizializzata
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

    //metodo per salvare un autore nel db, passaggi:
    //- apro la connessione tramite il metodo openConnection()
    //- controllo che l'autore non sia null
    //- scrivo la query da inviare
    //- trasformo l'oggetto di tipo Autore in un riga sul db -> ORM
    public void save(Autore autore){
        database.openConnection();
        if(autore == null){
            throw new NullPointerException("L'autore non può essere null");
        }

        //prendo la connessione aperta a riga 49 dall'oggetto database
        Connection connection = database.getConnection();

        //creo il PrepareSattement usando la connessione e passandogli la DML da controllare e inviare
        try(PreparedStatement ps = connection.prepareStatement(insert)){

        // colonna si aspetta
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
        //apro la connessione
        database.openConnection();
        //creo la query che è una SELECT
        String selectAll = "SELECT * FROM autori";
        //creo il PreparedStatement e gli passo la query
        Connection connection = database.getConnection();
        try(PreparedStatement ps = connection.prepareStatement(selectAll);
             //se ci sono placeholder li scambio con i valori ma in questo caso non ci sono placeholder quindi non devo fare nulla
            //eseguo la query
            ResultSet rs = ps.executeQuery()){
       
        while(rs.next()){

            Long id = rs.getLong(1); 
         
            String nome = rs.getString("nome");
            String cognome = rs.getString(3);

            Autore autore = new Autore(id, nome, cognome);
            // che poi salvo nella mappa
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
            // chiudo la connessione
            database.closeConnection();
        }
    } 
    
    //un metodo per eliminare un autore
    public void delete(Long id) {

        database.openConnection();

        String delete = "delete from autori where id = ?";
        Connection c = database.getConnection();

        try (PreparedStatement ps = c.prepareStatement(delete)) {

            ps.setLong(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Errore nella delete in AutoreDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }

    }
    public Autore findById(Long id) {
        database.openConnection();
        String selectById = "SELECT * FROM autori WHERE id = ?";
        Connection c = database.getConnection();
        Autore autore = null;
        try (PreparedStatement ps = c.prepareStatement(selectById)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                autore = new Autore(id, nome, cognome);
            }
        } catch (SQLException e) {
            System.out.println("Errore nella findById in AutoreDao: " + e.getMessage());
        } finally {
            database.closeConnection();
        }
        return autore;
    }


    
}
