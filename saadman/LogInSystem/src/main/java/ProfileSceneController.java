import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.plaf.nimbus.State;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProfileSceneController implements Initializable {
    @FXML
    ImageView backBtnImg;
    Main main = new Main();
    @FXML
    ImageView profilePictureImageView;
    @FXML
    Label fname;
    @FXML
    Label lname;
    @FXML
    Label age;
    @FXML
    Label address;
    @FXML
    Label clg;
    @FXML
    Label usernameLbl;
    @FXML
    Button editProfileBtn;
    @FXML
    Button changeProfilePictureBtn;

    ResultSet rs;

    @FXML
    void setUpBackBtn() throws IOException {
        main.setScene("welcomeScene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLbl.setText(LoginSceneController.loggedInUser);
        try {
            setprofile();
            setProfilePicture();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    void setprofile() throws SQLException {

        Statement st = Main.connect().createStatement();
        String  q = "SELECT * FROM userinfo WHERE username='"+LoginSceneController.loggedInUser+"'";
        ResultSet rs = st.executeQuery(q);
    while (rs.next()) {
        fname.setText(rs.getString(2));
        lname.setText(rs.getString(3));
        age.setText(rs.getString(4));
        address.setText(rs.getString(5));
        clg.setText(rs.getString(6));
    }
    }
    @FXML
    void setChangeProfilePictureBtn() throws SQLException, FileNotFoundException {
        Statement st = Main.connect().createStatement();
        st.executeQuery("SELECT * FROM userProfilePictures WHERE username='"+LoginSceneController.loggedInUser+"'");
        if(rs.next())
        {
            st.execute("DELETE FROM userProfilePictures WHERE username='"+LoginSceneController.loggedInUser+"'");
        }
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
    @FXML
    void setEditProfileBtn() throws SQLException, IOException {
            Main main = new Main();
            main.setScene("editProfileScene.fxml");
    }
    public void setProfilePicture()
    {
        try {

            Statement st = Main.connect().createStatement();
            rs = st.executeQuery("SELECT pic FROM userProfilePictures WHERE username='"+LoginSceneController.loggedInUser+"'");

            BufferedImage img = null;
            while (rs.next())
            {
                Blob blob = rs.getBlob(1);
                InputStream is = blob.getBinaryStream();
                img = ImageIO.read(is);
            }
            if(img==null)return;
            Image pic = SwingFXUtils.toFXImage(img,null);
            profilePictureImageView.setImage(pic);
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
