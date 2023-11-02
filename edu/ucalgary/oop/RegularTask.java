
package edu.ucalgary.oop;
/**
 * Author Utkarsh Gupta, Aryan Sharma
 * 
 * This class RegularTask extends the Tasks class and is used to create a regular task object
 * regular task cantains tasks like feeding animals and cleaning cages
 */


public class RegularTask extends Tasks {

    private int prepTime;

    /**
     * constructor for the regular task class
     * @param startTime
     * @param endTime
     * @param animalId
     * @param duration
     * @param prepTime
     * @param task
     */
   public RegularTask(int startTime, int endTime, int animalId, int duration, int prepTime, String task) {
        super(startTime, duration, endTime, animalId, task);
        this.prepTime = prepTime;
    }

    /**
     * getter for the prep time
     * @return
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * setter for the prep time
     * @param prepTime
     */
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

}
