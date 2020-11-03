package ru.appline.logic;


public class User {

    private String name;

    private String Vorname;

    private double salary;

    public User(String name, String vorname, double salary) {
        this.name = name;
        Vorname = vorname;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getVorname() {
        return Vorname;
    }

    public double getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
