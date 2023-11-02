
package edu.ucalgary.oop;/**
 * Author: Gurnoor Singh
 * 
 */

public class Formatter {
    /**
     * This class is used to format the output of the program.
     */
    public static String format(int animalId, String task, String animalName) {
        String formattedString = String.format("%-25s %-10s (%d)", task, animalName, animalId);
        return formattedString;
    }

}