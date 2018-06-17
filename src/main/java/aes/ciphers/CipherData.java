package aes.ciphers;

import java.io.Serializable;

public abstract class CipherData implements Serializable {
    
    
    public CipherData() {
        
    }
    
    abstract String getAlgorithmName();
    
    abstract String getAlgorithmFullName();
    
    abstract int getKeySize();
    
}
