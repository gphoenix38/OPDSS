package husseinm.opdss;

public class PipesModel {
    private int id;
    private double diameter;
    private double price;
    private boolean feasible;

    private double smin;
    private double manning;
    private double central_angle;
    private double R;
    private double relative_depth;
    private double rR;
    private double r;
    private double a;
    private double aA;
    private double v;
    private double slope;
    private double velocity;
    private double comm_flow;
    private double vFull;
    private double vV;
    private double vs;
    private double velocityS;

    private double final_velocity;
    private double adjacent_slope;
    private double us_invert;
    private double adj_inv_depth;
    private double nominal;
    private double mid;
    private double ds_invert;
    private double min_cover;
    private double up_stream;
    private double down_stream;
    private double pipe_length;

    private double trench;

    public PipesModel(int id, double diameter, double price) {
        this.id = id;
        this.diameter = diameter;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setComm_flow(double comm_flow) {
        this.comm_flow = comm_flow;
    }

    public double getComm_flow() {
        return comm_flow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public boolean checkMinVelocity() {
        return getVelocity() > 0.6;
    }

    public double getPrice() {
        return price;
    }

    public double getA() {
        return ((Math.PI * Math.pow((getDiameter() * 0.001), 2)) / 4) * getaA();
    }

    public double getVelocity() {
        return getComm_flow() / getA();
    }

    public double getaA() {
        return ((getCentral_angle() / 360) - (Math.sin(getCentral_angle() * (Math.PI / 180))) / (2 * Math.PI));
    }

    public double getSlope() {
        return slope;
    }

    public boolean checkMinSlope() {
        return getSlope() > getSmin();
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFeasible() {
        return feasible;
    }

    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    public void setManning(double manning) {
        this.manning = manning;
    }

    public double getManning() {
        return manning;
    }

    public double getSmin() {
        double vm = getV() * getManning();
        double rp = Math.pow(getR(), (2 / 3.0));
        double vr = vm / rp;
        double result = Math.pow(vr, 2);
        return result * 100;
    }

    public double getV() {
        return getComm_flow() / getA();
    }

    public double getR() {
        return (getDiameter() * 0.001) / 4;
    }

    public double r() {
        return getrR() * getR();
    }

    public double getCentral_angle() {
        return 2 * (Math.acos(1 - 2 * getRelative_depth()) * (180 / Math.PI));
    }

    public double getrR() {
        return (1 - ((360 * Math.sin(getCentral_angle() * (Math.PI / 180))) / (2 * Math.PI * getCentral_angle())));
    }

    public boolean getVelocityS() {
        return getVs() > 0.6;
    }

    public double getVs() {
        return getvV() * getvFull();
    }

    public double getvFull() {
        return (1 / getManning()) * (Math.pow(getR(), 2 / 3.0)) * Math.pow(getSlope(), 0.5);
    }

    public double getvV() {
        return Math.pow(getrR(), 2 / 3.0);
    }

    public double getRelative_depth() {
        if (diameter <= 250)
            return 0.5;
        else if (diameter > 250 && diameter <= 500)
            return 0.6;
        else
            return 0.7;
    }

    public double getDown_stream() {
        return down_stream;
    }

    public double getUp_stream() {
        return up_stream;
    }

    public double getNominal() {
        return nominal;
    }

    public void setPipe_length(double pipe_length) {
        this.pipe_length = pipe_length;
    }

    public double getPipe_length() {
        return pipe_length;
    }

    public double getAdjacent_slope() {
        return adjacent_slope;
    }

    public double getMid() {
        return ds_invert-(getPipe_length()*(getSlope()-getAdjacent_slope()));
    }

    public double getDs_invert() {
        return getDown_stream()-(getDown_stream()-getNominal()-(getDiameter()*0.001));
    }

    public double getTrench() {
        return (getDiameter()*0.001)+1;
    }
}
