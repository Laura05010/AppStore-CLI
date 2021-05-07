package main;
import java.util.ArrayList;

/**
 * A class to represent a BuyerDev user.
 */
public class BuyerDev extends User {

    private ArrayList<App> appsOwned;
    private ArrayList<App> appsToSell;
    private ArrayList<App> newPurchases;

    /**
     * Constructs a BuyerDev user that has a userName, userType and an accountBalance
     * Includes the apps that the user owns (appsOwned), the apps that the user can sell(appsToSell), and
     * the apps that the user bought today (newPurchases)
     *
     * @param userName the user name of the full standard user
     * @param userType the type of the user
     * @param accountBalance  the account balance of the user
     */
    public BuyerDev(String userName, String userType, double accountBalance) {
        super(userName, userType, accountBalance);
        this.appsOwned = new ArrayList<>();
        this.appsToSell = new ArrayList<>();
        this.newPurchases = new ArrayList<>();
    }

    /**
     * Returns the apps that the user owns.
     * @return the user's appsOwned
     */
    @Override
    public ArrayList<App> getAppsOwned() {
        return appsOwned;
    }

    /**
     * Returns the apps that of the user sells.
     * @return the user's inventory of apps for sale
     */
    @Override
    public ArrayList<App> getAppsToSell() {
        return appsToSell;
    }

    /**
     * Returns the apps that the user bought today.
     * @return the user's inventory of apps bought today
     */
    @Override
    public ArrayList<App> getNewPurchases() {
        return this.newPurchases;
    }
}
