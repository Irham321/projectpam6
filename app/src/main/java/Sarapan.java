package air.mobile.calcount;

public class Sarapan {
    private String name;
    private String info;
    private int imageResId;

    // Empty constructor required for Firebase
    public Sarapan() {}

    // Constructor
    public Sarapan(String name, String info, int imageResId) {
        this.name = name;
        this.info = info;
        this.imageResId = imageResId;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
