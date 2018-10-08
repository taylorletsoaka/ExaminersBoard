package com.vaadin;

public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String degree;
    private String yearOfStudy;
    private String outcome;

    public Student(String id, String firstName, String lastName, String degree, String yearOfStudy, String outcome){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.yearOfStudy = yearOfStudy;
        this.email = id + "@students.wits.ac.za";
        this.outcome = outcome;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getDegree(){
        return degree;
    }

    public String getYearOfStudy(){
        return yearOfStudy;
    }

    public String getOutcome() {
        return outcome;
    }

    /**
    @Override
    public int hashCode() {
        return id;
    }*/


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return firstName;
    }

}
