package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Observable;

/**
 * A class to represent the daily transaction file to be read and written to
 */
public class DailyReadWrite extends Observable{
    private final File daily;
    public Database database;
    private FileWriter transactionReceipt;
    private WriteTransactions writeTransaction;


    /**
     * Constructs the daily transaction file reader and the daily transaction receipt file writer for the class
     * and gives feedback to the user regarding the daily transaction file read
     *
     * @param filePath the file path of the daily transaction file to be read
     */
    public DailyReadWrite(String filePath) throws IOException {
        this.daily = new File(filePath);
        this.transactionReceipt = new FileWriter("transactionReceipt.txt");

        if (daily.exists()) {
            System.out.println("Reading off the daily transaction file: "+ daily.getName());
            System.out.println("FilePath of the daily transaction file: "+ daily.getPath());
        }
        this.database = new Database();
    }


    /**
     * Reads the daily transaction file, creates transactions in the program database
     * and informs the user about each transaction
     */
    public void readDaily() throws IOException {
        this.writeTransaction = new WriteTransactions(this.database);
        Scanner scanner = new Scanner(this.daily);
        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            String message = "";
            if(currLine.trim().equals("")){
                System.out.println("Fatal Error: daily transaction file contains an empty line");
                continue;
            }
            String number = currLine.charAt(0) + "" + currLine.charAt(1);

            switch (number) {
                case "00":
                    message = this.writeTransaction.writeLogIn(currLine);
                    break;
                case "01":
                    message = this.writeTransaction.writeCreateUser(currLine);
                    break;
                case "02":
                    message = this.writeTransaction.writeDeleteUser(currLine);
                    break;
                case "03":
                    message = this.writeTransaction.writeSell(currLine);
                    break;
                case "04":
                    message = this.writeTransaction.writeBuy(currLine);
                    break;
                case "05":
                    message = this.writeTransaction.writeRefund(currLine);
                    break;
                case "06":
                    message = this.writeTransaction.writeAddCredit(currLine);
                    break;
                case "07":
                    message = this.writeTransaction.writeRemoveGame(currLine);
                    break;
                case "08":
                    message = this.writeTransaction.writeLogOut(currLine);
                    break;
                default:
                    // TREAT THIS AS A FATAL ERROR AS IN @752
                    message = "Fatal Error: This is an invalid transaction at daily.txt";
                    break;
            }
            writeNotify(message, currLine);
        }
        if(!writeTransaction.checkLogOut()){
            System.out.println("Fatal Error: User hasn't logged out at daily.txt");
        }
        scanner.close();
        transactionReceipt.close();
        System.out.println("The daily.txt file has been processed");
    }

    /**
     * Notifies the user about the state of the transactions in the system
     * and writes the successful transactions onto the transaction receipt file
     *
     * @param currentTransaction the response from the current transaction
     * @param currLine the current transaction to be processed from daily.txt
     */
    public void writeNotify(String currentTransaction, String currLine) throws IOException {
        this.setChanged();
        this.notifyObservers(currentTransaction);
        if (!currentTransaction.startsWith(" ") && !currentTransaction.startsWith("Constraint") && !currentTransaction.startsWith("Fatal")){
            this.transactionReceipt.write(currLine + "\n");
        }
    }
}