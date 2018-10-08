package com.vaadin;
import java.sql.*;
import java.util.ArrayList;

/**
 * Helper class used to retrieve information from database tables
 */
public class Database{

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;
    private PreparedStatement preparedStatement = null;

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

    /**
     * Gets all the students in the database
     * @return an array list of students
     * @throws SQLException when it fails to execute a query
     */
    public ArrayList<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<Student>();
        String query = "SELECT * FROM student";
        try{
            result = statement.executeQuery(query);
            while(result.next()) {

                String id = Integer.toString(result.getInt("student_id"));
                String firstName = result.getString("student_first_name");
                String lastName = result.getString("student_last_name");
                String degree = result.getString("degree");
                String yearOfStudy = result.getString("year_of_study");
                String outcome = result.getString("outcome");
                students.add(new Student(id, firstName, lastName, degree, yearOfStudy, outcome));

            }
        }
        catch (SQLException e){

        }

        return students;
    }

    /**
     * Checks if the login details of a client are valid
     * @param username
     * @param password
     * @throws SQLException when we fail to execute a query
     * @return true if the username and password correspond to a record in the database, returns false otherwise
     */
    public boolean validLogin(String username, String password) {

        String query = String.format("SELECT * FROM staff WHERE staff_password = '%s' and staff_id = '%s'", password, username);
        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            if(result.next()){
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a name of a course corresponding to a given course id
     * @param course_id
     * @return course name given a course id
     * @throws SQLException
     */
    public String getCourseName(String course_id) throws SQLException {

        String courseName = null;
        String query = String.format("SELECT * FROM course WHERE course_id = '%s'", course_id);
        Statement statement = connection.createStatement();
        try {
            ResultSet result = statement.executeQuery(query);
            if(result.next()){
                courseName = result.getString("course_title");
            }
            return courseName;
        }
        catch (SQLException e) {
            return "No course for now";
        }

    }

    /**
     * Returns all the marks for a given student
     * @param student
     * @return an array list containing student Mark objects
     */
    public ArrayList<Marks> getMarks(Student student) {

        ArrayList<Marks> marks = new ArrayList<>();
        String query = String.format("SELECT * FROM enrollment WHERE student_id = '%s'", student.getId());

        try {

            result = statement.executeQuery(query);

            while(result.next()){
                String yearMark = result.getString("year_mark");
                String examMark = result.getString("exam_mark");
                String finalMark = result.getString("final_mark");
                String tutAttendance = result.getString("tut_attendance");
                String outcome = result.getString("outcome");
                String course_id = result.getString("course_code");
                String course = getCourseName(course_id);
                marks.add(new Marks(course, yearMark, examMark, finalMark, outcome, tutAttendance));
            }
            return marks;
        }
        catch (SQLException e){
            return marks;
        }
    }
}