package models;
import java.io.Serializable;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String pesel;
    private int phoneNumer;

    public Client(String name,String surname, String pesel, int phoneNumer) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.phoneNumer = phoneNumer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {this.surname = surname;}

    public String getSurname() {return surname;}

    public void setPesel(String pesel) {this.pesel = pesel;}

    public String getPesel() {return pesel;}

    public int getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(int phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    @Override
    public String toString(){
        return "Client " + name + " " + surname + " Pesel " + pesel + " phone number " + phoneNumer;
    }
}