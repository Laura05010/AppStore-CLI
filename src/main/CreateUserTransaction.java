package main;

/** A class to represent a create user transaction.
 */
public class CreateUserTransaction extends Transaction{
    private String newUserName;
    private double userBalance;
    private String userType;

    /**
     * Constructs a create user Transaction with the creator of the user, and the new user's name, balance and userType
     *
     * @param creatorName the user's creator
     * @param newUserName the name of the user to be created
     * @param userBalance the accountBalance of the user to be created
     * @param userType the type of the user to be created
     * @param database a copy of the current database
     */
    public CreateUserTransaction(String creatorName, String newUserName, double userBalance, String userType, Database database){
        super(creatorName, database);
        this.newUserName = newUserName;
        this.userBalance = userBalance;
        this.userType = userType;
    }

    /**
     * Returns a String representation when the admin is requesting to create a new user
     * @return a String representation if the create user transaction is valid or error otherwise.
     */
    @Override
    public String execute(){
        String invalid = checkInvalidity(newUserName, userBalance);
        if(invalid.equals("Valid")){
            UserFactory userfactory = new UserFactory();
            userfactory.setDatabase(database);
            User user = userfactory.createUserBase(this.userType, this.newUserName, this.userBalance);
            if(user != null){
                if(user.getUserType().equals("AA")){
                    database.addAdmin((Admin) user);
                }
                else{
                    database.addUser(user);
                }
                user.setDatabase(database);
                return toString();
            }
            return "Error: cannot create a user as user type is invalid";
        }
        return invalid;

    }

    /**
     * Returns a String based on the validity of the create user transaction to be executed
     * @return a String representation if the create user transaction is invalid, empty string otherwise.
     */
    private String checkInvalidity(String userName, double accountBalance){
        if(userName.length() > User.MAXUSERNAME || userName.startsWith(" ")){
            return "Constraint Error: cannot create a user as username is more than 15 characters";
        }
        else if(this.database.getUser(userName) != null){
            return "Constraint Error: cannot create a user with a username that already exists in the system";
        }
        else if(accountBalance > User.MAXBALANCE || accountBalance < 0){
            return "Constraint Error: cannot create a user as max account balance should be " + User.MAXBALANCE + " and positive";
        }
        else if(!this.getMainTransactionUser().getUserType().equals("AA")){
            return "Constraint Error: cannot proceed to create user as user is not an admin";
        }
        return "Valid";
    }

    /**
     * Returns a String representation of the create user transaction
     * @return a String representation of the create user transaction
     */
    @Override
    public String toString(){
        String readableUserType;
        switch (this.userType){
            case "AA":
                readableUserType = "admin ";
                break;
            case "BD":
                readableUserType = "buyer developer ";
                break;
            case "BS":
                readableUserType = "buyer ";
                break;
            case "DD":
                readableUserType = "developer ";
                break;
            default:
                readableUserType = "invalid ";
                break;

        }
        return "The admin: " + this.mainUser + ", has created a new " + readableUserType + "user: " + this.newUserName +
                ", with $" + userBalance + " in their account";
    }
}
