package main;

/** A class to represent a refund transaction
 */
public class RefundTransaction extends Transaction{

    private double refundAmount;
    private String sellerName;

    /**
     * Constructs a refund Transaction with the buyer, the seller, and the refundAmount to be refunded
     * @param buyerName the name of the buyer
     * @param sellerName the name of the seller
     * @param refundAmount the amount to be refunded
     * @param database a copy of the current database
     */
    public RefundTransaction(String buyerName, String sellerName, double refundAmount, Database database){
        super(buyerName, database);
        this.sellerName = sellerName;
        this.refundAmount = refundAmount;
    }

    /**
     * Returns a String representation when the user requests a refund
     * @return a String representation if the refund transaction is valid or error otherwise.
     */
    @Override
    public String execute(){
        User buyer = this.getMainTransactionUser();
        User seller = this.database.getUser(this.sellerName);
        String invalid = checkInvalidity(buyer, seller);
        if (invalid.equals("Valid")){
            double buyerNewAmount = Math.floor((buyer.getAccountBalance() + this.refundAmount) * 100.0)/100.0;
            double sellerNewAmount = Math.floor((seller.getAccountBalance() - this.refundAmount) * 100.0)/100.0;
            buyer.setAccountBalance(buyerNewAmount);
            seller.setAccountBalance(sellerNewAmount);
            return toString();
        }
        return invalid;
    }

    /**
     * Returns a String based on the validity of the refund transaction to be executed
     * @return a String representation if the refund transaction is valid or error otherwise.
     */
    private String checkInvalidity(User buyer, User seller){
        if(buyer == null || seller == null) {
            return "Constraint Error: cannot proceed refund as user doesn't exist";
        }
        else if(buyer.getUserType().equals("DD") || seller.getUserType().equals("BS")){
            return "Constraint Error: cannot proceed refund as user doesn't exist";
        }
        else if(buyer == seller){
            return "Constraint Error: cannot proceed refund as user cannot refund themselves";
        }

        if(seller.getAccountBalance() - this.refundAmount < 0){
            return "Constraint Error: seller has inefficient funds to refund";
        }
        if(buyer.getAccountBalance() + this.refundAmount > User.MAXBALANCE){
            return "Constraint Error: buyer might have max balance post refund";
        }

        return "Valid";

    }

    /**
     * Returns a String representation of the refund transaction
     * @return a String representation of the refund transaction
     */
    @Override
    public String toString(){
        return "The user: " + this.sellerName + ", refunds $" + this.refundAmount + " to another user: " + this.mainUser;
    }
}
