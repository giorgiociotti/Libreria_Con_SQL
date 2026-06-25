package com.generation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//responsabilità: creare la connessione con il server mysql e chiuderla
public class Database {

    private boolean connected;
    private String username;
    private String password;
    private String url;
    private String nomeSchema;
    private static Database instance;

    //
    private Connection connection;

    // percorso per raggiungere la classe Driver nella libreria
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Database() {
        this.connected = false;
        this.username = "root";
        this.password = "root";
        this.nomeSchema = "libreria154";

        // indirizzo al server mysql, alla sua porta e allo specifico schema a cui
        // connettersi
        // jdbc:mysql è il protocollo che indica a jdbc quale driver servirà per
        // relazionarsi con
        // il DBMS, nel nostro caso è mysql per cui quando si passa questo indirizzo
        // JDBC
        // cerca automaticamente tra i driver importati con il jar quello specifico
        // indicato qui
        // e lo carica a RUNTIME fornendo gli strumenti necessari per collegarsi con il
        // DBMS corretto
        // se avessimo usato un DBMS diverso,come ad esempio postgres SQL allora:
        // jdbc:postgresql
        this.url = "jdbc:mysql://localhost:3306/" + nomeSchema + "?useSSL=false&serverTimezone=UTC";
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // metodo che apre la connessione
    public void openConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                // prepara il driver necessario
                // la riga 51 non è necessaria ma permette di capire il flosso delle azioni:
                // PRIMA viene registratoil driver corretto
                Class.forName(DRIVER);

                // poi si apre la connessione
                // la classe DriverManager formisce al progrmma il driver corretto da utilzzare
                // per lo specifico DBMS in uso, inoltre crea la connessione e permette
                // di usare quindi i suoi metodi
                connection = DriverManager.getConnection(url, username, password);
                connected = true;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Errore nel Driver MySQL: controlla il percorso" +
                    " alla classe e di aver importato il JAR con la libreria e driver");
        } catch (SQLException e) {
            System.out.println("Errore nell'apertura della connessione nella classe Database " +
                    " controlla che username, password, nome delle schema e url siano corretti");
        }
    }

    // metodo che chiude la connessione
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connected = false;
            }
        } catch (Exception e) {
            System.out.println("Errore nella chiusura della connessione " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public Connection getConnection() {
        return connection;
    }

}
