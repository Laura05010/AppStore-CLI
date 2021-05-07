package main;
import java.util.ArrayList;

/** A class to represent a buy transaction
 */
public class BuyTransaction extends Transaction{

    private String developerName;
    private String appName;

    /**
     * Constructs a buy Transaction with the buyer, the developer, and the name of the app to be bought
     *
     * @param buyerName the name of the buyer
     * @param developerName the name of the developer
     * @param appName the name of the app to be bought
     * @param database a copy of the current database
     */
    public BuyTransaction(String buyerName, String developerName, String appName, Database database){
        super(buyerName, database);
        this.developerName = developerName;
        this.appName = appName;
    }

    /**
     * Returns a String representation of the buy transaction execution.
     *
     * @return a String representation if the buy transaction is successful or error otherwise.
     */
    @Override
    public String execute(){
        User buyer = this.getMainTransactionUser();
        User developer = this.database.getUser(this.developerName);
        String invalid = checkInvalidity(buyer, developer, appName);
        if(invalid.equals("Valid")){
            App app = this.database.appsAvailable(appName, developer);
            double buyerCurrValue = Math.floor((buyer.getAccountBalance() - app.getPrice()) * 100.0)/100.0;
            double developerCurrValue = Math.floor((developer.getAccountBalance() + app.getPrice()) * 100.0)/100.0;
            developer.setAccountBalance(developerCurrValue);
            buyer.setAccountBalance(buyerCurrValue);
            buyer.getAppsOwned().add(app);
            buyer.getNewPurchases().add(app);
            return toString();
        }
        return invalid;
    }

    /**
     * Returns a String based on the validity of the buy transaction to be executed
     *
     * @param buyer a buyer's object
     * @param developer a developer's object
     * @param appName the name of the app to be bought
     * @return a String representation if the buy transaction is invalid, empty string otherwise.
     */
    private String checkInvalidity(User buyer, User developer, String appName){
        if(buyer == null || developer == null) {
            return "Constraint Error: cannot proceed buy transaction as user doesn't exist";
        }

        else if (buyer.getUserType().equals("DD") || developer.getUserType().equals("BS")|| buyer == developer){
            return "Constraint Error: cannot proceed buy transaction as the user requesting this transacting does " +
                    "not have permission to proceed";
        }
        else if(!buyer.getUserName().equals(mainUser)){
            return "Constraint Error: user cannot buy an app on behalf of another user";
        }
        App app = this.database.appsAvailable(appName, developer);

        if (app == null || this.appAlreadyOwned(buyer, appName)){
            return "Constraint Error: cannot proceed buy transaction as app doesn't exist in developer's inventory or " +
                    "buyer already owns this app";
        }
        else if (this.database.getNewAppsAdded().contains(app)){
            return "Constraint Error: cannot proceed buy transaction as app is recently added";
        }

        if (buyer.getAccountBalance() - app.getPrice() < 0 || developer.getAccountBalance() + app.getPrice() > User.MAXBALANCE){
            return "Constraint Error: cannot proceed buy transaction as there are insufficient funds from the user or " +
                    "potential overflowing funds for the developer";
        }
        return "Valid";
    }

    /**
     * Returns true iff the buyer already owns the app
     *
     * @param buyer the user that is buying the app
     * @param appName the name of the app to be bought
     * @return true if the buyer already owns the app, false otherwise.
     */
    private boolean appAlreadyOwned(User buyer, String appName){
        ArrayList<App> apps = buyer.getAppsOwned();
        for (App app: apps){
            if (app.getAppName().equals(appName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a String representation of the buy transaction
     *
     * @return a String representation of the buy transaction
     */
    @Override
    public String toString(){
        return "The user: "+ this.mainUser + ", bought " + this.appName + " from another user: " + this.developerName + ", for " +
                this.database.appsAvailable(appName, this.database.getUser(this.developerName)).getPrice();
    }

}
