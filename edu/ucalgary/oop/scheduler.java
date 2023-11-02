package edu.ucalgary.oop;
/**
 * Authors Gurnoor Singh, Aryan Sharma, Utkarsh Gupta
 * This class generates the daily schedule 
 * Animal Codes - fox-0, beaver-1, coyote-2, raccoon-3, porcupine-4
 * These codes are used in the array[] to store the number of animals and kits
 * 
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class scheduler {
    private int[] numberOfAnimals = new int[5];
    private int[] numberOfKits = new int[5];
    private int[] numberOfVolunteers = new int[24];
    private int[] extraTimeNeeded = new int[24];
    private static int numberOfTasks = 0;
    private Formatter f= new Formatter();
    private ArrayList<Schedule> schedule = new ArrayList<Schedule>();

    /**
     * Constructor for the class
     * 
     * @param startHour
     * @param animalList
     * @param medical_task
     * @param regularTask
     * @throws Schedule_cannot_be_made
     */

    public ArrayList<Schedule> getSchedule(int startHour, ArrayList<Animal> animalList,
            ArrayList<MedicalTask> medical_task, ArrayList<RegularTask> regularTask) throws Schedule_cannot_be_made{
        this.numberOfKits[0] = getAnimalKits("fox", animalList);
        this.numberOfKits[1] = getAnimalKits("beaver", animalList);
        this.numberOfKits[1] = getAnimalKits("coyote", animalList);
        this.numberOfKits[1] = getAnimalKits("racoon", animalList);
        this.numberOfKits[1] = getAnimalKits("porcupine", animalList);

        this.numberOfAnimals[0] = getAnimalNumber("fox", animalList) - numberOfKits[0];
        this.numberOfAnimals[1] = getAnimalNumber("beaver", animalList) - numberOfKits[1];
        this.numberOfAnimals[2] = getAnimalNumber("coyote", animalList) - numberOfKits[2];
        this.numberOfAnimals[3] = getAnimalNumber("racoon", animalList) - numberOfKits[3];
        this.numberOfAnimals[4] = getAnimalNumber("porcupine", animalList) - numberOfKits[4];

        // medical_task.add(new MedicalTask(1, 55, 6265,
        // 1, 1, "CHECK*"));
        // medical_task.add(new MedicalTask(1, 45, 6265,
        // 1, 1, "CHECK**"));
        // medical_task.add(new MedicalTask(19, 45, 6265,
        // 1, 19, "CHECK***"));
        // medical_task.add(new MedicalTask(19, 45, 6265,
        // 1, 19, "CHECK***"));
        // medical_task.add(new MedicalTask(20, 35, 6265,
        // 1, 20, "CHECK**"));


        // if(numberOfTasks==0){
        // medical_task.add(new MedicalTask(00, 60, 6265,
        // 1, 00, "CHECK_Final"));
        // medical_task.add(new MedicalTask(00, 35, 64265,
        // 1, 00, "CHECK_Final2"));
        // medical_task.add(new MedicalTask(00, 35, 6265,
        // 1, 00, "check"));
        // numberOfTasks++;
        // }

                try{
        return scheduleMaker(animalList, medical_task, regularTask);}
        catch(Schedule_cannot_be_made e){
            // throw new Schedule_cannot_be_made(e.getMessage());
        }
        return scheduleMaker(animalList, medical_task, regularTask);

    }

    /**
     * This method works as a manager for the schedule maker. It calls other ethods to return the 
     * schedule which is stored as an arraylist of schedules.
     * scheduleMaker() is called first to generate the schedule for the day.    
     * 
     * @param animalType
     * @param animalList
     * @return
     */
    public ArrayList<Schedule> scheduleMaker(ArrayList<Animal> animalList, ArrayList<MedicalTask> medical_task,
            ArrayList<RegularTask> regularTask) throws Schedule_cannot_be_made {

                /**
                 * Initialising the schedule with 24 hours
                 */
        int currentHour = 0;
        while (currentHour < 24) {
            Schedule scheduleInLoop = new Schedule(currentHour);
            try{
                /**
                 * Checks if volunteer is needed for the current hour.
                 *  If yes, then it adds the volunteer task to the schedule and adds extra 60 mins to that hour
                 */
                 
            
            checkExtra60(scheduleInLoop, currentHour, medical_task);}
            catch(Schedule_cannot_be_made e){
                throw new Schedule_cannot_be_made(e.getMessage());
            }
            schedule.add(new Schedule(scheduleInLoop));
            currentHour++;
        }
        /**
         * This method adds the medical tasks to the schedule
         */
         
        setMedicalTasks(medical_task, animalList);


        /**
         * This loop calls the method to generate the schedule for the three hour slots
         */
        for (int j = 0; j < schedule.size(); j++) {
            if (j == 0) {
                generateThreeHourSchedule(j, numberOfAnimals[0], numberOfAnimals[3], animalList, regularTask);
            }
            if (j == 8) {
                generateThreeHourSchedule(j, numberOfAnimals[1], -1, animalList, regularTask);
            }
            if (j == 19) {
                generateThreeHourSchedule(j, numberOfAnimals[2], numberOfAnimals[4], animalList, regularTask);
            }
        }
        /**
         * This method adds the cleaning tasks to the schedule
         */
        cageClean(animalList, schedule, regularTask);
        /**
         * This method adds the feeding tasks to the schedule
         */
        checkTaskNotDone(regularTask, medical_task, animalList);
        BufferedWriter writer = null;
        try {

            /**
             * This method writes the schedule to a text file
             */
            writer = new BufferedWriter(new FileWriter("schedule.txt"));

            for (int i = 0; i < schedule.size(); i++) {
                writer.write("\n");
                writer.write("Hour = " + schedule.get(i).getHour());
                writer.write("\n");
                writer.newLine();
                for (int j = 0; j < schedule.get(i).getTasks().length && schedule.get(i).getTasks()[j] != null; j++) {
                    writer.write("TASKS :" + schedule.get(i).getTasks()[j]);
                    writer.newLine();
    
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
       
        return schedule;
    }

    /**
     * This method sets the medical tasks to the schedule
     * @param scheduleInLoop
     * @param currentHour
     * @param medical_task
     * @throws Schedule_cannot_be_made
     */
    public void setMedicalTasks(ArrayList<MedicalTask> medical_task, ArrayList<Animal> animalList) {
        for (int i = 0; i < medical_task.size(); i++) {
            for (int hour = medical_task.get(i).getStartTime(); hour <= medical_task.get(i).getEndTime(); hour++) {
                if (medical_task.get(i).getDone() == false) {
                    if (schedule.get(hour).getRemaing_time() >= medical_task.get(i).getDuration()) {
                        schedule.get(hour).add_task((f.format(medical_task.get(i).getAnimalId(),medical_task.get(i).getTask(),animalIdToanimalName(medical_task.get(i).getAnimalId(), animalList))), medical_task.get(i).getDuration());
                        medical_task.get(i).setDone(true);
                        
                    }
                }
            }
        }
    }

    /**
     * This method adds regular tasks in the best time slot(3 hour slots) for the animals
     * @param scheduleInLoop
     * @param currentHour
     * @param medical_task
     * @throws Schedule_cannot_be_made
     */
    public int generateThreeHourSchedule(int startHour, int animal1, int animal2, ArrayList<Animal> animalList,
        ArrayList<RegularTask> regularTasks) {
        int[] remainingTime = getRemainingTime(startHour);
        int doneAnimal1 = 0;
        int prepTimeAnimal1 = getPrepTime(startHour, regularTasks, 1);
        int feedTimeAnimal1 = getFeedTime(startHour, regularTasks, 1);
        int totalFeedTimeAnimal1 = prepTimeAnimal1 + feedTimeAnimal1 * animal1;
        if (animal2 == -1) {
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= totalFeedTimeAnimal1) {
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                                && regularTasks.get(i).getPrepTime() == 0
                                && regularTasks.get(i).getTaskDone() == false) {
                            schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());
                            regularTasks.get(i).setTaskDone(true);
                            animal1--;
                        }
                    }
                    doneAnimal1 = 1;
                }
            }
            if (doneAnimal1 == 0) {
                for (int mn = 0; mn < 3; mn++) {
                    if (remainingTime[mn] >= prepTimeAnimal1 + feedTimeAnimal1) {
                        for (int i = 0; i < regularTasks.size(); i++) {
                            if (regularTasks.get(i).getStartTime() == startHour
                                    && regularTasks.get(i).getEndTime() != 23 && regularTasks.get(i).getPrepTime() == 0
                                    && regularTasks.get(i).getTaskDone() == false) {
                                schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());;
                                regularTasks.get(i).setTaskDone(true);
                                animal1--;
                            }
                        }
                        doneAnimal1 = 1;
                    }
                }
            }

        } else {
            int doneAnimal2 = 0;
            int prepTimeAnimal2 = getPrepTime(startHour, regularTasks, 2);
            int feedTimeAnimal2 = getFeedTime(startHour, regularTasks, 2);
            int totalFeedTimeAnimal2 = prepTimeAnimal2 + (feedTimeAnimal2 * animal2);

            // CASE1 : BOTH ANIMALS CAN BE FED IN ONE HOUR
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= totalFeedTimeAnimal1 + totalFeedTimeAnimal2) {
                    if (animal1 > 0) {
                        schedule.get(mn + startHour).add_task(
                                "Prepare Food for " + animalIdToanimalType(startHour, animalList, regularTasks),
                                prepTimeAnimal1);
                    }
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                                && regularTasks.get(i).getPrepTime() != 0
                                && regularTasks.get(i).getTaskDone() == false) {

                            schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());;
                            regularTasks.get(i).setTaskDone(true);
                            animal1--;
                        }

                    }
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                                && regularTasks.get(i).getPrepTime() == 0
                                && regularTasks.get(i).getTaskDone() == false) {
                            schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());
                            regularTasks.get(i).setTaskDone(true);
                            animal2--;
                        }
                    }
                    return (1);
                }
            }

            // CASE2 : animal 1 in particular hour and animal 2 in another single hour

            remainingTime = getRemainingTime(startHour);
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= totalFeedTimeAnimal1) {
                    if (animal1 > 0) {
                        schedule.get(mn + startHour).add_task(
                                "Prepare Food for " + animalIdToanimalType(startHour, animalList, regularTasks),
                                prepTimeAnimal1);
                    }
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                                && regularTasks.get(i).getPrepTime() != 0
                                && regularTasks.get(i).getTaskDone() == false) {
                            schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());;
                            regularTasks.get(i).setTaskDone(true);
                            animal1--;
                        }
                    }
                    doneAnimal1 = 1;
                }
            }

            remainingTime = getRemainingTime(startHour);
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= totalFeedTimeAnimal2) {
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                                && regularTasks.get(i).getPrepTime() == 0
                                && regularTasks.get(i).getTaskDone() == false) {
                            schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());;
                            regularTasks.get(i).setTaskDone(true);
                            animal2--;
                        }

                    }
                    doneAnimal2 = 1;
                }

            }

            if (doneAnimal1 == 1 && doneAnimal2 == 1) {
                return (1);
            }

            // CASE4 : ALL Random

            remainingTime = getRemainingTime(startHour);
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= feedTimeAnimal2 && remainingTime[mn] > 0) {
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (remainingTime[mn] >= feedTimeAnimal2) {
                            if (regularTasks.get(i).getStartTime() == startHour
                                    && regularTasks.get(i).getEndTime() != 23
                                    && regularTasks.get(i).getPrepTime() == 0 && !regularTasks.get(i).getTaskDone()) {
                                schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());
                                regularTasks.get(i).setTaskDone(true);
                                animal2--;
                                remainingTime = getRemainingTime(startHour);
                            }
                        }
                    }
                }
            }
            remainingTime = getRemainingTime(startHour);
            for (int mn = 0; mn < 3; mn++) {
                if (remainingTime[mn] >= prepTimeAnimal1 + feedTimeAnimal1 && remainingTime[mn] > 0) {
                    if (animal1 > 0) {
                        schedule.get(mn + startHour).add_task(
                                "Prepare Food for " + animalIdToanimalType(startHour, animalList, regularTasks),
                                prepTimeAnimal1);
                    }
                    for (int i = 0; i < regularTasks.size(); i++) {
                        if (remainingTime[mn] >= feedTimeAnimal1) {
                            if (regularTasks.get(i).getStartTime() == startHour
                                    && regularTasks.get(i).getEndTime() != 23
                                    && regularTasks.get(i).getPrepTime() != 0
                                    && regularTasks.get(i).getTaskDone() == false) {
                                schedule.get(startHour + mn).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());
                                regularTasks.get(i).setTaskDone(true);
                                remainingTime = getRemainingTime(startHour);
                                animal1--;
                                if (remainingTime[mn] <= feedTimeAnimal1) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return (1);
        }
        return (0);
    }

    /**
     * This method is used to check if volunteer is needed for the hour
     * @param hourSchedule
     * @param hour
     * @param medicalTasks
     * @throws Schedule_cannot_be_made
     */
    public void checkExtra60(Schedule hourSchedule, int hour, ArrayList<MedicalTask>medicalTasks) throws Schedule_cannot_be_made {
        if (numberOfVolunteers[hour] == 1) {
            hourSchedule.add_task("**Volunteer Needed**", -60);
        } 
        else if (numberOfVolunteers[hour] > 1) {
            String commaSeparatedHours = "";
            for(int i=0; i<medicalTasks.size(); i++) {
                if(medicalTasks.get(i).getStartTime() == hour) {
                    commaSeparatedHours += i + " ";
                }
            }
             throw new Schedule_cannot_be_made(commaSeparatedHours);
           
        }
    }

    /**
     * This method is used to find the number of kits for a particular animal
     * @param animal1
     * @param animalList
     * @return
     */
    public int getAnimalKits(String animal1, ArrayList<Animal> animalList) {
        int total = 0;
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getAnimalType().equals(animal1) && animalList.get(i).isFed() == true) {
                total++;
            }
        }
        return total;
    }

    /**
     * This method is used to get total number of animals of a particular type
     * @param animal1
     * @param animalList
     * @return
     */
    private int getAnimalNumber(String animal1, ArrayList<Animal> animalList) {
        int total = 0;
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getAnimalType().equals(animal1)) {
                total++;
            }
        }
        return total;
    }

    /**
     * This method is used to get the remaining time for the next 3 hours
     * @param startHour
     * @param animalList
     * @param regularTasks
     * @return
     */
    public int[] getRemainingTime(int hourSlot) {
        int[] remainingTime = new int[3];
        for (int i = 0; i < 3; i++) {
            remainingTime[i] = schedule.get(hourSlot + i).getRemaing_time();
        }
        return remainingTime;
    }

    /**
     * This method is used to get the prep time of an animal from regular tasks
     * @param startHour
     * @param animalList
     * @param regularTasks
     * @return
     */
    public int getPrepTime(int startHour, ArrayList<RegularTask> regularTasks, int n) {
        int prepTime = 0;
        for (int i = 0; i < regularTasks.size(); i++) {
            if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                    && regularTasks.get(i).getPrepTime() != 0 && n == 1) {
                prepTime = regularTasks.get(i).getPrepTime();
                return prepTime;
            }
        }
        return 0;
    }

    /**
     * This method is used to get the feed time of an animal from regular tasks
     * @param startHour
     * @param animalList
     * @param regularTasks
     * @return
     */
    public int getFeedTime(int startHour, ArrayList<RegularTask> regularTasks, int n) {
        int feedTime = 0;
        if (n == 1) {
            for (int i = 0; i < regularTasks.size(); i++) {
                if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                        && regularTasks.get(i).getPrepTime() != 0) {
                    feedTime = regularTasks.get(i).getDuration();
                    return feedTime;
                }
            }
        } else {
            for (int i = 0; i < regularTasks.size(); i++) {
                if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                        && regularTasks.get(i).getPrepTime() == 0) {
                    feedTime = regularTasks.get(i).getDuration();
                    return feedTime;
                }
            }
        }
        return 0;
    }

    /**
     * This method is used set cage cleaining tasks for an animal
     * @param animalId
     * @param animalList
     * @return
     */
    public void cageClean(ArrayList<Animal> animalList, ArrayList<Schedule> schedule,
            ArrayList<RegularTask> regularTasks) {

        for (int j = 0; j < schedule.size(); j++) {

            for (int i = 0; i < regularTasks.size(); i++) {

                if (regularTasks.get(i).getEndTime() == 23 && regularTasks.get(i).getTaskDone() == false &&
                        schedule.get(j).getRemaing_time() >= regularTasks.get(i).getDuration()) {
                    schedule.get(j).add_task((f.format(regularTasks.get(i).getAnimalId(),regularTasks.get(i).getTask(),animalIdToanimalName(regularTasks.get(i).getAnimalId(), animalList))), regularTasks.get(i).getDuration());
                    regularTasks.get(i).setTaskDone(true);
                }

            }
        }
    }
    /**
     * This method is used to get the animal type from animal id
     * @param startHour
     * @param animalList
     * @param regularTasks
     * @return
     */
    public String animalIdToanimalType(int startHour, ArrayList<Animal> animalList,
            ArrayList<RegularTask> regularTasks) {
        for (int i = 0; i < regularTasks.size(); i++) {
            if (regularTasks.get(i).getStartTime() == startHour && regularTasks.get(i).getEndTime() != 23
                    && regularTasks.get(i).getPrepTime() != 0) {
                for (int j = 0; j < animalList.size(); j++) {
                    if (animalList.get(j).getAnimalID() == regularTasks.get(i).getAnimalId()) {
                        return animalList.get(j).getAnimalType();
                    }
                }
            }
        }
        return null;
    }

    /**
     * This method is used to get the animal name from animal id
     * @param startHour
     * @param animalList
     * @param regularTasks
     * @return
     */
    public String animalIdToanimalName(int aniId, ArrayList<Animal> animalList) {
        for (int i = 0; i < animalList.size(); i++) {
            
                for (int j = 0; j < animalList.size(); j++) {
                    if (animalList.get(j).getAnimalID() == aniId) {
                        return animalList.get(j).getAnimalName();
                    }
                }
            
        }
        return null;
    }
    
    /**
     * This method checks if the task is done or not
     * It throws an exception if the task is not done and can add a volunteer to that ask
     * @param regularTasks
     * @param MedicalTask
     * @param animalList
     * @throws Schedule_cannot_be_made
     */
    public void checkTaskNotDone(ArrayList<RegularTask> regularTasks, ArrayList<MedicalTask> MedicalTask,
            ArrayList<Animal> animalList) throws Schedule_cannot_be_made {
        int callFn = 0;
        int hour;
        for (int i = 0; i < 24; i++) {
            extraTimeNeeded[i] = 0;
        }
        for (int i = 0; i < regularTasks.size(); i++) {
            if (regularTasks.get(i).getTaskDone() == false) {
                hour = allocateBestHour(regularTasks.get(i).getStartTime(), regularTasks.get(i).getEndTime());
                extraTimeNeeded[hour] += regularTasks.get(i).getDuration();
                callFn = 1;

            }

        }
        for (int j = 0; j < MedicalTask.size(); j++) {
            if (MedicalTask.get(j).getDone() == false) {
               
                hour = allocateBestHour(MedicalTask.get(j).getStartTime(), MedicalTask.get(j).getEndTime());
                extraTimeNeeded[hour] += MedicalTask.get(j).getDuration();
                
                callFn = 1;
            }
        }

        if (callFn == 1) {
            try{
            callVolunteer(regularTasks, MedicalTask, animalList);}
            catch(Schedule_cannot_be_made e){
                throw new Schedule_cannot_be_made(e.getMessage());
            }
        }
        // will return 1 if all tasks are done then the exception wont be thrown
    }

    /**
     * This method is used to allocate the best hour for a task
     * The method finds he best hour by checking the number of volunteers and the remaining time
     * Lets say if a task can be added in hour 0 -2, then it will check remaining time in hose hours and 
     * number of volunteers in those hours and will allocate the best hour
     * @param startHour
     * @param endHour
     * @return
     */

    public int allocateBestHour(int startHour, int endHour) {
        int hour = startHour;
        for (int i = startHour; i < endHour; i++) {
            if (schedule.get(i).getRemaing_time() > hour && numberOfVolunteers[i] == 0) {
                hour = i;
            }
        }
        if (numberOfVolunteers[hour] == 0) {
            return hour;
        }
        for (int i = startHour; i < endHour; i++) {
            if (numberOfVolunteers[i] == 0) {
                return i;
            }
        }

        return hour;

    }

    /**
     * This method is used to call a volunteer
     * It will add a volunteer to the task which has the maximum extra time needed
     * This method also throws an exception if the schedule cannot be made
     * @param regularTasks
     * @param medical_task
     * @param animalList
     * @throws Schedule_cannot_be_made
     */
    public void callVolunteer(ArrayList<RegularTask> regularTasks, ArrayList<MedicalTask> medical_task,
            ArrayList<Animal> animalList) throws Schedule_cannot_be_made{

        schedule.clear();

        int max = 0;
        int maxIndex = 0;

        for (int j = 0; j < extraTimeNeeded.length; j++) {

            if (extraTimeNeeded[j] > max) {
                max = extraTimeNeeded[j];
                maxIndex = j;

            }
        }
        numberOfVolunteers[maxIndex]++;
        extraTimeNeeded[maxIndex] = 0;
        setEverythingtoFalse(medical_task, regularTasks);
        try{
        scheduleMaker(animalList, medical_task, regularTasks);}
        catch(Schedule_cannot_be_made e){
            throw new Schedule_cannot_be_made(e.getMessage());
        }

    }

    /**
     * This method is used to set all the tasks to false
     * This method is used when we add a volunteer to the schedule and we need to start again!
     * @param medical_task
     * @param regularTasks
     */
    public void setEverythingtoFalse(ArrayList<MedicalTask> medical_task, ArrayList<RegularTask> regularTasks) {
        for (int i = 0; i < regularTasks.size(); i++) {
            regularTasks.get(i).setTaskDone(false);
        }
        for (int j = 0; j < medical_task.size(); j++) {
            medical_task.get(j).setDone(false);
        }
        for (int i = 0; i < medical_task.size(); i++) {
            if (medical_task.get(i).getTask().equals("Kit feeding")) {
                int id = medical_task.get(i).getAnimalId();
                for (int j = 0; j < regularTasks.size(); j++) {
                    if (regularTasks.get(j).getAnimalId() == id) {
                        regularTasks.get(j).setTaskDone(true);
                    }
                }
            }
        }
    }

}