package com.phc.cim.DataElements;

public class RegistrationItem {
    private String name;
    private String id;
    private String status;

    // Constructor
    public RegistrationItem(String name, String id, String status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RegistrationItem{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

