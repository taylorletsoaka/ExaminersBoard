package com.vaadin;

/**
 * Helper class used to create Mark objects for a given student
 */
public class Marks {

    String outcome, finalMark, examMark, yearMark, tutAttendance, course;

    /**
     * Instantiates a Mark object
     * @param course
     * @param yearMark
     * @param examMark
     * @param finalMark
     * @param outcome
     * @param tutAttendance
     */
    public Marks(String course, String yearMark, String examMark, String finalMark, String outcome, String tutAttendance){
        this.course = course;
        this.yearMark = yearMark;
        this.examMark = examMark;
        this.finalMark = finalMark;
        this.outcome = outcome;
        this.tutAttendance = tutAttendance;
    }

    /**
     * Retrieves the name of a course
     * @return course name
     */
    public String getCourse() {
        return course;
    }

    /**
     * Retrieves a student's exam mark
     * @return exam mark
     */
    public String getExamMark() {
        return examMark;
    }

    /**
     * Retrieves a student's final mark
     * @return final mark, weighted average of exam and year mark
     */
    public String getFinalMark() {
        return finalMark;
    }

    /**
     * Retrieves a student's progress outcome for a given course
     * @return outcome, PASS if student's mark is 50 and above, FAIL otherwise
     */
    public String getOutcome() {
        return outcome;
    }

    /**
     * Retrieves a student's tutorial attendance, to be used as voting factor
     * @return tutorial attendance as a percentage
     */
    public String getTutAttendance() {
        return tutAttendance;
    }

    /**
     * Retrieves a student's year mark
     * @return year mark
     */
    public String getYearMark() {
        return yearMark;
    }

}
