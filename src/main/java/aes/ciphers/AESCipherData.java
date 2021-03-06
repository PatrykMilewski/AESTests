package aes.ciphers;

import aes.EncryptionBlockModeType;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class AESCipherData extends CipherData implements Serializable {
    
    private String algorithmName;
    private String algorithmFullName;
    private String hashAlgorithmName;
    private byte[] ivBytes;
    private byte[] keySalt;
    
    private int keySize;
    private int iterationsAmount;
    
    public AESCipherData(AlgorithmData algorithmData, EncryptionBlockModeType modeType) {
        AESAlgorithmData aesAlgorithmData = (AESAlgorithmData)algorithmData;
        
        SecureRandom random = new SecureRandom();
        byte keySalt[] = new byte[16];
        ivBytes = new byte[16];
        random.nextBytes(keySalt);
        random.nextBytes(ivBytes);
        
        algorithmName = aesAlgorithmData.getAlgorithmName();
        algorithmFullName = aesAlgorithmData.getFullAlgorithmName(modeType);
        hashAlgorithmName = aesAlgorithmData.getHashAlgorithmName();
        this.keySalt = keySalt;
        keySize = aesAlgorithmData.getKeySize();
        iterationsAmount = aesAlgorithmData.getKeyHashIterationsAmount();
    }
    
    public String getAlgorithmName() {
        return algorithmName;
    }
    
    public String getAlgorithmFullName() {
        return algorithmFullName;
    }
    
    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }
    
    public byte[] getIvBytes() {
        return ivBytes;
    }
    
    public byte[] getKeySalt() {
        return keySalt;
    }
    
    public int getKeySize() {
        return keySize;
    }
    
    public int getIterationsAmount() {
        return iterationsAmount;
    }
    
    public Cipher getCipher(String password, int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {
        
        Cipher cipher = Cipher.getInstance(algorithmFullName);
        
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(hashAlgorithmName);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), keySalt, iterationsAmount, keySize);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), algorithmName);
        
        if (algorithmName.equals("AES") && algorithmFullName.contains("ECB"))
            cipher.init(cipherMode, secret);
        else
            cipher.init(cipherMode, secret, ivParameterSpec);
    
        return cipher;
    }
}
