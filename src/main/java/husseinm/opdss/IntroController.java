package husseinm.opdss;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class IntroController implements Initializable {

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
    @FXML
    private JFXButton btn_next;
    @FXML
    private JFXButton btn_prev;
    @FXML
    private VBox phase_one;
    @FXML
    private VBox phase_two;
    @FXML
    private VBox phase_three;
    @FXML
    private TextField tf_diameter;
    @FXML
    private VBox vBox_diameter;
    @FXML
    private Label lbl_diameter;
    @FXML
    private TextField tf_price;
    @FXML
    private VBox vBox_discharge;
    @FXML
    private Label lbl_discharge;
    @FXML
    private TextField tf_discharge;
    @FXML
    private VBox vBox_price;
    @FXML
    private Label lbl_price;
    @FXML
    private TableView<ProjectModel> tableView;
    @FXML
    private TableColumn<ProjectModel, Double> col_diameter;
    @FXML
    private TableColumn<ProjectModel, Double> col_price;
    @FXML
    private JFXButton btn_add;

    private Label[] labels;
    private VBox[] vBoxes;
    private TextField[] textFields;
    private Boolean[] submit;

    private Label[] labels_p2;
    private VBox[] vBoxes_p2;
    private TextField[] textFields_p2;
    private Boolean[] submit_p2;

    private ConnectionUtil connection;

    private ArrayList<ProjectModel> list = new ArrayList<>();

    private int page = 1;

    private boolean edit = false;
    private int project_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connection = new ConnectionUtil();

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

        labels_p2 = new Label[]{lbl_diameter, lbl_price};
        textFields_p2 = new TextField[]{tf_diameter, tf_price};
        vBoxes_p2 = new VBox[]{vBox_diameter, vBox_price};
        submit_p2 = new Boolean[]{false, false};

        setListener();

        initTable();
    }



    private void initTable() {
        PropertyValueFactory<ProjectModel, Double> diameterCellValueFactory = new PropertyValueFactory<>("diameter");
        col_diameter.setCellValueFactory(diameterCellValueFactory);

        PropertyValueFactory<ProjectModel, Double> priceCellValueFactory = new PropertyValueFactory<>("price");
        col_price.setCellValueFactory(priceCellValueFactory);

        tableView.setItems(FXCollections.observableList(list));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        });
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

    private void setFocused2(int position, boolean focused) {

        for (int i = 0; i < textFields_p2.length; i++) {
            vBoxes_p2[i].getStyleClass().clear();
            labels_p2[i].getStyleClass().clear();
            if (position == i && focused) {
                vBoxes_p2[i].getStyleClass().add("vbox-focused");
                labels_p2[i].getStyleClass().add("title-focused");
            } else if (submit_p2[i]) {
                vBoxes_p2[i].getStyleClass().add("vbox-error");
                labels_p2[i].getStyleClass().add("title");
            } else {
                vBoxes_p2[i].getStyleClass().add("vbox");
                labels_p2[i].getStyleClass().add("title");
            }
        }
    }

    public void setEdit(Boolean edit, int project_id){
        this.edit = edit;
        this.project_id = project_id;
        fillData();
    }

    private void fillData() {

    }


    private void validate(int position) {
        if (position == 0) {
            if (connection.projectExists(textFields[position].getText().toLowerCase())) {
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

    public void onNext(ActionEvent event) {
        if (page == 1) {
            validateForEmpty();

            if (Arrays.stream(submit).anyMatch(aBoolean -> aBoolean))
                return;

            phase_one.setVisible(false);
            phase_two.setVisible(true);

            initPhaseTwo();
            page++;
        } else if (page == 2) {
            if (list.size() == 0) {
                lbl_error.setText("Please enter available pipe diameter");
                showToast();
                return;
            }

            phase_two.setVisible(false);
            phase_three.setVisible(true);

            initPhaseThree();

            page++;
        } else if (page == 3) {
            if (tf_discharge.getText().isEmpty()) {
                lbl_error.setText("Field can't be empty");
                showToast();
                return;
            } else if (!ValidateInput.validateDecimal(tf_discharge.getText())) {
                lbl_error.setText("Only decimal number are allowed");
                showToast();
                return;
            }

            saveIntoDb();
        }
    }

    private void saveIntoDb() {

        connection.addProject(new ProjectModel(
                0,
                tf_project.getText(),
                "",
                "",
                Double.parseDouble(tf_manning.getText()),
                Double.parseDouble(tf_roughness.getText()),
                Double.parseDouble(tf_nominal.getText()),
                Double.parseDouble(tf_nominal.getText()),
                Double.parseDouble(tf_excavation.getText()),
                Double.parseDouble(tf_backfilling.getText()),
                Double.parseDouble(tf_material.getText()),
                Double.parseDouble(tf_discharge.getText())
        ));

        int project_id = connection.getProject(tf_project.getText());

        for (ProjectModel model: list){
            connection.addPipes(model, project_id);
        }

        lbl_error.setText("Project Saved Successfully!");
        lbl_error.setStyle("-fx-background-color: green; -fx-text-fill: white;" +
                "-fx-background-radius: 5; -fx-opacity: 0.6");
        showToast();
       onBack();
    }

    private void initPhaseThree() {

        tf_discharge.focusedProperty().addListener((observable, oldValue, newValue) -> {
            vBox_discharge.getStyleClass().clear();
            lbl_discharge.getStyleClass().clear();
            if (newValue) {
                vBox_discharge.getStyleClass().add("vbox-focused");
                lbl_discharge.getStyleClass().add("title-focused");
            } else {
                vBox_discharge.getStyleClass().add("vbox");
                lbl_discharge.getStyleClass().add("title");
            }
        });
    }

    private void initPhaseTwo() {
        tf_diameter.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused2(0, newValue));

        tf_price.focusedProperty().addListener((observable, oldValue, newValue) -> setFocused2(1, newValue));


        tableView.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.DELETE){
                if (tableView.getSelectionModel().getSelectedItem()!=null){
                    list.remove(tableView.getSelectionModel().getSelectedItem());
                }
            }
        });
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

    public void onPrevious(ActionEvent event) {
        if (page == 3) {
            phase_three.setVisible(false);
            phase_two.setVisible(true);
            phase_one.setVisible(false);
            page--;
        } else if (page == 2) {
            phase_two.setVisible(false);
            phase_one.setVisible(true);
            phase_three.setVisible(false);
            page--;
        } else if (page == 1) {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit?");
        alert.setTitle("Confirmation");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            onBack();
        }
    }

    private void onBack() {
        try {
            connection.closeDb();
            Stage dialogStage = (Stage) tableView.getScene().getWindow();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"));
            Parent parent = loader.load();
            dialogStage.setTitle("OPDSS");
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProject() {
        try {
            Stage dialogStage = (Stage) tableView.getScene().getWindow();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main_project.fxml"));
            Parent parent = loader.load();
            MainProjectController controller =loader.getController();
            controller.setProject_id(connection.getProject(tf_project.getText()));
            dialogStage.setTitle("OPDSS - "+tf_project.getText());
            Scene scene = new Scene(parent);
            dialogStage.setResizable(true);
            dialogStage.setMaximized(true);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showToast() {
        Model model = new Model();
        new Thread((Runnable) model.worker).start();
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
        stateProperty.addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.RUNNING) {
                    lbl_error.setVisible(true);
                } else {
                    lbl_error.setVisible(false);
                }
            }
        });
    }

    public void onAddClicked(ActionEvent event) {
        if (tf_diameter.getText().isEmpty() | tf_price.getText().isEmpty()) {
            lbl_error.setText("Please fill required field(s)");
            showToast();
            return;
        } else if (!ValidateInput.validateDecimal(tf_diameter.getText()) |
                !ValidateInput.validateDecimal(tf_price.getText())) {
            lbl_error.setText("Only decimal number is allowed");
            showToast();
            return;
        }

        list.add(new ProjectModel(0, Double.parseDouble(tf_diameter.getText()), Double.parseDouble(tf_price.getText())));
        tableView.refresh();
    }
}
