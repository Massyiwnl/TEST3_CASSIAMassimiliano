import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ================ PATTERN STRATEGIA ================
interface StrategiaPagamento {
    boolean elaboraPagamento(double importo);
    String getMetodoPagamento();
}

class PagamentoCartaCredito implements StrategiaPagamento {
    private String numeroCarta;
    
    public PagamentoCartaCredito(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }
    
    @Override
    public boolean elaboraPagamento(double importo) {
        System.out.println("Pagamento di €" + importo + " elaborato con carta di credito: " + numeroCarta);
        return true;
    }
    
    @Override
    public String getMetodoPagamento() {
        return "Carta di Credito";
    }
}

class PagamentoPayPal implements StrategiaPagamento {
    private String email;
    
    public PagamentoPayPal(String email) {
        this.email = email;
    }
    
    @Override
    public boolean elaboraPagamento(double importo) {
        System.out.println("Pagamento di €" + importo + " elaborato con PayPal: " + email);
        return true;
    }
    
    @Override
    public String getMetodoPagamento() {
        return "PayPal";
    }
}

interface StrategiaSpedizione {
    double calcolaCostoSpedizione(double totaleOrdine);
    String getMetodoSpedizione();
    int getGiorniConsegna();
}

class SpedizioneStandard implements StrategiaSpedizione {
    @Override
    public double calcolaCostoSpedizione(double totaleOrdine) {
        return totaleOrdine > 50 ? 0 : 5.99;
    }
    
    @Override
    public String getMetodoSpedizione() {
        return "Spedizione Standard";
    }
    
    @Override
    public int getGiorniConsegna() {
        return 5;
    }
}

class SpedizioneExpress implements StrategiaSpedizione {
    @Override
    public double calcolaCostoSpedizione(double totaleOrdine) {
        return 12.99;
    }
    
    @Override
    public String getMetodoSpedizione() {
        return "Spedizione Express";
    }
    
    @Override
    public int getGiorniConsegna() {
        return 2;
    }
}

// ================ PATTERN DECORATORE ================
abstract class ArticoloAbbigliamento {
    protected String id;
    protected String nome;
    protected String tipo;
    protected double prezzo;
    
    public ArticoloAbbigliamento(String id, String nome, String tipo, double prezzo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.prezzo = prezzo;
    }
    
    public abstract double getPrezzo();
    public abstract String getDescrizione();
    
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public double getPrezzoBase() { return prezzo; }
}

class ArticoloAbbigliamentoBase extends ArticoloAbbigliamento {
    public ArticoloAbbigliamentoBase(String id, String nome, String tipo, double prezzo) {
        super(id, nome, tipo, prezzo);
    }
    
    @Override
    public double getPrezzo() {
        return prezzo;
    }
    
    @Override
    public String getDescrizione() {
        return nome + " (" + tipo + ")";
    }
}

abstract class DecoratoreAbbigliamento extends ArticoloAbbigliamento {
    protected ArticoloAbbigliamento articoloAbbigliamento;
    
    public DecoratoreAbbigliamento(ArticoloAbbigliamento articoloAbbigliamento) {
        super(articoloAbbigliamento.getId(), articoloAbbigliamento.getNome(), 
              articoloAbbigliamento.getTipo(), articoloAbbigliamento.getPrezzoBase());
        this.articoloAbbigliamento = articoloAbbigliamento;
    }
}

class DecoratoreSconto extends DecoratoreAbbigliamento {
    private double percentualeSconto;
    
    public DecoratoreSconto(ArticoloAbbigliamento articoloAbbigliamento, double percentualeSconto) {
        super(articoloAbbigliamento);
        this.percentualeSconto = Math.max(10, Math.min(80, percentualeSconto));
    }
    
    @Override
    public double getPrezzo() {
        return articoloAbbigliamento.getPrezzo() * (1 - percentualeSconto / 100);
    }
    
    @Override
    public String getDescrizione() {
        return articoloAbbigliamento.getDescrizione() + " (Sconto " + percentualeSconto + "%)";
    }
    
    public double getPercentualeSconto() {
        return percentualeSconto;
    }
}

// ================ PATTERN FACTORY METHOD ================
abstract class Utente {
    protected String id;
    protected String email;
    protected String nickname;
    protected String password;
    
    public Utente(String id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
    
    public abstract String getRuolo();
    public abstract void mostraMenu();
    
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getPassword() { return password; }
}

class UtenteAmministratore extends Utente {
    public UtenteAmministratore(String id, String email, String nickname, String password) {
        super(id, email, nickname, password);
    }
    
    @Override
    public String getRuolo() {
        return "AMMINISTRATORE";
    }
    
    @Override
    public void mostraMenu() {
        System.out.println("\n=== MENU AMMINISTRATORE ===");
        System.out.println("1. Aggiungi vestito");
        System.out.println("2. Rimuovi vestito");
        System.out.println("3. Aggiungi sconto");
        System.out.println("4. Visualizza ordini in attesa");
        System.out.println("5. Spedisci ordine");
        System.out.println("6. Visualizza inventario");
        System.out.println("0. Esci");
    }
}

class UtenteCliente extends Utente {
    private List<ArticoloAbbigliamento> carrello;
    private List<Ordine> storicoOrdini;
    
    public UtenteCliente(String id, String email, String nickname, String password) {
        super(id, email, nickname, password);
        this.carrello = new ArrayList<>();
        this.storicoOrdini = new ArrayList<>();
    }
    
    @Override
    public String getRuolo() {
        return "CLIENTE";
    }
    
    @Override
    public void mostraMenu() {
        System.out.println("\n=== MENU CLIENTE ===");
        System.out.println("1. Visualizza vestiti disponibili");
        System.out.println("2. Acquista vestito");
        System.out.println("3. Visualizza carrello");
        System.out.println("4. Procedi al pagamento");
        System.out.println("5. Visualizza stato ordini");
        System.out.println("0. Esci");
    }
    
    public List<ArticoloAbbigliamento> getCarrello() { return carrello; }
    public List<Ordine> getStoricoOrdini() { return storicoOrdini; }
    public void aggiungiAlCarrello(ArticoloAbbigliamento articolo) { carrello.add(articolo); }
    public void svuotaCarrello() { carrello.clear(); }
    public void aggiungiOrdine(Ordine ordine) { storicoOrdini.add(ordine); }
}

abstract class FabbricaUtenti {
    public abstract Utente creaUtente(String id, String email, String nickname, String password);
}

class FabbricaAmministratori extends FabbricaUtenti {
    @Override
    public Utente creaUtente(String id, String email, String nickname, String password) {
        return new UtenteAmministratore(id, email, nickname, password);
    }
}

class FabbricaClienti extends FabbricaUtenti {
    @Override
    public Utente creaUtente(String id, String email, String nickname, String password) {
        return new UtenteCliente(id, email, nickname, password);
    }
}

// ================ PATTERN OBSERVER ================
interface Osservatore {
    void aggiorna(String messaggio);
}

interface Soggetto {
    void registraOsservatore(Osservatore osservatore);
    void rimuoviOsservatore(Osservatore osservatore);
    void notificaOsservatori(String messaggio);
}

class ServizioNotifiche implements Osservatore {
    private String emailUtente;
    
    public ServizioNotifiche(String emailUtente) {
        this.emailUtente = emailUtente;
    }
    
    @Override
    public void aggiorna(String messaggio) {
        System.out.println("📧 Notifica per " + emailUtente + ": " + messaggio);
    }
}

class Ordine implements Soggetto {
    private String idOrdine;
    private String idCliente;
    private List<ArticoloAbbigliamento> articoli;
    private double totale;
    private StatoOrdine stato;
    private StrategiaPagamento strategiaPagamento;
    private StrategiaSpedizione strategiaSpedizione;
    private List<Osservatore> osservatori;
    
    public enum StatoOrdine {
        IN_ATTESA, PAGATO, SPEDITO, CONSEGNATO
    }
    
    public Ordine(String idOrdine, String idCliente) {
        this.idOrdine = idOrdine;
        this.idCliente = idCliente;
        this.articoli = new ArrayList<>();
        this.stato = StatoOrdine.IN_ATTESA;
        this.osservatori = new ArrayList<>();
    }
    
    @Override
    public void registraOsservatore(Osservatore osservatore) {
        osservatori.add(osservatore);
    }
    
    @Override
    public void rimuoviOsservatore(Osservatore osservatore) {
        osservatori.remove(osservatore);
    }
    
    @Override
    public void notificaOsservatori(String messaggio) {
        for (Osservatore osservatore : osservatori) {
            osservatore.aggiorna(messaggio);
        }
    }
    
    public void aggiornaStato(StatoOrdine nuovoStato) {
        this.stato = nuovoStato;
        String messaggio = "Ordine " + idOrdine + " aggiornato a: " + nuovoStato;
        notificaOsservatori(messaggio);
    }
    
    // Getter e Setter
    public String getIdOrdine() { return idOrdine; }
    public String getIdCliente() { return idCliente; }
    public List<ArticoloAbbigliamento> getArticoli() { return articoli; }
    public double getTotale() { return totale; }
    public StatoOrdine getStato() { return stato; }
    public StrategiaPagamento getStrategiaPagamento() { return strategiaPagamento; }
    public StrategiaSpedizione getStrategiaSpedizione() { return strategiaSpedizione; }
    
    public void setArticoli(List<ArticoloAbbigliamento> articoli) { this.articoli = new ArrayList<>(articoli); }
    public void setTotale(double totale) { this.totale = totale; }
    public void setStrategiaPagamento(StrategiaPagamento strategiaPagamento) { this.strategiaPagamento = strategiaPagamento; }
    public void setStrategiaSpedizione(StrategiaSpedizione strategiaSpedizione) { this.strategiaSpedizione = strategiaSpedizione; }
}

// ================ PATTERN SINGLETON ================
class DatabaseEcommerce {
    private static DatabaseEcommerce istanza;
    private List<Utente> utenti;
    private List<ArticoloAbbigliamento> inventario;
    private List<Ordine> ordini;
    private int prossimoIdUtente;
    private int prossimoIdOrdine;
    
    private DatabaseEcommerce() {
        utenti = new ArrayList<>();
        inventario = new ArrayList<>();
        ordini = new ArrayList<>();
        prossimoIdUtente = 1;
        prossimoIdOrdine = 1;
        inizializzaAmministratore();
    }
    
    public static DatabaseEcommerce getIstanza() {
        if (istanza == null) {
            istanza = new DatabaseEcommerce();
        }
        return istanza;
    }
    
    private void inizializzaAmministratore() {
        FabbricaAmministratori fabbricaAdmin = new FabbricaAmministratori();
        Utente admin = fabbricaAdmin.creaUtente("admin", "admin@negozio.com", "admin", "admin123");
        utenti.add(admin);
    }
    
    public String generaIdUtente() {
        return "utente" + (prossimoIdUtente++);
    }
    
    public String generaIdOrdine() {
        return "ordine" + (prossimoIdOrdine++);
    }
    
    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
    }
    
    public Utente getUtente(String id) {
        for (Utente utente : utenti) {
            if (utente.getId().equals(id)) {
                return utente;
            }
        }
        return null;
    }
    
    public Utente autenticaUtente(String loginId, String password) {
        for (Utente utente : utenti) {
            if ((utente.getEmail().equals(loginId) || utente.getNickname().equals(loginId)) 
                && utente.getPassword().equals(password)) {
                return utente;
            }
        }
        return null;
    }
    
    public void aggiungiArticoloAbbigliamento(ArticoloAbbigliamento articolo) {
        // Rimuovi articolo esistente con stesso ID se presente
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getId().equals(articolo.getId())) {
                inventario.remove(i);
                break;
            }
        }
        inventario.add(articolo);
    }
    
    public ArticoloAbbigliamento getArticoloAbbigliamento(String id) {
        for (ArticoloAbbigliamento articolo : inventario) {
            if (articolo.getId().equals(id)) {
                return articolo;
            }
        }
        return null;
    }
    
    public void rimuoviArticoloAbbigliamento(String id) {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getId().equals(id)) {
                inventario.remove(i);
                break;
            }
        }
    }
    
    public List<ArticoloAbbigliamento> getInventario() {
        return new ArrayList<>(inventario);
    }
    
    public void aggiungiOrdine(Ordine ordine) {
        ordini.add(ordine);
    }
    
    public Ordine getOrdine(String idOrdine) {
        for (Ordine ordine : ordini) {
            if (ordine.getIdOrdine().equals(idOrdine)) {
                return ordine;
            }
        }
        return null;
    }
    
    public List<Ordine> getOrdini() {
        return new ArrayList<>(ordini);
    }
    
    public List<Ordine> getOrdiniPerCliente(String idCliente) {
        List<Ordine> ordiniCliente = new ArrayList<>();
        for (Ordine ordine : ordini) {
            if (ordine.getIdCliente().equals(idCliente)) {
                ordiniCliente.add(ordine);
            }
        }
        return ordiniCliente;
    }
    
    public List<Ordine> getOrdiniInAttesa() {
        List<Ordine> ordiniInAttesa = new ArrayList<>();
        for (Ordine ordine : ordini) {
            if (ordine.getStato() == Ordine.StatoOrdine.PAGATO) {
                ordiniInAttesa.add(ordine);
            }
        }
        return ordiniInAttesa;
    }
}

// ================ PATTERN FACADE ================
class FacadeEcommerce {
    private DatabaseEcommerce database;
    private Scanner scanner;
    
    public FacadeEcommerce() {
        this.database = DatabaseEcommerce.getIstanza();
        this.scanner = new Scanner(System.in);
    }
    
    public void avviaApplicazione() {
        System.out.println("🛍️ Benvenuto nel negozio di abbigliamento!");
        
        while (true) {
            mostraMenuPrincipale();
            int scelta = leggiIntero();
            
            switch (scelta) {
                case 1:
                    gestisciLogin();
                    break;
                case 2:
                    gestisciRegistrazione();
                    break;
                case 0:
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Scelta non valida!");
            }
        }
    }
    
    private void mostraMenuPrincipale() {
        System.out.println("\n=== MENU PRINCIPALE ===");
        System.out.println("1. Accedi");
        System.out.println("2. Registrati");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }
    
    private void gestisciLogin() {
        System.out.print("Email o Nickname: ");
        String loginId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        Utente utente = database.autenticaUtente(loginId, password);
        if (utente != null) {
            System.out.println("Accesso effettuato con successo! Benvenuto " + utente.getNickname());
            gestisciSessioneUtente(utente);
        } else {
            System.out.println("Credenziali non valide!");
        }
    }
    
    private void gestisciRegistrazione() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        String idUtente = database.generaIdUtente();
        FabbricaClienti fabbricaClienti = new FabbricaClienti();
        Utente nuovoUtente = fabbricaClienti.creaUtente(idUtente, email, nickname, password);
        database.aggiungiUtente(nuovoUtente);
        
        System.out.println("Registrazione completata con successo!");
    }
    
    private void gestisciSessioneUtente(Utente utente) {
        while (true) {
            utente.mostraMenu();
            System.out.print("Scegli un'opzione: ");
            int scelta = leggiIntero();
            
            if (scelta == 0) {
                System.out.println("Disconnessione effettuata!");
                break;
            }
            
            if (utente instanceof UtenteAmministratore) {
                gestisciAzioneAmministratore((UtenteAmministratore) utente, scelta);
            } else if (utente instanceof UtenteCliente) {
                gestisciAzioneCliente((UtenteCliente) utente, scelta);
            }
        }
    }
    
    private void gestisciAzioneAmministratore(UtenteAmministratore admin, int scelta) {
        switch (scelta) {
            case 1:
                aggiungiArticoloAbbigliamento();
                break;
            case 2:
                rimuoviArticoloAbbigliamento();
                break;
            case 3:
                aggiungiSconto();
                break;
            case 4:
                visualizzaOrdiniInAttesa();
                break;
            case 5:
                spedisciOrdine();
                break;
            case 6:
                visualizzaInventario();
                break;
            default:
                System.out.println("Scelta non valida!");
        }
    }
    
    private void gestisciAzioneCliente(UtenteCliente cliente, int scelta) {
        switch (scelta) {
            case 1:
                visualizzaVestitiDisponibili();
                break;
            case 2:
                acquistaVestito(cliente);
                break;
            case 3:
                visualizzaCarrello(cliente);
                break;
            case 4:
                elaboraPagamento(cliente);
                break;
            case 5:
                visualizzaStatoOrdini(cliente);
                break;
            default:
                System.out.println("Scelta non valida!");
        }
    }
    
    private void aggiungiArticoloAbbigliamento() {
        System.out.print("ID vestito: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Tipologia: ");
        String tipo = scanner.nextLine();
        System.out.print("Prezzo: ");
        double prezzo = leggiDecimale();
        
        ArticoloAbbigliamento articolo = new ArticoloAbbigliamentoBase(id, nome, tipo, prezzo);
        database.aggiungiArticoloAbbigliamento(articolo);
        System.out.println("Vestito aggiunto con successo!");
    }
    
    private void rimuoviArticoloAbbigliamento() {
        System.out.print("ID vestito da rimuovere: ");
        String id = scanner.nextLine();
        
        if (database.getArticoloAbbigliamento(id) != null) {
            database.rimuoviArticoloAbbigliamento(id);
            System.out.println("Vestito rimosso con successo!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void aggiungiSconto() {
        System.out.print("ID vestito: ");
        String id = scanner.nextLine();
        ArticoloAbbigliamento articolo = database.getArticoloAbbigliamento(id);
        
        if (articolo != null) {
            System.out.print("Percentuale sconto (10-80%): ");
            double sconto = leggiDecimale();
            
            ArticoloAbbigliamento articoloScontato = new DecoratoreSconto(articolo, sconto);
            database.aggiungiArticoloAbbigliamento(articoloScontato);
            System.out.println("Sconto applicato con successo!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void visualizzaOrdiniInAttesa() {
        List<Ordine> ordiniInAttesa = database.getOrdiniInAttesa();
        System.out.println("\n=== ORDINI IN ATTESA DI SPEDIZIONE ===");
        
        if (ordiniInAttesa.isEmpty()) {
            System.out.println("Nessun ordine in attesa di spedizione.");
        } else {
            for (Ordine ordine : ordiniInAttesa) {
                Utente cliente = database.getUtente(ordine.getIdCliente());
                System.out.println("Ordine: " + ordine.getIdOrdine() + 
                                 " - Cliente: " + cliente.getNickname() + 
                                 " - Totale: €" + String.format("%.2f", ordine.getTotale()));
            }
        }
    }
    
    private void spedisciOrdine() {
        System.out.print("ID ordine da spedire: ");
        String idOrdine = scanner.nextLine();
        Ordine ordine = database.getOrdine(idOrdine);
        
        if (ordine != null && ordine.getStato() == Ordine.StatoOrdine.PAGATO) {
            ordine.aggiornaStato(Ordine.StatoOrdine.SPEDITO);
            System.out.println("Ordine spedito con successo!");
        } else {
            System.out.println("Ordine non trovato o non valido per la spedizione!");
        }
    }
    
    private void visualizzaInventario() {
        System.out.println("\n=== INVENTARIO ===");
        List<ArticoloAbbigliamento> inventario = database.getInventario();
        
        if (inventario.isEmpty()) {
            System.out.println("Inventario vuoto.");
        } else {
            for (ArticoloAbbigliamento articolo : inventario) {
                System.out.println(articolo.getId() + " - " + articolo.getDescrizione() + 
                                 " - €" + String.format("%.2f", articolo.getPrezzo()));
            }
        }
    }
    
    private void visualizzaVestitiDisponibili() {
        System.out.println("\n=== VESTITI DISPONIBILI ===");
        List<ArticoloAbbigliamento> inventario = database.getInventario();
        
        if (inventario.isEmpty()) {
            System.out.println("Nessun vestito disponibile.");
        } else {
            for (ArticoloAbbigliamento articolo : inventario) {
                System.out.println(articolo.getId() + " - " + articolo.getDescrizione() + 
                                 " - €" + String.format("%.2f", articolo.getPrezzo()));
            }
        }
    }
    
    private void acquistaVestito(UtenteCliente cliente) {
        System.out.print("ID vestito da acquistare: ");
        String id = scanner.nextLine();
        ArticoloAbbigliamento articolo = database.getArticoloAbbigliamento(id);
        
        if (articolo != null) {
            cliente.aggiungiAlCarrello(articolo);
            System.out.println("Vestito aggiunto al carrello!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void visualizzaCarrello(UtenteCliente cliente) {
        System.out.println("\n=== CARRELLO ===");
        List<ArticoloAbbigliamento> carrello = cliente.getCarrello();
        
        if (carrello.isEmpty()) {
            System.out.println("Carrello vuoto.");
        } else {
            double totale = 0;
            for (ArticoloAbbigliamento articolo : carrello) {
                System.out.println(articolo.getDescrizione() + " - €" + String.format("%.2f", articolo.getPrezzo()));
                totale += articolo.getPrezzo();
            }
            System.out.println("Totale: €" + String.format("%.2f", totale));
        }
    }
    
    private void elaboraPagamento(UtenteCliente cliente) {
        if (cliente.getCarrello().isEmpty()) {
            System.out.println("Carrello vuoto!");
            return;
        }
        
        // Calcolo totale
        double totale = 0;
        for (ArticoloAbbigliamento articolo : cliente.getCarrello()) {
            totale += articolo.getPrezzo();
        }
        
        // Scelta metodo di pagamento
        System.out.println("Scegli metodo di pagamento:");
        System.out.println("1. Carta di credito");
        System.out.println("2. PayPal");
        int sceltaPagamento = leggiIntero();
        
        StrategiaPagamento strategiaPagamento;
        if (sceltaPagamento == 1) {
            System.out.print("Numero carta: ");
            String numeroCarta = scanner.nextLine();
            strategiaPagamento = new PagamentoCartaCredito(numeroCarta);
        } else {
            strategiaPagamento = new PagamentoPayPal(cliente.getEmail());
        }
        
        // Scelta metodo di spedizione
        System.out.println("Scegli metodo di spedizione:");
        System.out.println("1. Spedizione standard");
        System.out.println("2. Spedizione express");
        int sceltaSpedizione = leggiIntero();
        
        StrategiaSpedizione strategiaSpedizione;
        if (sceltaSpedizione == 1) {
            strategiaSpedizione = new SpedizioneStandard();
        } else {
            strategiaSpedizione = new SpedizioneExpress();
        }
        
        double costoSpedizione = strategiaSpedizione.calcolaCostoSpedizione(totale);
        double totaleFinale = totale + costoSpedizione;
        
        // Creazione ordine
        String idOrdine = database.generaIdOrdine();
        Ordine ordine = new Ordine(idOrdine, cliente.getId());
        ordine.setArticoli(cliente.getCarrello());
        ordine.setTotale(totaleFinale);
        ordine.setStrategiaPagamento(strategiaPagamento);
        ordine.setStrategiaSpedizione(strategiaSpedizione);
        
        // Registrazione per notifiche
        ServizioNotifiche servizioNotifiche = new ServizioNotifiche(cliente.getEmail());
        ordine.registraOsservatore(servizioNotifiche);
        
        // Elaborazione pagamento
        if (strategiaPagamento.elaboraPagamento(totaleFinale)) {
            ordine.aggiornaStato(Ordine.StatoOrdine.PAGATO);
            database.aggiungiOrdine(ordine);
            cliente.aggiungiOrdine(ordine);
            cliente.svuotaCarrello();
            
            System.out.println("Ordine completato con successo!");
            System.out.println("ID Ordine: " + idOrdine);
            System.out.println("Totale pagato: €" + String.format("%.2f", totaleFinale));
        } else {
            System.out.println("Errore nel pagamento!");
        }
    }
    
    private void visualizzaStatoOrdini(UtenteCliente cliente) {
        System.out.println("\n=== STATO ORDINI ===");
        List<Ordine> ordini = cliente.getStoricoOrdini();
        
        if (ordini.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
        } else {
            for (Ordine ordine : ordini) {
                System.out.println("Ordine: " + ordine.getIdOrdine() + 
                                 " - Stato: " + ordine.getStato() + 
                                 " - Totale: €" + String.format("%.2f", ordine.getTotale()));
            }
        }
    }
    
    private int leggiIntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double leggiDecimale() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}

// ================ CLASSE PRINCIPALE ================
public class EcommerceSystem {
    public static void main(String[] args) {
        FacadeEcommerce facade = new FacadeEcommerce();
        facade.avviaApplicazione();
    }
}