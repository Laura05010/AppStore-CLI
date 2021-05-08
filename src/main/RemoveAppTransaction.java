package main;

/** A class to represent a remove app transaction
 */
public class RemoveAppTransaction extends Transaction{
    private String appName;
    private String ownerName;

    /**
     * Constructs a remove app Transaction with the user that requested the transaction, the current database,
     * the name of the app of be removed and the owner of the app
     *
     * @param userName the name of the current user requesting the transaction
     * @param database a copy of the current database
     * @param appName the name of the app to be removed
     * @param ownerName the name of the user that owns the app to be removed
     */
    public RemoveAppTransaction(String userName, Database database, String appName, String ownerName) {
        super(userName, database);
        this.ownerName = ownerName;
        this.appName = appName;
    }

    /**
     * Returns a String representation when the user requests to remove app
     * @return a String representation if the remove app transaction is valid or error otherwise.
     */
    @Override
    public String execute() {
        User owner = this.database.getUser(this.ownerName);
        String invalid = checkInvalidity(owner, this.appName);
        if(invalid.equals("Valid")){
            if(owner.getUserType().equals("BS")){
                owner.getAppsOwned().remove(appExistsInBuyerInv(owner, appName));
            }
            else if (owner.getUserType().equals("SS")){
                App app = this.database.appsAvailable(appName, owner);
                owner.getAppsToSell().remove(app);
                this.database.removeApp(app);
            }
            else{
                if(appExistsInBuyerInv(owner, appName) != null) {
                    owner.getAppsOwned().remove(appExistsInBuyerInv(owner, appName));
                }
                else {
                    App app = this.database.appsAvailable(appName, owner);
                    owner.getAppsToSell().remove(app);
                    this.database.removeApp(app);
                    app.setEmptyDeveloper();
                }
            }
            return toString();
        }
        return invalid;
    }

    /**
     * Returns a String based on the validity of the remove app transaction to be executed
     * @return a String representation if the remove app transaction is valid or error otherwise.
     */
    private String checkInvalidity(User owner, String appName){
        if(owner == null) {
            return "Constraint Error: cannot proceed remove app transaction as user doesn't exist";
        }
        if(!getMainTransactionUser().getUserType().equals("AA") && !ownerName.equals(mainUser)){
            return "Constraint Error: cannot proceed remove app transaction as user is non admin and removing apps " +
                    "other than their own";
        }
        return this.appOnTheSameDay(owner, appName);
    }

    /**
     * Returns a String representation of the remove app transaction
     * @return a String representation of the remove app transaction
     */
    @Override
    public String toString() {
        if(this.ownerName.equals(this.mainUser)){
            return "The user: " + this.ownerName + ", removes " + this.appName + " from their inventory";
        }
        return "The user: " + this.mainUser + ", removes " + this.appName + " from another user: " + this.ownerName + "'s inventory";
    }
}
