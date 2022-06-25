package husseinm.opdss;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvaPipeController implements Initializable {

    private ConnectionUtil connectionUtil;
    private int project_id = 0;
    private Stage stage;

    @FXML
    private VBox toast;
    @FXML
    private Label lbl_error;
    @FXML
    private TableView<ProjectModel> tableView;
    @FXML
    private TableColumn<ProjectModel, Double> col_diameter;
    @FXML
    private TableColumn<ProjectModel, Double> col_price;
    @FXML
    private TextField tf_diameter;
    @FXML
    private VBox vBox_diameter;
    @FXML
    private Label lbl_diameter;
    @FXML
    private TextField tf_price;
    @FXML
    private VBox vBox_price;
    @FXML
    private Label lbl_price;
    @FXML
    private JFXButton undo;
    @FXML
    private JFXButton btn_add;

    private final String STYLE_ERROR = "-fx-background-color: red; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.6";
    private final String STYLE_SUCCESS = "-fx-background-color: #00c500; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.6";
    private final String STYLE_INFO = "-fx-background-color: #FF8A00FF; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.8";

    private final int TYPE_ERROR = 0;
    private final int TYPE_SUCCESS = 1;
    private final int TYPE_INFO = 2;

    private ArrayList<ProjectModel> list = new ArrayList<>();
    private ProjectModel selected_model;
    private int selected_index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        undo.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            stage.getScene().setCursor(Cursor.HAND);
        });

        undo.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            stage.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    private void initTable() {
        PropertyValueFactory<ProjectModel, Double> diameterCellValueFactory = new PropertyValueFactory<>("diameter");
        col_diameter.setCellValueFactory(diameterCellValueFactory);

        PropertyValueFactory<ProjectModel, Double> priceCellValueFactory = new PropertyValueFactory<>("price");
        col_price.setCellValueFactory(priceCellValueFactory);

        tableView.setItems(FXCollections.observableList(list));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                selected_model = tableView.getSelectionModel().getSelectedItem();
                selected_index = tableView.getSelectionModel().getSelectedIndex();
                tf_diameter.setText(String.valueOf(selected_model.getDiameter()));
                tf_price.setText(String.valueOf(selected_model.getPrice()));
                btn_add.setText("EDIT");
            }
        });

        tableView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DELETE) {
                list.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.refresh();
                lbl_error.setText("Diameter deleted");
                showToast(TYPE_INFO, true);
            }
        });

        tf_diameter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && tf_price.getText().isEmpty()) btn_add.setText("ADD");
        });

        tf_price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && tf_diameter.getText().isEmpty()) btn_add.setText("ADD");
        });
    }

    public void setProject_id(int project_id, ConnectionUtil connectionUtil) {
        this.project_id = project_id;
        this.connectionUtil = connectionUtil;
        list.addAll(connectionUtil.getProPipes(project_id));
        initTable();
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        this.stage.setOnCloseRequest(event -> {
            MainProjectController.result.set(0);
        });
    }

    public void onCancel(ActionEvent event) {
        MainProjectController.result.set(0);
        stage.close();
    }

    public void onSave(ActionEvent event) {
        connectionUtil.deletePipes(project_id);
        for (ProjectModel model : list)
            connectionUtil.addPipes(model, project_id);
        MainProjectController.result.set(1);
        stage.close();
    }

    private void showToast(int type, boolean showUndo) {

        Model model = new Model();
        model.setTotal(showUndo ? 100 : 200);
        undo.setVisible(showUndo);
        undo.setText(showUndo ? "Undo" : "");
        new Thread((Runnable) model.worker).start();
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
        stateProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.RUNNING) {
                switch (type) {
                    case TYPE_ERROR:
                        lbl_error.setStyle(STYLE_ERROR);
                        break;
                    case TYPE_INFO:
                        lbl_error.setStyle(STYLE_INFO);
                        break;
                    case TYPE_SUCCESS:
                        lbl_error.setStyle(STYLE_SUCCESS);
                        break;
                }
                lbl_error.setVisible(true);
                toast.toFront();
            } else {
                lbl_error.setVisible(false);
                toast.toBack();
            }
        });
    }

    public void onAddClicked(ActionEvent event) {
        if (tf_diameter.getText().isEmpty() | tf_price.getText().isEmpty()) {
            lbl_error.setText("Please fill required field(s)");
            showToast(TYPE_ERROR, false);
            return;
        } else if (!ValidateInput.validateDecimal(tf_diameter.getText()) |
                !ValidateInput.validateDecimal(tf_price.getText())) {
            lbl_error.setText("Only decimal number is allowed");
            showToast(TYPE_ERROR, false);
            return;
        }
        if (btn_add.getText().equalsIgnoreCase("edit") && selected_model != null) {
            list.remove(selected_model);
            list.add(selected_index, new ProjectModel(0, Double.parseDouble(tf_diameter.getText()),
                    Double.parseDouble(tf_price.getText())));
        } else {
            list.add(new ProjectModel(0, Double.parseDouble(tf_diameter.getText()),
                    Double.parseDouble(tf_price.getText())));
        }
        tableView.refresh();
        tf_diameter.setText("");
        tf_price.setText("");
        btn_add.setText("ADD");
    }
}
