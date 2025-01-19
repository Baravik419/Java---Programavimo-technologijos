package courseWork.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import courseWork.Model.User;
import courseWork.StartGUI;
import courseWork.hibernateControllers.GenericHibernate;
import courseWork.hibernateControllers.UserHibernate;

import java.io.IOException;

public class Login {
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField passwordTextField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bookExchange");
    GenericHibernate hibernate = new GenericHibernate(entityManagerFactory);
    UserHibernate userHib = new UserHibernate(entityManagerFactory);

    public void validateAndConnect() throws IOException {
        User user = userHib.getEntityByLogin(loginTextField.getText(), passwordTextField.getText());
        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/Main.fxml"));
            Parent parent = fxmlLoader.load();

            Main main = fxmlLoader.getController();
            main.setData(entityManagerFactory, user);

            Scene scene = new Scene(parent);
            var stage = (Stage) loginTextField.getScene().getWindow();
            stage.setTitle("Book exchange platform");
            stage.setScene(scene);
            stage.show();

        }
    }

    public void registerNewUser() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGUI.class.getResource("/fxmls/Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book exchange platform");
        stage.setScene(scene);
        stage.show();
    }

}
