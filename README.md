# TEST3_CASSIAMassimiliano 11/07/2025
Test 3 11/07/2025

# Sistema E-commerce con Design Pattern

## Descrizione del Progetto

Questo progetto implementa un sistema e-commerce completo per la vendita di abbigliamento, utilizzando 6 design pattern fondamentali. Il sistema offre funzionalità complete per gestire utenti, prodotti, ordini e pagamenti attraverso un'interfaccia a console.

## Design Pattern Implementati

### 1. **Pattern Strategia (Strategy Pattern)**
Utilizzato per gestire diverse strategie di pagamento e spedizione in modo intercambiabile.

#### Pagamenti (`StrategiaPagamento`)
- **`PagamentoCartaCredito`**: Gestisce pagamenti con carta di credito
- **`PagamentoPayPal`**: Gestisce pagamenti tramite PayPal
- Ogni strategia implementa `elaboraPagamento()` e `getMetodoPagamento()`

#### Spedizioni (`StrategiaSpedizione`)
- **`SpedizioneStandard`**: Spedizione standard (5-7 giorni, gratis sopra €50, altrimenti €5.99)
- **`SpedizioneExpress`**: Spedizione express (2 giorni, €12.99)
- Ogni strategia implementa `calcolaCostoSpedizione()`, `getMetodoSpedizione()` e `getGiorniConsegna()`

### 2. **Pattern Decoratore (Decorator Pattern)**
Utilizzato per aggiungere funzionalità agli articoli di abbigliamento senza modificare la classe base.

#### Struttura
- **`ArticoloAbbigliamento`**: Classe astratta base per tutti gli articoli
- **`ArticoloAbbigliamentoBase`**: Implementazione concreta di base
- **`DecoratoreAbbigliamento`**: Decoratore astratto
- **`DecoratoreSconto`**: Applica sconti percentuali (10-80%) agli articoli

### 3. **Pattern Factory Method**
Utilizzato per creare diversi tipi di utenti senza specificare la classe esatta.

#### Fabbriche
- **`FabbricaAmministratori`**: Crea utenti amministratori
- **`FabbricaClienti`**: Crea utenti clienti
- Ogni fabbrica implementa `creaUtente()` per istanziare il tipo appropriato

### 4. **Pattern Osservatore (Observer Pattern)**
Utilizzato per notificare gli utenti sui cambiamenti di stato degli ordini.

#### Componenti
- **`ServizioNotifiche`**: Osservatore che invia notifiche email
- **`Ordine`**: Soggetto che notifica i cambiamenti di stato
- Quando lo stato di un ordine cambia, tutti gli osservatori registrati ricevono una notifica

### 5. **Pattern Singleton**
Utilizzato per garantire una singola istanza del database dell'applicazione.

#### `DatabaseEcommerce`
- Mantiene lo stato globale dell'applicazione
- Gestisce utenti, inventario e ordini
- Fornisce metodi per autenticazione e gestione dati
- Inizializza automaticamente un utente amministratore

### 6. **Pattern Facade**
Utilizzato per fornire un'interfaccia semplificata alle operazioni complesse del sistema.

#### `FacadeEcommerce`
- Gestisce l'interfaccia utente console
- Coordina le operazioni tra i diversi componenti
- Fornisce menu distinti per amministratori e clienti

## Struttura delle Classi

### Classi Principali

#### `Utente` (Astratta)
- **Attributi**: `id`, `email`, `nickname`, `password`
- **Metodi**: `getRuolo()`, `mostraMenu()`, getter per tutti gli attributi
- **Sottoclassi**:
  - `UtenteAmministratore`: Gestisce operazioni amministrative
  - `UtenteCliente`: Gestisce carrello e storico ordini

#### `ArticoloAbbigliamento` (Astratta)
- **Attributi**: `id`, `nome`, `tipo`, `prezzo`
- **Metodi**: `getPrezzo()`, `getDescrizione()`, getter per tutti gli attributi
- **Implementazioni**:
  - `ArticoloAbbigliamentoBase`: Articolo base
  - `DecoratoreSconto`: Articolo con sconto applicato

#### `Ordine`
- **Attributi**: 
  - `idOrdine`: Identificativo univoco
  - `idCliente`: ID del cliente
  - `articoli`: Lista degli articoli
  - `totale`: Totale dell'ordine
  - `stato`: Stato dell'ordine (IN_ATTESA, PAGATO, SPEDITO, CONSEGNATO)
  - `strategiaPagamento`: Strategia di pagamento
  - `strategiaSpedizione`: Strategia di spedizione
  - `osservatori`: Lista degli osservatori per le notifiche

## Funzionalità del Sistema

### Menu Principale
1. **Accedi**: Accesso con email/nickname e password
2. **Registrati**: Creazione nuovo account cliente
3. **Esci**: Termina l'applicazione

### Funzionalità Amministratore
1. **Aggiungi vestito**: Inserimento nuovi articoli nell'inventario
2. **Rimuovi vestito**: Eliminazione articoli dall'inventario
3. **Aggiungi sconto**: Applicazione sconti percentuali agli articoli
4. **Visualizza ordini in attesa**: Elenco ordini in attesa di spedizione
5. **Spedisci ordine**: Aggiornamento stato ordine a "SPEDITO"
6. **Visualizza inventario**: Elenco completo degli articoli disponibili

### Funzionalità Cliente
1. **Visualizza vestiti disponibili**: Catalogo prodotti con prezzi
2. **Acquista vestito**: Aggiunta articoli al carrello
3. **Visualizza carrello**: Riepilogo articoli selezionati e totale
4. **Procedi al pagamento**: Processo di acquisto completo
5. **Visualizza stato ordini**: Storico e stato degli ordini effettuati

## Processo di Pagamento Dettagliato

### 1. Validazione Carrello
- Verifica che il carrello non sia vuoto
- Calcola il totale degli articoli

### 2. Selezione Metodo di Pagamento
- **Carta di Credito**: Richiede numero carta
- **PayPal**: Utilizza email dell'utente

### 3. Selezione Metodo di Spedizione
- **Standard**: Calcola costi in base al totale (gratis sopra €50)
- **Express**: Costo fisso €12.99

### 4. Creazione Ordine
- Genera ID univoco per l'ordine
- Associa articoli, strategie e totale
- Registra servizio notifiche come osservatore

### 5. Elaborazione Pagamento
- Utilizza la strategia selezionata
- In caso di successo, aggiorna stato a "PAGATO"
- Salva ordine nel database e nello storico cliente
- Svuota il carrello

## Gestione Utenti

### Autenticazione
- Accesso possibile con email o nickname
- Password in chiaro (per semplicità demo)
- Utente admin predefinito: email `admin@negozio.com`, password `admin123`

### Tipi di Utente
- **Amministratore**: Accesso completo a gestione inventario e ordini
- **Cliente**: Accesso a shopping e gestione ordini personali

## Gestione Inventario

### Articoli
- Ogni articolo ha ID univoco, nome, tipologia e prezzo
- Possibilità di sovrascrivere articoli esistenti (stesso ID)
- Sconti applicati tramite Pattern Decoratore

### Sconti
- Percentuali comprese tra 10% e 80%
- Applicati dinamicamente senza modificare l'articolo originale
- Mostrati nella descrizione dell'articolo

## Stati degli Ordini

1. **IN_ATTESA**: Ordine creato ma non pagato
2. **PAGATO**: Pagamento completato, in attesa di spedizione
3. **SPEDITO**: Ordine spedito, in transito
4. **CONSEGNATO**: Ordine consegnato (non implementato automaticamente)

## Notifiche

### Sistema Osservatore
- Ogni cambio di stato ordine genera notifica
- Notifiche inviate all'email del cliente
- Formato: "📧 Notifica per [email]: Ordine [ID] aggiornato a: [STATO]"

## Gestione Errori

### Validazione Input
- Controllo formato numerico per scelte menu
- Gestione input non validi con messaggi di errore
- Vincoli sui valori di sconto (10-80%)

### Gestione Errori
- Autenticazione fallita: messaggio "Credenziali non valide"
- Articoli non trovati: messaggio "Vestito non trovato"
- Carrello vuoto: impedisce pagamento
- Operazioni non valide: messaggi informativi

## Caratteristiche Tecniche

### Struttura Dati
- `ArrayList` per gestire liste dinamiche
- Ricerca lineare per ID (adatto per dimensioni demo)
- Generazione ID incrementale automatica

### Interfaccia Utente
- Console testuale con menu numerici
- Formattazione prezzi con 2 decimali
- Separazione chiara tra sezioni con separatori

## Dati di Test
### Utente Amministratore Predefinito
- **Email**: `admin@negozio.com`
- **Nickname**: `admin`
- **Password**: `admin123`

### Esempio di Utilizzo
1. Avvia l'applicazione
2. Accedi come admin per aggiungere articoli
3. Disconnettiti e registra nuovo cliente
4. Accedi come cliente per effettuare acquisti
5. Ritorna come admin per gestire spedizioni

## Considerazioni di Design

### Vantaggi dei Pattern Utilizzati
- **Strategia**: Permette cambio algoritmi a runtime
- **Decoratore**: Aggiunge funzionalità senza ereditarietà
- **Factory**: Disaccoppia creazione oggetti da utilizzo
- **Osservatore**: Notifiche automatiche e disaccoppiate
- **Singleton**: Garantisce coerenza dati globali
- **Facade**: Semplifica interfaccia complessa

