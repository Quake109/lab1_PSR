package models;

import java.io.Serializable;

public class ParcelPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uniqueID;
    private String address;
    private int capacity;

    public ParcelPoint(String uniqueID, String address, int capacity) {
        this.uniqueID = uniqueID;
        this.address = address;
        this.capacity = capacity;
    }

    public String getUniqueID() {
        return uniqueID;
    }
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setAddress(String address) {this.address = address;}

    public String getAddress() {return address;}

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return "PercelPoint ID: " + uniqueID + " address: " + address + " Percel Point capacity: " + capacity;
    }
}