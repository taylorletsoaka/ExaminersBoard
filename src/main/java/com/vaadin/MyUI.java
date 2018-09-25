package com.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        final VerticalLayout loginPage = new VerticalLayout();
        HomePage homePage = new HomePage();

        int index = homePage.getComponentIndex(homePage.logout);
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button signInButton = new Button("Sign In");
        signInButton.setStyleName("primary");
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
           }
        });

        homePage.logout.addClickListener(clickEvent -> {
            setContent(loginPage);
        });

        setContent(loginPage);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
