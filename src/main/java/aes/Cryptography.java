package aes;

import aes.ciphers.*;
import org.apache.commons.lang3.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Cryptography {

    public <E extends Serializable> AESEncryptedObject<E> encryptObject(E object, String password, EncryptionBlockModeType blockModeType) throws Exception {
    
        AESAlgorithmData aesAlgorithmData = new AESAlgorithmData();
        AESCipherData AESCipherData = new AESCipherData(aesAlgorithmData, blockModeType);
        Cipher cipher = AESCipherData.getCipher(password, Cipher.ENCRYPT_MODE);
        
        byte[] serializedObject = SerializationUtils.serialize(object);
        byte[] encryptedObject = cipher.doFinal(serializedObject);
        return new AESEncryptedObject<>(encryptedObject, AESCipherData);
    }
    
    public <E extends Serializable> RSAEncryptedObject<E> encryptObject(E object, PublicKey publicKey) throws Exception {
        
        RSAAlgorithmData rsaAlgorithmData = new RSAAlgorithmData();
        RSACipherData rsaCipherData= new RSACipherData(rsaAlgorithmData);
        Cipher cipher = rsaCipherData.getEncryptCipher(publicKey);
        
        byte[] serializedObject = SerializationUtils.serialize(object);
        byte[] encryptedObject = cipher.doFinal(serializedObject);
        return new RSAEncryptedObject<>(encryptedObject, rsaCipherData);
    }
    
    /**
     * Encrypt stream with RSA algorithm and given public key.
     * @param inputStream
     * @param outputStream
     * @param publicKey
     * @throws Exception
     */
    
    public void encryptStream(InputStream inputStream, OutputStream outputStream, PublicKey publicKey) throws Exception {
    
        RSACipherData rsaCipherData = new RSACipherData(new RSAAlgorithmData());
        Cipher cipher = rsaCipherData.getEncryptCipher(publicKey);
        
        try (CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            inputStream.transferTo(cipherOutputStream);
        }
        
    }
    
    /**
     * Encrypt stream with AES algorithm and provided password.
     * @param dataInput
     * @param dataOutput
     * @param password
     * @param blockModeType
     * @throws Exception
     */
    
    public void encryptStream(InputStream dataInput, OutputStream dataOutput, String password,
                              EncryptionBlockModeType blockModeType) throws Exception {
        
        AlgorithmData aesAlgorithmData = new AESAlgorithmData();
        AESCipherData AESCipherData = new AESCipherData(aesAlgorithmData, blockModeType);
        Cipher cipher = AESCipherData.getCipher(password, Cipher.ENCRYPT_MODE);
        
        try (CipherOutputStream cipherOutputStream = new CipherOutputStream(dataOutput, cipher)) {
            CipherDataManager.writeCipherData(AESCipherData, dataOutput);
            dataInput.transferTo(cipherOutputStream);
        }
    }
    
    public <E extends Serializable> E decryptObject(AESEncryptedObject<E> object, String password) throws Exception {
        
        if (!object.isEncrypted())
            throw new Exception("Object is not encrypted");
        
        AESCipherData AESCipherData = object.getCipherData();
        Cipher cipher = AESCipherData.getCipher(password, Cipher.DECRYPT_MODE);
        
        byte[] decryptedObject = cipher.doFinal(object.getSerializedObject());
        return object.getOriginalObject(decryptedObject);
    }
    
    public <E extends Serializable> E decryptObject(RSAEncryptedObject<E> object, PrivateKey privateKey) throws Exception {
        
        if(!object.isEncrypted())
            throw new Exception("Object is not encrypted!");
        
        RSACipherData rsaCipherData = object.getCipherData();
        Cipher cipher = rsaCipherData.getDecryptCipher(privateKey);
        
        byte[] decryptedObject = cipher.doFinal(object.getSerializedObject());
        return object.getOriginalObject(decryptedObject);
    }
    
    /**
     * Decrypt stream with private RSA key.
     * @param inputStream
     * @param outputStream
     * @param privateKey
     * @throws Exception
     */
    
    public void decryptStream(InputStream inputStream, OutputStream outputStream, PrivateKey privateKey) throws Exception {
        RSACipherData rsaCipherData = CipherDataManager.readCipherData(inputStream);
        Cipher cipher = rsaCipherData.getDecryptCipher(privateKey);
        
        try (CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            inputStream.transferTo(cipherOutputStream);
        }
    }
    
    /**
     * Decrypt stream with password using AES algorithm.
     * @param inputStream
     * @param outputStream
     * @param password
     * @throws Exception
     */
    
    public void decryptStream(InputStream inputStream, OutputStream outputStream, String password) throws Exception {
        AESCipherData AESCipherData = CipherDataManager.readCipherData(inputStream);
        Cipher cipher = AESCipherData.getCipher(password, Cipher.DECRYPT_MODE);
        
        try (CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            inputStream.transferTo(cipherOutputStream);
        }
    }
}