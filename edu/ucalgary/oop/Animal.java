package edu.ucalgary.oop;
/**
 * Author : Utkarsh Gupta, Gurnoor Singh
 * 
 * this class is used to create an animal object
 * this class stores the animal id, animal name, animal type,
 *  if the animal is fed and if the animal's cage is cleaned
 *  
 */

public class Animal {
    private final int ANIMAL_ID;
    private final String ANIMAL_NAME;
    private final String ANIMAL_TYPE;
    private boolean fed = false;
    private boolean cleaned = false;

/**
 * constructor for the animal class
 * 
 * @param animalID
 * @param animalName
 * @param animalType
 */
    public Animal(int animalID, String animalName, String animalType) {
        try {
            this.ANIMAL_ID = animalID;
            this.ANIMAL_NAME = animalName;
            this.ANIMAL_TYPE = animalType;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Animal Id not present");
        }
    }
    
    /**
     * getter for the animal id
     * @return animal_id
     */
    public int getAnimalID() {
        return ANIMAL_ID;
    }
    /**
     * getter for the animal name
     * @return animalName
     */
    public String getAnimalName() {
        return ANIMAL_NAME;
    }
    /**
     * getter for the animal type
     * @return animalType
     */

    public String getAnimalType() {
        return ANIMAL_TYPE;
    }

    /**
     * getter for the fed variable
     * @return
     */
    public boolean isFed() {
        return fed;
    }

    /**
     * setter for the fed variable
     * @param fed
     */
    public void setFed(boolean fed) {
        this.fed = fed;
    }

    /**
     * getter for the cageCleaned variable
     * @return
     */
    public boolean isCleaned() {
        return cleaned;
    }

    /**
     * setter for the cageCleaned variable
     * @param cleaned
     */
    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }
}