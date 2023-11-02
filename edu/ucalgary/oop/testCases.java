package edu.ucalgary.oop;

import org.junit.Test;
import org.junit.Assert;
import java.util.*;


public class testCases {

    
    
// test case 1: test if the database is connected
Animal animal = new Animal(1, "test", "Chetah");
    @Test
    public void testingGetAnimalID() {
        Assert.assertEquals(1, animal.getAnimalID());
    }
    @Test
    public void testingGetAnimalName() {
        Assert.assertEquals("test", animal.getAnimalName());
    }
    @Test
    public void testingIsCleaned() {
        Assert.assertEquals(false, animal.isCleaned());
    }
    @Test
    public void testingIsFed() {
        Assert.assertEquals(false, animal.isFed());
    }
    @Test
    public void testingSetCleaned() {
        animal.setCleaned(true);
        Assert.assertEquals(true, animal.isCleaned());
    }
    @Test
    public void testingSetFed() {
        animal.setFed(true);
        Assert.assertEquals(true, animal.isFed());
    }
    @Test 
    public void testingGetAnimalType() {
        Assert.assertEquals("Chetah", animal.getAnimalType());
    }

    // Testing Regular Tasks
    RegularTask task = new RegularTask(1, 2, 3, 4, 5, "test");
    @Test
    public void testingGetTaskRegularTask() {
        Assert.assertEquals("test", task.getTask());
    }
    @Test
    public void testingGetAnimalIdFromRegularTask() {
        Assert.assertEquals(3, task.getAnimalId());
    }
    @Test
    public void testingGetDurationRegularTask() {
        Assert.assertEquals(4, task.getDuration());
    }
    @Test
    public void testingGetEndTimeRegularTask() {
        Assert.assertEquals(2, task.getEndTime());
    }
    @Test
    public void testingGetStartTimeRegularTask() {
        Assert.assertEquals(1, task.getStartTime());
    }
    @Test
    public void testingGetPrepTimeRegularTask() {
        Assert.assertEquals(5, task.getPrepTime());
    }
    @Test
    public void testingGetTaskDoneRegularTask() {
        Assert.assertEquals(false, task.getTaskDone());
    }
    @Test
    public void testingSetTaskDoneRegularTask() {
        task.setTaskDone(true);
        Assert.assertEquals(true, task.getTaskDone());
    }
    // testing MedicalTasks
    MedicalTask medicalTask = new MedicalTask(1, 2, 3, 4, 5, "test");
    @Test
    public void testingGetTaskMedicalTask() {
        Assert.assertEquals("test", medicalTask.getTask());
    }
    @Test
    public void testingGetAnimalIdFromMedicalTask() {
        Assert.assertEquals(4, medicalTask.getAnimalId());
    }
    @Test
    public void testingGetDurationMedicalTask() {
        Assert.assertEquals(2, medicalTask.getDuration());
    }
    @Test
    public void testingGetEndTimeMedicalTask() {
        Assert.assertEquals(5, medicalTask.getEndTime());
    }
    @Test
    public void testingGetStartTimeMedicalTask() {
        Assert.assertEquals(1, medicalTask.getStartTime());
    }
    @Test
    public void testingGetTaskDoneMedicalTask() {
        Assert.assertEquals(false, medicalTask.getTaskDone());
    }
    @Test
    public void testingSetTaskDoneMedicalTask() {
        medicalTask.setTaskDone(true);
        Assert.assertEquals(true, medicalTask.getTaskDone());
    }
    // Test Schedule
    Schedule schedule = new Schedule(1);

    @Test
    public void testingGetHourSchedule() {
        Assert.assertEquals(1, schedule.getHour());
    }
    @Test
    public void testingGetRemaingTimeSchedule() {
        Assert.assertEquals(60, schedule.getRemaing_time());
    }
    @Test
    public void testingGetTasksSchedule() {
        Assert.assertEquals(0, schedule.getTask_counter());
    }
    @Test
    public void testingGetTaskCounterSchedule() {
        Assert.assertEquals(0, schedule.getTask_counter());
    }
    @Test
    public void testingSetRemaingTimeSchedule() {
        schedule.setRemaing_time(50);
        Assert.assertEquals(50, schedule.getRemaing_time());
    }
    @Test
    public void testingAddTaskSchedule() {
        schedule.add_task("test", 10);
        Assert.assertEquals(1, schedule.getTask_counter());
    }
    //    Testing Scheduler Class
    ArrayList <Animal> animalList = new ArrayList<Animal>();
    ArrayList<MedicalTask> medicalTaskList = new ArrayList<MedicalTask>();
    ArrayList<RegularTask> regularTaskList = new ArrayList<RegularTask>();

  
    @Test
    public void testingIfSchedulerCallsforVolunteer2() throws Schedule_cannot_be_made {
        medicalTaskList.add(new MedicalTask(1, 55, 25,
        1, 1, "CHECK*"));
        medicalTaskList.add(new MedicalTask(1, 45, 26,
        1, 1, "CHECK**"));
        scheduler schedule = new scheduler();

        ArrayList<Schedule> schedule2;
        Check_volunteer_needed check;
        int vol[] = new int[24];
        try{
        schedule2=schedule.getSchedule(0, animalList, medicalTaskList, regularTaskList);
        check = new Check_volunteer_needed(schedule2);
        vol = check.getVolunteer_needed();
        Assert.assertEquals(1, vol[1]);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

  
    // Testing regular Tasks(feeding and cage cleaning)
    @Test
    public void testingIfSchedulerCallsforVolunteer3() throws Schedule_cannot_be_made {
        regularTaskList.add(new RegularTask(1, 55, 25,
        1, 1, "FEED*"));
        regularTaskList.add(new RegularTask(1, 45, 26,
        1, 1, "CLEAN**"));
        scheduler schedule = new scheduler();

        ArrayList<Schedule> schedule2;
        Check_volunteer_needed check;
        int vol[] = new int[24];
        try{
        schedule2=schedule.getSchedule(0, animalList, medicalTaskList, regularTaskList);
        check = new Check_volunteer_needed(schedule2);
        vol = check.getVolunteer_needed();
        Assert.assertEquals(1, vol[0]);
        }
        catch(Exception e){
            System.out.println(e);
        }
       


    }

    @Test
    public void testingIfSchedulerCallsforVolunteer4() throws Schedule_cannot_be_made {
        regularTaskList.add(new RegularTask(1, 55, 25,
        1, 1, "FEED*"));
        regularTaskList.add(new RegularTask(1, 45, 26,
        1, 1, "CLEAN**"));
        scheduler schedule = new scheduler();

        ArrayList<Schedule> schedule2;
        Check_volunteer_needed check;
        int vol[] = new int[24];
        try{
        schedule2=schedule.getSchedule(0, animalList, medicalTaskList, regularTaskList);
        check = new Check_volunteer_needed(schedule2);
        vol = check.getVolunteer_needed();
        Assert.assertEquals(1, vol[1]);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

 
  




   
    


    

    


    

    
    
    
   

    

 
    

   




    
}
