module com.xyz.mpeg2mp4 {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.xyz.mpeg2mp4 to javafx.fxml;
  exports com.xyz.mpeg2mp4;
}