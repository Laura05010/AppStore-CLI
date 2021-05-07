package main;

/** A class to represent a delete user transaction.
 */
public class DeleteUserTransaction extends Transaction{
    private String userToDelete;

    /**
     * Constructs a delete user Transaction with the executioner and the user to be deleted
     * @param executionerName the name of the executioner
     * @param userToDelete the name of the user to be deleted from the program
     * @param database a copy of the current database
     */
    public DeleteUserTransaction(String executionerName, String userToDelete, Database database){
        super(executionerName, database);
        this.userToDelete = userToDelete;
    }

    /**
     * Returns a String representation when the admin is requesting to delete a user
     * @return a String representation if the delete user transaction is valid or error otherwise.
     */
    @Override
    public String execute(){
        User currUser = this.database.getUser(this.userToDelete);
        String invalid = checkInvalidity(this.getMainTransactionUser(), currUser);
        if(invalid.equals("Valid")){
            String currUserType = currUser.getUserType();
            if(!(currUserType.equals("AA"))) {
                this.database.removeUser(currUser);
            }
            else{
                this.database.removeAdmin(currUser);
            }
            // Remove the game from any user that can sell it
            if(currUserType.equals("AA") || currUserType.equals("BD") || currUserType.equals("DD")){
                for(App app: currUser.getAppsToSell()){
                    this.database.removeApp(app);
                    app.setEmptyDeveloper();
                }
            }
            return toString();
        }
        return checkInvalidity(this.getMainTransactionUser(), currUser);
    }

    /**
     * Returns a String based on the validity of the delete user transaction to be executed
     * @return a String representation if the delete user transaction is invalid, empty string otherwise.
     */
    private String checkInvalidity(User executioner, User executed){
        if(!(executioner.getUserType().equals("AA"))){
            return "Constraint Error: cannot delete user as executioner must be an admin";
        }
        else if (executed == null){
            return "Constraint Error: cannot delete a user that is not in the system";
        }

        else if(executed.getUserName().equals(executioner.getUserName())){
            return "Constraint Error: executioner cannot delete themselves";
        }
        return "Valid";
    }

    /**
     * Returns a String representation of the delete user transaction
     * @return a String representation of the delete user transaction
     */
    @Override
    public String toString(){
        return "The user: "+ this.userToDelete + " has been deleted";
    }
}
