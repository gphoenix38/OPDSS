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
import java.util.Arrays;
import java.util.ResourceBundle;

public class VariablesController implements Initializable {

    private int project_id;
    private Stage stage;

    @FXML
    private Label lbl_title_project_name;
    @FXML
    private VBox vBox_project;
    @FXML
    private TextField tf_project;
    @FXML
    private Label lbl_title_manning;
    @FXML
    private VBox vBox_manning;
    @FXML
    private TextField tf_manning;
    @FXML
    private Label lbl_title_roughness;
    @FXML
    private VBox vBox_roughness;
    @FXML
    private TextField tf_roughness;
    @FXML
    private Label lbl_title_nominal;
    @FXML
    private VBox vBox_nominal;
    @FXML
    private TextField tf_nominal;
    @FXML
    private Label lbl_title_minimum;
    @FXML
    private VBox vBox_minimum;
    @FXML
    private TextField tf_minimum;
    @FXML
    private Label lbl_title_excavation;
    @FXML
    private VBox vBox_excavation;
    @FXML
    private TextField tf_excavation;
    @FXML
    private Label lbl_title_backfilling;
    @FXML
    private VBox vBox_backfilling;
    @FXML
    private TextField tf_backfilling;
    @FXML
    private Label lbl_title_material;
    @FXML
    private VBox vBox_material;
    @FXML
    private TextField tf_material;
    @FXML
    private Label lbl_error;

    private Label[] labels;
    private VBox[] vBoxes;
    private TextField[] textFields;
    private Boolean[] submit;

    private ConnectionUtil connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit = new Boolean[]{false, false, false, false, false, false, false, false};

        labels = new Label[]{
                lbl_title_project_name, lbl_title_manning, lbl_title_roughness, lbl_title_nominal,
                lbl_title_minimum, lbl_title_excavation, lbl_title_backfilling, lbl_title_material
        };

        vBoxes = new VBox[]{
                vBox_project, vBox_manning, vBox_roughness, vBox_nominal, vBox_minimum, vBox_excavation,
                vBox_backfilling, vBox_material
        };

        textFields = new TextField[]{
                tf_project, tf_manning, tf_roughness, tf_nominal, tf_minimum, tf_excavation, tf_backfilling,
                tf_material
        };

        setListener();
    }

    public void setListener() {
        tf_project.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(0, newValue));
        tf_manning.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(1, newValue));
        tf_roughness.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(2, newValue));
        tf_nominal.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(3, newValue));
        tf_minimum.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(4, newValue));
        tf_excavation.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(5, newValue));
        tf_backfilling.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(6, newValue));
        tf_material.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused(7, newValue));
    }

    private void setFocused(int position, boolean focused) {

        if (!textFields[position].getText().isEmpty() && !focused) {
            validate(position);
        }

        for (int i = 0; i < textFields.length; i++) {
            vBoxes[i].getStyleClass().clear();
            labels[i].getStyleClass().clear();
            if (position == i && focused) {
                vBoxes[i].getStyleClass().add("vbox-focused");
                labels[i].getStyleClass().add("title-focused");
            } else if (submit[i]) {
                vBoxes[i].getStyleClass().add("vbox-error");
                labels[i].getStyleClass().add("title");
            } else {
                vBoxes[i].getStyleClass().add("vbox");
                labels[i].getStyleClass().add("title");
            }
        }
    }

    public void setProject_id(int project_id, ConnectionUtil connection) {
        this.project_id = project_id;
        this.connection = connection;
        fillFields();
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            MainProjectController.result.set(0);
        });
    }

    private void fillFields() {
        ProjectModel model = connection.getProject(project_id);
        tf_project.setText(model.getName());
        tf_manning.setText(String.valueOf(model.getManning()));
        tf_roughness.setText(String.valueOf(model.getRoughness()));
        tf_nominal.setText(String.valueOf(model.getNominal()));
        tf_minimum.setText(String.valueOf(model.getMinimum()));
        tf_excavation.setText(String.valueOf(model.getExcavation()));
        tf_backfilling.setText(String.valueOf(model.getBack_filling()));
        tf_material.setText(String.valueOf(model.getMaterial()));
    }

    private void validate(int position) {
        ProjectModel model = connection.getProject(project_id);
        if (position == 0) {
            if (!model.getName().equalsIgnoreCase(tf_project.getText()) &&
                    connection.projectExists(textFields[position].getText().toLowerCase())) {
                lbl_error.setText("Project with same name already exists");
                submit[position] = true;
                showToast();
            } else {
                submit[position] = false;
            }
        }

        if (position == 1 | position == 2) {
            if (!ValidateInput.validateDecimal(textFields[position].getText())) {
                lbl_error.setText("Only decimal number is allowed");
                submit[position] = true;
                showToast();
            } else if (Double.parseDouble(textFields[position].getText()) >= 1.0) {
                lbl_error.setText("Enter a decimal value less than 1.0");
                submit[position] = true;
                showToast();
            } else
                submit[position] = false;
        }

        if (position > 2) {
            if (!ValidateInput.validateDecimal(textFields[position].getText())) {
                lbl_error.setText("Only decimal number is allowed");
                submit[position] = true;
                showToast();
            } else
                submit[position] = false;
        }
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

    public void onSave(ActionEvent event) {
        validateForEmpty();

        if (Arrays.stream(submit).anyMatch(aBoolean -> aBoolean))
            return;

        boolean result = connection.updateProject(new ProjectModel(
                0,
                tf_project.getText(),
                "",
                "",
                Double.parseDouble(tf_manning.getText()),
                Double.parseDouble(tf_roughness.getText()),
                Double.parseDouble(tf_nominal.getText()),
                Double.parseDouble(tf_minimum.getText()),
                Double.parseDouble(tf_excavation.getText()),
                Double.parseDouble(tf_backfilling.getText()),
                Double.parseDouble(tf_material.getText()),
                0.0), project_id
        );

        MainProjectController.result.set(result ? 1 : -1);
        stage.close();
        connection.closeDb();
    }

    private void validateForEmpty() {
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().isEmpty()) {
                submit[i] = true;
                lbl_error.setText("Empty fields are not allowed");
                showToast();
                vBoxes[i].getStyleClass().add("vbox-error");
                labels[i].getStyleClass().add("title");
            }
        }
    }
}
