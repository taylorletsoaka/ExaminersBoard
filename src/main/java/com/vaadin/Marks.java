package com.vaadin;

public class Marks {

    String outcome, finalMark, examMark, yearMark, tutAttendance, course;

    public Marks(String course, String yearMark, String examMark, String finalMark, String outcome, String tutAttendance){
        this.course = course;
        this.yearMark = yearMark;
        this.examMark = examMark;
        this.finalMark = finalMark;
        this.outcome = outcome;
        this.tutAttendance = tutAttendance;
    }

    public String getCourse() {
        return course;
    }

    public String getExamMark() {
        return examMark;
    }

    public String getFinalMark() {
        return finalMark;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getTutAttendance() {
        return tutAttendance;
    }

    public String getYearMark() {
        return yearMark;
    }

}
