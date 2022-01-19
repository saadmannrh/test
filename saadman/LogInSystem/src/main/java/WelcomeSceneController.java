import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class WelcomeSceneController implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    Label homeLbl;
    @FXML
    Label profileLbl;
    @FXML
    Label inboxLbl;
    @FXML
    Label usernameLbl;
    @FXML
    ImageView profilePictureImageView;

    ResultSet rs;

    @FXML
    void setUpProfileLbl() throws IOException, SQLException {
        Main main = new Main();
        Statement st = Main.connect().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM userinfo where username='"+LoginSceneController.loggedInUser+"'");
        if(rs.next())
        {
            main.setScene("profileScene.fxml");
        }
        else main.setScene("updateProfileScene.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLbl.setText(LoginSceneController.loggedInUser);
        setProfilePicture();
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
            st.close();
            rs.close();
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
