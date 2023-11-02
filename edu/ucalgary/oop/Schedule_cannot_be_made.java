
package edu.ucalgary.oop;
/**
 * Author : Utkarsh Gupta
 */


/**
 * This class is used to throw an exception when the schedule cannot be made
 * this class extends the Exception class
 * the exception is predominantly used by the scheduler class
 * 
 */

public class Schedule_cannot_be_made extends Exception{
    public Schedule_cannot_be_made(String message){
        super(message);
    }
}
