
import java.io.Serializable;

public class EncryptedData implements Serializable {
    
    public byte[] getData() {
        return data;
    }
    
    public byte[] getSalt() {
        return salt;
    }
    
    public byte[] getIv() {
        return iv;
    }
    
    private final byte[] data;
    private final byte[] salt;
    private final byte[] iv;
    
    public EncryptedData(byte[] data, byte[] salt, byte[] iv) {
        this.data = data;
        this.salt = salt;
        this.iv = iv;
    }
    
    public String getOriginalObject() {
        return new String(data);
    }
    
    
}
