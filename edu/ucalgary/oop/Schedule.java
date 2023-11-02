
package edu.ucalgary.oop;
/**
 * Author Aryan Sharma, Gurnoor Singh
 * This class is used to store an hourly schedule and cantains methods to add
 * tasks to the schedule
 * and set remining time for the schedule'
 * 
 */

public class Schedule {
    private int hour;
    private int remaing_time = 60;
    private String[] tasks = new String[250];
    private int task_counter = 0;

    /**
     * constructor for the schedule class
     * 
     * @param hour
     */
    public Schedule(int hour) {
        this.hour = hour;
    }

    /**
     * copy constructor for the schedule class
     * 
     * @param schedule
     */
    public Schedule(Schedule schedule) {
        this.hour = schedule.hour;
        this.remaing_time = schedule.remaing_time;
        this.tasks = schedule.tasks;
        this.task_counter = schedule.task_counter;
    }

    /**
     * method to set the remaing time for the schedule
     * 
     * @param duration
     */
    public void new_remaintime(int duration) { // throws exception if duration is greater than remaing time
        remaing_time = remaing_time - duration;
    }

    /**
     * method to add a task to the schedule
     * 
     * @param task
     * @param duration
     */
    public void add_task(String task, int duration) {
        tasks[task_counter] = task;
        task_counter++;
        new_remaintime(duration);
    }

    /**
     * getter for the hour
     * 
     * @return
     */
    public int getHour() {
        return hour;
    }

    /**
     * getter for the remaing time
     * 
     * @return
     */
    public int getRemaing_time() {
        return remaing_time;
    }

    /**
     * setter for the remaing time
     * 
     * @param remaing_time
     */
    public void setRemaing_time(int remaing_time) {
        this.remaing_time = remaing_time;
    }

    /**
     * getter for the tasks
     * 
     * @return
     */
    public String[] getTasks() {
        return tasks;
    }

    /**
     * getter for the task counter
     * 
     * @return
     */
    public int getTask_counter() {
        return task_counter;
    }

}