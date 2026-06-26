package com.generation.services;

import java.util.Map;
import java.util.Optional;

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
        }
        return instance;
    }

    
    public void aggiungiAutore(String nome, String cognome) {
        Autore autore = new Autore(0L, nome, cognome);
        autoreDao.save(autore);
    }

    public Map<Long, Autore> listaAutori() {

        return autoreDao.readAll();
    }

    public void aggiornaAutore(Long id, String nome, String cognome) {
        if (id <= 0 || id == null) {
            throw new IllegalArgumentException("L'id non può essere null o minore o uguale a zero");
        }
        Optional<Autore> optionalAutore = autoreDao.findById(id);
        if (optionalAutore.isEmpty()) {
            return;
        }
        Autore a = optionalAutore.get();
        a.setNome(nome);
        a.setCognome(cognome);

        autoreDao.update(a);
    }

    public void eliminareAutore(Long id) {
        if (id <= 0 || id == null) {
            throw new IllegalArgumentException("L'id non può essere null o minore" +id);
        }
        Optional<Autore> optionalAutore = autoreDao.findById(id);
        if (optionalAutore.isEmpty()) {
            return;
        }
        autoreDao.delete(id);
    }
 }
