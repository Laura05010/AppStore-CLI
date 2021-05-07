package main;

import java.util.ArrayList;

/** A class to represent a transaction
 */
public abstract class Transaction {

    protected String mainUser;
    protected Database database;

    /**
     * Constructs a Transaction with the user and the current database
     * @param userName the name of the current user requesting the transaction
     * @param database a copy of the current database
     */
    public Transaction(String userName, Database database) {
        this.mainUser = userName;
        this.database = database;
    }

    /**
     * Return the current user object
     */
    public User getMainTransactionUser(){
        return database.getUser(mainUser);
    }

    /**
     * Returns a String representation when the transaction is executed.
     * @return a String representation if the transaction is valid or error otherwise.
     */
    public abstract String execute();

    /**
     * Returns a String based on whether the App exists in the user's inventory and is not bought on the same day
     * @return a String representation if the App exists in the user's inventory and not bought on the same day, error otherwise.
     */
    protected String AppOnTheSameDay(User owner, String AppName){
        App currApp = null;
        boolean sameDay;
        if(!owner.getUserType().equals("BS")){
            if(owner.getUserType().equals("BD")|| owner.getUserType().equals("AA")){
                currApp = appExistsInBuyerInv(owner, AppName);
            }
            if (currApp == null){
                currApp = this.database.appsAvailable(AppName, owner);
                sameDay = this.database.getNewAppsAdded().contains(currApp);
            }
            else{
                sameDay = owner.getNewPurchases().contains(currApp);
            }
        }
        else{
            currApp = appExistsInBuyerInv(owner, AppName);
            sameDay = owner.getNewPurchases().contains(currApp);
        }
        if (currApp == null){
            return "Constraint Error: cannot proceed remove App transaction as App doesn't exist in the inventory";
        }
        if (sameDay){
            return "Constraint Error: cannot proceed remove App transaction as App was either brought or put on sale" +
                    " on the same day";
        }
        return "Valid";
    }

    /**
     * Returns the App that belongs to the user or null if the App is not part of the user's inventory
     * @param buyer the user that is buying the product
     * @param AppName the App's name
     * @return App object if the App belongs to the user, null otherwise.
     */

    protected App appExistsInBuyerInv(User buyer, String AppName){
        ArrayList<App> Apps = buyer.getAppsOwned();
        for (App g: Apps){
            if (g.getAppName().equals(AppName)){
                return g;
            }
        }
        return null;
    }

    /**
     * Returns a String representation of the transaction
     * @return a String representation of transaction
     */
    public abstract String toString();

}
