package aes;

import java.io.Serializable;
import java.util.function.Supplier;

public enum EncryptionBlockModeType implements Serializable, Supplier<String> {
    ECB("ECB"),
    CBC("CBC"),
    CFB("CFB"),
    OFB("OFB");
    
    public String fullName;
    
    EncryptionBlockModeType(String fullName) {
        this.fullName = fullName;
    }
    
    
    @Override
    public String get() {
        return fullName;
    }
}
