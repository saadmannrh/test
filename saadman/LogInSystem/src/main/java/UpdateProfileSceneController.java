import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateProfileSceneController implements Initializable {
    @FXML
    Label usernameLbl;
    @FXML
    Button updateProfileBtn;
    @FXML
    Button updateBtn;
    @FXML
    TextField fnameTextField;
    @FXML
    TextField lnameTextField;
    @FXML
    TextField ageTextField;
    @FXML
    TextField collegeTextField;
    @FXML
    TextField addressTextField;
    @FXML
    Label nothingToShowHereLbl;
    @FXML
    ImageView backBtnImg;
    @FXML
    Button uploadProfilePictureBtn;

    Main main = new Main();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLbl.setText(LoginSceneController.loggedInUser);
    }
    @FXML
    void setUpBackBtn() throws IOException {
        main.setScene("welcomeScene.fxml");

    }
    @FXML
    void setUpdateProfileBtn()
    {

        updateProfileBtn.setVisible(false);
        fnameTextField.setVisible(true);
        lnameTextField.setVisible(true);
        ageTextField.setVisible(true);
        collegeTextField.setVisible(true);
        updateBtn.setVisible(true);
        addressTextField.setVisible(true);
        nothingToShowHereLbl.setVisible(false);
        uploadProfilePictureBtn.setVisible(false);

    }
    @FXML
    void setUpdateBtn() throws SQLException {
        String userName= LoginSceneController.loggedInUser;
        String fname = fnameTextField.getText();
        String lname = lnameTextField.getText();
        String age  = ageTextField.getText();
        String address = addressTextField.getText();
        String collge = collegeTextField.getText();
        main.addUserInfo(userName,fname,lname,age,address,collge);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Updated Profile. Please Refresh. ");
        alert.show();

    }
    @FXML
    void setUploadProfilePictureBtn() throws FileNotFoundException, SQLException {
        FileChooser fc = new FileChooser();
        InputStream fin = new FileInputStream(fc.showOpenDialog(Main.stg).getAbsoluteFile());
        PreparedStatement ps = Main.connect().prepareStatement("INSERT INTO userProfilePictures(username,pic) VALUES (?,?)");
        ps.setString(1,LoginSceneController.loggedInUser);
        ps.setBinaryStream(2,fin);
        ps.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Profile Picture Uploaded.");
        alert.show();
    }

}
