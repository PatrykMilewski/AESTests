package aes.ciphers;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.security.*;

public class RSACipherData extends CipherData implements Serializable {
    
    private String algorithmName;
    private String algorithmFullName;
    
    private int keySize;
    
    public RSACipherData(AlgorithmData algorithmData) {
        RSAAlgorithmData rsaAlgorithmData = (RSAAlgorithmData)algorithmData;
        
        algorithmName = rsaAlgorithmData.getAlgorithmName();
        algorithmFullName = rsaAlgorithmData.getFullAlgorithmName();
        keySize = rsaAlgorithmData.getKeySize();
    }
    
    @Override
    String getAlgorithmName() {
        return algorithmName;
    }
    
    @Override
    String getAlgorithmFullName() {
        return algorithmFullName;
    }
    
    @Override
    int getKeySize() {
        return keySize;
    }
    
    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithmName);
        generator.initialize(keySize);
        return generator.genKeyPair();
    }
    
    public Cipher getEncryptCipher(PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmFullName);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher;
    }
    
    public Cipher getDecryptCipher(PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmFullName);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher;
    }
    
}
