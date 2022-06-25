package husseinm.opdss;

public class ProjectModel {
    private int id;
    private String name;
    private String created_at;
    private String rgb;
    private double manning;
    private double roughness;
    private double nominal;
    private double minimum;
    private double excavation;
    private double back_filling;
    private double material;
    private double design_discharge;

    private double diameter;
    private double price;

    public ProjectModel(int id, String name, String created_at, String rgb,
                        double manning, double roughness, double nominal,
                        double minimum, double excavation, double back_filling,
                        double material, double design_discharge) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.rgb = rgb;
        this.manning = manning;
        this.roughness = roughness;
        this.nominal = nominal;
        this.minimum = minimum;
        this.excavation = excavation;
        this.back_filling = back_filling;
        this.material = material;
        this.design_discharge = design_discharge;
    }

    public ProjectModel(int id, double diameter, double price) {
        this.id = id;
        this.diameter = diameter;
        this.price = price;
    }

    public ProjectModel(int id, String name, String created_at, String rgb) {
        this.id = id;
        this.name = name;
        this.rgb = rgb;
        this.created_at = created_at;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public double getManning() {
        return manning;
    }

    public void setManning(double manning) {
        this.manning = manning;
    }

    public double getRoughness() {
        return roughness;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public double getNominal() {
        return nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getExcavation() {
        return excavation;
    }

    public void setExcavation(double excavation) {
        this.excavation = excavation;
    }

    public double getBack_filling() {
        return back_filling;
    }

    public void setBack_filling(double back_filling) {
        this.back_filling = back_filling;
    }

    public double getMaterial() {
        return material;
    }

    public void setMaterial(double material) {
        this.material = material;
    }

    public double getDesign_discharge() {
        return design_discharge;
    }

    public void setDesign_discharge(double design_discharge) {
        this.design_discharge = design_discharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
