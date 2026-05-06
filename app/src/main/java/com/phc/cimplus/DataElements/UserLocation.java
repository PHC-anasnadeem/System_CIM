package com.phc.cimplus.DataElements;

public class UserLocation {
    private String username;
    private String latitude;
    private String longitude;
    private String lastUpdated;
    private String message;
    private boolean success;

    public UserLocation() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getLatitudeDouble() {
        try {
            return Double.parseDouble(latitude);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getLongitudeDouble() {
        try {
            return Double.parseDouble(longitude);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
