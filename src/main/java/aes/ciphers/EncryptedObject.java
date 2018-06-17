package aes.ciphers;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public abstract class EncryptedObject<E> implements Serializable {
    
    CipherData cipherData;
    private byte[] serializedObject;
    private boolean isEncrypted;
    
    public EncryptedObject(byte[] serializedObject, CipherData cipherData, boolean isEncrypted) {
        this.serializedObject = serializedObject;
        this.cipherData = cipherData;
        this.isEncrypted = isEncrypted;
    }
    
    public E getOriginalObject(byte[] data) {
        return (E)SerializationUtils.deserialize(data);
    }
    
    public byte[] getSerializedObject() {
        return serializedObject;
    }
    
    public boolean isEncrypted() {
        return isEncrypted;
    }
    
    public abstract CipherData getCipherData();
}
