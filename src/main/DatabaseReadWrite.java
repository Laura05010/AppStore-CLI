package main;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to represent the database file to be read and overwritten
 */
public class DatabaseReadWrite extends Observable{
    public static final String INTERMEDIATE_FILE = "intermediateDatabase.txt";
    public File databaseFile;
    public Database database;
    public FileWriter intermediateDatabaseFile;

    /**
     * Constructs the database file reader and writer for the class and updates the client about the database status
     * after populating it
     *
     * @param filePath String representing the file path of the database file to be read and overwritten
     */
    public DatabaseReadWrite(String filePath) throws IOException {
        this.databaseFile = new File(filePath);
        this.intermediateDatabaseFile = new FileWriter(INTERMEDIATE_FILE);
        if (databaseFile.exists()) {
            System.out.println("Reading off database file: " + this.databaseFile.getName());
            System.out.println("FilePath of database file: "+ this.databaseFile.getPath());
        }
        this.database = new Database();
    }

    /**
     * Reads the database file, populates the system's database, and informs the user about the end of this operation
     */
    public void readDatabase() throws IOException {
        Scanner scanner = new Scanner(this.databaseFile);
        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            format(currLine);
        }
        scanner.close();
        System.out.println("The database.txt file has been processed");
    }

    /**
     * Writes the state of the database at the end of the day (i.e. end of the daily file) to an intermediate database
     * file
     */
    public void translateDatabase() throws IOException {
        List<Admin> admins = new ArrayList<>(this.database.getAllAdmins().values());
        List<User> users = new ArrayList<>(this.database.getAllUsers().values());
        List<User> newList = Stream.concat(admins.stream(), users.stream()).collect(Collectors.toList());

        // Populate the users
        for (User user: newList){
            this.intermediateDatabaseFile.write("1," + user.getUserName() + "," + user.getUserType() + "," +
                    String.format("%09.2f", user.getAccountBalance())+ "\n");
        }

        // Populate the apps
        Collection<App> allApps = this.database.getAllApps().values();
        for ( App app: allApps) {
            this.intermediateDatabaseFile.write("2," + app.getAppName() + "," +
                    String.format("%05.2f", app.getPrice()) + "," + app.getDeveloper().getUserName() + "," +
                    app.getCategory() + "\n");
        }

        // Assign apps to respective inventories
        for (User user: newList){
            for (App app: user.getAppsOwned()){
                if(app.getDeveloper() != null){
                    this.intermediateDatabaseFile.write("3," + user.getUserName() + "," + app.getAppName() + "," +
                            app.getDeveloper().getUserName() + "\n");
                }
                else{
                    this.intermediateDatabaseFile.write("3," + user.getUserName() + "," + app.getAppName() + "," +
                            "" + "\n");
                }
            }
        }
        intermediateDatabaseFile.close();
    }

    /**
     * Overwrites the original database file for the following day
     *
     * @param filePath String representing the file path of the database file to be overwritten
     */
    public void overwriteDatabaseTXT(String filePath) throws IOException {
        FileWriter databaseWriter = new FileWriter(filePath);
        File toDelete = new File(INTERMEDIATE_FILE);

        Scanner scanner = new Scanner(toDelete);

        while (scanner.hasNext()) {
            String currLine = scanner.nextLine();
            databaseWriter.write(currLine + "\n");
        }

        scanner.close();
        databaseWriter.close();
        System.out.println("The curr database file overwrote database.txt: " + toDelete.delete());

    }

    /**
     * Feeds the system's database before the day starts
     *
     * @param currLine String representing the current line the file is reading
     */
    private void format(String currLine) throws IOException {
        Pattern userRegexPattern = Pattern.compile("(1,)(.{0,15})(,)([A+|B+|D+|S+]{0,2})(,)(\\d{6}.\\d{2})");
        Matcher userRegexMatcher = userRegexPattern.matcher(currLine);
        Pattern appInventoryRegexPattern = Pattern.compile("(3,)(.{0,15})(,)(.{0,30})(,)(.{0,15})");
        Matcher appInventoryMatcher = appInventoryRegexPattern.matcher(currLine);
        Pattern appRegex = Pattern.compile("(2,)(.{0,30})(,)(\\d{2}.\\d{2})(,)(.{0,15})(,)(\\w+)");
        Matcher appRegexMatcher = appRegex.matcher(currLine);

        String userName;
        String sellerName;
        String appName;

        // Takes in User elements
        if (userRegexMatcher.matches()){
            userName = userRegexMatcher.group(2);
            String userType = userRegexMatcher.group(4);
            double accountBalance = Double.parseDouble(userRegexMatcher.group(6));
            feedUserDatabase(userName,userType, accountBalance, currLine);
        }

        // Takes in app inventory elements
        else if (appInventoryMatcher.matches()){
            userName = appInventoryMatcher.group(2);
            appName = appInventoryMatcher.group(4);
            sellerName = appInventoryMatcher.group(6);
            feedAppInventories(userName, appName, sellerName, currLine);
        }

        // Takes in app elements
        else if (appRegexMatcher.matches()){
            appName = appRegexMatcher.group(2);
            double price = Double.parseDouble(appRegexMatcher.group(4));
            sellerName = appRegexMatcher.group(6);
            String category = appRegexMatcher.group(8);
            feedAppDatabase(appName, price, sellerName, category, currLine);
        }

    }

    /**
     * Feeds the user databases before the day starts
     *
     * @param userName String representing the user's username
     * @param userType String representing the user's usertype
     * @param accountBalance Double representing the user's account balance
     * @param currLine String representing the current line the file is reading
     */
    private void feedUserDatabase(String userName, String userType, double accountBalance, String currLine) throws IOException {
        UserFactory userfactory = new UserFactory();
        userfactory.setDatabase(this.database);
        User user = userfactory.createUserBase(userType, userName, accountBalance);
        // Check the user type before adding the appropriate user type to database
        if(user != null){
            if(userType.equals("AA")) {
                this.database.addAdmin((Admin)user);
            }
            else{
                this.database.addUser(user);
            }
            user.setDatabase(this.database);
            notify("The user: " + userName+ ", has been added to the system", currLine);
        }
        else {
            notify("Invalid: an empty user cannot be added to the system", currLine);
        }
    }

    /**
     * Feeds the user app inventories before the day starts
     *
     * @param userName String representing the user's username
     * @param appName String representing the app's name
     * @param sellerName String representing the seller's username
     * @param currLine String representing the current line the file is reading
     */
    private void feedAppInventories(String userName, String appName, String sellerName, String currLine) throws IOException{
        User user = this.database.getUser(userName);
        User seller = this.database.getUser(sellerName);
        App app = this.database.appsAvailable(appName,seller);

        if (user == null)  {
            notify("Invalid: the user is not in the system", currLine);
        }

        if (seller == null){
            notify("Invalid: the seller is not in the system", currLine);
        }

        if (app == null){
            notify("Invalid: the app: " + appName + " is not in the system or is not sold by this seller: " + sellerName, currLine);
        }

        else if (!(user == null || seller == null || app == null)){
            if (sellerName.equals(" ")){
                app = new App(appName, 0, null, null);
            }

            user.getAppsOwned().add(app);
            notify("The app has been added to the user inventories", currLine);

        }

    }
    /**
     * Feeds the app database before the day starts
     *
     * @param appName String representing the app's name
     * @param price Double representing the app's price
     * @param sellerName String representing the seller's name
     * @param category String representing the app's category
     * @param currLine String representing the current line the file is reading
     */
    private void feedAppDatabase(String appName, double price, String sellerName, String category, String currLine) throws IOException {
        User seller = this.database.getUser(sellerName);
        App app = new App(appName, price, seller, category);
        if (seller != null){
            this.database.addApp(app);
            seller.getAppsToSell().add(app);
            notify("The app has been added to the system", currLine);
        }
        else{
            notify("Invalid: the seller is not in the system", currLine);
        }


    }

    /**
     * Notifies the user about the state of the operations as the database is fed
     *
     * @param currentOperation String representing the current operation
     * @param currLine String representing the current line the file is reading
     */
    private void notify(String currentOperation, String currLine) throws IOException {
        this.notifyObservers(currentOperation);
        if (!currentOperation.startsWith("Invalid")) {
            System.out.println("This database entry was successful: " + currLine);
        }
    }

}