package com.generation.services;

import java.util.Map;

import com.generation.dao.AutoreDao;
import com.generation.models.Autore;

public class AutoreService {

    private final AutoreDao autoreDao;
    private static AutoreService instance;

    public AutoreService() {
        this.autoreDao = AutoreDao.getInstance();
    }

    public static AutoreService getInstance() {
        if (instance == null) {
            instance = new AutoreService();
        }
        return instance;
    }

    // fare le CRUD
    public void aggiungiAutore(String nome, String cognome) {
        Autore autore = new Autore(0L, nome, cognome);
        autoreDao.save(autore);
    }
    
    public Map<Long, Autore> listaAutori() {
        
        return autoreDao.readAll();
    }

}
