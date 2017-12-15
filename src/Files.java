import java.io.Serializable;

public class Files implements Serializable {
    String name;
    String src;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Files(String name, String src){
        this.name = name;
        this.src = src;

    }
}
