package aes.ciphers;


import java.io.Serializable;

public class RSAEncryptedObject<E> extends EncryptedObject<E> implements Serializable {
    
    public RSAEncryptedObject(byte[] serializedObject, RSACipherData rsaCipherData) {
        super(serializedObject, rsaCipherData, true);
    }
    
    @Override
    public RSACipherData getCipherData() {
        return (RSACipherData)cipherData;
    }
}
