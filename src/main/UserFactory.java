package main;

/**
 * A class to represent a UserFactory.
 */
public class UserFactory {

    protected Database database;

    /**
     * Sets the database
     * @param database the current database
     */
    protected void setDatabase(Database database){this.database = database;}

    /**
     * Creates a user based on the usertype, userName, and accountBalance
     * @param userType the type of the user
     * @param userName the user name of the customer
     * @param accountBalance the account balance of the user
     * @return User object representing the user
     */
    public User createUserBase(String userType, String userName, double accountBalance) {

        User user = null;

        if (userType.equalsIgnoreCase("AA")) {
            user = new Admin(userName, userType, accountBalance);
        } else if (userType.equalsIgnoreCase("BD")) {
            user = new BuyerDev(userName, userType, accountBalance);
        } else if (userType.equalsIgnoreCase("BS")) {
            user = new Buyer(userName, userType, accountBalance);
        } else if (userType.equalsIgnoreCase("DD")) {
            user = new Developer(userName, userType, accountBalance);
        }
        return user;
    }
}