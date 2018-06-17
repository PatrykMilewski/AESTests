package aes.ciphers;

import java.io.Serializable;

public abstract class AlgorithmData implements Serializable {
    
    abstract String getAlgorithmName();
    
    abstract int getKeySize();
}
