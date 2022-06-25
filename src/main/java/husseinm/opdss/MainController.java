package husseinm.opdss;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private JFXButton btn_new;
    @FXML
    private ListView<ProjectModel> listView;
    @FXML
    private VBox page_one;

    private ConnectionUtil connection;
    ObservableList<ProjectModel> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//    if (!checkDbExists()){
//        createDb();
//    }
        connection = new ConnectionUtil();

        fillList();
    }

    private void createDb() {
        File file = new File(System.getProperty("user.home") + File.separator + "OPDSS", "opdss.sqlite");
        file.mkdir();
    }

    private boolean checkDbExists() {
        File file = new File(System.getProperty("user.home") + File.separator + "OPDSS", "opdss.sqlite");
        return file.exists();
    }

    private void fillList() {
        list = FXCollections.observableList(connection.getProjects());
        listView.setCellFactory(param -> new LabelCell<>());
        listView.setItems(list);

        listView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2 && (!listView.getSelectionModel().getSelectedItems().isEmpty())) {
                openProject(listView.getSelectionModel().getSelectedItem());
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                new Task() {
                    @Override
                    protected Object call() throws Exception {
                        listView.setItems(setupSearchField(newValue));
                        return null;
                    }
                }.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        listView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()== KeyCode.DELETE&&listView.getSelectionModel().getSelectedItem()!=null)
                showDeleteAlert(listView.getSelectionModel().getSelectedItem());
        });
    }

    private void showDeleteAlert(ProjectModel selectedItem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you delete this Project?");
        alert.setTitle("Confirmation");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            connection.deletePipes(selectedItem.getId());
            connection.deleteGraph(selectedItem.getId());
            connection.deletePoints(selectedItem.getId());
            connection.deleteProject(selectedItem.getId());
            fillList();
        }
    }

    public void onNewClicked(ActionEvent event) {
        try {
            connection.closeDb();
            Stage dialogStage = (Stage) page_one.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("intro.fxml"));
            Parent parent = loader.load();
            dialogStage.setTitle("OPDSS - New Project ");
            dialogStage.setResizable(false);
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProject(ProjectModel model) {
        try {
            connection.closeDb();
            Stage dialogStage = (Stage) page_one.getScene().getWindow();
            dialogStage.setResizable(true);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main_project.fxml"));
            Parent parent = loader.load();
            MainProjectController controller = loader.getController();
            controller.setProject_id(model.getId());
            dialogStage.setTitle("OPDSS - " + model.getName());
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
            dialogStage.setMaximized(true);
            connection.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Select Directory");
    }

    @FXML
    public void showDialog() {
        try {
            Stage dialogStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog.fxml"));
            VBox root = loader.load();
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            DialogController controller = loader.getController();
            controller.setStage(dialogStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            fillList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<ProjectModel> setupSearchField(String key_val) {
        ObservableList<ProjectModel> temp = FXCollections.observableArrayList();
        String key = key_val.toLowerCase();

        for (ProjectModel model : list) {
            try {
                if (model.getName().toLowerCase().contains(key)
                || model.getCreated_at().contains(key))
                    temp.add(model);
            } catch (Exception e) {
                //    e.printStackTrace();
            }
        }

        return temp;

    }

    public static class LabelCell<S> extends JFXListCell<ProjectModel> {
        //final ImageView imageView = new ImageView();

        @Override
        protected void updateItem(ProjectModel item, boolean empty) {
            super.updateItem(item, empty);

            // label.getStylesheets().add(getClass().getResource("/resources/styles/option.css").toExternalForm());

            if (empty || item == null) {
                //  label.setText(null);
                setText(null);
                setGraphic(null);
                return;
            }
            Label label = new Label();
            Label label_created = new Label();
            Label f_label = new Label();
            BorderPane hBox = new BorderPane();
            VBox vBox = new VBox();
            vBox.setPrefWidth(25.0);
            vBox.setPrefHeight(25.0);
            vBox.getChildren().add(f_label);
            vBox.setAlignment(Pos.CENTER);


            label.setText(item.getName());
            label_created.setText(item.getCreated_at());
            f_label.setText(String.valueOf(item.getName().charAt(0)).toUpperCase(Locale.ROOT));
            label.setPadding(new Insets(5, 5, 5, 5));

            f_label.setStyle("-fx-font-family: Berlin Sans FB; -fx-font-size: 20; -fx-text-fill: white;");
            vBox.setStyle("-fx-background-color: rgb(" + item.getRgb() + "); -fx-background-radius: 5");
            label.setStyle("-fx-font-family: Calibri; -fx-font-size: 16");
            label_created.setStyle("-fx-font-family: Calibri; -fx-font-size: 14;");

            HBox box = new HBox();
            box.getChildren().addAll(vBox, label);
            hBox.setLeft(box);
            hBox.setRight(label_created);

            setText("");
            setGraphic(hBox);
        }

        public static int getRandomColor() {
            SecureRandom random = new SecureRandom();

            return random.nextInt(255);
        }
    }
}