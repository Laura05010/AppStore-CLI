package main;
import java.util.ArrayList;

/**
 * A class to represent a Developer user.
 */
public class Developer extends User{

    private ArrayList<App> appsToSell;

    /**
     * Constructs a Developer user that has a userName, userType and an accountBalance
     * Includes the apps that the user can sell(appsToSell)
     *
     * @param userName the user name of the customer
     * @param userType the type of the user
     * @param accountBalance the account balance of the user
     */
    public Developer(String userName, String userType, double accountBalance){
        super(userName, userType, accountBalance);
        this.appsToSell = new ArrayList<>();
    }

    /**
     * Returns the apps that the Developer can sell.
     * @return the Developer's inventory of apps for sale
     */
    @Override
    public ArrayList<App> getAppsToSell() {
        return this.appsToSell;
    }

}
