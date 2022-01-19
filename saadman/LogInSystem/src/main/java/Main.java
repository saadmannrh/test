import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;

public class Main extends Application {
    static Stage stg;
    @Override
    public void start(Stage primaryStage) throws Exception {
        if(!doesTableExist("users"))createTable();
        if(!doesTableExist("userinfo"))createuserinfoTable();
        if(!doesTableExist("userProfilePictures"))createProfilePictureTable();
        stg = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("SignUpScene2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stg.setScene(scene);
        stg.show();
    }
    public void setScene(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        stg.setScene(scene);
    }
    public static Connection connect() throws SQLException {
        final String JDBC_URL = "jdbc:derby:CreatingJavaDB;create=true";
        Connection conn = DriverManager.getConnection(JDBC_URL);
        if(conn==null)
        {
            System.out.println("Failed to connect. ");
        }
        return conn;
    }
    public static boolean doesTableExist(String tableName) throws SQLException {
        DatabaseMetaData databaseMetaData = connect().getMetaData();
        ResultSet rs = databaseMetaData.getTables(null,null,tableName.toUpperCase(Locale.ROOT),null);
        if(rs.next()) return true;
        else return false;
    }
    public static void createTable() throws SQLException {
        Statement st =connect().createStatement();
        st.execute("CREATE TABLE users(id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),username VARCHAR(255),email varchar(255),password VARCHAR(255))");
    }
    public static void addData(String username,String email,String password) throws SQLException {
        Statement st = connect().createStatement();
        String q = "INSERT INTO users(username,email,password) VALUES ('"+username+"','"+email+"','"+password+"')";
        st.execute(q);
    }
    public void addUserInfo(String userName,String fname,String lname,String age,String address,String college) throws SQLException {
        Statement st = connect().createStatement();
        if(!doesTableExist("userinfo"))
        {
            st.execute("CREATE TABLE userinfo(username VARCHAR(255),fname VARCHAR(255),lname VARCHAR (255),age VARCHAR(255),address VARCHAR(255),colleage VARCHAR(255))");
        }
        String q = "INSERT INTO userinfo(username,fname,lname,age,address,colleage) VALUES ('"+userName+"','"+fname+"','"+lname+"','"+age+"','"+address+"','"+college+"')";
        st.execute(q);
    }
    public void createuserinfoTable() throws SQLException {
        Statement st = connect().createStatement();
        st.execute("CREATE TABLE userinfo(username VARCHAR(255),fname VARCHAR(255),lname VARCHAR (255),age VARCHAR(255),address VARCHAR(255),colleage VARCHAR(255))");
    }
    public void createProfilePictureTable() throws SQLException {
        Statement st = connect().createStatement();
        st.execute("CREATE TABLE userProfilePictures(username VARCHAR(255),pic BLOB(16M))");
    }
    public void addProfilePicturesToDB()
    {

    }
    public static void main(String[] args) {
        launch(args);
    }
}
