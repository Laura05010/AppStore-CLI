package main;

/** A class to represent the add credit transaction.
 */
public class AddCreditTransaction extends Transaction{

    private double credit;
    private String receivingUserName;

    /**
     * Constructs an add credit Transaction with the user and the credit to be added to the user's own accountBalance
     *
     * @param userName the name of the current user requesting the transaction
     * @param credit the amount of credit to be added to the user's balance
     * @param database a copy of the current database
     */
    public AddCreditTransaction(String userName, double credit, Database database){
        this(userName, userName, credit, database);
    }

    /**
     * Constructs a Add Credit Transaction with the user, receiving user and
     * the credit to be transferred from the user to the receiving user's accountBalance
     *
     * @param adminName the name of the admin that is adding the credit
     * @param receivingUserName the name of the user that is receiving the credit
     * @param credit the amount of credit to be added to the user's balance
     * @param database a copy of the current database
     */
    public AddCreditTransaction(String adminName, String receivingUserName, double credit, Database database) {
        super(adminName, database);
        this.credit = credit;
        this.receivingUserName = receivingUserName;
    }

    /**
     * Returns a String representation when the add credit transaction is executed.
     *
     * @return a String representation if the add credit transaction is successful or error otherwise.
     */
    @Override
    public String execute(){
        String invalid = checkInvalidity(this.receivingUserName, this.credit);
        if(invalid.equals("Valid")){
            User currUser = this.database.getUser(this.receivingUserName);
            double value = Math.floor((currUser.getAccountBalance() + this.credit) * 100.0)/100.0;
            currUser.setAccountBalance(value);
            getMainTransactionUser().creditToUse = Math.floor(( getMainTransactionUser().creditToUse - this.credit) * 100.0)/100.0;
            return toString();
        }
        return invalid;
    }

    /**
     * Returns a String representation if the add credit transaction is invalid.
     *
     * @param receivingUserName the receiving user's name
     * @param credits the amount of money being added to the receiving user
     *
     * @return  a String representation if the add credit transaction is invalid, empty string otherwise.
     */
    private String checkInvalidity(String receivingUserName, double credits){
        User currUser = this.database.getUser(receivingUserName);
        // checks if the user exists
        if (currUser == null){
            return "Constraint Error: cannot add credit as username doesn't exist";
        }
        if (!getMainTransactionUser().getUserType().equals("AA") && !mainUser.equals(currUser.userName)){
            return "Constraint Error: cannot add credit as non-admin cannot add credit to other users";
        }
        // checks that credit doesn't exceed limit
        if (credits > User.MAXCREDIT || getMainTransactionUser().creditToUse - credits < 0){
            return "Constraint Error: cannot add credit as the max credit is $1000.00";
        }
        // checks if the credit is greater than 0
        if (credits < 0){
            return "Constraint Error: cannot add credit due to negative values";
        }

        double value = currUser.getAccountBalance() + credits;
        // checks if the new amount doesn't not exceed limit
        // if value maxes then final account balance must be MAX BALANCE
        if (value > User.MAXBALANCE){
            this.credit = Math.floor((User.MAXBALANCE - currUser.getAccountBalance()) * 100.0)/100.0;
            System.out.println("Warning: this transfer is overflowing the max credit of 999,999.99");
        }
        return "Valid";
    }

    /**
     * Returns a String representation of the add credit transaction
     *
     * @return a String representation of the add credit transaction
     */
    @Override
    public String toString(){
        if (this.mainUser.equals(this.receivingUserName)){
            return "The user: "+ this.mainUser + ", added " + this.credit + " to themselves.";
        }
        return "The user: " + this.mainUser + ", added " + this.credit + " to another user: " + this.receivingUserName;
    }
}
