import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProfileController {
    @FXML
    Button doneBtn;
    @FXML
    TextField fname;
    @FXML
    TextField lname;
    @FXML
    TextField age;
    @FXML
    TextField colname;
    @FXML
    TextField address;
    @FXML
    ImageView backBtn;

    @FXML
    void setDoneBtn() throws SQLException {
        String fnameTxt = fname.getText();
        String lnameTxt = lname.getText();
        String ageTxt = age.getText();
        String colnameTxt = colname.getText();
        String addressTxt = address.getText();

        Statement st = Main.connect().createStatement();
        st.execute("DELETE FROM userinfo WHERE username='"+LoginSceneController.loggedInUser+"'");
        Main main = new Main();
        main.addUserInfo(LoginSceneController.loggedInUser,fnameTxt,lnameTxt,ageTxt,addressTxt,colnameTxt);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Profile Updated.");
        alert.show();
    }
    @FXML
    void setBackBtn() throws IOException {
        Main main = new Main();
        main.setScene("welcomeScene.fxml");
    }
}
