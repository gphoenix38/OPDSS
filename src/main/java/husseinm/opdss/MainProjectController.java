package husseinm.opdss;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainProjectController implements Initializable {

    private ConnectionUtil connection;

    @FXML
    private AnchorPane root;
    @FXML
    private Label lbl_error;
    @FXML
    private JFXButton btn_exit;
    @FXML
    private JFXButton btn_connect;
    @FXML
    private JFXButton btn_run;
    @FXML
    private SVGPath svg_connect;
    @FXML
    private JFXButton btn_show_details;

    @FXML
    private TableView<Graph2Model> tableView;
    @FXML
    private TableColumn<Graph2Model, String> col_pipe;
    @FXML
    private TableColumn<Graph2Model, String> col_comm;
    @FXML
    private TableColumn<Graph2Model, String> col_us;
    @FXML
    private TableColumn<Graph2Model, String> col_ds;
    @FXML
    private TableColumn<Graph2Model, String> col_len_sewer;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_ground_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_ava_pipes;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_final_velo;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_smin;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_mod_cost;

    @FXML
    private TableView<Graph2Model> tableView_detailed;
    @FXML
    private TableColumn<Graph2Model, String> col_dpipe;
    @FXML
    private TableColumn<Graph2Model, String> col_dcomm;
    @FXML
    private TableColumn<Graph2Model, String> col_dus;
    @FXML
    private TableColumn<Graph2Model, String> col_dds;
    @FXML
    private TableColumn<Graph2Model, String> col_dlen_sewer;
    @FXML
    private TableColumn<Graph2Model, String> col_dground_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dava_pipes;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_for_min_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_for_all;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_head_loss;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drelative_depth;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drelative_depth1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcentral_angle;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmodified_const_cost;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drR;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dr;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drSmall;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_daA;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvV;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_da;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dv;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvSmall;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvfull;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmanning_coefficient;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dfinal_design_velocity;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dfinal_design_velocity1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dfinal_design_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dfinal_design_slope1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dava_head;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_droughness_coefficient;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dhead_loss;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dhead_loss1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcover;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dus_invert_depth;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dds_invert_depth;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmod_ivert_depth;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dwidth_trench;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvol_excavation;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmod_vol_excavation;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drate_excavation;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcost_excavation;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmod_cost_excavation;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvol_backfilling;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvol_mod_backfilling;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drate_backfilling;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcost_backfilling;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcost_mod_backfilling;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dunit_price;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcost_pipe;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmanhole_size;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dvol_material_manhole;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_drate_material_manhole;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcost_manhole;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_mod_const_cost;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dtotal_cost_of_const;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_min_velocity;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_min_velocity1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_min_velocity_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dsmin;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dsmin1;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dv_06;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dslope_06;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_for_slope_06;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dv_partial;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dadjacent_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dmin_cover;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dadjasted_invert_depth;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dadjasted_slope;
    @FXML
    private TableColumn<Graph2Model, CustomCell> col_dcheck_adjasted_slope;
    @FXML
    private JFXButton show_graph;

    private int project_id;
    private boolean point = true;
    private int id = 0;
    private String pipe_id = "";

    private double x1 = 0.0;
    private double x2 = 0.0;
    private double y1 = 0.0;
    private double y2 = 0.0;

    private final int TYPE_ERROR = 0;
    private final int TYPE_SUCCESS = 1;
    private final int TYPE_INFO = 2;

    private ArrayList<Point> points = new ArrayList<>();

    private final String STYLE_ERROR = "-fx-background-color: red; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.6";
    private final String STYLE_SUCCESS = "-fx-background-color: #00c500; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.6";
    private final String STYLE_INFO = "-fx-background-color: #FF8A00FF; -fx-text-fill: white;" +
            "-fx-background-radius: 5; -fx-opacity: 0.8";

    private final String STYLE_BTN_ON = "-fx-background-color: #FF8A00FF; -fx-text-fill: white";
    private final String STYLE_SVG_ON = "-fx-fill: white";

    private Label lbl_p1 = new Label("P1");
    private Label lbl_p2 = new Label("P2");

    private Point point1;
    private Point point2;

    public static IntegerProperty result = new SimpleIntegerProperty();

    private int page = 1;
    public static DoubleProperty feasible_diameter = new SimpleDoubleProperty(0.0);
    public static IntegerProperty feasible_id = new SimpleIntegerProperty(-1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connection = new ConnectionUtil();

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                System.out.println("x1: " + x1 + " y1: " + y1);
                System.out.println("x2: " + x2 + " y2: " + y2);
                if (x1 != 0 && y1 != 0) {
                    root.getChildren().remove(lbl_p2);
                    x2 = 0.0;
                    y2 = 0.0;
                } else if (x1 != 0 && y1 != 0) {
                    root.getChildren().remove(lbl_p1);
                    x1 = 0.0;
                    y1 = 0.0;
                }
            }

            if (event.isControlDown()) {
                switchConnect();
            }

            if (event.getCode() == KeyCode.ENTER &&
                    x1 != 0 && y1 != 0 && x2 != 0 && y2 != 0) {

//                connectLine(x1, y1, x2, y2);
                x1 = 0.0;
                y1 = 0.0;
                x2 = 0.0;
                y2 = 0.0;
            }
        });


        root.setOnMouseClicked(event -> {
            System.out.println("X: " + event.getX() + " Y: " + event.getY());

            Point pt = new Point(event.getX(), event.getY(), 0.0, project_id);

            Circle circle = getCircle(pt);
            VBox vBox = getVBox();
            TextField textField = getTextField();
            Label label = new Label();
            label.setText("Elevation");

            vBox.getChildren().addAll(textField, label);
            vBox.setAlignment(Pos.CENTER);


            if (point && btn_connect.getStyle().isEmpty()) {
                root.getChildren().add(circle);

                root.getChildren().add(vBox);

                vBox.setLayoutX(event.getX());
                vBox.setLayoutY(event.getY());
                vBox.setVisible(true);
                vBox.toFront();
                point = false;
                textField.requestFocus();

                textField.setOnAction(event1 -> {
                    if (textField.getText().isEmpty()) {
                        root.getChildren().remove(circle);
                        vBox.setVisible(false);
                        point = true;
                        return;
                    }

                    if (!ValidateInput.validateDecimal(textField.getText())) {
                        lbl_error.setText("Only decimal number are allowed");
                        showToast(TYPE_ERROR);
                        return;
                    }

                    pt.setElevation(Double.parseDouble(textField.getText()));
                    if (!connection.pointExists(pt.getX(), pt.getY())) {
                        connection.addPoints(pt);
                        System.out.println("Points Saved: " + pt.getX() + " " + pt.getY());
                        lbl_error.setText("Node saved");
                        init();
                    }
                    showToast(TYPE_INFO);

                    textField.setText("");
                    vBox.setVisible(false);
                    point = true;
                });
            }
        });

        btn_exit.setOnAction(event -> onBack());

        btn_connect.setOnAction(event -> {
            switchConnect();
        });

        btn_run.setOnAction(event -> {
            connection.connectDb();
            if (connection.getLines(project_id).size() == 0) {
                lbl_error.setText("Add pipes in order to run");
                showToast(TYPE_ERROR);
                return;
            }

            run();
        });
    }

    private void run() {
        page = 2;
        show_graph.setVisible(true);
        root.setVisible(false);
        tableView.setVisible(true);
        tableView_detailed.setVisible(false);

        RunModel model = new RunModel();
        model.init(project_id, connection);

        new Thread((Runnable) model.worker).start();
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
        stateProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                initTable();
            }
        });
    }

    private void initTable() {
        PropertyValueFactory<Graph2Model, String> pipeCellValueFactory = new PropertyValueFactory<>("pipe_id");
        col_pipe.setCellValueFactory(pipeCellValueFactory);

        PropertyValueFactory<Graph2Model, String> commCellValueFactory = new PropertyValueFactory<>("comm_flow");
        col_comm.setCellValueFactory(commCellValueFactory);
        col_comm.setCellFactory(param -> new LabelTableCell<>());

        PropertyValueFactory<Graph2Model, String> usCellValueFactory = new PropertyValueFactory<>("us_elevation");
        col_us.setCellValueFactory(usCellValueFactory);
        col_us.setCellFactory(param -> new LabelTableCell<>());

        PropertyValueFactory<Graph2Model, String> dsCellValueFactory = new PropertyValueFactory<>("ds_elevation");
        col_ds.setCellValueFactory(dsCellValueFactory);
        col_ds.setCellFactory(param -> new LabelTableCell<>());

        PropertyValueFactory<Graph2Model, String> lengthSewerCellValueFactory = new PropertyValueFactory<>("pipe_length");
        col_len_sewer.setCellValueFactory(lengthSewerCellValueFactory);
        col_len_sewer.setCellFactory(param -> new LabelTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> slopeCellValueFactory = new PropertyValueFactory<>("final_design_slope");
        col_ground_slope.setCellValueFactory(slopeCellValueFactory);
        col_ground_slope.setCellFactory(param -> new CustomTableCell2<>());

        PropertyValueFactory<Graph2Model, CustomCell> avaPipeCellValueFactory = new PropertyValueFactory<>("pipeDiameter");
        col_ava_pipes.setCellValueFactory(avaPipeCellValueFactory);
        col_ava_pipes.setCellFactory(param -> new CustomTableCell2<>());

        PropertyValueFactory<Graph2Model, CustomCell> dvFullCellValueFactory = new PropertyValueFactory<>("final_design_velocity");
        col_final_velo.setCellValueFactory(dvFullCellValueFactory);
        col_final_velo.setCellFactory(param -> new CustomTableCell2<>());

        PropertyValueFactory<Graph2Model, CustomCell> dsMinCellValueFactory = new PropertyValueFactory<>("s_min");
        col_smin.setCellValueFactory(dsMinCellValueFactory);
        col_smin.setCellFactory(param -> new CustomTableCell2<>());
//
        PropertyValueFactory<Graph2Model, CustomCell> dModTotalCostConstCellValueFactory = new PropertyValueFactory<>("mod_total_cost_const");
        col_mod_cost.setCellValueFactory(dModTotalCostConstCellValueFactory);
        col_mod_cost.setCellFactory(param -> new CustomTableCell2<>());

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btn_show_details.setVisible(true);
                System.out.println("Pipe ID: " + newValue.getP_id());
                id = newValue.getP_id();
                pipe_id = newValue.getPipe_id();
            }
        });

        tableView.setItems(FXCollections.observableList(RunModel.graph2Models));
    }

    private void initDetailedTable(int pipe_id, String p_id) {
        PropertyValueFactory<Graph2Model, String> dpipeCellValueFactory = new PropertyValueFactory<>("pipe_id");
        col_dpipe.setCellValueFactory(dpipeCellValueFactory);

        PropertyValueFactory<Graph2Model, String> dcommCellValueFactory = new PropertyValueFactory<>("comm_flow");
        col_dcomm.setCellValueFactory(dcommCellValueFactory);

        PropertyValueFactory<Graph2Model, String> dusCellValueFactory = new PropertyValueFactory<>("us_elevation");
        col_dus.setCellValueFactory(dusCellValueFactory);

        PropertyValueFactory<Graph2Model, String> ddsCellValueFactory = new PropertyValueFactory<>("ds_elevation");
        col_dds.setCellValueFactory(ddsCellValueFactory);

        PropertyValueFactory<Graph2Model, String> dlengthSewerCellValueFactory = new PropertyValueFactory<>("pipe_length");
        col_dlen_sewer.setCellValueFactory(dlengthSewerCellValueFactory);

        PropertyValueFactory<Graph2Model, String> dslopeCellValueFactory = new PropertyValueFactory<>("slope");
        col_dground_slope.setCellValueFactory(dslopeCellValueFactory);

        PropertyValueFactory<Graph2Model, CustomCell> davaPipeCellValueFactory = new PropertyValueFactory<>("pipeDiameter");
        col_dava_pipes.setCellValueFactory(davaPipeCellValueFactory);
        col_dava_pipes.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> drelativeDepthCellValueFactory = new PropertyValueFactory<>("relative_depth");
        col_drelative_depth.setCellValueFactory(drelativeDepthCellValueFactory);
        col_drelative_depth1.setCellValueFactory(drelativeDepthCellValueFactory);
        col_drelative_depth.setCellFactory(param -> new CustomTableCell<>());
        col_drelative_depth1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dcentralAngleCellValueFactory = new PropertyValueFactory<>("central_angle");
        col_dcentral_angle.setCellValueFactory(dcentralAngleCellValueFactory);
        col_dcentral_angle.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> drRCellValueFactory = new PropertyValueFactory<>("rR");
        col_drR.setCellValueFactory(drRCellValueFactory);
        col_drR.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRCellValueFactory = new PropertyValueFactory<>("r");
        col_dr.setCellValueFactory(dRCellValueFactory);
        col_dr.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRSmallCellValueFactory = new PropertyValueFactory<>("rSmall");
        col_drSmall.setCellValueFactory(dRSmallCellValueFactory);
        col_drSmall.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> daACellValueFactory = new PropertyValueFactory<>("aA");
        col_daA.setCellValueFactory(daACellValueFactory);
        col_daA.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dvVCellValueFactory = new PropertyValueFactory<>("vV");
        col_dvV.setCellValueFactory(dvVCellValueFactory);
        col_dvV.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> daCellValueFactory = new PropertyValueFactory<>("a");
        col_da.setCellValueFactory(daCellValueFactory);
        col_da.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dvCellValueFactory = new PropertyValueFactory<>("v");
        col_dv.setCellValueFactory(dvCellValueFactory);
        col_dv.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckMinVelocityCellValueFactory = new PropertyValueFactory<>("check_min_velocity");
        col_dcheck_min_velocity.setCellValueFactory(dCheckMinVelocityCellValueFactory);
        col_dcheck_min_velocity.setCellFactory(param -> new CustomTableCell<>());
        col_dcheck_min_velocity1.setCellValueFactory(dCheckMinVelocityCellValueFactory);
        col_dcheck_min_velocity1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckMinVelocitySlopeCellValueFactory = new PropertyValueFactory<>("check_min_velocity_slope");
        col_dcheck_min_velocity_slope.setCellValueFactory(dCheckMinVelocitySlopeCellValueFactory);
        col_dcheck_min_velocity_slope.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dSminCellValueFactory = new PropertyValueFactory<>("s_min");
        col_dsmin.setCellValueFactory(dSminCellValueFactory);
        col_dsmin.setCellFactory(param -> new CustomTableCell<>());
        col_dsmin1.setCellValueFactory(dSminCellValueFactory);
        col_dsmin1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dV06CellValueFactory = new PropertyValueFactory<>("v06");
        col_dv_06.setCellValueFactory(dV06CellValueFactory);
        col_dv_06.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dSlope06CellValueFactory = new PropertyValueFactory<>("slope06");
        col_dslope_06.setCellValueFactory(dSlope06CellValueFactory);
        col_dslope_06.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckSlope06CellValueFactory = new PropertyValueFactory<>("check_slope_06");
        col_dcheck_for_slope_06.setCellValueFactory(dCheckSlope06CellValueFactory);
        col_dcheck_for_slope_06.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dVPartialCellValueFactory = new PropertyValueFactory<>("v_partial");
        col_dv_partial.setCellValueFactory(dVPartialCellValueFactory);
        col_dv_partial.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dAdjacentSlopeCellValueFactory = new PropertyValueFactory<>("adjacent_slope");
        col_dadjacent_slope.setCellValueFactory(dAdjacentSlopeCellValueFactory);
        col_dadjacent_slope.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dMinCoverCellValueFactory = new PropertyValueFactory<>("min_cover");
        col_dmin_cover.setCellValueFactory(dMinCoverCellValueFactory);
        col_dmin_cover.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dAdjustedSlopeCellValueFactory = new PropertyValueFactory<>("adjusted_slope");
        col_dadjasted_slope.setCellValueFactory(dAdjustedSlopeCellValueFactory);
        col_dadjasted_slope.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dAdjustedInvertDepthCellValueFactory = new PropertyValueFactory<>("adjusted_invert_depth");
        col_dadjasted_invert_depth.setCellValueFactory(dAdjustedInvertDepthCellValueFactory);
        col_dadjasted_invert_depth.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckAdjustedSlopeCellValueFactory = new PropertyValueFactory<>("check_adjusted_slope");
        col_dcheck_adjasted_slope.setCellValueFactory(dCheckAdjustedSlopeCellValueFactory);
        col_dcheck_adjasted_slope.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dvSlopeCellValueFactory = new PropertyValueFactory<>("v_slope");
        col_dvSmall.setCellValueFactory(dvSlopeCellValueFactory);
        col_dvSmall.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dvFullCellValueFactory = new PropertyValueFactory<>("vFull");
        col_dvfull.setCellValueFactory(dvFullCellValueFactory);
        col_dvfull.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dmanningCellValueFactory = new PropertyValueFactory<>("manning");
        col_dmanning_coefficient.setCellValueFactory(dmanningCellValueFactory);
        col_dmanning_coefficient.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dFinalDesignVelocityCellValueFactory = new PropertyValueFactory<>("final_design_velocity");
        col_dfinal_design_velocity.setCellValueFactory(dFinalDesignVelocityCellValueFactory);
        col_dfinal_design_velocity.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dFinalDesignVelocityCellValueFactory1 = new PropertyValueFactory<>("final_design_velocity");
        col_dfinal_design_velocity1.setCellValueFactory(dFinalDesignVelocityCellValueFactory1);
        col_dfinal_design_velocity1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dFinalDesignSlopeCellValueFactory = new PropertyValueFactory<>("final_design_slope");
        col_dfinal_design_slope.setCellValueFactory(dFinalDesignSlopeCellValueFactory);
        col_dfinal_design_slope.setCellFactory(param -> new CustomTableCell<>());
        col_dfinal_design_slope1.setCellValueFactory(dFinalDesignSlopeCellValueFactory);
        col_dfinal_design_slope1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> davaHeadCellValueFactory = new PropertyValueFactory<>("ava_head");
        col_dava_head.setCellValueFactory(davaHeadCellValueFactory);
        col_dava_head.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRoughnessHeadCellValueFactory = new PropertyValueFactory<>("roughness");
        col_droughness_coefficient.setCellValueFactory(dRoughnessHeadCellValueFactory);
        col_droughness_coefficient.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dHeadLossCellValueFactory = new PropertyValueFactory<>("head_loss");
        col_dhead_loss.setCellValueFactory(dHeadLossCellValueFactory);
        col_dhead_loss.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dHeadLossCellValueFactory1 = new PropertyValueFactory<>("head_loss");
        col_dhead_loss1.setCellValueFactory(dHeadLossCellValueFactory1);
        col_dhead_loss1.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCoverCellValueFactory = new PropertyValueFactory<>("cover");
        col_dcover.setCellValueFactory(dCoverCellValueFactory);
        col_dcover.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dUsInvertCellValueFactory = new PropertyValueFactory<>("us_invert");
        col_dus_invert_depth.setCellValueFactory(dUsInvertCellValueFactory);
        col_dus_invert_depth.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dDsInvertCellValueFactory = new PropertyValueFactory<>("ds_invert");
        col_dds_invert_depth.setCellValueFactory(dDsInvertCellValueFactory);
        col_dds_invert_depth.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModInvertDepthCellValueFactory = new PropertyValueFactory<>("mod_invert_depth");
        col_dmod_ivert_depth.setCellValueFactory(dModInvertDepthCellValueFactory);
        col_dmod_ivert_depth.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dTrenchCellValueFactory = new PropertyValueFactory<>("trench");
        col_dwidth_trench.setCellValueFactory(dTrenchCellValueFactory);
        col_dwidth_trench.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dVolExcavationCellValueFactory = new PropertyValueFactory<>("vol_excavation");
        col_dvol_excavation.setCellValueFactory(dVolExcavationCellValueFactory);
        col_dvol_excavation.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModVolExcavationCellValueFactory = new PropertyValueFactory<>("mod_vol_excavation");
        col_dmod_vol_excavation.setCellValueFactory(dModVolExcavationCellValueFactory);
        col_dmod_vol_excavation.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRateExcavationCellValueFactory = new PropertyValueFactory<>("rate_excavation");
        col_drate_excavation.setCellValueFactory(dRateExcavationCellValueFactory);
        col_drate_excavation.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCostExcavationCellValueFactory = new PropertyValueFactory<>("cost_excavation");
        col_dcost_excavation.setCellValueFactory(dCostExcavationCellValueFactory);
        col_dcost_excavation.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModCostExcavationCellValueFactory = new PropertyValueFactory<>("mod_cost_excavation");
        col_dmod_cost_excavation.setCellValueFactory(dModCostExcavationCellValueFactory);
        col_dmod_cost_excavation.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dVolBackfillingCellValueFactory = new PropertyValueFactory<>("vol_backfilling");
        col_dvol_backfilling.setCellValueFactory(dVolBackfillingCellValueFactory);
        col_dvol_backfilling.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModVolBackfillingCellValueFactory = new PropertyValueFactory<>("mod_vol_backfilling");
        col_dvol_mod_backfilling.setCellValueFactory(dModVolBackfillingCellValueFactory);
        col_dvol_mod_backfilling.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRateBackfillingCellValueFactory = new PropertyValueFactory<>("rate_backfilling");
        col_drate_backfilling.setCellValueFactory(dRateBackfillingCellValueFactory);
        col_drate_backfilling.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCostBackfillingCellValueFactory = new PropertyValueFactory<>("cost_backfilling");
        col_dcost_backfilling.setCellValueFactory(dCostBackfillingCellValueFactory);
        col_dcost_backfilling.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModCostBackfillingCellValueFactory = new PropertyValueFactory<>("mod_cost_backfilling");
        col_dcost_mod_backfilling.setCellValueFactory(dModCostBackfillingCellValueFactory);
        col_dcost_mod_backfilling.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dUnitPriceCellValueFactory = new PropertyValueFactory<>("pipe_price");
        col_dunit_price.setCellValueFactory(dUnitPriceCellValueFactory);
        col_dunit_price.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCostPriceCellValueFactory = new PropertyValueFactory<>("cost_pipe");
        col_dcost_pipe.setCellValueFactory(dCostPriceCellValueFactory);
        col_dcost_pipe.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dManholeCellValueFactory = new PropertyValueFactory<>("manhole_size");
        col_dmanhole_size.setCellValueFactory(dManholeCellValueFactory);
        col_dmanhole_size.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dVolMaterialCellValueFactory = new PropertyValueFactory<>("vol_material_used");
        col_dvol_material_manhole.setCellValueFactory(dVolMaterialCellValueFactory);
        col_dvol_material_manhole.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dRateMaterialCellValueFactory = new PropertyValueFactory<>("rate_material");
        col_drate_material_manhole.setCellValueFactory(dRateMaterialCellValueFactory);
        col_drate_material_manhole.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCostManholeCellValueFactory = new PropertyValueFactory<>("cost_manhole");
        col_dcost_manhole.setCellValueFactory(dCostManholeCellValueFactory);
        col_dcost_manhole.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckSlopeCellValueFactory = new PropertyValueFactory<>("check_slope");
        col_dcheck_for_min_slope.setCellValueFactory(dCheckSlopeCellValueFactory);
        col_dcheck_for_min_slope.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckAllCellValueFactory = new PropertyValueFactory<>("check_all");
        col_dcheck_for_all.setCellValueFactory(dCheckAllCellValueFactory);
        col_dcheck_for_all.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dCheckHeadLossCellValueFactory = new PropertyValueFactory<>("check_head_loss");
        col_dcheck_head_loss.setCellValueFactory(dCheckHeadLossCellValueFactory);
        col_dcheck_head_loss.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dTotalCostConstCellValueFactory = new PropertyValueFactory<>("total_cost_const");
        col_dtotal_cost_of_const.setCellValueFactory(dTotalCostConstCellValueFactory);
        col_dtotal_cost_of_const.setCellFactory(param -> new CustomTableCell<>());

        PropertyValueFactory<Graph2Model, CustomCell> dModTotalCostConstCellValueFactory = new PropertyValueFactory<>("mod_total_cost_const");
        PropertyValueFactory<Graph2Model, CustomCell> dModTotalCostConstCellValueFactory2 = new PropertyValueFactory<>("mod_total_cost_const2");
        col_mod_const_cost.setCellValueFactory(dModTotalCostConstCellValueFactory);
        col_mod_const_cost.setCellFactory(param -> new CustomTableCell<>());
        col_dmodified_const_cost.setCellValueFactory(dModTotalCostConstCellValueFactory2);
        col_dmodified_const_cost.setCellFactory(param -> new CustomTableCell<>());

        ArrayList<Graph2Model> models = new ArrayList<>();
        GraphModel graphModel = connection.getGraph(pipe_id);

        double total_len = connection.getTotalLength(project_id);
        ProjectModel projectModel = connection.getProject(project_id);

//        for (GraphModel model : models) {
        ArrayList<GraphModel> graphModels = new ArrayList<>();
        graphModels.add(graphModel);
        graphModels.addAll(connection.getFlowCont(graphModel.getUp_stream(), project_id));

        double comm_flow = getCommFlow(getIds(graphModels),
                total_len, projectModel.getDesign_discharge());

        ArrayList<PipesModel> pipesModels = connection.getPipes(project_id);
        for (int i = 0; i < pipesModels.size(); i++) {
            Graph2Model model = new Graph2Model();
            model.setPipe_diameter(pipesModels.get(i).getDiameter());
            model.setPipe_price(pipesModels.get(i).getPrice());
            model.setAva_pipe_id(pipesModels.get(i).getId());
            model.setComm_flow(comm_flow);
            model.setP_id(graphModel.getId());
            model.setPipe_id(p_id);
            model.setUs_elevation(connection.getPoint(graphModel.getUp_stream()).getElevation());
            model.setDs_elevation(connection.getPoint(graphModel.getDown_stream()).getElevation());
            model.setPipe_length(graphModel.getPipe_length());
            model.setHidePipe(i != 0);
            model.setManning(projectModel.getManning());
            model.setRoughness(projectModel.getRoughness());
            model.setCover(projectModel.getNominal());
            model.setMin_cover(projectModel.getMinimum());
            model.setRate_excavation(projectModel.getExcavation());
            model.setRate_backfilling(projectModel.getBack_filling());
            model.setRate_material(projectModel.getMaterial());
            model.setSlope();
            models.add(model);

//                pipesModel.setComm_flow(comm_flow);
//
//                if (pipesModel.checkMinSlope() &&
//                        pipesModel.getVelocityS()) {
//                    model.setPipesModel(pipesModel);
//                    break;
//                }
        }
//        }

//        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GraphModel>() {
//            @Override
//            public void changed(ObservableValue<? extends GraphModel> observable, GraphModel oldValue, GraphModel newValue) {
//                btn_show_details.setVisible(newValue!=null);
//            }
//        });

        tableView_detailed.setItems(FXCollections.observableList(models));
    }

    private Graph2Model getFeasibility(int pipe_id) {
        ArrayList<Graph2Model> models = new ArrayList<>();
        DoubleProperty f_diameter = new SimpleDoubleProperty(0.0);
        IntegerProperty f_id = new SimpleIntegerProperty(-1);

        GraphModel graphModel = connection.getGraph(pipe_id);

        double total_len = connection.getTotalLength(project_id);
        ProjectModel projectModel = connection.getProject(project_id);

//        for (GraphModel model : models) {
        ArrayList<GraphModel> graphModels = new ArrayList<>();
        graphModels.add(graphModel);
        graphModels.addAll(connection.getFlowCont(graphModel.getUp_stream(), project_id));

        double comm_flow = getCommFlow(getIds(graphModels),
                total_len, projectModel.getDesign_discharge());

        ArrayList<PipesModel> pipesModels = connection.getPipes(project_id);
        for (int i = 0; i < pipesModels.size(); i++) {
            Graph2Model model = new Graph2Model();
            model.getCheck_all(f_diameter, f_id);
            model.setPipe_diameter(pipesModels.get(i).getDiameter());
            model.setPipe_price(pipesModels.get(i).getPrice());
            model.setAva_pipe_id(pipesModels.get(i).getId());
            model.setComm_flow(comm_flow);
            model.setP_id(graphModel.getId());
            model.setPipe_id(String.valueOf(i));
            model.setUs_elevation(connection.getPoint(graphModel.getUp_stream()).getElevation());
            model.setDs_elevation(connection.getPoint(graphModel.getDown_stream()).getElevation());
            model.setPipe_length(graphModel.getPipe_length());
            model.setHidePipe(i != 0);
            model.setManning(projectModel.getManning());
            model.setRoughness(projectModel.getRoughness());
            model.setCover(projectModel.getNominal());
            model.setRate_excavation(projectModel.getExcavation());
            model.setRate_backfilling(projectModel.getBack_filling());
            model.setRate_material(projectModel.getMaterial());
            models.add(model);

            if (f_id.get() > 0) return model;
        }
        return null;
    }

    private double getCommFlow(ArrayList<Integer> ids,
                               double total_len,
                               double design_discharge) {
        double total = 0.0;

        for (Integer integer : ids) {
            GraphModel model = connection.getGraph(integer);
            total += (model.getPipe_length() / total_len) * design_discharge;
        }

        return total;
    }

    private ArrayList<Integer> getIds(ArrayList<GraphModel> models) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (GraphModel model : models) {
            integers.add(model.getId());
        }

        return integers;
    }

    private void switchConnect() {
        point1 = null;
        point2 = null;
        if (btn_connect.getStyle().isEmpty()) {
            btn_connect.setStyle(STYLE_BTN_ON);
            svg_connect.setStyle(STYLE_SVG_ON);
        } else {
            btn_connect.setStyle(null);
            svg_connect.setStyle(null);
        }
    }

    private void init() {
        root.getChildren().clear();
        for (Point point : connection.getPoints(project_id)) {
            root.getChildren().add(getCircle(point));
            root.getChildren().add(getNodeLabel(point));
        }

        for (GraphModel model : connection.getLines(project_id)) {
            Point point1 = connection.getPoint(model.getUp_stream());
            Point point2 = connection.getPoint(model.getDown_stream());
            if (point1 != null && point2 != null) {
                drawLine(point1, point2);
                root.getChildren().add(getPipeLabel(model));
            }
        }
    }

    private Label getNodeLabel(Point point) {
        Label label = new Label();
        label.setText("N-" + point.getSn());
        label.setLayoutX(point.getX() - 5);
        label.setLayoutY(point.getY() - 20);
        return label;
    }

    private Label getPipeLabel(GraphModel model) {
        Point point1 = connection.getPoint(model.getUp_stream());
        Point point2 = connection.getPoint(model.getDown_stream());
        Point point = Util.getMidPoint(point1, point2);
        Label label = new Label();
        label.setText("P" + model.getSn());
        label.setLayoutX(point.getX());
        label.setLayoutY(point.getY());
        return label;
    }

    private VBox getVBox() {
        VBox vBox = new VBox();
        vBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        return vBox;
    }

    private TextField getTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color:  #FF8A00FF;" +
                "-fx-border-width: 2; -fx-border-radius: 15");
        textField.setPrefSize(50, 40);
        textField.setAlignment(Pos.CENTER);

        return textField;
    }

    private void connectLine(Point point1, Point point2) {
        Line line = new Line();
        line.setStrokeWidth(3.0);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStartX(point1.getX());
        line.setEndX(point2.getX());
        line.setStartY(point1.getY());
        line.setEndY(point2.getY());

        root.getChildren().add(line);
        line.toBack();

        int up_id = point1.compareElevation(point2) ? point1.getId() : point2.getId();
        int down_id = point1.compareElevation(point2) ? point2.getId() : point1.getId();

        System.out.println("Line exists: " + connection.lineExists(up_id, down_id, project_id));

        if (!connection.lineExists(up_id, down_id, project_id)) {
            VBox vBox = getVBox();
            TextField textField = getTextField();
            Label label = new Label();
            label.setText("Pipe Length");

            vBox.getChildren().addAll(textField, label);
            vBox.setAlignment(Pos.CENTER);

            root.getChildren().add(vBox);

            vBox.setLayoutX(Util.getMidPoint(point1, point2).getX());
            vBox.setLayoutY(Util.getMidPoint(point1, point2).getY());
            System.out.println("VBox mid: " + vBox.getLayoutX() + " " + vBox.getLayoutY());
            vBox.setVisible(true);
            vBox.toFront();
            point = false;
            textField.requestFocus();

            textField.setOnAction(event1 -> {
                if (textField.getText().isEmpty()) {
                    root.getChildren().remove(line);
                    vBox.setVisible(false);
                    point = true;
                    return;
                }


                if (!ValidateInput.validateDecimal(textField.getText())) {
                    lbl_error.setText("Only decimal number are allowed");
                    showToast(TYPE_ERROR);
                    return;
                }

                GraphModel graphModel = new GraphModel(0, 0, Double.parseDouble(textField.getText()),
                        up_id, down_id, "", project_id);
                graphModel.setUp_elevation(connection.getPoint(up_id).getElevation());
                graphModel.setDown_elevation(connection.getPoint(down_id).getElevation());
                connection.addLine(graphModel);
                lbl_error.setText("Pipe saved");
                init();
                showToast(TYPE_INFO);

                textField.setText("");
                vBox.setVisible(false);
                point = true;
            });
        }
    }

    private void drawLine(Point down_stream, Point up_stream) {
        Arrow line = new Arrow();

        line.setStartX(down_stream.getX());
        line.setEndX(up_stream.getX());
        line.setStartY(down_stream.getY());
        line.setEndY(up_stream.getY());

        root.getChildren().add(line);
        line.toBack();
    }


    public void setProject_id(int project_id) {
        this.project_id = project_id;
        init();
    }

    public Circle getCircle(Point pt) {
        Circle circle = new Circle();
        circle.setRadius(5);
        circle.setStrokeWidth(0);
        circle.setFill(Color.valueOf("#FF8A00FF"));
        circle.setLayoutX(pt.getX());
        circle.setLayoutY(pt.getY());

        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
            MainApplication.getStage().getScene().setCursor(Cursor.HAND);
            point = false;
        });

        circle.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
            MainApplication.getStage().getScene().setCursor(Cursor.DEFAULT);
            point = true;
        });

        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            MainApplication.getStage().getScene().setCursor(Cursor.HAND);
            if (event1.getClickCount() == 1 && !btn_connect.getStyle().isEmpty()) {
                if (point1 == null) {
                    point1 = pt;
                } else if (!point1.equals(pt)) {
                    point2 = pt;
                    connectLine(point1, point2);
                    point1 = null;
                    point2 = null;
                }
            } else if (event1.getClickCount() == 2) {
                init();
                VBox vBox = getVBox();
                TextField textField = getTextField();
                Label label = new Label();
                label.setText("Elevation");

                vBox.getChildren().addAll(textField, label);
                vBox.setAlignment(Pos.CENTER);

                root.getChildren().add(vBox);

                vBox.setLayoutX(pt.getX());
                vBox.setLayoutY(pt.getY());
                vBox.setVisible(true);
                vBox.toFront();
                point = false;
                textField.requestFocus();
                textField.setText(String.valueOf(pt.getElevation()));

                textField.setOnAction(event -> {
                    if (textField.getText().isEmpty() || String.valueOf(pt.getElevation()).equalsIgnoreCase(textField.getText())) {
                        vBox.setVisible(false);
                        point = true;
                        return;
                    }

                    if (!ValidateInput.validateDecimal(textField.getText())) {
                        lbl_error.setText("Only decimal number are allowed");
                        showToast(TYPE_ERROR);
                        return;
                    }

                    pt.setElevation(Double.parseDouble(textField.getText()));

                    connection.updatePoint(pt);
                    System.out.println("Points Saved: " + pt.getX() + " " + pt.getY());
                    lbl_error.setText("Node Updated");
                    init();
                    showToast(TYPE_INFO);

                    textField.setText("");
                    vBox.setVisible(false);
                    point = true;
                });

            }
        });


        return circle;
    }

    private void showToast(int type) {

        Model model = new Model();
        new Thread((Runnable) model.worker).start();
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.worker.stateProperty();
        stateProperty.addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
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
                } else {
                    lbl_error.setVisible(false);
                }
            }
        });
    }

    private void onBack() {
        try {
            Stage dialogStage = (Stage) root.getScene().getWindow();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"));
            Parent parent = loader.load();
            dialogStage.setTitle("OPDSS");
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
            connection.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShowGraph(ActionEvent event) {
        page = 1;
        root.setVisible(true);
        tableView.setVisible(false);
        btn_show_details.setVisible(false);
        tableView_detailed.setVisible(false);
        show_graph.setVisible(false);
    }

    public void onShowDetailed(ActionEvent event) {
        if (id != 0) {
            page = 3;
            initDetailedTable(id, pipe_id);
            root.setVisible(false);
            tableView.setVisible(false);
            tableView_detailed.setVisible(true);
        }
    }

    public void editVariables(ActionEvent event) {
        try {
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("variables.fxml"));
            Parent parent = loader.load();
            VariablesController controller = loader.getController();
            controller.setProject_id(project_id, connection);
            controller.setStage(dialogStage);
            dialogStage.setTitle("OPDSS - Edit Variables");
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

//            IntegerBinding binding = new IntegerBinding() {
//                {
//                    super.bind(result);
//                }
//                @Override
//                protected int computeValue() {
//                    System.out.println("Compute is called");
//                    return result.get();
//                }
//            };

            switch (result.get()) {
                case 1:
                    connection.connectDb();
                    lbl_error.setText("Updated!");
                    showToast(TYPE_SUCCESS);
                    refresh();
                    break;
                case -1:
                    connection.connectDb();
                    lbl_error.setText("Error occurred while updating. Please try again");
                    showToast(TYPE_ERROR);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avaPipes(ActionEvent event) {
        try {
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("available_pipes.fxml"));
            Parent parent = loader.load();
            AvaPipeController controller = loader.getController();
            controller.setProject_id(project_id, connection);
            controller.setStage(dialogStage);
            dialogStage.setTitle("OPDSS - Available Pipes");
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

//            IntegerBinding binding = new IntegerBinding() {
//                {
//                    super.bind(result);
//                }
//                @Override
//                protected int computeValue() {
//                    System.out.println("Compute is called");
//                    return result.get();
//                }
//            };

            switch (result.get()) {
                case 1:
                    connection.connectDb();
                    lbl_error.setText("Updated!");
                    showToast(TYPE_SUCCESS);
                    refresh();
                    break;
                case -1:
                    connection.connectDb();
                    lbl_error.setText("Error occurred while updating. Please try again");
                    showToast(TYPE_ERROR);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void designDischarge(ActionEvent event) {
        try {
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("design_discharge.fxml"));
            Parent parent = loader.load();
            DesignDischargeController controller = loader.getController();
            controller.setProject_id(project_id, connection);
            controller.setStage(dialogStage);
            dialogStage.setTitle("OPDSS - Design Discharge");
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);
            dialogStage.centerOnScreen();
            dialogStage.showAndWait();

//            IntegerBinding binding = new IntegerBinding() {
//                {
//                    super.bind(result);
//                }
//                @Override
//                protected int computeValue() {
//                    System.out.println("Compute is called");
//                    return result.get();
//                }
//            };

            switch (result.get()) {
                case 1:
                    connection.connectDb();
                    lbl_error.setText("Updated!");
                    showToast(TYPE_SUCCESS);
                    refresh();
                    break;
                case -1:
                    connection.connectDb();
                    lbl_error.setText("Error occurred while updating. Please try again");
                    showToast(TYPE_ERROR);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        switch (page) {
            case 2:
                run();
                break;
            case 3:
                if (id != 0) {
                    initDetailedTable(id, pipe_id);
                }
                break;
        }
    }

    public static class LabelTableCell<S> extends TableCell<S, String> {
        //final ImageView imageView = new ImageView();
        Label label = new Label();

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                label.setText(null);
                setText(null);
                setGraphic(null);
                return;
            }

            try{
                Double val = Double.parseDouble(item);
                label.setText(String.format("%.2f", val));
                setGraphic(label);
                return;
            }catch (Exception e){
                //error
            }
            label.setText(item);
            setGraphic(label);

        }
    }

    public static class CustomTableCell<S> extends TableCell<S, CustomCell> {
        //final ImageView imageView = new ImageView();
        Label label = new Label();
        String b0 = "-fx-border-width: 0 0 0 0";
        String b1 = "-fx-border-width: 0 0 0 2";
        String b2 = "-fx-border-width: 0 0 2 2";
        String b3 = "-fx-border-width: 2 0 2 2";
        String b4 = "-fx-border-width: 2 2 2 2";
        String b5 = "-fx-border-width: 2 0 2 0";
        String b6 = "-fx-border-width: 2 2 2 0";

        @Override
        protected void updateItem(CustomCell item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            String style = "-fx-border-color: #00c853;";

            switch (item.getBorder()) {
                case 0:
                    style += b0;
                    break;
                case 1:
                    style += b1;
                    break;
                case 2:
                    style += b2;
                    break;
                case 3:
                    style += b3;
                    break;
                case 4:
                    style += b4;
                    break;
                case 5:
                    style += b5;
                    break;
                case 6:
                    style += b6;
                    break;
            }
            if (item.getId() == feasible_id.get()) {
                setStyle(style);
            }
            try{
                Double val = Double.parseDouble(item.getValue());
                label.setText(String.format("%.2f", val));
                setGraphic(label);
                return;
            }catch (Exception e){
                //error
            }
            label.setText(item.getValue());
            setGraphic(label);

//            if (item.getValue().equalsIgnoreCase("not feasible")) {
//                setStyle("-fx-background-color: #f44336; -fx-opacity: 0.7;");
//                label.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
//            } else if (item.getValue().equalsIgnoreCase("feasible")) {
//                label.setStyle("-fx-text-fill: black; -fx-font-weight: bold");
//            }

        }
    }

    public static class CustomTableCell2<S> extends TableCell<S, CustomCell> {
        //final ImageView imageView = new ImageView();
        Label label = new Label();

        @Override
        protected void updateItem(CustomCell item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            try{
                Double val = Double.parseDouble(item.getValue());
                label.setText(String.format("%.2f", val));
                setGraphic(label);
                return;
            }catch (Exception e){
                //error
            }
            label.setText(item.getValue());
            setGraphic(label);
        }
    }
}
