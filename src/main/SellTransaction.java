package main;

/** A class to represent a sell transaction
 */
public class SellTransaction extends Transaction{

    private double price;
    private String appName;
    private String category;

    /**
     * Constructs a sell Transaction with the seller, the name of the app to be sold and its price
     * @param sellerName the name of the seller
     * @param appName the name of the app to be sold
     * @param price the price of the app
     * @param category the category of the app
     * @param database a copy of the current database
     */
    public SellTransaction(String sellerName,  String appName,  double price, String category, Database database){
        super(sellerName, database);
        this.appName = appName;
        this.price = price;
        this.category = category;
    }

    /**
     * Returns a String representation when the user requests to sell app
     * @return a String representation if the sell app transaction is valid or error otherwise.
     */
    @Override
    public String execute(){
        User seller = this.getMainTransactionUser();
        String invalid = checkInvalidity(seller, this.appName, this.price);
        if(invalid.equals("Valid")){
            App app = new App(appName, price, seller, category);
            seller.getAppsToSell().add(app);
            this.database.addApp(app);
            this.database.getNewAppsAdded().add(app);
            return toString();
        }
        return invalid;
    }

    /**
     * Returns a String based on the validity of the sell app transaction to be executed
     * @return a String representation if the sell app transaction is valid or error otherwise.
     */
    private String checkInvalidity(User seller, String appName, double price){
        if(seller == null){
            return "Constraint Error: cannot sell app as user doesn't exist";
        }
        else if (seller.getUserType().equals("BS")){
            return "Constraint Error: cannot sell app as user is a buyer";
        }
        else if(!seller.getUserName().equals(mainUser)){
            return "Constraint Error: cannot sell app as user cannot put a app on behalf of another user";
        }
        else if(appName.length() > User.MAXAPPTITLE){
            return "Constraint Error: cannot sell app as app name should be at max length of 25";
        }
        else if(price > User.MAXAPPPRICE) {
            return "Constraint Error: cannot sell app as the max sale price must be $99.99";
        }
        else if(this.database.getAllApps().containsKey(appName)){
            return "Constraint Error: cannot sell app that already exists in the platform";
        }

        if(this.database.appsAvailable(appName,seller) != null){
            return "Constraint Error: cannot proceed sell transaction as app exists in seller's inventory";
        }
        else if ((seller.getUserType().equals("AA") || seller.getUserType().equals("BD")) &&
                appExistsInBuyerInv(seller, appName) != null){
            return "Constraint Error: cannot proceed sell transaction as seller is attempting to sell a app that " +
                    "they brought";
        }

        return "Valid";

    }

    /**
     * Returns a String representation of the sell transaction
     * @return a String representation of the sell transaction
     */
    @Override
    public String toString(){
        return "The user: "+ this.mainUser + ", is selling " + this.appName + " for $" + this.price;
    }
}
