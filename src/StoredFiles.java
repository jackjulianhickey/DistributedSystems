public class StoredFiles {

    String name;
    String src;

    public StoredFiles(String name, String src){
        this.name = name;
        this.src = src;
    }
    public StoredFiles(){

    }

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

}

