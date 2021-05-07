package main;
import java.util.ArrayList;

/**
 * A class to represent a Buyer user.
 */
public class Buyer extends User{

    private ArrayList<App> appsOwned;
    private ArrayList<App> newPurchases;

    /**
     * Constructs a Buyer user that has a userName, userType and an accountBalance
     * Includes the apps that the user owns (appsOwned) and the apps that the user bought today (newPurchases)
     *
     * @param userName the user name of the customer
     * @param userType the type of the user
     * @param accountBalance the account balance of the user
     */
    public Buyer(String userName, String userType, double accountBalance){
        super(userName, userType, accountBalance);
        this.appsOwned = new ArrayList<>();
        this.newPurchases = new ArrayList<>();
    }

    /**
     * Returns the apps that of the user owns.
     *
     * @return the Buyer's inventory of apps for sale
     */
    @Override
    public ArrayList<App> getAppsOwned() {
        return appsOwned;
    }

    /**
     * Returns the apps that the user bought today.
     *
     * @return the Buyer's inventory of apps bought today
     */
    @Override
    public ArrayList<App> getNewPurchases() {
        return this.newPurchases;
    }

}