package husseinm.opdss;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DesignDischargeController implements Initializable {

    private int project_id;
    private Stage stage;

    @FXML
    private VBox vBox_discharge;
    @FXML
    private Label lbl_discharge;
    @FXML
    private TextField tf_discharge;
    @FXML
    private Label lbl_error;

    private ProjectModel model;
    private ConnectionUtil connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            MainProjectController.result.set(0);
        });
    }

    public void setProject_id(int project_id, ConnectionUtil connection) {
        this.project_id = project_id;
        this.connection = connection;
        model = connection.getProject(project_id);

        tf_discharge.setText(String.valueOf(model.getDesign_discharge()));
    }

    public void onSave() {
        if (tf_discharge.getText().isEmpty()) {
            lbl_error.setText("Field can't be empty");
            showToast();
            return;
        }else if (!ValidateInput.validateDecimal(tf_discharge.getText())){
            lbl_error.setText("Only decimal number is allowed");
            showToast();
            return;
        }

        boolean result = connection.updateDesignDischarge(tf_discharge.getText(), project_id);
        MainProjectController.result.set(result?1:-1);
        stage.close();
    }

    private void showToast() {
        Model model = new Model();
        new Thread((Runnable) model.worker).start();
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
        stateProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.RUNNING) {
                lbl_error.setVisible(true);
            } else {
                lbl_error.setVisible(false);
            }
        });
    }

    public void onCancel(ActionEvent event) {
        MainProjectController.result.set(0);
        stage.close();
    }
}
