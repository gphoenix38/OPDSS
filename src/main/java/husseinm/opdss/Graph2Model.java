package husseinm.opdss;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;

public class Graph2Model {
    private int p_id;
    private String pipe_id;
    private double pipe_length;
    private double us_elevation;
    private double ds_elevation;
    private double comm_flow;
    private double slope;
    private boolean hidePipe;

    private int ava_pipe_id;
    private double pipe_diameter;
    private CustomCell pipeDiameter;
    private double pipe_price;
    private String check_slope;
    private String check_head_loss;

    private double relative_depth;
    private Integer central_angle;
    private double rR;
    private double r;
    private double rSmall;
    private double aA;
    private double a;
    private double v;
    private String check_min_velocity;
    private double s_min;
    private double vV;
    private double vFull;
    private double sMin;
    private double ava_head;
    private double head_loss;
    private double us_invert;
    private double ds_invert;
    private double mod_invert_depth;
    private double trench;
    private double vol_excavation;
    private double mod_vol_excavation;
    private double cost_excavation;
    private double mod_cost_excavation;
    private double vol_backfilling;
    private double mod_vol_backfilling;
    private double cost_backfilling;
    private double mod_cost_backfilling;
    private double cost_pipe;
    private double manhole_size;
    private double vol_material_used;
    private double cost_manhole;
    private double total_cost_const;
    private double mod_total_cost_const;
    private double mod_total_cost_const2;
    private CustomCell v_slope;
    private CustomCell check_min_velocity_slope;
    private CustomCell v06;
    private CustomCell slope06;
    private CustomCell check_slope_06;
    private CustomCell v_partial;
    private CustomCell adjacent_slope;
    private CustomCell adjusted_slope;
    private CustomCell adjusted_invert_depth;
    private CustomCell check_adjusted_slope;
    private CustomCell final_design_velocity;
    private CustomCell final_design_slope;
    private CustomCell check_all;

    private double manning;
    private double roughness;
    private double cover;
    private double rate_excavation;
    private double rate_backfilling;
    private double rate_material;
    private double min_cover;

    public int getP_id() {
        return p_id;
    }

    public void setAva_pipe_id(int ava_pipe_id) {
        this.ava_pipe_id = ava_pipe_id;
    }

    public int getAva_pipe_id() {
        return ava_pipe_id;
    }

    public void setMin_cover(double min_cover) {
        this.min_cover = min_cover;
    }

    public CustomCell getCheck_all() {
        boolean result = getCheck_slope().getValue().equalsIgnoreCase("feasible") && getCheck_min_velocity().getValue().equalsIgnoreCase("feasible");
        if (result && (MainProjectController.feasible_diameter.get()==0||pipe_diameter<MainProjectController.feasible_diameter.get())){
            MainProjectController.feasible_diameter.set(pipe_diameter);
            MainProjectController.feasible_id.set(getAva_pipe_id());
        }
        return new CustomCell(getAva_pipe_id(), result?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getCheck_all(DoubleProperty doubleProperty, IntegerProperty integerProperty) {
        boolean result = getCheck_slope().getValue().equalsIgnoreCase("feasible") && getCheck_min_velocity().getValue().equalsIgnoreCase("feasible");
        if (result && (doubleProperty.get()==0||pipe_diameter<doubleProperty.get())){
            System.out.println("RES");
            doubleProperty.set(pipe_diameter);
            integerProperty.set(getAva_pipe_id());
        }
        return new CustomCell(getAva_pipe_id(), result?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getMin_cover() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(min_cover), 5);
    }

    public CustomCell getAdjusted_invert_depth() {
        double invert_depth = Double.parseDouble(getMod_invert_depth().getValue())<0?
                Double.parseDouble(getMod_invert_depth().getValue())-Double.parseDouble(getMod_invert_depth().getValue())+min_cover:
                (Math.max(Double.parseDouble(getMod_invert_depth().getValue()), min_cover));
        return new CustomCell(getAva_pipe_id(), String.valueOf(invert_depth), 5);
    }

    public CustomCell getAdjusted_slope() {
        double adjusted_slope = ((slope/100*pipe_length)-(Double.parseDouble(getUs_invert().getValue())-Double.parseDouble(getAdjusted_invert_depth().getValue())))/pipe_length;
        return new CustomCell(getAva_pipe_id(), String.valueOf(adjusted_slope*100), 5);
    }

    public CustomCell getCheck_adjusted_slope() {
        boolean check = slope>Double.parseDouble(getAdjusted_slope().getValue());
        return new CustomCell(getAva_pipe_id(), check?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getFinal_design_slope() {
        return getAdjusted_slope();
    }

    public CustomCell getFinal_design_velocity() {
        double fdv = (1/manning)*(Math.pow(Double.parseDouble(getRSmall().getValue()), 2/3.0))*Math.pow(Double.parseDouble(getAdjusted_slope().getValue())/100f,0.5);
        return new CustomCell(getAva_pipe_id(), String.valueOf(fdv), 5);
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public CustomCell getV06() {
        double v06 = Math.max(Double.parseDouble(getV().getValue()), 0.6);
        return new CustomCell(getAva_pipe_id(), String.valueOf(v06), 5);
    }

    public CustomCell getSlope06() {
        double compute = Math.pow(((Double.parseDouble(getV06().getValue())*0.013)/(Math.pow(Double.parseDouble(getRSmall().getValue()), 2/3.0))), 2)*100;
        double slope06 = Double.parseDouble(getV_slope().getValue())>0.6?Double.parseDouble(getS_min().getValue()):compute;
        return new CustomCell(getAva_pipe_id(), String.valueOf(slope06), 5);
    }

    public CustomCell getCheck_slope_06() {
        boolean feasible;
        if (Double.parseDouble(getSlope06().getValue())>Double.parseDouble(getS_min().getValue())){
            feasible = slope > Double.parseDouble(getSlope06().getValue());
        }else
            feasible = false;
        return new CustomCell(getAva_pipe_id(), feasible?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getV_partial() {
        double v_partial = Double.parseDouble(getV().getValue())>0.6?Double.parseDouble(getV().getValue()):Double.parseDouble(getV06().getValue());
        return new CustomCell(getAva_pipe_id(), String.valueOf(v_partial), 5);
    }

    public CustomCell getAdjacent_slope() {
        double adjacent_slope = Double.parseDouble(getV().getValue())>0.6?Double.parseDouble(getS_min().getValue()):Double.parseDouble(getSlope06().getValue());
        return new CustomCell(getAva_pipe_id(), String.valueOf(adjacent_slope), 5);
    }

    public CustomCell getRelative_depth() {
        if (pipe_diameter <= 250)
            return new CustomCell(getAva_pipe_id(), String.valueOf(0.5), 5);
        else if (pipe_diameter > 250 && pipe_diameter <= 500)
            return new CustomCell(getAva_pipe_id(), String.valueOf(0.6), 5);
        else
            return new CustomCell(getAva_pipe_id(), String.valueOf(0.7), 5);
    }

    public CustomCell getCentral_angle() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(2 * (Math.acos(1 - 2 * Double.parseDouble(getRelative_depth().getValue())) * (180 / Math.PI))), 5);
    }

    public CustomCell getRR() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((1 - ((360 * Math.sin(Double.parseDouble(getCentral_angle().getValue()) * (Math.PI / 180))) / (2 * Math.PI * Double.parseDouble(getCentral_angle().getValue()))))), 5);
    }

    public CustomCell getR() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((getPipe_diameter() * 0.001) / 4), 5);
    }

    public CustomCell getRSmall() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getRR().getValue()) * Double.parseDouble(getR().getValue())), 5);
    }

    public CustomCell getAA() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(((Double.parseDouble(getCentral_angle().getValue()) / 360.0) - (Math.sin(Double.parseDouble(getCentral_angle().getValue()) * (Math.PI / 180))) / (2 * Math.PI))), 5);
    }

    public CustomCell getA() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(((Math.PI * Math.pow((getPipe_diameter() * 0.001), 2)) / 4) * Double.parseDouble(getAA().getValue())), 5);
    }

    public CustomCell getV() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(comm_flow / Double.parseDouble(getA().getValue())), 5);
    }

    public CustomCell getCheck_min_velocity() {
        return new CustomCell(getAva_pipe_id(), Double.parseDouble(getV().getValue())>0.6?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getS_min() {
        return new CustomCell(getAva_pipe_id(),
                String.valueOf(Math.pow(((Double.parseDouble(getV().getValue())*Double.parseDouble(getManning().getValue()))/(Math.pow(Double.parseDouble(getRSmall().getValue()), 2/3.0))), 2)*100), 5);
    }

    public CustomCell getVV() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Math.pow(Double.parseDouble(getRR().getValue()), 2 / 3.0)), 5);
    }

    public CustomCell getVFull() {
        double vFull = (1/manning)*(Math.pow(Double.parseDouble(getR().getValue()), 2/3.0))*Math.pow(slope/100, 0.5);
        return new CustomCell(getAva_pipe_id(), String.valueOf(vFull), 5);
    }

    public CustomCell getV_slope() {
        double vSlope = Double.parseDouble(getVV().getValue()) * Double.parseDouble(getVFull().getValue());
        return new CustomCell(getAva_pipe_id(), String.valueOf(vSlope), 5);
    }

    public CustomCell getCheck_min_velocity_slope() {
        return new CustomCell(getAva_pipe_id(), Double.parseDouble(getV_slope().getValue())>0.6?"Feasible":"Not Feasible", 5);
    }

    public CustomCell getSMin() {
        double vm = Double.parseDouble(getV().getValue()) * Double.parseDouble(getManning().getValue());
        double rp = Math.pow(Double.parseDouble(getR().getValue()), (2 / 3.0));
        double vr = vm / rp;
        double result = Math.pow(vr, 2);
        return new CustomCell(getAva_pipe_id(), String.valueOf(result * 100), 5);
    }

    public CustomCell getAva_head() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(us_elevation - ds_elevation), 5);
    }

    public CustomCell getHead_loss() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getRoughness().getValue()) * pipe_length * Math.pow(Double.parseDouble(getV_slope().getValue()), 2) / (8 * 9.81 * Double.parseDouble(getRSmall().getValue()))), 5);
    }

    public CustomCell getUs_invert() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(us_elevation - (us_elevation - Double.parseDouble(getCover().getValue()) - (getPipe_diameter() * 0.001))), 5);
    }

    public CustomCell getDs_invert() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(ds_elevation - (ds_elevation - Double.parseDouble(getCover().getValue()) - (getPipe_diameter() * 0.001))), 5);
    }

    public CustomCell getMod_invert_depth() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getDs_invert().getValue()) - pipe_length * ((slope / 100) - (Double.parseDouble(getSMin().getValue()) / 100))), 5);
    }

    public CustomCell getTrench() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((getPipe_diameter() * 0.001) + 1), 5);
    }

    public CustomCell getVol_excavation() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(0.5 * (Double.parseDouble(getUs_invert().getValue()) + Double.parseDouble(getDs_invert().getValue())) * pipe_length * Double.parseDouble(getTrench().getValue())), 5);
    }

    public CustomCell getMod_vol_excavation() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(0.5 * (Double.parseDouble(getUs_invert().getValue()) + Double.parseDouble(getMod_invert_depth().getValue())) * pipe_length * Double.parseDouble(getTrench().getValue())), 5);
    }

    public CustomCell getCost_excavation() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getVol_excavation().getValue()) * Double.parseDouble(getRate_excavation().getValue())), 5);
    }

    public CustomCell getMod_cost_excavation() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getMod_vol_excavation().getValue()) * Double.parseDouble(getRate_excavation().getValue())), 5);
    }

    public CustomCell getVol_backfilling() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((Double.parseDouble(getVol_excavation().getValue())- ((Math.PI / 4) * Math.pow((getPipe_diameter() * 0.001), 2) * pipe_length)) * 1.5), 5);
    }

    public CustomCell getMod_vol_backfilling() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((Double.parseDouble(getMod_vol_excavation().getValue()) - ((Math.PI / 4) * Math.pow(getPipe_diameter() * 0.001, 2) * pipe_length)) * 1.5), 5);
    }

    public CustomCell getCost_pipe() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getPipe_price().getValue()) * (pipe_length / 6)), 5);
    }

    public CustomCell getManhole_size() {
        if (getPipe_diameter() < 400)
            return new CustomCell(getAva_pipe_id(), String.valueOf(1200), 5);
        else if (getPipe_diameter() >= 400 && getPipe_diameter() < 500)
            return new CustomCell(getAva_pipe_id(), String.valueOf(1350), 5);
        else if (getPipe_diameter() >= 500 && getPipe_diameter() < 700)
            return new CustomCell(getAva_pipe_id(), String.valueOf(1500), 5);
        else if (getPipe_diameter() >= 700 && getPipe_diameter() < 950)
            return new CustomCell(getAva_pipe_id(), String.valueOf(1800), 5);
        else if (getPipe_diameter() >= 950 && getPipe_diameter() < 1000)
            return new CustomCell(getAva_pipe_id(), String.valueOf(1850), 5);
        else
            return new CustomCell(getAva_pipe_id(), String.valueOf(1900), 5);


    }

    public CustomCell getVol_material_used() {
        return new CustomCell(getAva_pipe_id(), String.valueOf((Math.PI / 4) * ((Math.pow((getPipe_diameter() * 0.001) + 0.4, 2) - (Math.pow(getPipe_diameter() * 0.001, 2))) * Double.parseDouble(getUs_invert().getValue()) + (Math.pow((getPipe_diameter() * 0.001) + 0.8, 2)) * 0.25)), 5);
    }

    public CustomCell getTotal_cost_const() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getCost_excavation().getValue()) + Double.parseDouble(getCost_backfilling().getValue()) +
                Double.parseDouble(getCost_pipe().getValue()) + Double.parseDouble(getCost_manhole().getValue())), 5);
    }

    public CustomCell getMod_total_cost_const() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getMod_cost_excavation().getValue()) +
                Double.parseDouble(getMod_cost_backfilling().getValue()) + Double.parseDouble(getCost_pipe().getValue()) +
                Double.parseDouble(getCost_manhole().getValue())), 6);
    }

    public CustomCell getMod_total_cost_const2() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getMod_cost_excavation().getValue()) +
                Double.parseDouble(getMod_cost_backfilling().getValue()) + Double.parseDouble(getCost_pipe().getValue()) +
                Double.parseDouble(getCost_manhole().getValue())), 5);
    }

    public String getPipe_id() {
        return hidePipe ? "" : pipe_id;
    }

    public CustomCell getCost_backfilling() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getVol_backfilling().getValue())
                * Double.parseDouble(getRate_backfilling().getValue())), 5);
    }

    public CustomCell getMod_cost_backfilling() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getMod_vol_backfilling().getValue()) * Double.parseDouble(getRate_backfilling().getValue())), 5);
    }

    public CustomCell getCost_manhole() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(Double.parseDouble(getVol_material_used().getValue()) *
                Double.parseDouble(getRate_material().getValue())), 5);
    }

    public void setPipe_id(String pipe_id) {
        this.pipe_id = pipe_id;
    }

    public void setHidePipe(boolean hidePipe) {
        this.hidePipe = hidePipe;
    }

    public String getPipe_length() {
        return String.valueOf(hidePipe ? "" : pipe_length);
    }

    public CustomCell getPipe_price() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(pipe_price), 5);
    }

    public void setPipe_price(double pipe_price) {
        this.pipe_price = pipe_price;
    }

    public void setPipe_length(double pipe_length) {
        this.pipe_length = pipe_length;
    }

    public String getUs_elevation() {
        return String.valueOf(hidePipe ? "" : us_elevation);

    }

    public void setUs_elevation(double us_elevation) {
        this.us_elevation = us_elevation;
    }

    public void setManning(double manning) {
        this.manning = manning;
    }

    public CustomCell getManning() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(manning), 5);
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public CustomCell getRoughness() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(roughness), 5);

    }

    public void setCover(double cover) {
        this.cover = cover;
    }

    public CustomCell getCover() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(cover), 5);

    }

    public void setRate_excavation(double rate_excavation) {
        this.rate_excavation = rate_excavation;
    }

    public CustomCell getRate_excavation() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(rate_excavation), 5);

    }

    public void setRate_backfilling(double rate_backfilling) {
        this.rate_backfilling = rate_backfilling;
    }

    public CustomCell getRate_backfilling() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(rate_backfilling), 5);
    }

    public void setRate_material(double rate_material) {
        this.rate_material = rate_material;
    }

    public CustomCell getRate_material() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(rate_material), 5);
    }

    public String getDs_elevation() {
        return String.valueOf(hidePipe ? "" : ds_elevation);
    }

    public void setDs_elevation(double ds_elevation) {
        this.ds_elevation = ds_elevation;
    }

    public String getComm_flow() {
        return String.valueOf(hidePipe ? "" : comm_flow);
    }

    public void setComm_flow(double comm_flow) {
        this.comm_flow = comm_flow;
    }

    public String getSlope() {
        if (pipe_length != 0)
            this.slope = ((us_elevation - ds_elevation) / pipe_length) * 100;

        return String.valueOf(slope == 0 | hidePipe ? "" : slope);

    }

    public double getS(){
        return slope;
    }

    public void setSlope() {
        if (pipe_length == 0)
            this.slope =  0.0;
        else
            this.slope = ((us_elevation - ds_elevation) / pipe_length) * 100;
    }

    public double getPipe_diameter() {
        return pipe_diameter;
    }

    public CustomCell getPipeDiameter() {
        return new CustomCell(getAva_pipe_id(), String.valueOf(getPipe_diameter()), 3);
    }

    public void setPipe_diameter(double pipe_diameter) {
        this.pipe_diameter = pipe_diameter;
    }

    public CustomCell getCheck_slope() {
        String value = slope > Double.parseDouble(getS_min().getValue()) ? "Feasible" : "Not Feasible";
        return new CustomCell(getAva_pipe_id(), value, 5);
    }

    public void setCheck_slope(String check_slope) {
        this.check_slope = check_slope;
    }

    public CustomCell getCheck_head_loss() {
        String value = Double.parseDouble(getAva_head().getValue()) > Double.parseDouble(getHead_loss().getValue()) ? "Feasible" : "Not Feasible";
        return new CustomCell(getAva_pipe_id(), value, 5);
    }

    public void setCheck_head_loss(String check_head_loss) {
        this.check_head_loss = check_head_loss;
    }

    public boolean isFeasible(){
        return getCheck_slope().getValue().equalsIgnoreCase("feasible") &&
                getCheck_head_loss().getValue().equalsIgnoreCase("feasible");
    }
}
