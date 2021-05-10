package main;

import java.util.Observable;
import java.util.Observer;

/** A class to represent a text view of FileReader
 */
public class TextView implements Observer {
    /**
     * Updates the user
     * @param o the observable
     * @param arg the argument used to update the user
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }

    /**
     * Interacts with the client via messages on the prompt
     * @param message a message to be displayed to the client
     */
    public void interact(String message){
        System.out.println(message);
    }


}