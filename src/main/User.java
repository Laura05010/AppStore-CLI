package main;
import java.util.ArrayList;

/**
 * A class to represent a user.
 */
public class User{

    public static final double MAXCREDIT = 1000.00, MAXBALANCE = 999999.99, MAXAPPPRICE = 99.99;
    public static final int MAXUSERNAME = 15, MAXAPPTITLE = 30;
    protected String userName;
    protected String userType;
    protected double accountBalance;
    protected Database currDatabase;
    protected double creditToUse = MAXCREDIT;


    /**
     * Constructs a User that has a userName, userType and an accountBalance
     *
     * @param userName the user name of the customer
     * @param userType the type of the user
     * @param accountBalance the account balance of the user
     */
    public User(String userName, String userType, double accountBalance){
        this.userName = userName;
        this.userType = userType;
        this.accountBalance = accountBalance;
    }

    /**
     * Returns the username of the user.
     * @return the user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the usertype of the user.
     * @return the user's type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Returns the account balance of the user.
     * @return the user's account balance
     */
    public double getAccountBalance() {
        return accountBalance;
    }

    /**
     * Returns the current database of the system.
     * @return the database of the system
     */
    public Database getDatabase() {
        return this.currDatabase;
    }

    /**
     * Returns the apps that the user owns.
     * @return the user's ArrayList of appsOwned
     */
    public ArrayList<App> getAppsOwned(){
        return new ArrayList<>();
    }

    /**
     * Returns the apps that of the user sells.
     * @return the user's inventory of apps for sale
     */
    public ArrayList<App> getAppsToSell() {
        return new ArrayList<>();
    }

    /**
     * Returns the apps that the user bought today.
     * @return the user's inventory of apps bought today
     */
    public ArrayList<App> getNewPurchases() {
        return new ArrayList<>();
    }

    /**
     * Sets a new account balance for the user
     * @param newAccountBalance the account balance of the user
     */
    protected void setAccountBalance(double newAccountBalance){
        this.accountBalance = newAccountBalance;
    }

    /**
     * Sets the database.
     * @param database the database of the system
     */
    public void setDatabase(Database database){
        this.currDatabase = database;
    }

    /**
     * Adds credit to the users.
     * Returns a string representation based on whether the requestAddCredit operation is successful
     * @param userName the name the current user requesting the operation
     * @param credit the credit to be transferred
     *
     * @return a string representation based on whether the requestAddCredit operation is successful, error otherwise.
     */
    public String requestAddCredit(String userName, double credit){
        if(!this.userName.equals(userName)){
            return "Constraint Error: cannot proceed add credit as non admin attempts to add credit to another user";
        }
        Transaction addCredit = new AddCreditTransaction(userName, credit, this.currDatabase);
        return addCredit.execute();
    }

    /**
     * Buys the app for the user.
     * Returns a string representation based on whether the requestBuy operation is successful
     * @param appName the app the buyer wants to buy
     * @param sellerName the seller's name
     * @param buyerName the buyer's user name
     *
     * @return a string representation based on whether the requestBuy operation is successful, error otherwise.
     */
    public String requestBuy(String appName, String sellerName, String buyerName){
        Transaction buy = new BuyTransaction(buyerName, sellerName, appName, this.currDatabase);
        return buy.execute();
    }

    /**
     * Allows seller to request putting the app up for sale.
     * Returns a string representation based on whether the requestSell operation is successful
     * @param appName the app name
     * @param appPrice the original price of the app
     * @param userName the seller's user name
     *
     * @return a string representation based on whether the requestSell operation is successful, error otherwise.
     */
    public String requestSell(String appName, double appPrice, String userName){
        Transaction sell = new SellTransaction(userName, appName, appPrice, this.currDatabase);

        return sell.execute();
    }

    /**
     * Allows owner to request removing the app from their inventory.
     * Returns a string representation based on whether the requestRemoveApp operation is successful
     * @param appName the app name
     * @param ownerName the name of the owner (optional)
     *
     * @return a string representation based on whether the requestRemoveApp operation is successful, error otherwise.
     */
    public String requestRemoveApp(String appName, String ownerName){
        if(ownerName.equals("")){
            ownerName = this.userName;
        }
        Transaction removeApp = new RemoveAppTransaction(this.userName, this.currDatabase, appName, ownerName);
        return removeApp.execute();
    }
}