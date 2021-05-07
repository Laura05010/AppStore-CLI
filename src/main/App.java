package main;

import java.util.ArrayList;

/**
 * A class to represent an App in the AppStore
 */
public class App {
    private String appName;
    private double price;
    private User developer;
    private String category;

    /**
     * Constructs a app object with a appName, originalPrice, discount
     * and the developer responsible for selling this app
     * @param appName the app name
     * @param price the price of the app
     * @param developer a User object representing the developer of the app
     * @param category the category of the app
     */
    public App(String appName, double price, User developer, String category) {
        this.appName = appName;
        this.price = price;
        this.developer = developer;
        this.category = category;
    }

    /**
     * Returns the name of the app
     * @return the name of the app
     */
    public String getAppName() {
        return this.appName;
    }

    /**
     * Returns the original app price
     * @return the original price of the app
     */
    public double getAppPrice() {
        return this.price;
    }


    /**
     * Returns the user that is selling the app
     * @return the User object representing the developer of the app
     */
    public User getDeveloper() {
        return this.developer;
    }

    /**
     * Returns the price of the app
     * @return the price of the app
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Returns the category of the app
     * @return the category of the app
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Sets the developer to an empty developer
     */
    protected void setEmptyDeveloper() {
        this.developer = null;
    }
}
