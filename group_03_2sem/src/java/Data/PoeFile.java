package Data;
//Author: Andreas, Adam

public class PoeFile {
    
    private byte[] data;
    private String name;    
    private String extension;

    public PoeFile(byte[] data, String name, String extension) {
        this.data = data;
        this.name = name;
        this.extension = extension;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
}
