package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class to play out the interaction of the client and the program
 */
public class Controller{
    public static final int DAY = 1;

    /**
     * Runs the Controller
     */
    public static void main(String[] args) throws IOException {
        controller(DAY);
    }

    /**
     * The main interactions of the controller
     * Gets the valid daily and database text files
     * Populates the data before the day starts and manages the program by feeding the transactions to it
     * Updates the client
     * Creates a receipt file of the successful transactions throughout the day: transactionReceipt.txt
     * Overrides the database feeder file for the following day
     */
    public static void controller(int day) throws IOException{
        // Create and hook up the Model, View and this controller

        // -------View:
        // Implements Observer
        TextView view = new TextView();
        view.interact("-----------------------------------------------------------");
        view.interact("-----------------------------------------------------------");
        view.interact("WELCOME TO THE APP STORE ON DAY " + day + "!");
        checkConditions(view, day);
        //Gets the valid database text files to read
        String database = getFileName("database text");
        // -------Model: database
        DatabaseReadWrite databaseReader = new DatabaseReadWrite(database);
        //link fileReader to the model.
        databaseReader.addObserver(view);

        // --------Controller
        // 1. Read the database to feed the system
        databaseReader.readDatabase();

        view.interact("-----------------------------------------------------------");
        view.interact("TO START DAY " + day+ "," );
        //Gets the valid daily text files to read
        String daily = getFileName("daily text");
        // -------Model: daily
        DailyReadWrite reader = new DailyReadWrite(daily);
        //link fileReader to the model.
        reader.addObserver(view);

        // 2. Set the main database for the transactions to happen
        reader.database = databaseReader.database;
        // 3. Read the daily.txt
        reader.readDaily();
        // 4. At the end of the day, update the final database file onto an intermediate file
        databaseReader.translateDatabase();
        // 5. Overwrite database.txt
        databaseReader.overwriteDatabaseTXT(database);

        endDayOptions(view, day);
    }

    /**
     * Gets the valid daily and database text files for the client and program interactions to be possible
     *
     * @param fileType the type of file that we are looking for from the client:
     *      database.txt: The file that populates the database before the day starts
     *      daily.txt: The file that populates the transactions that happen throughout a day of the program
     * @return String representation of the file name, if the file is given and exists in the directory
     */
    private static String getFileName(String fileType){
        String fileName = "";
        File theFile = new File(fileName);
        while(!theFile.exists()) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Please enter your " + fileType + " file path here : ");
            fileName = keyboard.nextLine();
            theFile = new File(fileName);
        }
        return fileName;
    }

    /**
     * Checks if the client has a database text file and daily text file to run this program
     * @param view the view of the program
     * @param day the current day
     */
    private static void checkConditions(TextView view, int day){
        if (day == 1) {
            view.interact("Please make sure that you have the required files at hand");
            Scanner keyboard = new Scanner(System.in);
            view.interact("Do you have a database text file and daily text file? Y/N");
            String response = keyboard.nextLine();

            if (response.equalsIgnoreCase("Y") || (response.equalsIgnoreCase("YES")) || response.startsWith("Y") || response.startsWith("y")) {
                view.interact("Awesome! Let's prepare for the day " + day);
            } else if (response.equalsIgnoreCase("N") || (response.equalsIgnoreCase("NO")) || response.startsWith("N") || response.startsWith("n")) {
                view.interact("Please get these files and try again soon! Have a great day!");
                System.exit(0);
            } else {
                view.interact("Invalid response please try again");
                checkConditions(view, day);
            }
        }
        else{
            view.interact("Before we begin, ");
        }
    }

    /**
     * Allows the client to either proceed onto the next day or exit the program
     * @param view the view of the program
     * @param day the current day
     */
    private static void endDayOptions(TextView view, int day) throws IOException {
        view.interact("-----------------------------------------------------------");
        view.interact("-----------------------------------------------------------");
        view.interact("NOW THAT WE HAVE REACHED THE END OF DAY " + day + ",");
        Scanner keyboard = new Scanner(System.in);
        view.interact("Would you like to continue onto the next day? Y/N");
        String response = keyboard.nextLine();

        if (response.equalsIgnoreCase("Y") || (response.equalsIgnoreCase("YES")) || response.startsWith("Y") || response.startsWith("y") ) {
            controller(day + 1);
        }
        else if (response.equalsIgnoreCase("N") || (response.equalsIgnoreCase("NO")) || response.startsWith("N") || response.startsWith("n") )  {
            view.interact("Thank you for using the App Store! Have a wonderful day.");
        }
        else{
            view.interact("Invalid response please try again");
            endDayOptions(view, day);
        }

    }


}