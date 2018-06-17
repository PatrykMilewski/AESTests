package aes.ciphers;

import java.io.Serializable;
import java.util.function.Supplier;

public class AESAlgorithmData extends AlgorithmData implements Serializable {
    
    private final static String BLOCK_MODE = "&BLOCK_MODE&";
    
    private final static String ALGORITHM_NAME = "AES";
    private final static String ALGORITHM_NAME_WITH_BLOCK_MODE = "AES/" + BLOCK_MODE + "/PKCS5Padding";
    private final static String HASH_ALGORITHM_NAME = "PBKDF2WithHmacSHA1";
    
    private final static int KEY_SIZE = 256;
    private final static int KEY_HASH_ITERATIONS_AMOUNT = 4096;
    
    public String getAlgorithmName() {
        return ALGORITHM_NAME;
    }
    
    public String getFullAlgorithmName(Supplier<String> mode) {
        return ALGORITHM_NAME_WITH_BLOCK_MODE.replace(BLOCK_MODE, mode.get());
    }
    
    public String getHashAlgorithmName() {
        return HASH_ALGORITHM_NAME;
    }
    
    public int getKeySize() {
        return KEY_SIZE;
    }
    
    public int getKeyHashIterationsAmount() {
        return KEY_HASH_ITERATIONS_AMOUNT;
    }
    
    public int getKeySaltSize() {
        return 16;
    }
    
    public int getIvSaltSize() {
        return 16;
    }
}
