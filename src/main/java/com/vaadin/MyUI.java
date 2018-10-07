package com.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    static ArrayList<Student> studentList = new ArrayList<>();
    static Database database = new Database();
    Grid<Student> grid = new Grid<>();
    Grid<Marks> marksContainer = new Grid<>();
    final VerticalLayout loginPage = new VerticalLayout();
    HomePage homePage = new HomePage();

    @Override
    protected void init(VaadinRequest vaadinRequest)  {
        onCreate();
    }

    private void onCreate() {

        loginPage.setResponsive(true);
        homePage.setResponsive(true);

        TextField usernameField = new TextField("Username");
        usernameField.setResponsive(true);
        usernameField.setIcon(VaadinIcons.USER);
        PasswordField passwordField = new PasswordField("Password");
        Button signInButton = new Button("Sign In");
        signInButton.setStyleName("primary");
        signInButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        loginPage.addComponents(usernameField, passwordField, signInButton);
        loginPage.setComponentAlignment(usernameField, Alignment.TOP_CENTER);
        loginPage.setComponentAlignment(passwordField, Alignment.TOP_CENTER);
        loginPage.setComponentAlignment(signInButton, Alignment.TOP_CENTER);
        setContent(loginPage);

        homePage.logout.addClickListener(clickEvent -> {
            setContent(loginPage);
        });

        signInButton.addClickListener(clickEvent -> {

            String username = usernameField.getValue();
            String password = passwordField.getValue();

            if(!database.validLogin(username, password)){
                Notification.show("Invalid Login");
            }
            else{
                displayAllStudents();
            }
        });
    }

    private void displayAllStudents() {

        homePage.studentTable.removeAllComponents();
        setContent(homePage);
        studentList = database.getStudents();
        homePage.title.setValue("Examiner's Board Meeting");
        grid.removeAllColumns();

        grid.setResponsive(true);
        grid.setWidth("100%");
        grid.setHeight("100%");

        grid.setItems(studentList);

        grid.addColumn(Student::getId).setCaption("Student number");
        grid.addColumn(Student::getFirstName).setCaption("First name");
        grid.addColumn(Student::getlastName).setCaption("Last name");
        grid.addColumn(Student::getDegree).setCaption("Degree");
        grid.addColumn(Student::getYearOfStudy).setCaption("Year of Study");
        homePage.studentTable.addComponent(grid);

        grid.addItemClickListener(itemClick -> {
            displayStudentsMarks(itemClick);
        });
    }

    private void displayStudentsMarks(Grid.ItemClick<Student> itemClick) {

        homePage.studentTable.removeAllComponents();

        Student student = itemClick.getItem();
        ArrayList<Marks> marks = database.getMarks(student);

        marksContainer.removeAllColumns();
        marksContainer.setResponsive(true);
        marksContainer.setWidth("100%");

        if(marks.size() > 0){
            marksContainer.setHeightByRows(marks.size());
        }
        else {
            marksContainer.setHeightByRows(1);
        }

        marksContainer.setItems(marks);

        marksContainer.addColumn(Marks::getCourse).setCaption("Course");
        marksContainer.addColumn(Marks::getYearMark).setCaption("Year Mark");
        marksContainer.addColumn(Marks::getExamMark).setCaption("Exam Mark");
        marksContainer.addColumn(Marks::getFinalMark).setCaption("Final Exam");
        marksContainer.addColumn(Marks::getOutcome).setCaption("Outcome");
        marksContainer.addColumn(Marks::getTutAttendance).setCaption("Tut Attendance");

        Button voteButton = new Button("Vote");
        voteButton.setResponsive(true);
        voteButton.setStyleName("primary");

        HorizontalLayout toolbar = new HorizontalLayout(voteButton);
        toolbar.setResponsive(true);

        homePage.studentTable.addComponents(toolbar, marksContainer);

        homePage.title.setValue(String.format("%s %s", student.getFirstName(), student.getlastName()));

        voteButton.addClickListener(clickEvent -> {
           voteSession(clickEvent, student);
        });

    }

    private void voteSession(Button.ClickEvent clickEvent, Student student) {

    }

    private Button createVoteButton(Student student) {
        Button button = new Button();
        button.setIcon(VaadinIcons.THUMBS_UP);
        return button;
    }

    private void voteFor(Student person) {
    }

    private Student updateList(String filterText) {
        List<Student> students = Arrays.asList();

        for (int i = 0; i < studentList.size(); i++){
            if(studentList.get(i).getFirstName().contains(filterText)){
                students.add(studentList.get(i));
            }
        }

        return students.get(0);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }













    /*
               grid.addComponentColumn(this::createVoteButton);
               beginVoteButton.addClickListener(e -> {
                   grid.asSingleSelect().clear();
               });

               /*
               TextField filterText = new TextField();
               filterText.setResponsive(true);
               filterText.setPlaceholder("filter by name...");
               filterText.addValueChangeListener(e -> updateList(filterText.getValue()));
               filterText.setValueChangeMode(ValueChangeMode.LAZY);

               Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
               clearFilterTextBtn.setResponsive(true);
               clearFilterTextBtn.setDescription("Clear the current filter");
               clearFilterTextBtn.addClickListener(e -> filterText.clear());

               CssLayout filtering = new CssLayout();
               filtering.setResponsive(true);
               filtering.addComponents(filterText, clearFilterTextBtn);
               filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
               */
}
