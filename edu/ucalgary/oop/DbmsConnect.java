package edu.ucalgary.oop;
/*
@author Utkarsh Gupta, ARyan Sharma, Naina Gupta
href = "utkarsh.gupta@ucalgary.ca", "aryan.sharma2@ucalgary.ca", "naina.gupta@ucalgary.ca"
@version 1.8
@since 1.0
 */

import java.util.ArrayList;
import java.sql.*;

public class DbmsConnect {
    private Connection dbConnect;
    private ResultSet results;
    private ResultSet trying_task;
    private ResultSet trying_treatments;


    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "user1", "ensf"); // ONLY FOR
                                                                                                    // UG
        } 
        catch (SQLException e) {
            System.out.println("Error: Could not connect to the database");
            e.printStackTrace();
        }
    }

    /**
     * selects all the animals from the database and populates the arraylist
     * @param animalList
     * @param regular_task
     */
    public void select_animal_name(ArrayList<Animal> animalList, ArrayList<RegularTask> regular_task) 
    {

        StringBuffer animal_name = new StringBuffer();
        int i = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while (results.next()) {

                animal_name.append(results.getString("AnimalID"));
                animal_name.append('\n');
                int ani_id = results.getInt("AnimalID");
                String ani_name=results.getString("AnimalNickname");
                animalList.add(new Animal(ani_id, results.getString("AnimalNickname"),       
                results.getString("AnimalSpecies")));
                String taskCageClean="Clean Cage : "+ani_name;
                String taskFeed="Feed : "+ani_name;
                
                switch (animalList.get(i).getAnimalType()) {
                    case "fox":
                        regular_task.add(new RegularTask(0,3,ani_id,5,5,taskFeed));
                        regular_task.add(new RegularTask(0,23,ani_id,5,0,taskCageClean));
                        break;
                    case "porcupine":
                        regular_task.add(new RegularTask(19,22,ani_id,5,0,taskFeed));
                        regular_task.add(new RegularTask(0,23,ani_id,10,0,taskCageClean));

                        break;
                    case "coyote":
                        regular_task.add(new RegularTask(19,22,ani_id,5,10,taskFeed));
                        regular_task.add(new RegularTask(0,23,ani_id,5,0,taskCageClean));
                        break;
                    case "beaver":
                        regular_task.add(new RegularTask(8,11,ani_id,5,0,taskFeed));
                        regular_task.add(new RegularTask(0,23,ani_id,5,0,taskCageClean));
                        break;
                    case "raccoon":
                        regular_task.add(new RegularTask(0,3,ani_id,5,0,taskFeed)); 
                        regular_task.add(new RegularTask(0,23,ani_id,5,0,taskCageClean));
                        break;
            }
            i++;
        }
        myStmt.close();
    }

        catch (SQLException ex) {
            System.out.println("Error: Could not execute query");
            ex.printStackTrace();
        }
    }

    /**
     * selects the all tasks from the database and populates the arraylist
     * @param medical_task
     */
    public void select_medical_from_database(ArrayList<MedicalTask> medical_task) {
        try {
            Statement myStmt = dbConnect.createStatement();
            Statement myStmt2 = dbConnect.createStatement();

            int taskid;
            int animalid;
            int starttime;

            trying_treatments = myStmt.executeQuery("SELECT * FROM TREATMENTS");
            trying_task = myStmt2.executeQuery("SELECT * FROM TASKS");

            while (trying_treatments.next()) {
                taskid = trying_treatments.getInt("TaskID");
                animalid = trying_treatments.getInt("AnimalID");
                starttime = trying_treatments.getInt("StartHour");
                trying_task = myStmt2.executeQuery("SELECT * FROM TASKS");

                while (trying_task.next()) {
                    if (trying_task.getInt("TaskID") == taskid) {
                        medical_task.add(new MedicalTask(starttime, trying_task.getInt("Duration"),
                                taskid, animalid, trying_task.getInt("MaxWindow") + starttime,
                                trying_task.getString("Description")));
                    }
                }
            }

            myStmt.close();
            myStmt2.close();
        } catch (SQLException ex) {
            System.out.println("Error: Could not execute query");
            ex.printStackTrace();
        }
    }

    /**
     * updates the medical task in the database
     * @param regular_task
     */

    public void update_medical_time(ArrayList<MedicalTask> medical_task, int taskid, int new_time){
        System.out.println("Updating medical task");
        try {
            System.out.println("Updating medical task tryyyyyyy");
            Statement myStmt = dbConnect.createStatement();
            String sql = "UPDATE TREATMENTS SET StartHour = "+new_time+" WHERE TreatmentID = "+taskid;
            myStmt.executeUpdate(sql);
            myStmt.close();
        } catch (SQLException ex) {
            System.out.println("Error: Could not execute query");
            ex.printStackTrace();
        }
    }


    /**
     * closes the connection to the database
     * @param regular_task
     */
    public void closeConnection() {
        // Close the connection
        try {
            dbConnect.close();
        } catch (SQLException e) {
            System.out.println("Error: Could not close the database connection");
            e.printStackTrace();
        }
    }
}