import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ================ STRATEGY PATTERN ================
interface PaymentStrategy {
    boolean processPayment(double amount);
    String getPaymentMethod();
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Pagamento di €" + amount + " elaborato con carta di credito: " + cardNumber);
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "Carta di Credito";
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Pagamento di €" + amount + " elaborato con PayPal: " + email);
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
}

interface ShippingStrategy {
    double calculateShippingCost(double orderTotal);
    String getShippingMethod();
    int getDeliveryDays();
}

class StandardShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderTotal) {
        return orderTotal > 50 ? 0 : 5.99;
    }
    
    @Override
    public String getShippingMethod() {
        return "Spedizione Standard";
    }
    
    @Override
    public int getDeliveryDays() {
        return 5;
    }
}

class ExpressShipping implements ShippingStrategy {
    @Override
    public double calculateShippingCost(double orderTotal) {
        return 12.99;
    }
    
    @Override
    public String getShippingMethod() {
        return "Spedizione Express";
    }
    
    @Override
    public int getDeliveryDays() {
        return 2;
    }
}

// ================ DECORATOR PATTERN ================
abstract class ClothingItem {
    protected String id;
    protected String name;
    protected String type;
    protected double price;
    
    public ClothingItem(String id, String name, String type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }
    
    public abstract double getPrice();
    public abstract String getDescription();
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public double getBasePrice() { return price; }
}

class BasicClothingItem extends ClothingItem {
    public BasicClothingItem(String id, String name, String type, double price) {
        super(id, name, type, price);
    }
    
    @Override
    public double getPrice() {
        return price;
    }
    
    @Override
    public String getDescription() {
        return name + " (" + type + ")";
    }
}

abstract class ClothingDecorator extends ClothingItem {
    protected ClothingItem clothingItem;
    
    public ClothingDecorator(ClothingItem clothingItem) {
        super(clothingItem.getId(), clothingItem.getName(), clothingItem.getType(), clothingItem.getBasePrice());
        this.clothingItem = clothingItem;
    }
}

class DiscountDecorator extends ClothingDecorator {
    private double discountPercentage;
    
    public DiscountDecorator(ClothingItem clothingItem, double discountPercentage) {
        super(clothingItem);
        this.discountPercentage = Math.max(10, Math.min(80, discountPercentage));
    }
    
    @Override
    public double getPrice() {
        return clothingItem.getPrice() * (1 - discountPercentage / 100);
    }
    
    @Override
    public String getDescription() {
        return clothingItem.getDescription() + " (Sconto " + discountPercentage + "%)";
    }
    
    public double getDiscountPercentage() {
        return discountPercentage;
    }
}

// ================ FACTORY METHOD PATTERN ================
abstract class User {
    protected String id;
    protected String email;
    protected String nickname;
    protected String password;
    
    public User(String id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
    
    public abstract String getRole();
    public abstract void showMenu();
    
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getPassword() { return password; }
}

class AdminUser extends User {
    public AdminUser(String id, String email, String nickname, String password) {
        super(id, email, nickname, password);
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== MENU AMMINISTRATORE ===");
        System.out.println("1. Aggiungi vestito");
        System.out.println("2. Rimuovi vestito");
        System.out.println("3. Aggiungi sconto");
        System.out.println("4. Visualizza acquisti utenti");
        System.out.println("5. Spedisci pacco");
        System.out.println("6. Visualizza inventario");
        System.out.println("0. Logout");
    }
}

class CustomerUser extends User {
    private List<ClothingItem> cart;
    private List<Order> orderHistory;
    
    public CustomerUser(String id, String email, String nickname, String password) {
        super(id, email, nickname, password);
        this.cart = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
    }
    
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== MENU CLIENTE ===");
        System.out.println("1. Visualizza vestiti disponibili");
        System.out.println("2. Acquista vestito");
        System.out.println("3. Visualizza carrello");
        System.out.println("4. Procedi al checkout");
        System.out.println("5. Visualizza stato ordini");
        System.out.println("0. Logout");
    }
    
    public List<ClothingItem> getCart() { return cart; }
    public List<Order> getOrderHistory() { return orderHistory; }
    public void addToCart(ClothingItem item) { cart.add(item); }
    public void clearCart() { cart.clear(); }
    public void addOrder(Order order) { orderHistory.add(order); }
}

abstract class UserFactory {
    public abstract User createUser(String id, String email, String nickname, String password);
}

class AdminUserFactory extends UserFactory {
    @Override
    public User createUser(String id, String email, String nickname, String password) {
        return new AdminUser(id, email, nickname, password);
    }
}

class CustomerUserFactory extends UserFactory {
    @Override
    public User createUser(String id, String email, String nickname, String password) {
        return new CustomerUser(id, email, nickname, password);
    }
}

// ================ OBSERVER PATTERN ================
interface Observer {
    void update(String message);
}

interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}

class NotificationService implements Observer {
    private String userEmail;
    
    public NotificationService(String userEmail) {
        this.userEmail = userEmail;
    }
    
    @Override
    public void update(String message) {
        System.out.println("📧 Notifica per " + userEmail + ": " + message);
    }
}

class Order implements Subject {
    private String orderId;
    private String customerId;
    private List<ClothingItem> items;
    private double total;
    private OrderStatus status;
    private PaymentStrategy paymentStrategy;
    private ShippingStrategy shippingStrategy;
    private List<Observer> observers;
    
    public enum OrderStatus {
        PENDING, PAID, SHIPPED, DELIVERED
    }
    
    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        String message = "Ordine " + orderId + " aggiornato a: " + newStatus;
        notifyObservers(message);
    }
    
    // Getters e Setters
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<ClothingItem> getItems() { return items; }
    public double getTotal() { return total; }
    public OrderStatus getStatus() { return status; }
    public PaymentStrategy getPaymentStrategy() { return paymentStrategy; }
    public ShippingStrategy getShippingStrategy() { return shippingStrategy; }
    
    public void setItems(List<ClothingItem> items) { this.items = new ArrayList<>(items); }
    public void setTotal(double total) { this.total = total; }
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) { this.paymentStrategy = paymentStrategy; }
    public void setShippingStrategy(ShippingStrategy shippingStrategy) { this.shippingStrategy = shippingStrategy; }
}

// ================ SINGLETON PATTERN ================
class EcommerceDatabase {
    private static EcommerceDatabase instance;
    private List<User> users;
    private List<ClothingItem> inventory;
    private List<Order> orders;
    private int nextUserId;
    private int nextOrderId;
    
    private EcommerceDatabase() {
        users = new ArrayList<>();
        inventory = new ArrayList<>();
        orders = new ArrayList<>();
        nextUserId = 1;
        nextOrderId = 1;
        initializeAdminUser();
    }
    
    public static EcommerceDatabase getInstance() {
        if (instance == null) {
            instance = new EcommerceDatabase();
        }
        return instance;
    }
    
    private void initializeAdminUser() {
        AdminUserFactory adminFactory = new AdminUserFactory();
        User admin = adminFactory.createUser("admin", "admin@shop.com", "admin", "admin123");
        users.add(admin);
    }
    
    public String generateUserId() {
        return "user" + (nextUserId++);
    }
    
    public String generateOrderId() {
        return "order" + (nextOrderId++);
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public User getUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    
    public User authenticateUser(String loginId, String password) {
        for (User user : users) {
            if ((user.getEmail().equals(loginId) || user.getNickname().equals(loginId)) 
                && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    
    public void addClothingItem(ClothingItem item) {
        // Rimuovi item esistente con stesso ID se presente
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equals(item.getId())) {
                inventory.remove(i);
                break;
            }
        }
        inventory.add(item);
    }
    
    public ClothingItem getClothingItem(String id) {
        for (ClothingItem item : inventory) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
    
    public void removeClothingItem(String id) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equals(id)) {
                inventory.remove(i);
                break;
            }
        }
    }
    
    public List<ClothingItem> getInventory() {
        return new ArrayList<>(inventory);
    }
    
    public void addOrder(Order order) {
        orders.add(order);
    }
    
    public Order getOrder(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }
    
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
    
    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }
    
    public List<Order> getPendingOrders() {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == Order.OrderStatus.PAID) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }
}

// ================ FACADE PATTERN ================
class EcommerceFacade {
    private EcommerceDatabase database;
    private Scanner scanner;
    
    public EcommerceFacade() {
        this.database = EcommerceDatabase.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    public void startApplication() {
        System.out.println("🛍️ Benvenuto nel negozio di abbigliamento!");
        
        while (true) {
            showMainMenu();
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 0:
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Scelta non valida!");
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n=== MENU PRINCIPALE ===");
        System.out.println("1. Login");
        System.out.println("2. Registrazione");
        System.out.println("0. Esci");
        System.out.print("Scegli un'opzione: ");
    }
    
    private void handleLogin() {
        System.out.print("Email o Nickname: ");
        String loginId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        User user = database.authenticateUser(loginId, password);
        if (user != null) {
            System.out.println("Login effettuato con successo! Benvenuto " + user.getNickname());
            handleUserSession(user);
        } else {
            System.out.println("Credenziali non valide!");
        }
    }
    
    private void handleRegistration() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        String userId = database.generateUserId();
        CustomerUserFactory customerFactory = new CustomerUserFactory();
        User newUser = customerFactory.createUser(userId, email, nickname, password);
        database.addUser(newUser);
        
        System.out.println("Registrazione completata con successo!");
    }
    
    private void handleUserSession(User user) {
        while (true) {
            user.showMenu();
            System.out.print("Scegli un'opzione: ");
            int choice = getIntInput();
            
            if (choice == 0) {
                System.out.println("Logout effettuato!");
                break;
            }
            
            if (user instanceof AdminUser) {
                handleAdminAction((AdminUser) user, choice);
            } else if (user instanceof CustomerUser) {
                handleCustomerAction((CustomerUser) user, choice);
            }
        }
    }
    
    private void handleAdminAction(AdminUser admin, int choice) {
        switch (choice) {
            case 1:
                addClothingItem();
                break;
            case 2:
                removeClothingItem();
                break;
            case 3:
                addDiscount();
                break;
            case 4:
                viewPendingOrders();
                break;
            case 5:
                shipOrder();
                break;
            case 6:
                viewInventory();
                break;
            default:
                System.out.println("Scelta non valida!");
        }
    }
    
    private void handleCustomerAction(CustomerUser customer, int choice) {
        switch (choice) {
            case 1:
                viewAvailableClothes();
                break;
            case 2:
                buyClothing(customer);
                break;
            case 3:
                viewCart(customer);
                break;
            case 4:
                processCheckout(customer);
                break;
            case 5:
                viewOrderStatus(customer);
                break;
            default:
                System.out.println("Scelta non valida!");
        }
    }
    
    private void addClothingItem() {
        System.out.print("ID vestito: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Tipologia: ");
        String type = scanner.nextLine();
        System.out.print("Prezzo: ");
        double price = getDoubleInput();
        
        ClothingItem item = new BasicClothingItem(id, name, type, price);
        database.addClothingItem(item);
        System.out.println("Vestito aggiunto con successo!");
    }
    
    private void removeClothingItem() {
        System.out.print("ID vestito da rimuovere: ");
        String id = scanner.nextLine();
        
        if (database.getClothingItem(id) != null) {
            database.removeClothingItem(id);
            System.out.println("Vestito rimosso con successo!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void addDiscount() {
        System.out.print("ID vestito: ");
        String id = scanner.nextLine();
        ClothingItem item = database.getClothingItem(id);
        
        if (item != null) {
            System.out.print("Percentuale sconto (10-80%): ");
            double discount = getDoubleInput();
            
            ClothingItem discountedItem = new DiscountDecorator(item, discount);
            database.addClothingItem(discountedItem);
            System.out.println("Sconto applicato con successo!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void viewPendingOrders() {
        List<Order> pendingOrders = database.getPendingOrders();
        System.out.println("\n=== ORDINI IN ATTESA DI SPEDIZIONE ===");
        
        if (pendingOrders.isEmpty()) {
            System.out.println("Nessun ordine in attesa di spedizione.");
        } else {
            for (Order order : pendingOrders) {
                User customer = database.getUser(order.getCustomerId());
                System.out.println("Ordine: " + order.getOrderId() + 
                                 " - Cliente: " + customer.getNickname() + 
                                 " - Totale: €" + String.format("%.2f", order.getTotal()));
            }
        }
    }
    
    private void shipOrder() {
        System.out.print("ID ordine da spedire: ");
        String orderId = scanner.nextLine();
        Order order = database.getOrder(orderId);
        
        if (order != null && order.getStatus() == Order.OrderStatus.PAID) {
            order.updateStatus(Order.OrderStatus.SHIPPED);
            System.out.println("Ordine spedito con successo!");
        } else {
            System.out.println("Ordine non trovato o non valido per la spedizione!");
        }
    }
    
    private void viewInventory() {
        System.out.println("\n=== INVENTARIO ===");
        List<ClothingItem> inventory = database.getInventory();
        
        if (inventory.isEmpty()) {
            System.out.println("Inventario vuoto.");
        } else {
            for (ClothingItem item : inventory) {
                System.out.println(item.getId() + " - " + item.getDescription() + 
                                 " - €" + String.format("%.2f", item.getPrice()));
            }
        }
    }
    
    private void viewAvailableClothes() {
        System.out.println("\n=== VESTITI DISPONIBILI ===");
        List<ClothingItem> inventory = database.getInventory();
        
        if (inventory.isEmpty()) {
            System.out.println("Nessun vestito disponibile.");
        } else {
            for (ClothingItem item : inventory) {
                System.out.println(item.getId() + " - " + item.getDescription() + 
                                 " - €" + String.format("%.2f", item.getPrice()));
            }
        }
    }
    
    private void buyClothing(CustomerUser customer) {
        System.out.print("ID vestito da acquistare: ");
        String id = scanner.nextLine();
        ClothingItem item = database.getClothingItem(id);
        
        if (item != null) {
            customer.addToCart(item);
            System.out.println("Vestito aggiunto al carrello!");
        } else {
            System.out.println("Vestito non trovato!");
        }
    }
    
    private void viewCart(CustomerUser customer) {
        System.out.println("\n=== CARRELLO ===");
        List<ClothingItem> cart = customer.getCart();
        
        if (cart.isEmpty()) {
            System.out.println("Carrello vuoto.");
        } else {
            double total = 0;
            for (ClothingItem item : cart) {
                System.out.println(item.getDescription() + " - €" + String.format("%.2f", item.getPrice()));
                total += item.getPrice();
            }
            System.out.println("Totale: €" + String.format("%.2f", total));
        }
    }
    
    private void processCheckout(CustomerUser customer) {
        if (customer.getCart().isEmpty()) {
            System.out.println("Carrello vuoto!");
            return;
        }
        
        // Calcolo totale
        double total = 0;
        for (ClothingItem item : customer.getCart()) {
            total += item.getPrice();
        }
        
        // Scelta metodo di pagamento
        System.out.println("Scegli metodo di pagamento:");
        System.out.println("1. Carta di credito");
        System.out.println("2. PayPal");
        int paymentChoice = getIntInput();
        
        PaymentStrategy paymentStrategy;
        if (paymentChoice == 1) {
            System.out.print("Numero carta: ");
            String cardNumber = scanner.nextLine();
            paymentStrategy = new CreditCardPayment(cardNumber);
        } else {
            paymentStrategy = new PayPalPayment(customer.getEmail());
        }
        
        // Scelta metodo di spedizione
        System.out.println("Scegli metodo di spedizione:");
        System.out.println("1. Spedizione standard");
        System.out.println("2. Spedizione express");
        int shippingChoice = getIntInput();
        
        ShippingStrategy shippingStrategy;
        if (shippingChoice == 1) {
            shippingStrategy = new StandardShipping();
        } else {
            shippingStrategy = new ExpressShipping();
        }
        
        double shippingCost = shippingStrategy.calculateShippingCost(total);
        double finalTotal = total + shippingCost;
        
        // Creazione ordine
        String orderId = database.generateOrderId();
        Order order = new Order(orderId, customer.getId());
        order.setItems(customer.getCart());
        order.setTotal(finalTotal);
        order.setPaymentStrategy(paymentStrategy);
        order.setShippingStrategy(shippingStrategy);
        
        // Registrazione per notifiche
        NotificationService notificationService = new NotificationService(customer.getEmail());
        order.registerObserver(notificationService);
        
        // Processamento pagamento
        if (paymentStrategy.processPayment(finalTotal)) {
            order.updateStatus(Order.OrderStatus.PAID);
            database.addOrder(order);
            customer.addOrder(order);
            customer.clearCart();
            
            System.out.println("Ordine completato con successo!");
            System.out.println("ID Ordine: " + orderId);
            System.out.println("Totale pagato: €" + String.format("%.2f", finalTotal));
        } else {
            System.out.println("Errore nel pagamento!");
        }
    }
    
    private void viewOrderStatus(CustomerUser customer) {
        System.out.println("\n=== STATO ORDINI ===");
        List<Order> orders = customer.getOrderHistory();
        
        if (orders.isEmpty()) {
            System.out.println("Nessun ordine trovato.");
        } else {
            for (Order order : orders) {
                System.out.println("Ordine: " + order.getOrderId() + 
                                 " - Stato: " + order.getStatus() + 
                                 " - Totale: €" + String.format("%.2f", order.getTotal()));
            }
        }
    }
    
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}

// ================ MAIN CLASS ================
public class EcommerceSystem {
    public static void main(String[] args) {
        EcommerceFacade facade = new EcommerceFacade();
        facade.startApplication();
    }
}