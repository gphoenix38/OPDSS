package husseinm.opdss;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    @FXML
    private TextField textField;
    @FXML
    private JFXButton button_ok;
    @FXML
    private JFXButton button_cancel;
    @FXML
    private Label lbl_error;

    private Stage stage;

    private ConnectionUtil connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = new ConnectionUtil();
        button_cancel.setOnAction(event -> {
            if (stage!=null) stage.close();
        });
        button_ok.setOnAction(event -> validate());
    }

    private void validate() {
        if (textField.getText().isEmpty()){
            lbl_error.setText("*Field can't be empty");
            return;
        }else if (connection.projectExists(textField.getText())){
            lbl_error.setText("*Project with same name exists");
            return;
        }

        if (connection.addProject(textField.getText())){
            if (stage!=null) stage.close();
        }else{
            lbl_error.setText("*Some error has been occurred while inserting into database");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
