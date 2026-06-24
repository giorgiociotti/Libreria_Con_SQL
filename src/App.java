import com.generation.models.Autore;
import com.generation.models.AutoreDao;

public class App {
    public static void main(String[] args) throws Exception {
        Autore autore = new Autore("Giorgio", "Bianchi");
        AutoreDao autoreDao = AutoreDao.getInstance();
        autoreDao.save(autore);
        System.out.println("Autore salvato: " + autore);
        autoreDao.delete(autore.getId());
        System.out.println("Autore eliminato: " + autore);
    }
}
