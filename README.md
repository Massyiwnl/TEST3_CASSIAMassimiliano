# TEST3_CASSIAMassimiliano 11/07/2025
Test 3 11/07/2025

# Sistema E-commerce con Design Patterns

## Descrizione del Progetto

Questo progetto implementa un sistema e-commerce completo per la vendita di abbigliamento, utilizzando 6 design patterns fondamentali. Il sistema  offre funzionalità complete per gestire utenti, prodotti, ordini e pagamenti attraverso un'interfaccia a console.

## Design Patterns Implementati

### 1. **Strategy Pattern**
Utilizzato per gestire diverse strategie di pagamento e spedizione in modo intercambiabile.

#### Pagamenti (`PaymentStrategy`)
- **`CreditCardPayment`**: Gestisce pagamenti con carta di credito
- **`PayPalPayment`**: Gestisce pagamenti tramite PayPal
- Ogni strategia implementa `processPayment()` e `getPaymentMethod()`

#### Spedizioni (`ShippingStrategy`)
- **`StandardShipping`**: Spedizione standard (5-7 giorni, gratis sopra €50, altrimenti €5.99)
- **`ExpressShipping`**: Spedizione express (2 giorni, €12.99)
- Ogni strategia implementa `calculateShippingCost()`, `getShippingMethod()` e `getDeliveryDays()`

### 2. **Decorator Pattern**
Utilizzato per aggiungere funzionalità agli articoli di abbigliamento senza modificare la classe base.

#### Struttura
- **`ClothingItem`**: Classe astratta base per tutti gli articoli
- **`BasicClothingItem`**: Implementazione concreta di base
- **`ClothingDecorator`**: Decorator astratto
- **`DiscountDecorator`**: Applica sconti percentuali (10-80%) agli articoli

### 3. **Factory Method Pattern**
Utilizzato per creare diversi tipi di utenti senza specificare la classe esatta.

#### Fabbriche
- **`AdminUserFactory`**: Crea utenti amministratori
- **`CustomerUserFactory`**: Crea utenti clienti
- Ogni factory implementa `createUser()` per istanziare il tipo appropriato

### 4. **Observer Pattern**
Utilizzato per notificare gli utenti sui cambiamenti di stato degli ordini.

#### Componenti
- **`NotificationService`**: Observer che invia notifiche email
- **`Order`**: Subject che notifica i cambiamenti di stato
- Quando lo stato di un ordine cambia, tutti gli observer registrati ricevono una notifica

### 5. **Singleton Pattern**
Utilizzato per garantire una singola istanza del database dell'applicazione.

#### `EcommerceDatabase`
- Mantiene lo stato globale dell'applicazione
- Gestisce utenti, inventario e ordini
- Fornisce metodi per autenticazione e gestione dati
- Inizializza automaticamente un utente amministratore

### 6. **Facade Pattern**
Utilizzato per fornire un'interfaccia semplificata alle operazioni complesse del sistema.

#### `EcommerceFacade`
- Gestisce l'interfaccia utente console
- Coordina le operazioni tra i diversi componenti
- Fornisce menu distinti per amministratori e clienti

## Struttura delle Classi

### Classi Core

#### `User` (Astratta)
- **Attributi**: `id`, `email`, `nickname`, `password`
- **Metodi**: `getRole()`, `showMenu()`, getter per tutti gli attributi
- **Sottoclassi**:
  - `AdminUser`: Gestisce operazioni amministrative
  - `CustomerUser`: Gestisce carrello e storico ordini

#### `ClothingItem` (Astratta)
- **Attributi**: `id`, `name`, `type`, `price`
- **Metodi**: `getPrice()`, `getDescription()`, getter per tutti gli attributi
- **Implementazioni**:
  - `BasicClothingItem`: Articolo base
  - `DiscountDecorator`: Articolo con sconto applicato

#### `Order`
- **Attributi**: 
  - `orderId`: Identificativo univoco
  - `customerId`: ID del cliente
  - `items`: Lista degli articoli
  - `total`: Totale dell'ordine
  - `status`: Stato dell'ordine (PENDING, PAID, SHIPPED, DELIVERED)
  - `paymentStrategy`: Strategia di pagamento
  - `shippingStrategy`: Strategia di spedizione
  - `observers`: Lista degli observer per le notifiche

## Funzionalità del Sistema

### Menu Principale
1. **Login**: Accesso con email/nickname e password
2. **Registrazione**: Creazione nuovo account cliente
3. **Esci**: Termina l'applicazione

### Funzionalità Amministratore
1. **Aggiungi vestito**: Inserimento nuovi articoli nell'inventario
2. **Rimuovi vestito**: Eliminazione articoli dall'inventario
3. **Aggiungi sconto**: Applicazione sconti percentuali agli articoli
4. **Visualizza acquisti utenti**: Elenco ordini in attesa di spedizione
5. **Spedisci pacco**: Aggiornamento stato ordine a "SHIPPED"
6. **Visualizza inventario**: Elenco completo degli articoli disponibili

### Funzionalità Cliente
1. **Visualizza vestiti disponibili**: Catalogo prodotti con prezzi
2. **Acquista vestito**: Aggiunta articoli al carrello
3. **Visualizza carrello**: Riepilogo articoli selezionati e totale
4. **Procedi al checkout**: Processo di acquisto completo
5. **Visualizza stato ordini**: Storico e stato degli ordini effettuati

## Processo di Checkout Dettagliato

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
- Registra servizio notifiche come observer

### 5. Processamento Pagamento
- Utilizza la strategia selezionata
- In caso di successo, aggiorna stato a "PAID"
- Salva ordine nel database e nello storico cliente
- Svuota il carrello

## Gestione Utenti

### Autenticazione
- Login possibile con email o nickname
- Password in chiaro (per semplicità demo)
- Utente admin predefinito: email `admin@shop.com`, password `admin123`

### Tipi di Utente
- **Admin**: Accesso completo a gestione inventario e ordini
- **Customer**: Accesso a shopping e gestione ordini personali

## Gestione Inventario

### Articoli
- Ogni articolo ha ID univoco, nome, tipologia e prezzo
- Possibilità di sovrascrivere articoli esistenti (stesso ID)
- Sconti applicati tramite Decorator Pattern

### Sconti
- Percentuali comprese tra 10% e 80%
- Applicati dinamicamente senza modificare l'articolo originale
- Mostrati nella descrizione dell'articolo

## Stati degli Ordini

1. **PENDING**: Ordine creato ma non pagato
2. **PAID**: Pagamento completato, in attesa di spedizione
3. **SHIPPED**: Ordine spedito, in transito
4. **DELIVERED**: Ordine consegnato (non implementato automaticamente)

## Notifiche

### Sistema Observer
- Ogni cambio di stato ordine genera notifica
- Notifiche inviate all'email del cliente
- Formato: " Notifica per [email]: Ordine [ID] aggiornato a: [STATO]"

## Gestione Errori

### Input Validation
- Controllo formato numerico per scelte menu
- Gestione input non validi con messaggi di errore
- Vincoli sui valori di sconto (10-80%)

### Error Handling
- Autenticazione fallita: messaggio "Credenziali non valide"
- Articoli non trovati: messaggio "Vestito non trovato"
- Carrello vuoto: impedisce checkout
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

### Estensibilità
- Facile aggiunta nuovi metodi di pagamento (implementare `PaymentStrategy`)
- Facile aggiunta nuovi metodi di spedizione (implementare `ShippingStrategy`)
- Possibilità di aggiungere nuovi tipi di decorator per articoli
- Struttura modulare permette estensioni future


## Dati di Test
### Utente Amministratore Predefinito
- **Email**: `admin@shop.com`
- **Nickname**: `admin`
- **Password**: `admin123`

### Esempio di Utilizzo
1. Avvia l'applicazione
2. Login come admin per aggiungere articoli
3. Logout e registra nuovo cliente
4. Login come cliente per effettuare acquisti
5. Ritorna come admin per gestire spedizioni

## Considerazioni di Design

### Vantaggi dei Pattern Utilizzati
- **Strategy**: Permette cambio algoritmi runtime
- **Decorator**: Aggiunge funzionalità senza ereditarietà
- **Factory**: Disaccoppia creazione oggetti da utilizzo
- **Observer**: Notifiche automatiche e disaccoppiate
- **Singleton**: Garantisce coerenza dati globali
- **Facade**: Semplifica interfaccia complessa
