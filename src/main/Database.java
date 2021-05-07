package main;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * A class to represent the database of the program.
 */

public class Database {
    private HashMap<String, User> allUsers;
    private HashMap<String, Admin> allAdmins;
    private HashMap<String, App> allApps;
    private ArrayList<App> newAppsAdded;

    /**
     * Constructs an empty database consisting of all the users, the admins, the apps put up for sale,
     * and the apps added to the App Store today
     */
    public Database(){
        this.allUsers = new HashMap<>(); // All types of users except the admins
        this.allAdmins = new HashMap<>(); // Only admins
        this.allApps = new HashMap<>();
        this.newAppsAdded = new ArrayList<>();
    }

    /**
     * Returns the Hashmap of all the Apps in the platform.
     *
     * @return Hashmap where the name of the Apps maps to the App object
     */
    public HashMap<String, App> getAllApps(){
        return this.allApps;
    }

    /**
     * Returns the ArrayList of all the new Apps added to the platform today.
     *
     * @return ArrayList of the new Apps are created today.
     */
    public ArrayList<App> getNewAppsAdded(){
        return this.newAppsAdded;
    }

    /**
     * Returns the Hashmap of all the admins users in the database.
     *
     * @return Hashmap where the name of the admin maps to the respective admin user object
     */
    public HashMap<String, Admin> getAllAdmins(){
        return this.allAdmins;
    }

    /**
     * Returns the Hashmap of all the non admin users in the database.
     * @return Hashmap where the name of the non Admin maps to the Users class
     */
    public HashMap<String, User> getAllUsers(){
        return this.allUsers;
    }

    /**
     * Returns the User object based on the user name that points to this user on the database.
     * @param userName the name of the user
     *
     * @return User corresponding to the userName from the database or null
     */
    public User getUser(String userName){
        // returns a user if they exist else return null
        User currUser = null;
        //gets other users
        if (this.allUsers.containsKey(userName)){
            currUser = this.allUsers.get(userName);
        }
        //gets admin
        else if (this.allAdmins.containsKey(userName)){
            currUser = this.allAdmins.get(userName);
        }
        return currUser;
    }


    /**
     * Adds the user to the allUsers hashmap iff not already in it
     *
     * @param user the User representing the user to add to the database of users
     */
    public void addUser(User user){
        if(!(this.allUsers.containsKey(user.getUserName())) || this.allUsers.isEmpty()){
            this.allUsers.put(user.getUserName(), user);
        }
    }

    /**
     * Removes the user from the allUsers hashmap iff it exists on the user database
     *
     * @param user the User representing the user to remove
     */
    public void removeUser(User user){
        this.allUsers.remove(user.getUserName());
    }

    /**
     * Adds the admin to the allAdmins hashmap iff not already in it
     *
     * @param admin the Admin class representing the admin to add
     */
    public void addAdmin(Admin admin){
        if(!(this.allAdmins.containsKey(admin.getUserName())) || this.allAdmins.isEmpty()) {
            this.allAdmins.put(admin.getUserName(), admin);
        }
    }


    /**
     * Removes the admin from the allAdmins hashmap iff it exists on the admin database
     *
     * @param admin the Admin object representing the admin to add to the database of admins
     */
    public void removeAdmin(User admin){
        this.allAdmins.remove(admin.getUserName());
    }

    /**
     * Adds the App to the allApps hashmap iff not already in it
     *
     * @param app the App object representing the App to add to the App database
     */
    public void addApp(App app){
        if(!(this.allApps.containsKey(app.getAppName())) || this.allAdmins.isEmpty()) {
            this.allApps.put(app.getAppName(), app);
        }
        else{
            System.out.println("This app already exists on the App Store");
        }
    }

    /**
     * Removes the App from the allApps hashmap iff existing
     *
     * @param App the App class representing the App to remove
     */
    public void removeApp(App App){
        this.allApps.remove(App.getAppName());
    }

    /**
     * Returns the App if the user has the App for sale in their inventory, otherwise it returns null.
     *
     * @param appName the App's name
     * @param user the user that is selling the game
     * @return the App object if the user has the App for sale in their inventory, otherwise null
     */
    public App appsAvailable(String appName, User user) {
        if (this.allApps.containsKey(appName)) {
            App app = allApps.get(appName);
            if (app.getDeveloper() == user) {
                return app;
            }
        }
        return null;
    }
}
