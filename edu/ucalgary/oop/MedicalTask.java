package edu.ucalgary.oop;
/*
 * Author Utkarsh Gupta, Aryan Sharma
 * 
 * The MedicalTask Class extends the Tasks class and is used to create a medical task object
 */

public class MedicalTask extends Tasks {

    private int taskId;
    private boolean done = false;

    /**
     * constructor for the medical task class
     * 
     * @param startTime
     * @param duration
     * @param taskId
     * @param animalId
     * @param endTime
     * @param Task
     */
    public MedicalTask(int startTime, int duration, int taskId,
            int animalId, int endTime, String Task) {
        super(startTime, duration, endTime, animalId, Task);
        this.taskId = taskId;
    }

    /**
     * getter for the task id
     * 
     * @return
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * setter for the done variable
     * 
     * @param done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * getter for the done variable
     * 
     * @return
     */

    public boolean getDone() {
        return done;
    }

}
