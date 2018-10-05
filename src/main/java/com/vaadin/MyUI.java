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

    @Override
    protected void init(VaadinRequest vaadinRequest)  {

        final VerticalLayout loginPage = new VerticalLayout();
        loginPage.setResponsive(true);
        HomePage homePage = new HomePage();
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

        signInButton.addClickListener(clickEvent -> {
           if(usernameField.isEmpty() || passwordField.isEmpty()){
               Notification.show("Invalid Login");
           }
           else{
               setContent(homePage);
               studentList = database.getStudents();
               homePage.title.setValue("Examiner's Board Meeting");

               grid.setResponsive(true);
               grid.setWidth("100%");
               grid.setHeight("100%");

               grid.setItems(studentList);

               grid.addColumn(Student::getId).setCaption("Student number");
               grid.addColumn(Student::getFirstName).setCaption("First name");
               grid.addColumn(Student::getlastName).setCaption("Last name");
               grid.addColumn(Student::getDegree).setCaption("Degree");
               grid.addColumn(Student::getYearOfStudy).setCaption("Year of Study");

               grid.addComponentColumn(this::createVoteButton);

               Button beginVoteButton = new Button("Begin Vote Session");
               beginVoteButton.setResponsive(true);
               beginVoteButton.setStyleName("primary");

               beginVoteButton.addClickListener(e -> {
                   grid.asSingleSelect().clear();
               });

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
               HorizontalLayout toolbar = new HorizontalLayout(filtering, beginVoteButton);
               toolbar.setResponsive(true);

               homePage.studentTable.addComponents(toolbar, grid);
           }
        });

        homePage.logout.addClickListener(clickEvent -> {
            setContent(loginPage);
        });

        setContent(loginPage);
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
}
