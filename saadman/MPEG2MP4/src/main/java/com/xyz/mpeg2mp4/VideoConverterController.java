package com.xyz.mpeg2mp4;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class VideoConverterController {

  @FXML
  private AnchorPane pane;
  @FXML
  private Label primaryLbl;
  @FXML
  private Button avi2Mp4Btn;
  @FXML
  private Button mpeg2Mp4Btn;
  @FXML
  private Button chooseFileBtn;
  @FXML
  private Button chooseOutBtn;
  @FXML
  private Label selectedFileLabel;

  @FXML
  private Label selectedOutputLabel;
  @FXML
  private Label convertingLbl;
  private String path;
  private String out;
  private String fileName;

  @FXML
  protected void onAvi2Mp4Btn(){

  }
  @FXML
  protected void onMpeg2Mp4Btn(){

  }
  @FXML
  protected void onChooseFileBtn(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choose video file to convert");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Video Files,","*.avi","*.mpeg"));

    Stage stage = new Stage();
    File selectedFile = fileChooser.showOpenDialog(stage);

    if(selectedFile!=null){
      System.out.println("Selected file name -> "+selectedFile.getName());
      selectedFileLabel.setText(selectedFileLabel.getText() + " "+selectedFile.getName());
      path=selectedFile.getAbsolutePath();
      fileName = selectedFile.getName();
    }
  }
  @FXML
  public void onChooseOutBtn(){
    DirectoryChooser folderChooser = new DirectoryChooser();
    folderChooser.setTitle("Choose Location to store converted video");

    Stage stage = new Stage();
    File selectedFile = folderChooser.showDialog(stage);


    if(selectedFile!=null){
      out=selectedFile.getAbsolutePath();
      selectedOutputLabel.setText(selectedOutputLabel.getText()+" "+ selectedFile.getName());
    }
  }
  @FXML
  protected void onConvertBtn(){

    convertingLbl.setVisible(true);

    if(path!=null && out!=null){
      Convert2Mp4.convert(path,out,fileName);
    }
    convertingLbl.setText("Conversion completed.");
  }

}