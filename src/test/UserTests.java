package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    Database database;
    Admin admin;
    Buyer buyer;
    Developer developer;
    BuyerDev buyerDev;
    App app1;
    App app2;
    App app3;
    App app4;
    App app5;
    App app6;

    @BeforeEach
    public void setUp() {
        // Sets up the database
        database = new Database();
        admin = new Admin("theOG", "AA", 25.00);
        database.addAdmin(admin);
        admin.setDatabase(database);

        buyer = new Buyer("Ashlyn", "BS", 999099.00);
        database.addUser(buyer);
        buyer.setDatabase(database);

        developer = new Developer("Frieda", "DD", 193819.00);
        database.addUser(developer);
        developer.setDatabase(database);

        buyerDev = new BuyerDev("Avonlea", "BD", 15.00);
        database.addUser(buyerDev);
        buyerDev.setDatabase(database);

        app1 = new App("Super Smash", 80.0, developer, "app");
        database.addApp(app1);
        developer.getAppsToSell().add(app1);

        app2 = new App("Guitar Hero", 70.0, buyerDev, "app");
        database.addApp(app2);
        buyerDev.getAppsToSell().add(app2);

        app3 = new App("Majora's Mask", 60.0, developer, "app");
        database.addApp(app3);
        buyerDev.getAppsToSell().add(app3);

        app4 = new App("Fire Emblem", 40.0, buyerDev, "app");
        database.addApp(app4);
        buyerDev.getAppsToSell().add(app4);

        app5 = new App("Pokemon Diamond", 30.0, admin, "app");
        database.addApp(app5);
        admin.getAppsToSell().add(app5);

        app6 = new App("Xenoblade", 99.99, buyerDev, "app");
        database.addApp(app6);
        buyerDev.getAppsToSell().add(app6);
    }

    @Test
    public void testCreateUser() {
        //Invalid username
        String string1 = admin.requestCreateUser("Pekwachnamaykoskwaskwaypinwanik", "BS", 25.00);

        //Valid
        String string2 = admin.requestCreateUser("CoolGuy", "DD", 200.00);

        // Invalid Account Balance
        String string3 = admin.requestCreateUser("Imposter", "AA", 1000000.00);

        //Already existing user
        String string4 = admin.requestCreateUser("CoolGuy", "AA", 100.00);


        assertEquals("Constraint Error: cannot create a user as username is more than 15 characters", string1);
        assertEquals("The admin: theOG, has created a new developer user: CoolGuy, with $200.0 in their account", string2);
        assertEquals("Constraint Error: cannot create a user as max account balance should be 999999.99 and positive", string3);
        assertEquals("Constraint Error: cannot create a user with a username that already exists in the system", string4);
        assertEquals("CoolGuy", database.getUser("CoolGuy").getUserName());

    }

    @Test
    public void testRemoveUser() {
        // Remove existing user
        String string1 = admin.requestDeleteUser(buyer.getUserName());

        // Tries to remove non existing user

        String string2 = admin.requestDeleteUser("HELLO");

        // The Admin deleting themselves
        String string3 = admin.requestDeleteUser(admin.getUserName());

        assertEquals("The user: " + buyer.getUserName() + " has been deleted", string1);
        assertEquals("Constraint Error: cannot delete a user that is not in the system", string2);
        assertEquals("Constraint Error: executioner cannot delete themselves", string3);
        assertNull(database.getUser(buyer.getUserName()));

    }

    @Test
    public void testAddCreditNotAdmin(){
        // regular adding money
        developer.requestAddCredit(developer.getUserName(), 25.00);
        assertEquals(193844.00, developer.getAccountBalance());
        // Add too much money
        String string1 = buyer.requestAddCredit(buyer.getUserName(), 910);
        assertEquals("The user: Ashlyn, added 900.98 to themselves.", string1);
        assertEquals(999999.98, buyer.getAccountBalance());
        // Add negative money
        String string2 = buyer.requestAddCredit(buyer.getUserName(), -910);
        assertEquals("Constraint Error: cannot add credit due to negative values", string2);

        //add credit to other use even though non admin. The expected error msg is not implemented yet
        String string3 = buyer.requestAddCredit(developer.getUserName(), 5);
        assertEquals("Constraint Error: cannot proceed add credit as non admin attempts to add credit to another user", string3);

    }

    @Test
    public void testAddCreditAdmin() {
        // admin adds credit to their own account
        String string1 = admin.requestAddCredit(admin.getUserName(), 2.00);
        assertEquals("The user: theOG, added 2.0 to themselves.", string1);
        assertEquals(27.00, admin.getAccountBalance());

        // admin adds credit to an existing user
        String string2 = admin.requestAddCredit(buyer.getUserName(), 5.00);
        assertEquals("The user: theOG, added 5.0 to another user: Ashlyn", string2);
        assertEquals(999104.00, buyer.getAccountBalance());

        // admin adds credit to not existing user
        String string3 = admin.requestAddCredit("Johnny", 9.00);
        assertEquals("Constraint Error: cannot add credit as username doesn't exist", string3);
    }

    @Test
    public void testSellApp(){
        // Sell new app
        String string1 = buyerDev.requestSell("Angry Birds", 20.00,  buyerDev.getUserName(), "Game");
        assertEquals("The user: Avonlea, is selling Angry Birds for $20.0", string1);

        // Sell app that other people sell too
        String string2 = buyerDev.requestSell("Majora's Mask", 40.00,buyerDev.getUserName(), "Game");
        assertEquals("Constraint Error: cannot sell app that already exists in the platform", string2);

        // Sell app that's already in the developer's inventory
        String string3 = buyerDev.requestSell("Fire Emblem", 30.00, buyerDev.getUserName(), "Game");
        assertEquals("Constraint Error: cannot sell app that already exists in the platform", string3);
        //buyer
        String invalid = buyer.requestSell("Call of Duty", 300.00, buyer.getUserName(), "Game");
        assertEquals("Constraint Error: cannot sell app as user is a buyer", invalid);
        //too expensive
        String invalid1 = admin.requestSell("Yoshi's Land", 1000,  admin.getUserName(),"Game");
        assertEquals("Constraint Error: cannot sell app as the max sale price must be $99.99", invalid1);

    }

    @Test
    public void testBuyApp() {
        // app exists in developer inventory (needed!)
        String string1 = buyer.requestBuy(app1.getAppName(), developer.getUserName(), buyer.getUserName());
        assertEquals("The user: Ashlyn, bought Super Smash from another user: Frieda, for 80.0", string1);
        assertEquals(193899, developer.getAccountBalance());
        assertEquals(999019, buyer.getAccountBalance());

        // app doesn't exist in developer's inventory
        String string2 = admin.requestBuy(app2.getAppName(), developer.getUserName(), admin.getUserName());
        assertEquals("Constraint Error: cannot proceed buy transaction as app doesn't exist in developer's inventory or " +
                "buyer already owns this app", string2);

        // Too poor (not enough money from buyer)
        String string3 = buyerDev.requestBuy(app5.getAppName(), admin.getUserName(), buyerDev.getUserName());
        assertEquals("Constraint Error: cannot proceed buy transaction as there are insufficient funds from the user or " +
                "potential overflowing funds for the developer", string3);

        // Buyer cant buy duplicates
        String string4 = buyer.requestBuy(app1.getAppName(), developer.getUserName(), buyer.getUserName());
        assertEquals("Constraint Error: cannot proceed buy transaction as app doesn't exist in developer's inventory or " +
                "buyer already owns this app", string4);

        // FS/ Admin cant buy from themselves
        String string5 = admin.requestBuy(app5.getAppName(), admin.getUserName(), admin.getUserName());
        assertEquals("Constraint Error: cannot proceed buy transaction as the user requesting this transacting does not have permission to proceed", string5);

        // Buyers cannot buy a app that's recently added
        buyerDev.requestSell("Angry Birds", 20.00, buyerDev.getUserName(),"Game");
        String string6 = admin.requestBuy("Angry Birds", buyerDev.getUserName(), admin.getUserName());
        assertEquals("Constraint Error: cannot proceed buy transaction as app is recently added", string6);
    }

    @Test
    public void testRefundApp() {
        // regular refund
        buyer.requestBuy(app1.getAppName(), developer.getUserName(), buyer.getUserName());
        String string1 = admin.requestRefund(buyer.getUserName(), developer.getUserName(), app1.getPrice());
        assertEquals("The user: Frieda, refunds $80.0 to another user: Ashlyn", string1);
        assertEquals(999099.00, buyer.getAccountBalance());
        assertEquals(193819.00, developer.getAccountBalance());

        // switch buyer and developer (developer refunds from buyer)
        String string2 = admin.requestRefund(developer.getUserName(), buyer.getUserName(), app1.getPrice());
        assertEquals("Constraint Error: cannot proceed refund as user doesn't exist", string2);

        // admin can refund themselves
        String valid = admin.requestRefund(admin.getUserName(), admin.getUserName(), 50.11);
        assertEquals("Constraint Error: cannot proceed refund as user cannot refund themselves", valid);
        // admin.buy(app1.getAppName(), developer.getUserName(), admin.getUserName());
        String string3 = admin.requestRefund(admin.getUserName(), developer.getUserName(), app1.getPrice());
        assertEquals("The user: Frieda, refunds $80.0 to another user: theOG", string3);

        // full standard cant refund themselves
        String string4 = admin.requestRefund(buyerDev.getUserName(), buyerDev.getUserName(), app1.getPrice());
        assertEquals("Constraint Error: cannot proceed refund as user cannot refund themselves", string4);

        // check if user exists
        String string5 = admin.requestRefund("java", buyer.getUserName(), app1.getPrice());
        assertEquals("Constraint Error: cannot proceed refund as user doesn't exist", string5);

        // buyer's credit balance after refund is overpassing max amount
        //buyer.buy(app6.getAppName(), buyerDev.getUserName(), buyer.getUserName());
        String string6 = admin.requestRefund(buyer.getUserName(), buyerDev.getUserName(), app6.getPrice());
        assertEquals("Constraint Error: seller has inefficient funds to refund", string6);
    }

    @Test
    public void testRemoveApp(){
        String valid = buyerDev.requestRemoveApp("Guitar Hero", "Avonlea");
        assertEquals("The user: " + "Avonlea" + ", removes " + "Guitar Hero" + " from their inventory", valid);
        //correct print statement was outputted, just failed because it doesn't use toString method.
        String invalid = buyerDev.requestRemoveApp("Guitar Hero", "Avonlea");
        assertEquals("Constraint Error: cannot proceed remove app transaction as app doesn't exist in the inventory", invalid);


        String valid1 = admin.requestRemoveApp("Xenoblade", "Avonlea");
        assertEquals( "The user: " + "theOG" + ", removes " + "Xenoblade" + " from another user: " + "Avonlea" + "'s inventory",valid1);

        String invalid1 = admin.requestRemoveApp("Xenoblade", "fdsfdasfdsfdasf");
        assertEquals( "Constraint Error: cannot proceed remove app transaction as user doesn't exist",invalid1);

        String invalid2 = buyer.requestRemoveApp("Pokemon Diamond", "theOG");
        assertEquals( "Constraint Error: cannot proceed remove app transaction as user is non admin and removing apps other than their own",invalid2);

    }

}

