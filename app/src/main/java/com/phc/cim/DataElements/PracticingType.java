package com.phc.cim.DataElements;

public class PracticingType {
    private final String id;
    private final String description;

    // Update the constructor to accept String for id
    public PracticingType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    // Change the getter method to return String
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description; // This is used to display the description in the spinner
    }
}


