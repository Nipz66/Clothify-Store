package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label forgetPasswordLabel;

    @FXML
    private Label signUpLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        forgetPasswordLabel.setOnMouseClicked(event -> handleForgetPassword(event));
        signUpLabel.setOnMouseClicked(event -> goToSignUp(event));
    }

    // Handle user login
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both email and password.");
            return;
        }

        // Check credentials with the database
        if (authenticateUser(username, password)) {
            System.out.println("Login successful");
            messageLabel.setText("Login successful");
            // Redirect to user dashboard or main application scene
            loadScene("user_dashboard.fxml");
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }

    // Handle "Forget Password" action
    private void handleForgetPassword(MouseEvent event) {
        // Load the password reset page
        System.out.println("Redirecting to password reset page...");
        loadScene("reset_password.fxml");
    }

    // Handle "Sign Up" action
    private void goToSignUp(MouseEvent event) {
        // Redirect to the sign-up page
        System.out.println("Redirecting to the sign-up page...");
        loadScene("signup.fxml");
    }

    // Load a different scene
    private void loadScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(fxmlFile.replace(".fxml", "").replace("_", " ").toUpperCase());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Authenticate user by checking the database
    private boolean authenticateUser(String username, String password) {
        boolean isAuthenticated = false;
        String url = "jdbc:mysql://localhost:3306/pos_system";
        String dbUser = "root";
        String dbPassword = "password";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
}
