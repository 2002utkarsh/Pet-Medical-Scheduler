package edu.ucalgary.oop;
/**
 * Author Gurnoor Singh
 * 
 * This class is used to check if there are any volunteers needed.
 * It returns an array of size 24, where each index represents the hour of the 
 * day and the corresponding value is the number of volunteers needed.
 */

import java.util.ArrayList;

public class Check_volunteer_needed {
    private int[] volunteer_needed = new int[24];

    /**
     * Constructor for the class
     */
    public Check_volunteer_needed(ArrayList<Schedule> schedule) {
        for (int i = 0; i < schedule.size(); i++) {

            for (int j = 0; j < schedule.get(i).getTasks().length; j++) {
                if (schedule.get(i).getTasks()[j] == null) {
                    continue;
                }
                /**
                 * Checks id=f the shedule cantains **Volunteer Needed** String
                 * If that hour needs an volunteer, increment the value at the index
                 */
                if (schedule.get(i).getTasks()[j].equals("**Volunteer Needed**")) {
                    volunteer_needed[schedule.get(i).getHour()]++;
                }
            }
        }
    }

    /**
     * Getter for the array
     * 
     * @return volunteer_needed array
     */
    public int[] getVolunteer_needed() {
        return volunteer_needed;
    }
}
