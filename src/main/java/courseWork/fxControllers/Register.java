package courseWork.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import courseWork.Model.Client;
import courseWork.Model.User;
import courseWork.StartGUI;
import courseWork.Utils.FxUtils;
import courseWork.hibernateControllers.GenericHibernate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Register implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField surNameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public DatePicker birthDateDatePicker;
    @FXML
    public Button registerButton;
    @FXML
    public Button clearButton;
    @FXML
    public TextField loginTextField;
    @FXML
    public TextField passwordTextField;


    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bookExchange");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void registerUser() throws IOException {
        Client client = new Client(loginTextField.getText(), passwordTextField.getText(), nameTextField.getText(), surNameTextField.getText(), addressTextField.getText(), birthDateDatePicker.getValue(), emailTextField.getText());
        if(client != null && (loginTextField.getText() != "" && passwordTextField.getText() != "")) {
            hibernate.create(client);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/Main.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Book Exchange Test");
            stage.setScene(scene);
            stage.showAndWait();
        }
        else {
            FxUtils.generateAlert(Alert.AlertType.ERROR, "Register error", "Empty fields, fill them!");
        }
    }

    public void clearFields()
    {
        nameTextField.clear();
        surNameTextField.clear();
        addressTextField.clear();
        emailTextField.clear();
        birthDateDatePicker.setValue(null);
        loginTextField.clear();
        passwordTextField.clear();
    }
}
