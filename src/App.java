import java.util.Optional;

import com.generation.dao.AutoreDao;
import com.generation.models.Autore;

public class App {
    public static void main(String[] args) throws Exception {

        Autore nuovoAutore = new Autore(0L, "William", "Shakespeare");

        // se non ho mai chiamato getInstance() di AutoreDao
        // allora la prima volta crea l'istanza, le prossime volte ritornerà quella
        // creata a riga 11
        AutoreDao ad = AutoreDao.getInstance();

        // ad.save(nuovoAutore);

        // Long idAutoreDaEliminare = 13L;
        // se on delete restrict/no action
        // prima devo trovare tutti i libri dell'autore
        // li cancello così che non ci siano più FK associate a quell'autore
        // poi posso cancellare l'autore
        // ad.delete(idAutoreDaEliminare);

        nuovoAutore.setNome("Willy");
        System.out.println("autore aggiornato nel nome: " + nuovoAutore.toString());

        // dovrei recuperare l'autore da aggiornare cercandolo dalla tabella
        // non avavndo ancora il metodo di ricerca ne creo uno con un id che esiste
        // nella tabella
        // cambio ad esempio solo il cognome
        // Autore autoreDaAggiornare = new Autore(12L, "Jean", "Claude");
        // ad.update(autoreDaAggiornare);

        // System.out.println(ad.readAll());
        Long idAutoreDaCercare = 22L;
        Optional<Autore> optional = ad.findById(idAutoreDaCercare);
        Autore autoreDaAggiornare = optional.get();
        System.out.println("autore trovato: " + autoreDaAggiornare.toString());

        autoreDaAggiornare.setCognome("Franco");

        ad.update(autoreDaAggiornare);

        optional = ad.findById(idAutoreDaCercare);
        System.out.println("autore aggiornato: " + optional.get().toString());
    }
}
