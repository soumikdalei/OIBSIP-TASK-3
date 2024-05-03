import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
class User {
    private String userId;
    private String pin;
    private double balance;


    User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;

    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double b){
        this.balance=b;
    }


}
class Transaction{
    private String type;
    private double amount; private String date;
    Transaction(String type,double amount){
        this.type=type;
        this.amount=amount;
        Date d = new Date();
        String s = d.toString();
        this.date = s;
    }
    public String getType(){
        return type;
    }
    public double getamount(){
        return amount;
    }
    public String getDate() {
        return date;
    }

}
class TransactionHistory{
    private ArrayList<Transaction> transactions;
    public TransactionHistory(){
        transactions=new ArrayList<>();
    }
    public void addTransactions(Transaction transaction){
        transactions.add(transaction);
    }
    public void showTransactionHistory(){
        for (Transaction t:transactions){
            System.out.println("Type:"+t.getType()+" Amount:"+ t.getamount()+" Time:"+t.getDate());
        }
    }
}
class ATM{
    private HashMap<String, User> users;
    private TransactionHistory transactionHistory;
    public Scanner sc=new Scanner(System.in);
    private boolean authenticator(String userId,String pin){
        if(users.containsKey(userId)){
            User user=users.get(userId);
            return user.getPin().equals(pin);
        }
        return false;
    }
    private void withdraw(){
        System.out.println("Enter amount to be withdrawn:");
        double withdraw=sc.nextDouble();
        sc.nextLine();
        String userId=getUserIdFromUser();
        if(withdraw<=0){
            System.out.println("Invalid entry");
            return;
        }
        User user=users.get(userId);
        if(user.getBalance()<withdraw){
            System.out.println("Insufficient Balance");
            return;
        }
        user.setBalance(user.getBalance()-withdraw);
        transactionHistory.addTransactions(new Transaction("Withdraw",withdraw));
        System.out.println("Withdraw Successful,Current Balance:"+user.getBalance());

    }
    private void deposit(){
        System.out.println("Enter amount to be deposited:");
        double deposit=sc.nextDouble();
        sc.nextLine();
        String userId=getUserIdFromUser();
        if(deposit<=0){
            System.out.println("Invalid Amount");
            return;
        }
        User user=users.get(userId);
        user.setBalance(user.getBalance()+deposit);
        transactionHistory.addTransactions(new Transaction("Deposit",deposit));
        System.out.println("Withdraw Successful,Current Balance:"+user.getBalance());
    }
    private void transfer(){
        System.out.println("Enter recipient's ID:");
        String recipientId=sc.nextLine();
        System.out.println("Enter amount to be transferred:");
        double amount=sc.nextDouble();
        sc.nextLine();
        String userId=getUserIdFromUser();
        if(!users.containsKey(recipientId)){
            System.out.println("Recipient's user id not found!");
            return;
        }
        if(amount<=0){
            System.out.println("Invalid amount");
            return;
        }
        User sender=users.get(userId);
        User recipient=users.get(recipientId);
        if(sender.getBalance()<amount){
            System.out.println("Insufficient balance");
            return;
        }
        sender.setBalance(sender.getBalance()-amount);
        recipient.setBalance(recipient.getBalance()+amount);
        transactionHistory.addTransactions(new Transaction("Transfer to " + recipientId, amount));
        System.out.println("Transfer successful. Current balance: " + sender.getBalance());
    }
    private void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }
    public ATM(){
        users=new HashMap<>();
        users.put("123456", new User("123456", "1234", 1000.0));
        users.put("654321", new User("654321", "4321", 2000.0));
        transactionHistory = new TransactionHistory();
    }
    public void start() {
        System.out.println("Welcome to the ATM");
        System.out.print("Enter user ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        if (authenticator(userId, pin)) {
            System.out.println("Authentication successful!");
            int choice;
            do { displayMenu();
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline character
                switch (choice) {
                    case 1:
                        transactionHistory.showTransactionHistory();
                        break;
                    case 2:
                        withdraw();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 5);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }
    private String getUserIdFromUser() {
        System.out.print("Enter your user ID: ");
        return sc.nextLine();
    }
}
public class TASK_3 {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}