
package edu.ucalgary.oop;
/**
 * Author Aryan Sharma, Utkarsh Gupta, Gurnoor Singh
 * 
 * This class is used by the MedicalTask and RegularTask classes to create a
 * task object
 * 
 */
public class Tasks {
    private int AnimalID;
    private int startTime;
    private int duration;
    private int endTime;
    private String task;
    private boolean taskDone = false;

    /**
     * constructor for the tasks class
     * 
     * @param startTime
     * @param duration
     * @param endTime
     * @param AnimalID
     * @param task
     */
    public Tasks(int startTime, int duration, int endTime, int AnimalID, String task) {
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = endTime;
        this.AnimalID = AnimalID;
        this.task = task;
    }

    /**
     * getter for the start time
     * 
     * @return
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * getter for the duration
     * 
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * getter for the end time
     * 
     * @return
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * getter for the animal id
     * 
     * @return
     */
    public int getAnimalId() {
        return AnimalID;
    }

    /**
     * setter for the animal id
     * 
     * @param AnimalID
     */
    public String getTask() {
        return task;
    }

    /**
     * getter for the task
     * 
     * @return
     */
    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }

    /**
     * setter for the task done
     * 
     * @param taskDone
     */
    public boolean getTaskDone() {
        return taskDone;
    }

    /**
     * getter for the task done
     * 
     * @return
     */
    public void alterStartTime(int startTime) {
        this.startTime = startTime;
    }
}
