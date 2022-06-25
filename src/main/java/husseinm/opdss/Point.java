package husseinm.opdss;

public class Point {
    private int id;
    private int sn;
    private double x;
    private double y;
    private double elevation;
    private int project_id;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(int id, int sn, double x, double y, double elevation, int project_id) {
        this.id = id;
        this.sn = sn;
        this.x = x;
        this.project_id = project_id;
        this.y = y;
        this.elevation = elevation;
    }

    public Point(double x, double y, double elevation, int project_id) {
        this.x = x;
        this.y = y;
        this.project_id = project_id;
        this.elevation = elevation;
    }


    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean compareX(Point point) {
        return getX() > point.getX();
    }

    public boolean compareY(Point point) {
        return getY() > point.getY();
    }

    public boolean compareElevation(Point point) {
        return getElevation() > point.getElevation();
    }
}
