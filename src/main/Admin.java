package main;
import java.util.ArrayList;

/**
 * A class to represent an Admin User.
 */
public class Admin extends User{

    private ArrayList<App> appsOwned;
    private ArrayList<App> appsToSell;
    private ArrayList<App> newPurchases;

    /**
     * Constructs an Admin user that has a userName, userType and an accountBalance
     * Includes the games that the user owns (gamesOwned), the games that the user can sell(gamesToSell), and
     * the games that the user bought today (newPurchases)
     *
     * @param userName the user name of the admin
     * @param userType the type of the user
     * @param accountBalance the account balance of the user
     */
    public Admin(String userName, String userType, double accountBalance){
        super(userName, userType, accountBalance);
        this.appsOwned = new ArrayList<>();
        this.appsToSell = new ArrayList<>();
        this.newPurchases = new ArrayList<>();
    }

    /**
     * Returns the apps that the admin owns.
     *
     * @return the buyer's gamesOwned
     */
    @Override
    public ArrayList<App> getAppsOwned() {
        return this.appsOwned;
    }

    /**
     * Returns the games that of the admin sells.
     *
     * @return the Admin's inventory of games for sale
     */
    @Override
    public ArrayList<App> getAppsToSell() {
        return this.appsToSell;
    }

    /**
     * Returns the games that the admin bought today.
     *
     * @return the Admin's inventory of games bought today
     */
    @Override
    public ArrayList<App> getNewPurchases() {
        return this.newPurchases;
    }

    /**
     * Creates a user using the userName, userType and accountBalance.
     * Returns a String representation of the transaction
     *
     * @param newUserName the user name of the user to be created
     * @param userType the user type of the user to be created
     * @param accountBalance the account balance of the user to be created
     * @return a String representation of the create user operation
     */
    public String requestCreateUser(String newUserName, String userType, double accountBalance){
        Transaction createUser = new CreateUserTransaction(this.userName, newUserName, accountBalance, userType, this.getDatabase());
        return createUser.execute();

    }

    /**
     * Removes a user and return a String representation of the operation
     *
     * @param userToRemove the name of the user to be deleted
     * @return a String representation of the delete user operation
     */
    public String requestDeleteUser(String userToRemove){
        Transaction deleteUser = new DeleteUserTransaction(this.userName, userToRemove, this.getDatabase());
        return deleteUser.execute();
    }

    /**
     * Adds credit to the User and return a String representation of the operation
     *
     * @param receivingUser the user name of the user to receive the credit
     * @param credit the credit to be added to the user
     * @return a String representation of the add credit operation
     */
    @Override
    public String requestAddCredit(String receivingUser, double credit){
        Transaction addCredit = new AddCreditTransaction(this.userName, receivingUser, credit, this.getDatabase());
        return addCredit.execute();
    }

    /**
     * Refunds the User and return a String representation of the operation
     *
     * @param buyerName the buyer's user name
     * @param sellerName the seller's user name
     * @param creditTransfer the credit to be refunded
     * @return a String representation of the refund operation
     */
    public String requestRefund(String buyerName, String sellerName, double creditTransfer) {
        Transaction refund = new RefundTransaction(buyerName, sellerName, creditTransfer, this.getDatabase());
        return refund.execute();

    }

    /**
     * Removes the game from a User's inventory of games owned.
     *
     * @param gameName the game name
     * @param ownerName the name of the owner (optional)
     * @return the String representation of the remove game operation
     */
    @Override
    public String requestRemoveGame(String gameName, String ownerName){
        if (!ownerName.equals("")) {
            Transaction removeGame = new RemoveGameTransaction(this.userName, this.currDatabase, gameName, ownerName);

            return removeGame.execute();
        }
        return "Constraint Error: cannot remove a game that does not have an owner";
    }
}