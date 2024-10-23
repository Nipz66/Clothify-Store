package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginDashboardControllerForm implements Initializable {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button userLoginButton;

    @FXML
    private Button adminLoginButton;

    private void loadScene(String fxmlFile) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());


            Stage stage = (Stage) userLoginButton.getScene().getWindow();
            stage.setScene(scene);
            // Update the window title using the file name, formatted properly
            stage.setTitle(fxmlFile.replace(".fxml", "").replace("_", " ").toUpperCase());
            stage.show();
        } catch (Exception e) {
            // Print the exception stack trace if there's an error loading the scene
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load and set the logo image in the ImageView component
        Image logo = new Image(getClass().getResourceAsStream("/Images/Black_and_White_Illustrative_Clothing_Store_Logo__1_-removebg-preview.png"));
        logoImageView.setImage(logo);

        // Set up action listeners to load the appropriate scenes when the buttons are clicked
        userLoginButton.setOnAction(event -> loadScene("/view/user_login.fxml"));
        adminLoginButton.setOnAction(event -> loadScene("admin_login.fxml"));
    }
}
