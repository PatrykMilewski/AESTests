package aes.ciphers;

import java.io.Serializable;

public class AESEncryptedObject<E extends Serializable> extends EncryptedObject<E> implements Serializable {
    
    
    public AESEncryptedObject(byte[] serializedObject, AESCipherData cipherData) {
        super(serializedObject, cipherData, true);
    }
    
    @Override
    public AESCipherData getCipherData() {
        return (AESCipherData)cipherData;
    }
    
}
