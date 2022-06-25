package husseinm.opdss;

public class CustomCell {
    private int id;
    private String value;
    private int border;

    public CustomCell(int id, String value, int border) {
        this.id = id;
        this.value = value;
        this.border = border;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }
}
