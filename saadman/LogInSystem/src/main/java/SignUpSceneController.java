import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpSceneController {
    ResultSet rs;
    Alert alert = new Alert(Alert.AlertType.NONE);
    @FXML
    public PasswordField passwordTextFiled;
    @FXML
    public TextField userNameTextField;
    @FXML
    Button signUpBtn;
    @FXML
    TextField emailTextField;
    @FXML
    Button logInBtn;
    @FXML
    void setLogInBtn() throws IOException {
        Main main = new Main();
        main.setScene("LoginScene.fxml");
    }
    @FXML
    void setSignUpBtn() throws SQLException {
        if(userNameTextField.getText().equals("") || passwordTextFiled.getText().equals("") || emailTextField.getText().equals(""))
        {

            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Field cannot be empty");
            alert.show();
        }
        else
        {
            Statement st = Main.connect().createStatement();
            rs = st.executeQuery("SELECT * FROM users WHERE username='"+userNameTextField.getText()+"'");
            if(rs.next())
            {

                alert.setContentText("Username already taken.");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                userNameTextField.clear();
                alert.show();
            }
            else
            {
                Main.addData(userNameTextField.getText(),emailTextField.getText(),passwordTextFiled.getText());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Account Created.");
                alert.show();
            }
        }
    }

}
