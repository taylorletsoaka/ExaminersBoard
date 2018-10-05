package com.vaadin;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database{

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    public Database() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/facultydatabase", "witsadmin", "witsadmin");
            statement = connection.createStatement();
        }

        catch(Exception exception) {
            System.out.println("Error:" + exception);
        }
    }

    public ArrayList<Student> getStudents() {


        ArrayList<Student> students = new ArrayList<Student>();

        try{
            result = statement.executeQuery("SELECT * FROM student");

            while(result.next()) {

                String id = Integer.toString(result.getInt("student_number"));
                String firstName = result.getString("student_firstname");
                String lastName = result.getString("student_lastname");
                String degree = result.getString("degree");
                String yearOfStudy = result.getString("Year_of_study");

                students.add(new Student(id, firstName, lastName, degree, yearOfStudy));

            }
        }
        catch (SQLException e){

        }

        return students;
    }

}