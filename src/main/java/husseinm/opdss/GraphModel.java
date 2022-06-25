package husseinm.opdss;

import java.util.ArrayList;

public class GraphModel {
    private int id;
    private int sn;
    private double pipe_length;
    private int up_stream;
    private int down_stream;
    private String slope;
    private int project_id;

    private double up_elevation;
    private double down_elevation;

    private String pipe_id;
    private String p_id;
    private double flow_in_pipe;
    private ArrayList<Integer> flow_cont_pipes;
    private double comm_flow;
    private double pipe_total_length;
    private double design_discharge;
    private double ava_pipe;
    private double smin;


    private PipesModel pipesModel;

    public GraphModel(int id, int sn, double pipe_length, int up_stream, int down_stream, String slope, int project_id) {
        this.id = id;
        this.sn = sn;
        this.pipe_length = pipe_length;
        this.up_stream = up_stream;
        this.down_stream = down_stream;
        this.slope = slope;
        this.project_id = project_id;
    }

    public void setP_id(String pipe_id) {
        this.p_id = pipe_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setPipesModel(PipesModel pipesModel) {
        this.pipesModel = pipesModel;
    }

    public PipesModel getPipesModel() {
        return pipesModel;
    }

    public String getPipe_id() {

        return "P-" + getSn();
    }

    public double getAva_pipe() {
        return pipesModel.getDiameter();
    }

    public double getFlow_in_pipe() {
        return (getPipe_length() / getPipe_total_length()) * getDesign_discharge();
    }


    public void setPipe_total_length(double pipe_total_length) {
        this.pipe_total_length = pipe_total_length;
    }

    public double getPipe_total_length() {
        return pipe_total_length;
    }

    public void setDesign_discharge(double design_discharge) {
        this.design_discharge = design_discharge;
    }

    public double getDesign_discharge() {
        return design_discharge;
    }

    public double getDown_elevation() {
        return down_elevation;
    }

    public double getUp_elevation() {
        return up_elevation;
    }

    public void setDown_elevation(double down_elevation) {
        this.down_elevation = down_elevation;
    }

    public void setUp_elevation(double up_elevation) {
        this.up_elevation = up_elevation;
    }

    public int getId() {
        return id;
    }

    public double getSmin() {
        return pipesModel.getSmin();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSn() {
        return sn;
    }

    public void setComm_flow(double comm_flow) {
        this.comm_flow = comm_flow;
    }

    public double getComm_flow() {
        return comm_flow;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public double getPipe_length() {
        return pipe_length;
    }

    public void setPipe_length(double pipe_length) {
        this.pipe_length = pipe_length;
    }

    public int getUp_stream() {
        return up_stream;
    }

    public void setUp_stream(int up_stream) {
        this.up_stream = up_stream;
    }

    public int getDown_stream() {
        return down_stream;
    }

    public void setDown_stream(int down_stream) {
        this.down_stream = down_stream;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public double getGSLope() {
        if (getPipe_length() == 0)
            return 0.0;
        else
            return ((getUp_elevation() - getDown_elevation()) / getPipe_length()) * 100;
    }

    public String getSlope() {
        if (getPipe_length() == 0)
            return "0.000%";
        else
            return String.format("%.3f%s",
                    ((getUp_elevation() - getDown_elevation()) / getPipe_length()) * 100,
                    "%");
    }

    public void setSlope(String slope) {
        this.slope = slope;
    }
}
