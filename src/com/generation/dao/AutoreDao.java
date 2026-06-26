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
        //- trasformo l'oggetto di tipo Autore in un riga sul db -> ORM
        //PRIMA dei SET del PS:
        //"insert into autori(nome,cognome) values (?,?)";

        //l'oggetto ps è di tipo PreparedStatement e ha dei metodi per settare i valori dei placeholder
        //funziona creando un oggetto di tipo PreparedStatement che è come un postino che
        // prende la DML e la consegna al DBMS, ma prima di consegnarla la controlla e se ci sono dei placeholder
        // li sostituisce con i valori che gli passo con i metodi setString, setLong, setInt ecc. a seconda del tipo
        // di dato che voglio passare
        //i segnaposti o placeholder si contano a partire da 1
        //il set prende in input(il numero del segnaposto, il valore da inserire)
        //uso setString perché il tipo che passo come valore è String
        //a questo punto ps converte la stringa in un variabile di tipo VARCHAR che è il tipo di dato che la
        // colonna si aspetta
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());

        //inoltre il PreparedStatement è in grado di controllare la DML e se ci sono errori di sintassi o di tipo di dato, 
        // oppure controlla se esistono le colonne inserite nella query, il nome della tabella e il tipo di dato
        // infatti il valore che passiamo come sostituito del placeholder deve essere di un tipo congruo al tipo di dato
        // che la colonna si aspetta, questi errori se succedono vengono segnalati dal ps prima di inviare al DBMS la DML 
        //DOPO i SET
        //"insert into autori(nome,cognome) values ("Oscar","Wilde")";
        
        //se la DML è pronta provo ad inviarla
        //la insert è una DML per cui executeUpdate()
        //perché una DML è un'operazione di aggiornamento del db e non una query di lettura
            int nRows = ps.executeUpdate();
            System.out.println("Numero di righe manipolate: " + nRows);
        }catch(SQLException e){
            System.out.println("errore nella insert di AutoreDao: " + e.getMessage());
        }finally{
            database.closeConnection();
        }

    }

    //metodo per leggere tutti gli autori del db
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
        //come ritorno ho una tabella, un oggetto di tipo ResultSet che devo convertire in oggetti di tipo Autore
        //conversione di una riga in un oggetto

        //se devo fare una conversione riga per riga di un numero di righe che non conosco a priori, 
        // allora uso un ciclo while che scorre tutte le righe del ResultSet
        while(rs.next()){ //next() sposta il cursore del ResultSet alla riga successiva e ritorna true se c'è una riga, false se non ci sono più righe
            //per ogni riga devo leggere i valori delle colonne e creare un oggetto di tipo Autore con quei valori
            //mi aspetto questi dati nel ResultSet:
            //id     nome    cognome
            //1     nome1   cognome1
            //2     nome2   cognome2

            //per leggere i valori delle colonne uso i metodi getXXX() del ResultSet, 
            // dove XXX è il tipo di dato che mi serve in Java e che viene convertito dal ResultSet dal
            // tipo di dato della colonna del db
            //Attenzione perché ci deve essere congruenza tra il tipo di dato della colonna del db e 
            // il tipo di dato che mi aspetto in Java, altrimenti ci sarà un errore di conversione
            //il metodo getXXX() prende in input il numero della colonna o il nome della colonna,
            //il numero della colonna parte da 1 e non da 0 come negli array
            Long id = rs.getLong(1); 
            //oppure posso anche usare l'alias se lo avete messo nella select passata al ps
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


    //metodo per cercare un autore nel db sapendo l'id

    //un metodo aggiornare i dati di un autore
    // metodo per aggiornare i dati di un autore
    public void update (Autore autore) {
        //apro la connessione
        database.openConnection();
        // creo la query
        String update = "UPDATE autori SET nome = ?, cognome = ? WHERE id = ?";
        // creo il PreparedStatement (postino)
        Connection c = database.getConnection();
         try (PreparedStatement ps = c.prepareStatement(update)) {
            ps.setString(1, autore.getNome());
            ps.setString(2, autore.getCognome());
            ps.setLong(3, autore.getId()); 
            //setto i parametri ad ogni ? nome, cognome e id
            // la invio
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore nell'update in AutoreDao: "+e.getMessage());
        } finally {
            // chiudo la connessione
            database.closeConnection();
        }
    } 
    
    //un metodo per eliminare un autore
    public void delete(Long id){
        //apro la connessione
        database.openConnection();
        //creo la query
        String delete = "delete from autori where id = ?";
        //creo il PreparedStatement (postino)
        Connection c = database.getConnection();

        try(PreparedStatement ps = c.prepareStatement(delete)){
        //setto la query se necessario, cioè se ci sono placeholder ma trasformare
        //PRIMA: "delete from autori where id = ?";
            ps.setLong(1, id); //se ad esempio id = 12L
            
            //DOPO l'azione di ps: "delete from autori where id = 12";
            //la invio
            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println("Errore nella delete in AutoreDao: " + e.getMessage());
        }finally{
            //chiudo la connessione
            database.closeConnection();
        }

    }


    
}
