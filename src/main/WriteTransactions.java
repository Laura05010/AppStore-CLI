package main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class to represent the execution of the transaction
 */
public class WriteTransactions{
    private String currUser, regex, userName, sellerName, buyerName, appName;
    private ArrayList<String> transactionElements;
    private double accountBalance;
    private final double EPSILON = 0.001;
    public Database database;

    /**
     * Constructs the daily transaction file reader and the daily transaction receipt file writer for the class
     * and gives feedback to the user regarding the daily transaction file
     *
     * @param database the current database of the daily.txt
     */
    public WriteTransactions(Database database) {
        this.database = database;
        this.currUser = null;
    }
    /**
     * Converts each line of the daily transaction file into the transaction elements needed
     * to create transactions in the system
     *
     * @param currLine the current line of the file that is being read
     * @param regex    the regex used to match the current line
     * @return ArrayList<String> an array list with the elements of the transaction in order to be instantiated as
     * variables for the official transactions
     */
    public ArrayList<String> format(String currLine, String regex) {
        Pattern pattern;
        Matcher matcher;
        ArrayList<String> transactionElements = new ArrayList<>();
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(currLine);

        // NOTE: groups start @ 1.
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (!matcher.group(i).matches("\\s")) {
                    transactionElements.add(matcher.group(i));
                }
            }
        }
        return transactionElements;
    }

    /**
     * Performs the user's login transaction (00) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeLogIn(String currLine) {
        regex = "(\\d{2})(\\s)(.{0,15})(\\s)([A+|B+|D+|S+]{0,2})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            String currUserName = transactionElements.get(1).replaceFirst("\\s++$", "");
            String userBalanceString = transactionElements.get(3).replaceFirst("\\s++$", "");

            if (this.currUser != null) {
                return "Fatal Error: Another user is already logged in at daily.txt";

            } else if (this.database.getUser(currUserName) == null) {
                return "Fatal Error: User doesn't exist in our system at daily.txt";

            } else {
                System.out.println("Log in is happening...");
                this.currUser = currUserName;

                if (!accurateBalance(currUserName, userBalanceString)) {
                    System.out.println("The user: " + userName + "'s balance contradicts database");
                }
                return "The user: " + this.currUser + ", has logged in";
            }
        }

        return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Performs the create user transaction (01) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeCreateUser(String currLine){
        regex = "(\\d{2})(\\s)(.{0,15})(\\s+)([A+|B+|D+|S+]{0,2})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            userName = transactionElements.get(1).replaceFirst("\\s++$", "");
            String userType = transactionElements.get(2).replaceFirst("\\s++$", "");
            accountBalance = Double.parseDouble(transactionElements.get(3));

            if (this.currUser == null) {
                return "Fatal Error: User isn't logged in to execute create transaction in daily.txt";

            } else if (!this.database.getUser(this.currUser).getUserType().equals("AA")) {
                return "Fatal Error: User must be an admin to access create transaction in daily.txt";

            } else {
                System.out.println("Create user is happening...");
                Admin user = this.database.getAllAdmins().get(this.currUser);
                return user.requestCreateUser(userName, userType, accountBalance);
            }

        }
        return "Fatal Error: invalid formatting in daily.txt";

    }


    /**
     * Performs the delete user transaction (02) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeDeleteUser (String currLine){
        regex = "(\\d{2})(\\s)(.{0,15})(\\s+)([A+|B+|D+|S+]{0,2})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            userName = transactionElements.get(1).replaceFirst("\\s++$", "");
            String userBalanceString = transactionElements.get(3).replaceFirst("\\s++$", "");
            if (this.currUser == null) {
                return "Fatal Error: User isn't logged in to execute delete transaction in daily.txt";
            } else if (!this.database.getUser(this.currUser).getUserType().equals("AA")) {
                return "Fatal Error: User must be admin to access delete transaction in daily.txt";
            } else {
                if (this.database.getUser(userName) != null && !accurateBalance(userName, userBalanceString)) {
                    System.out.println("The user: " + userName + "'s balance contradicts database");
                }
                System.out.println("Delete user is happening...");
                return this.database.getAllAdmins().get(this.currUser).requestDeleteUser(userName);
            }
        }
        return "Fatal Error: invalid formatting in daily.txt";
    }


    /**
     * Performs the sell transaction (03) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeSell (String currLine){
        regex = "(\\d{2})(\\s)(.{0,30})(\\s)(.{0,15})(\\s)(\\d{2}\\.\\d{2})(\\s)(\\d{3})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            appName = transactionElements.get(1).replaceFirst("\\s++$", "");
            sellerName = transactionElements.get(2).replaceFirst("\\s++$", "");
            double price = Double.parseDouble(transactionElements.get(3));
            String category = new CategoryFactory().createCategory(transactionElements.get(4));

            if (this.currUser != null) {
                return this.database.getUser(this.currUser).requestSell(appName, price, sellerName, category);
            } else {
                return "Fatal Error: User isn't logged in to execute sell transaction in daily.txt";
            }
        }
        return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Performs the buy transaction (04) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeBuy (String currLine){
        regex = "(\\d{2})(\\s)(.{0,30})(\\s)(.{0,15})(\\s)(.{0,15})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            appName = transactionElements.get(1).replaceFirst("\\s++$", "");
            sellerName = transactionElements.get(2).replaceFirst("\\s++$", "");
            buyerName = transactionElements.get(3).replaceFirst("\\s++$", "");

            if (this.currUser != null) {
                return this.database.getUser(this.currUser).requestBuy(appName, sellerName, buyerName);
            } else {
                return "Fatal Error: User isn't logged in to execute buy transaction in daily.txt";
            }
        }
        return "Fatal Error: invalid formatting in daily.txt";

    }


    /**
     * Performs the refund transaction (05) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeRefund (String currLine){
        regex = "(\\d{2})(\\s)(.{0,15})(\\s)(.{0,15})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            buyerName = transactionElements.get(1).replaceFirst("\\s++$", "");
            sellerName = transactionElements.get(2).replaceFirst("\\s++$", "");
            // this needs to be in this scope as var are special to this operation
            double refundCredit = Double.parseDouble(transactionElements.get(3));
            if (this.currUser != null && this.database.getAllAdmins().get(this.currUser) != null) {
                return this.database.getAllAdmins().get(this.currUser).requestRefund(buyerName, sellerName, refundCredit);
            } else {
                return "Fatal Error: User isn't logged in to execute refund transaction in daily.txt";
            }
        } return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Performs the add credit transaction (06) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeAddCredit (String currLine){
        regex = "(\\d{2})(\\s)(.{0,15})(\\s+)([A+|B+|D+|S+]{0,2})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            userName = transactionElements.get(1).replaceFirst("\\s++$", "");
            accountBalance = Double.parseDouble(transactionElements.get(3));

            // add credit transaction for non-users (BS, SS, FS)
            if (currUser != null) {
                return this.database.getUser(this.currUser).requestAddCredit(userName, accountBalance);
            } else {
                return "Fatal Error: User isn't logged in to execute add credit transaction in daily.txt";
            }
        } return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Performs the remove game transaction (08) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeRemoveGame (String currLine){
        regex = "(\\d{2})(\\s)(.{0,30})(\\s)(.{0,15})(\\s)((.{0,15})?)$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            appName = transactionElements.get(1).replaceFirst("\\s++$", "");
            sellerName = transactionElements.get(2).replaceFirst("\\s++$", ""); // in this case; the owner's name
            if (transactionElements.size() == 4) {
                userName = transactionElements.get(3).replaceFirst("\\s++$", "");
            }

            if (this.currUser == null) {
                return "Fatal Error: User isn't logged in to execute remove game transaction in daily.txt";
            } else {
                return this.database.getUser(currUser).requestRemoveApp(appName, sellerName);
            }
        }
        return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Performs the user's log out transaction (10) in the system
     * and notifies the user about the state of this operation.
     *
     * @param currLine the current transaction to be processed from daily.txt
     * @return a String representation of the current state of the transaction
     */
    public String writeLogOut (String currLine){
        regex = "(\\d{2})(\\s)(.{0,15})(\\s)([A+|B+|D+|S+]{0,2})(\\s)(\\d{6}\\.\\d{2})$";
        transactionElements = format(currLine, regex);
        if (checkTransactionElements(transactionElements)) {
            userName = transactionElements.get(1).replaceFirst("\\s++$", "");
            String userBalanceString = transactionElements.get(3).replaceFirst("\\s++$", "");
            if (this.currUser != null && this.currUser.equals(userName)) {
                // logout
                if (!accurateBalance(userName, userBalanceString)) {
                    System.out.println("The user: " + userName + "'s balance contradicts database");
                }
                System.out.println("Logout is happening...");
                this.currUser = null;
                return "The user: " + userName + ", has logged out";

            }
            else {
                return "Fatal Error:" + userName + ", is not logged in daily.txt";
            }
        } return "Fatal Error: invalid formatting in daily.txt";
    }

    /**
     * Returns true iff there are transaction elements for transactions to be created
     *
     * @param transactionElements an array of string elements for transactions to be created
     * @return true if there are transaction elements for transactions to be created, false otherwise.
     */
    private boolean checkTransactionElements(ArrayList<String> transactionElements) {
        return transactionElements.size() != 0;
    }

    /**
     * Returns true iff the given user's account balance is accurate.
     *
     * @param userName the name of the user that is being looked
     * @param balanceString the string representation of the user's balance
     * @return true if the given user's account balance is accurate, false otherwise
     */
    private boolean accurateBalance(String userName, String balanceString){
        double accurateBalance = this.database.getUser(userName).getAccountBalance();
        double statedBalance = Double.parseDouble(balanceString);
        return Math.abs(statedBalance - accurateBalance) < EPSILON;
    }

    /**
     * Returns true iff the system ends with a log out
     *
     * @return true if the system ended with a log out, false otherwise
     */
    protected boolean checkLogOut(){
        return this.currUser == null;
    }

}



